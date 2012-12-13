/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.facade.UsersFacade;
import ec.edu.sga.modelo.usuarios.Users;
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
 * @author edison
 */
@Named(value = "userController")
@ConversationScoped
public class UserController implements Serializable {

    private List<Users> resultlist;
    @EJB
    private  UsersFacade ejbFacade;
    private Users current;
    private Long usersId;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public UserController(){
        System.out.println("Llamando al constructor de UserController");
        resultlist = new ArrayList<Users>();
        current = new Users();
    }

    //______________________________get and set________________________________
    public List<Users> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Users> resultlist) {
        this.resultlist = resultlist;
    }

    public Users getCurrent() {
        if(current== null){
            current= new Users();
        }
        return current;
    }

    

    public void setCurrent(Users current) {
        System.out.println("Ingreso a fijar users" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getUsersId() {
        if (current != null) {
            this.usersId = current.getId();
            return this.usersId;
        }
        return null;
    }

    public void setUsersId(Long usersId) {
        conversation.begin();
        if (usersId != null && usersId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(usersId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.usersId = current.getId();
             System.out.println("Ingreso a editar users: " +current.getUsuario());

        } else {
            System.out.println("Ingreso a crear un nuevo users");
            this.current = new Users();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar el users: " + current.getUsuario());
        ejbFacade.create(current);
        this.endConversation();
         String summary = ResourceBundle.getBundle("/Bundle").getString("CursoCreated");
        JsfUtil.addInformacionMessage(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getUsuario());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el paralelo: " + current.getUsuario());
        this.endConversation();
        return "/users/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el users: " + current.getUsuario());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el users");
        this.endConversation();
        return "/users/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en users");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en Users");
        }

    }

    
    
     // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
