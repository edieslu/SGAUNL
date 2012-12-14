package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuTipousuarioFacade;
import ec.edu.sga.modelo.usuarios.MenuTipousuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @authoredison
 */
@Named(value="menuTipousuarioController")
@ConversationScoped
public class MenuTipousuarioController implements  Serializable{

    @EJB
    private MenuTipousuarioFacade dao;
     @EJB
    private MenuTipousuarioFacade ejbFacade;
    private MenuTipousuario selected;
    private MenuTipousuario current;
    @Inject
    private  Conversation conversation;
    
    

    // ---------------------- Constructor de la Clase ----------------------

    public MenuTipousuarioController() {
        selected = new MenuTipousuario();
         current = new MenuTipousuario();
    }

    
     //-------------------------------------------GETTERS AND SETTER------------------------------------------------
    
public MenuTipousuario getCurrent() {
        return current;
    }

    public void setCurrent(MenuTipousuario current) {
        System.out.println("Ingreso a fijar MenuTipoController: " + current);
        this.beginConversation();
        this.current = current;
    }
    
    
    
    
    public MenuTipousuario getSelected() {
        if (selected == null) { selected = new MenuTipousuario(); }
        return selected;
    } // Fin public Usuario getSelected

    // ---------------------- Métodos del Managed Bean ----------------------

    public String index() {
        return "/menu_tipousuario/index";
    } // Fin public String index

    public List<MenuTipousuario> listado() {        
        return dao.findAll();
    } // Fin public List<Usuario> listado

   
  
    
    

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

        System.out.println("========> INGRESO a Crear una instancia de MenuTipo Usuario: " + current.getId());
        this.current = new MenuTipousuario();
        return "/index?faces-redirect=true";
       
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo MenuTipoUsuario: " + current.getId());
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

        System.out.println("========> INGRESO a Actualizar al MenuTipoUsurio: " + current.getId());
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
        System.out.println("========> INGRESO a Eliminar MenuTipoUsuario: " + current.getId());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
      //  this.findAll();

        this.endConversation();

        String summary = "Curso Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/index?faces-redirect=true";

    }
    

}
