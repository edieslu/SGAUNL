/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.RolFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaMedica;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.Rol;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "rolController")
@ConversationScoped
public class RolController implements Serializable {

    /**
     * Creates a new instance of RolController
     */
    private Rol current;
    private String criterio;
    private DataModel items = null;
    @EJB
    private ec.edu.sga.facade.RolFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Rol> resultlist;
    @Inject
    Conversation conversation;
    

    public RolController() {
        System.out.println("Constructor de Rol Controller");
        current = new Rol();
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Rol: " + current.getNombre());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated");
        SessionUtil.agregarMensajeInformacion(summary);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Rol: " + current.getNombre());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/usuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Rol: " + current.getNombre());
        ejbFacade.remove(current);

        // this.find();

        this.endConversation();

        String summary = "Rol Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/usuario/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/usuario/List?faces-redirect=true";
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

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public Rol getCurrent() {
        return current;
    }

    public void setCurrent(Rol current) {
        this.current = current;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public DataModel getItems() {
        return items;
    }

    public void setItems(DataModel items) {
        this.items = items;
    }

    public RolFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(RolFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public PaginationHelper getPagination() {
        return pagination;
    }

    public void setPagination(PaginationHelper pagination) {
        this.pagination = pagination;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<Rol> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Rol> resultlist) {
        this.resultlist = resultlist;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    public String cargar(){
        resultlist= ejbFacade.findAll();
        return "usuario/PruebaTable";
        
    } 
}
