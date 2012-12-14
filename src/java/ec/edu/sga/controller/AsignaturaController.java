package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.AsignaturaFacade;
import ec.edu.sga.modelo.academico.Asignatura;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named("asignaturaController")
@ConversationScoped
public class AsignaturaController implements Serializable {

    private Asignatura current;
    private Long asignaturaId;
    private Conversation conversation;
    @EJB
    private AsignaturaFacade ejbFacade;

    //_________________________________Constructores_________________________________________//
    public AsignaturaController() {
        current = new Asignatura();
    }

    //_________________________GETTERS AND SETTERS___________________________________-//
    public Asignatura getCurrent() {
        return current;
    }

    public void setCurrent(Asignatura current) {
        System.out.println("Ingreso a fijar una Asignatura en current" + this.current);
        System.out.println("Inicio la conversación desde setCurrent");
        this.beginConversation();
        current = new Asignatura();
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    //________________________________Métodos_____________________________________________//
//________________________Metodos para iniciar y finalizar la conversación____________________//
    public void beginConversation() {

        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en AsignaturaController");
        }

    } //Fin del método beginConversation

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en AsignaturaController");

        }
    }//fin del método endConversation

    //___________________________Métodos para operaciones de tipo CRUD____________________________//
    public String persist() {
        System.out.println("Antes de crear la asignatura: " + current.getNombreAsignatura());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        System.out.println("Después de crear la asignatura: " + current.getNombreAsignatura());
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/asignatura/List?faces-redirect=true";
    } //Fin del método persist

    public String update() {
        System.out.println("Antes de actualizar la asignatura: " + current.getNombreAsignatura());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("Después de actualizar la asignatura: " + current.getNombreAsignatura());
        this.endConversation();
        SessionUtil.agregarMensajeErrorOtraPágina("mensaje.actualizacion");
        return "/asignatura/List?faces-redirect=true";
    } // Fin del método Update

    public String delete() {
        System.out.println("Antes de eliminar la asignatura: " + current.getNombreAsignatura());
        ejbFacade.remove(current);
        System.out.println("Después de eliminar la asignatura");
        this.endConversation();
        SessionUtil.agregarMensajeErrorOtraPágina("mensaje.eliminacion");
        return "/asignatura/List?faces-redirect=true";
    } // Fin del método delete

    public String cancelEdit() {
        this.endConversation();
        return "/asignatura/List?faces-redirect=true";

    }

    //___________________Métodos que devuelven una lista de items de tipo Asignatura___________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}//Fin de la clase Asignatura Controller

