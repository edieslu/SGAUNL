package ec.edu.sga.controller;

import ec.edu.sga.modelo.matriculacion.Estudiante;
import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.EstudianteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("estudianteController")
@SessionScoped
public class EstudianteController implements Serializable {

    private Estudiante current;
    private DataModel items = null;
    @EJB
    private ec.edu.sga.facade.EstudianteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Estudiante> resultlist;

    public EstudianteController() {
        resultlist = new ArrayList<Estudiante>();
    }

    public Estudiante getSelected() {
        if (current == null) {
            current = new Estudiante();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Estudiante getCurrent() {
        return current;
    }

    public void setCurrent(Estudiante current) {
        this.current = current;
    }

    public EstudianteFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(EstudianteFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<Estudiante> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Estudiante> resultlist) {
        this.resultlist = resultlist;
    }

    
    private EstudianteFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Estudiante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Estudiante();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addInformacionMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Estudiante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addInformacionMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Estudiante) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addInformacionMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Estudiante.class)
    public static class EstudianteControllerConverter implements Converter {

        @Inject
       // private BeanPrincipal bean;
        private EstudianteController bean;

        @Override
        public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
            //for (MiObjeto o : this.bean.getMisObjetos()) {
            for (Estudiante o : this.bean.getResultlist()) {
                if (o.getId() == Integer.parseInt(valor)) {
                    return o; //así me ahorro la query
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext contexto, UIComponent componente, Object objeto) {
            if (objeto != null) {
                return ((Estudiante) objeto).getId().toString();
            } else {
                return "";
            }
        }
    }
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            EstudianteController controller = (EstudianteController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "estudianteController");
//            return controller.ejbFacade.find(getKey(value));
//        }
//
//        java.lang.Long getKey(String value) {
//            java.lang.Long key;
//            key = Long.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Long value) {
//            StringBuffer sb = new StringBuffer();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof Estudiante) {
//                Estudiante o = (Estudiante) object;
//                return getStringKey(o.getId());
//            } else {
//                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Estudiante.class.getName());
//            }
//        }

}
