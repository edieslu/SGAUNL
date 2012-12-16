package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuTipoUsuarioFacade;
import ec.edu.sga.modelo.usuarios.MenuTipoUsuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "menuTipoUsuarioController")
@ConversationScoped
public class MenuTipoUsuarioController implements Serializable {

    @EJB
    private MenuTipoUsuarioFacade ejbFacade;
    private MenuTipoUsuario current;
    @Inject
    private Conversation conversation;

    // ---------------------- Constructor de la Clase ----------------------
    public MenuTipoUsuarioController() {
        current = new MenuTipoUsuario();
    }

    //-------------------------------------------GETTERS AND SETTER------------------------------------------------
    public MenuTipoUsuario getCurrent() {
        if (current == null) {
            current = new MenuTipoUsuario();
        }
        return current;
    }

    public void setCurrent(MenuTipoUsuario current) {
        System.out.println("Ingreso a fijar MenuTipoUsuarioController: " + current);
        this.beginConversation();
        this.current = current;
    }

    public MenuTipoUsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(MenuTipoUsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
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
    public String index() {
        return "/menu_tipousuario/index";
    } // Fin public String index

    public List<MenuTipoUsuario> listado() {
        return ejbFacade.findAll();
    } // Fin public List<Usuario> listado

    //_______________________PERSISTIR OBJETOS________________________________//
    public String createInstance() {
        System.out.println("========> INGRESO a Crear una instancia de MenuTipo Usuario: " + current.getId());
        this.current = new MenuTipoUsuario();
        return "/index?faces-redirect=true";

    }

    public String persist() {
        System.out.println("========> INGRESO a Grabar nuevo MenuTipoUsuario: " + current.getId());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/index?faces-redirect=true";


    }

    public String update() {
        System.out.println("========> INGRESO a Actualizar al MenuTipoUsurio: " + current.getId());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/index?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar MenuTipoUsuario: " + current.getId());
        ejbFacade.remove(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/index?faces-redirect=true";
    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/index?faces-redirect=true";
    }
}
