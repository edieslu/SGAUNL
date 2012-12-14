/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.FichaSocioeconomicaFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "fichaSocioeconomicaController")
@ConversationScoped
public class FichaSocioeconomicaController implements Serializable {

    private FichaSocioeconomica current;
    private Ficha ficha;
    private DataModel items = null;
    @EJB
    private ec.edu.sga.facade.FichaSocioeconomicaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<FichaSocioeconomica> resultlist;
    @Inject
    Conversation conversation;

    public FichaSocioeconomicaController() {
        current = new FichaSocioeconomica();
//        ficha= new Ficha();
//        current.setFicha(ficha);
        resultlist = new ArrayList<FichaSocioeconomica>();
    }

    public String createInstance() {
        //return "/vehicle/Edit?faces-redirect=true";
        System.out.println("========> INGRESO a Crear Instance FichaSocioeconomica: ");
        this.current = new FichaSocioeconomica();
        return "/estudiante/Edit?faces-redirect=true";
        //return "/vehicle/BrandEdit";
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Ficha Socioeconomica: ");
        ejbFacade.create(current);
        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");

        return "/usuario/Create?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Estudiante: ");
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");

        return "/estudiante/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Estudiante: ");
        ejbFacade.remove(current);

        // this.find();

        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");

        return "/estudiante/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/estudiante/List?faces-redirect=true";
    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("========> INICIANDO CONVERSACION: ");
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("========> FINALIZANDO CONVERSACION: ");
        }
    }

    public FichaSocioeconomica getSelected() {
        if (current == null) {
            current = new FichaSocioeconomica();
            selectedItemIndex = -1;
        }
        return current;
    }

    public FichaSocioeconomica getCurrent() {
        return current;
    }

    public void setCurrent(FichaSocioeconomica current) {
        this.current = current;
    }

    public FichaSocioeconomicaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(FichaSocioeconomicaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<FichaSocioeconomica> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<FichaSocioeconomica> resultlist) {
        this.resultlist = resultlist;
    }

    private FichaSocioeconomicaFacade getFacade() {
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
        current = (FichaSocioeconomica) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FichaSocioeconomica();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            SessionUtil.agregarMensajeInformacion(ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            SessionUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (FichaSocioeconomica) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

//    public String update() {
//        try {
//            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated"));
//            return "View";
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }
    public String destroy() {
        current = (FichaSocioeconomica) getItems().getRowData();
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
            SessionUtil.agregarMensajeInformacion(ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"));
        } catch (Exception e) {
            SessionUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
