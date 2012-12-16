/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.FichaPersonalFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
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
@Named(value = "fichaPersonalController")
@ConversationScoped
public class FichaPersonalController implements Serializable {

    private FichaPersonal current;
    @EJB
    private ec.edu.sga.facade.FichaPersonalFacade ejbFacade;
    private List<FichaPersonal> resultlist;
    @Inject
    Conversation conversation;

    /**
     * ********************* CONSTRUCTOR **********************
     */
    public FichaPersonalController() {
        current = new FichaPersonal();
        resultlist = new ArrayList<FichaPersonal>();

    }

//_________________________GETTERS AND SETTERS___________________________________-//
    public FichaPersonal getCurrent() {
        if (current == null) {
            current = new FichaPersonal();
        }
        return current;
    }

    public void setCurrent(FichaPersonal current) {
        this.current = current;
    }

    public FichaPersonalFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(FichaPersonalFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<FichaPersonal> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<FichaPersonal> resultlist) {
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

    public String createInstance() {

        System.out.println("========> INGRESO a Crear Instance Ficha Personal: ");
        this.current = new FichaPersonal();
        return "/usuario/Create?faces-redirect=true";

    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nueva Ficha Personal: ");
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/usuario/Created?faces-redirect=true";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Ficha Personal: ");
        ejbFacade.edit(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/usuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Ficha Personal ");
        ejbFacade.remove(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/usuario/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/usuario/List?faces-redirect=true";
    }
    
    //___________________Métodos que devuelven una lista de items de tipo Ficha Personal___________________//

    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
