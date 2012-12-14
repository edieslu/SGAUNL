/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.PerfilFacade;
import ec.edu.sga.modelo.usuarios.Perfil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author lucho
 */
@Named(value = "perfilController")
@ConversationScoped
public class PerfilController implements Serializable {

   private List<Perfil> resultlist;
    @EJB
    private  PerfilFacade ejbFacade;
    private Perfil current;
    private Long perfilId;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public PerfilController(){
        System.out.println("Llamando al constructor de PerfilController");
        resultlist = new ArrayList<Perfil>();
        current = new Perfil();
    }

    //______________________________get and set________________________________
    public List<Perfil> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Perfil> resultlist) {
        this.resultlist = resultlist;
    }

    public Perfil getCurrent() {
        return current;
    }

    

    

    public void setCurrent(Perfil current) {
        System.out.println("Ingreso a fijar perfil" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getPerfilId() {
        if (current != null) {
            this.perfilId = current.getId();
            return this.perfilId;
        }
        return null;
    }

    public void setPerfilId(Long perfilId) {
        conversation.begin();
        if (perfilId != null && perfilId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(perfilId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.perfilId = current.getId();
             System.out.println("Ingreso a editar perfiles: " +current.getDescripcion());

        } else {
            System.out.println("Ingreso a crear un nuevo perfil");
            this.current = new Perfil();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar perfiles: " + current.getDescripcion());
        ejbFacade.create(current);
        this.endConversation();
         String summary = ResourceBundle.getBundle("/Bundle").getString("CursoCreated");
        SessionUtil.agregarMensajeInformacion(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getDescripcion());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el perfil: " + current.getDescripcion());
        this.endConversation();
        return "/perfil/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el perfil: " + current.getDescripcion());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el perfil");
        this.endConversation();
        return "/perfil/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en perfil");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en Perfil");
        }

    }

    
    
     // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }
}
