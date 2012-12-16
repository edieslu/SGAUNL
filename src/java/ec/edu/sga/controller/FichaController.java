/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.FichaFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "fichaController")
@ConversationScoped
public class FichaController implements Serializable {

    private Ficha current;
    @EJB
    private ec.edu.sga.facade.FichaFacade ejbFacade;
    private List<Ficha> resultlist;
    @Inject
    Conversation conversation;

    /**
     * ********************* CONSTRUCTOR **********************
     */
    public FichaController() {
        this.current = new Ficha();
        resultlist = new ArrayList<Ficha>();
    }

//_________________________GETTERS AND SETTERS___________________________________-//
    public Ficha getCurrent() {
        if (current == null) {
            current = new Ficha();
        }
        return current;
    }

    public void setCurrent(Ficha current) {
        this.current = current;
    }

    public FichaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(FichaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Ficha> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Ficha> resultlist) {
        this.resultlist = resultlist;
    }
    //________________________________Métodos_____________________________________________//
//________________________Metodos para iniciar y finalizar la conversación____________________//

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
    //___________________________Métodos para operaciones de tipo CRUD____________________________//

    public String persist() {

        System.out.println("========> INGRESO a Grabar nueva ficha: ");
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/usuario/Created?faces-redirect=true";
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
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/estudiante/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/estudiante/List?faces-redirect=true";
    }

//___________________Métodos que devuelven una lista de items de tipo Ficha___________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
