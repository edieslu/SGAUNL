package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuFacade;
import ec.edu.sga.modelo.usuarios.Menu;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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
    private MenuFacade ejbFacade;
    private Menu current;
    @Inject
    Conversation conversation;
    private Long menuId;

    // ---------------------- Constructor de la Clase ----------------------
    public MenuController() {
        current = new Menu();

    }

    //_________________________GETTERS AND SETTERS___________________________________-//
    public Menu getCurrent() {
        if (current == null) {
            current = new Menu();
        }
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

        System.out.println("========> Ingreso a fijar el id de Menu: " + menuId);
        this.beginConversation();
        if (menuId != null && menuId.longValue() > 0) {
            current = ejbFacade.find(menuId);
            this.menuId = this.current.getId();
            System.out.println("========> INGRESO a Editar un Menu: " + current.getNombre());
        } else {
            System.out.println("========> INGRESO a Crear un Menu ");
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
    // ---------------------- Métodos del Managed Bean ----------------------

    public List<Menu> getListado() {
        return ejbFacade.findAllOrderMenu();
    }

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

    //_______________________PERSISTIR OBJETOS________________________________//
    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Menu: " + current.getNombre());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/menu/List?faces-redirect=true";


    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Curso: " + current.getNombre());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");

        return "/menu/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Curso: " + current.getNombre());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
        //  this.findAll();
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/menu/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/menu/List?faces-redirect=true";
    }
}
