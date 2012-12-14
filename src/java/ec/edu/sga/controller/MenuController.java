package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuFacade;
import ec.edu.sga.modelo.usuarios.Menu;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "menuController")
@ConversationScoped
public class MenuController implements Serializable {

    @EJB
    private MenuFacade dao;
    @EJB
    private MenuFacade ejbFacade;
    private Menu selected;
    private Menu current;
    @Inject
    Conversation conversation;
    private Long menuId;

    // ---------------------- Constructor de la Clase ----------------------
    public MenuController() {
        current = new Menu();
        selected = new Menu();
    }

    public Menu getSelected() {
        if (selected == null) {
            selected = new Menu();
        }
        return selected;
    }

    // ---------------------- Métodos del Managed Bean ----------------------
    public String index() {
        return "/menu/index";
    }

    public List<Menu> getListado() {
        return ejbFacade.findAllOrderMenu();
    }

    // --------------------- Métodos de Ayuda para acceder al Bean por otras Clases ---------------------
    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(dao.findAll(), true);
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

    public Menu getCurrent() {
        return current;
    }

    public void setCurrent(Menu current) {
        System.out.println("Ingreso a fijar curso: " + current);
        this.beginConversation();
        this.current = current;
    }

    public Long getMenuId() {
        if (current != null) {
            menuId = current.getId();
            return menuId;
        }

        return null;
    }

    public void setMenuId(Long menuId) {

        System.out.println("========> Ingreso a fijar el id de un Menu: " + menuId);
        this.beginConversation();
        if (menuId != null && menuId.longValue() > 0) {

            this.menuId = this.current.getId();


            System.out.println("========> INGRESO a Editar un Menu: " + current.getNombre());
        } else {
            System.out.println("========> INGRESO a Crear un Menu: ");
            this.current = new Menu();
        }


    }

    //________________MÉTODOS PARA INICIALIZAR Y FINALIZAR LA CONVERSACIÓN_________//
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

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/index?faces-redirect=true";
    }

    //_______________________PERSISTIR OBJETOS________________________________//
    public String createInstance() {

        System.out.println("========> INGRESO a Crear una instancia de Menu: " + current.getNombre());
        this.current = new Menu();
        return "/index?faces-redirect=true";

    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Menu: " + current.getNombre());
        current.setCreated(new Date());
        current.setUpdated(new Date());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("CursoCreated");
        SessionUtil.agregarMensajeInformacion(summary);

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index?faces-redirect=true";


    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Curso: " + current.getNombre());
        current.setUpdated(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Curso: " + current.getNombre());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
        //  this.findAll();

        this.endConversation();

        String summary = "Curso Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/index?faces-redirect=true";

    }

    
}
