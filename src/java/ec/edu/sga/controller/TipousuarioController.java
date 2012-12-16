package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.TipoUsuarioFacade;
import ec.edu.sga.modelo.usuarios.TipoUsuario;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named
@SessionScoped
public class TipousuarioController {

    @EJB
    private TipoUsuarioFacade ejbFacade;
    private TipoUsuario current;

    // ---------------------- Constructor de la Clase ----------------------
    public TipousuarioController() {
    }
    
    //-------------------------------------------GETTERS AND SETTER------------------------------------------------

    public TipoUsuario getCurrent() {
        if (current == null) {
            current = new TipoUsuario();
        }
        return current;
    }

    public void setCurrent(TipoUsuario current) {
        this.current = current;
    }

    public TipoUsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TipoUsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    // ---------------------- Métodos del Managed Bean ----------------------
    public String index() {
        return "/tipousuario/index";
    }

    public List<TipoUsuario> listado() {
        return ejbFacade.findAll();
    }

    public String create() {
        current = new TipoUsuario();
        return "/tipousuario/new";
    } // Fin public String create

    public String agregar() {
        Date d = new Date();
        current.setFechaCreacion(d);
        current.setFechaActualizacion(d);
        ejbFacade.create(current);
        return "/tipousuario/index";
    } // Fin public String agregar

    public String edit(int codigo) {
        current = ejbFacade.find(codigo);
        return "/tipousuario/edit";
    } // Fin public TipoUsuario edit

    public String guardar() {
        Date d = new Date();
        current.setFechaActualizacion(d);
        ejbFacade.edit(current);
        return "/tipousuario/index";
    } // Fin public String guardar

    public String eliminar(int codigo) {
        current = ejbFacade.find(codigo);
        try {
            ejbFacade.remove(current);
        } catch (Exception e) {
            SessionUtil.addErrorMessage("No se puede eliminar, posibles datos asociados");
        }
        return "/tipousuario/index";
    } // Fin public String eliminar

    // --------------------- Métodos de Ayuda para acceder al Bean por otras Clases ---------------------
    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(ejbFacade.findAll(), true);
    }

    // Genera una lista con los items seleccionados (uno o muchos según selectOne). Para tablas relacionadas.
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {

        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;

    }
}
