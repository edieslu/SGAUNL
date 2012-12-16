/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.FichaSocioeconomicaFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
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
@Named(value = "fichaSocioeconomicaController")
@ConversationScoped
public class FichaSocioeconomicaController implements Serializable {

    private FichaSocioeconomica current;
    @EJB
    private FichaSocioeconomicaFacade ejbFacade;
    private List<FichaSocioeconomica> resultlist;
    @Inject
    Conversation conversation;

    /**
     * ********************* CONSTRUCTOR **********************
     */
    public FichaSocioeconomicaController() {
        current = new FichaSocioeconomica();
        resultlist = new ArrayList<FichaSocioeconomica>();
    }
    
    //_________________________GETTERS AND SETTERS___________________________________-//

    public FichaSocioeconomica getCurrent() {
        if (current == null) {
            current = new FichaSocioeconomica();
        }
        return current;
    }

    public void setCurrent(FichaSocioeconomica current) {
        this.current = current;
    }

    public FichaSocioeconomicaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(FichaSocioeconomicaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<FichaSocioeconomica> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<FichaSocioeconomica> resultlist) {
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

        System.out.println("========> INGRESO a Crear Instance FichaSocioeconomica ");
        this.current = new FichaSocioeconomica();
        return "/usuario/Edit?faces-redirect=true";

    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Ficha Socioeconomica: ");
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/usuario/Create?faces-redirect=true";


    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Ficha Socioeconomica ");
        ejbFacade.edit(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/usuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Socioeconomica ");
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
