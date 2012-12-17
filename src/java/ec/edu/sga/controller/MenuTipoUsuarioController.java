package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuTipoUsuarioFacade;
import ec.edu.sga.modelo.usuarios.MenuTipoUsuario;
import ec.edu.sga.modelo.usuarios.TipoUsuario;
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
    private Long menuTipoUsuarioId;
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

    public Long getMenuTipoUsuarioId() {
        if (current == null) {
            menuTipoUsuarioId = current.getId();
        }
        return menuTipoUsuarioId;
    }

    public void setMenuTipoUsuarioId(Long menuTipoUsuarioId) {
        System.out.println("========> Ingreso a fijar el id de Menu Tipo Usuario: " + menuTipoUsuarioId);
        this.beginConversation();
        if (menuTipoUsuarioId != null && menuTipoUsuarioId.longValue() > 0) {
            current = ejbFacade.find(menuTipoUsuarioId);
            this.menuTipoUsuarioId = this.current.getId();
            System.out.println("========> INGRESO a Editar un Menu Tipo Usuario: ");
        } else {
            System.out.println("========> INGRESO a Crear un Menu Tipo Usuario: ");
            this.current = new MenuTipoUsuario();
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
    public String index() {
        return "/menu_tipousuario/index";
    } // Fin public String index

    public List<MenuTipoUsuario> getListado() {
        return ejbFacade.findAll();
    } // Fin public List<Usuario> listado

    //_______________________PERSISTIR OBJETOS________________________________//
    public String persist() {
        System.out.println("========> INGRESO a Grabar nuevo MenuTipoUsuario: " + current.getId());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/menu_tipousuario/List?faces-redirect=true";


    }

    public String update() {
        System.out.println("========> INGRESO a Actualizar al MenuTipoUsuario: " + current.getId());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/menu_tipousuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar MenuTipoUsuario: " + current.getId());
        ejbFacade.remove(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/menu_tipousuario/List?faces-redirect=true";
    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/menu_tipousuario/List?faces-redirect=true";
    }
}
