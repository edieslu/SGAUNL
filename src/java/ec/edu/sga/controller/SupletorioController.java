package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.facade.SupletorioFacade;
import ec.edu.sga.modelo.academico.Supletorio;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("supletorioController")
@ConversationScoped
public class SupletorioController implements Serializable {

    private Supletorio current;
    private Long supletorioId;
    @Inject
    private Conversation conversation;
    @EJB
    private SupletorioFacade ejbFacade;
    @EJB
    private AnioLectivoFacade ejbFacadeAnio;

    //_________________________________Constructores_________________________________________//
    public SupletorioController() {
        System.out.println("Llamando al constructor de Supletorio");
        current = new Supletorio();
    }

    //_________________________GETTERS AND SETTERS___________________________________-//
    public Supletorio getCurrent() {
        return current;
    }

    public void setCurrent(Supletorio current) {
        System.out.println("Ingreso a fijar una Supletorio en current" + this.current);
        System.out.println("Inicio la conversación desde setCurrent de Supletorio");
        this.beginConversation();
        this.current = current;
    }

    public Long getSupletorioId() {
        if (current != null) {
            supletorioId = current.getId();
            return supletorioId;
        }
        return null;
    }

    public void setSupletorioId(Long supletorioId) {
        System.out.println("========> Ingreso a fijar el id de un Supletorio: " + supletorioId);
        this.beginConversation();
        if (supletorioId != null && supletorioId.longValue() > 0) {
            current = ejbFacade.find(supletorioId);
            System.out.println("Este es el valor de current.getId: " + current.getId());
            this.supletorioId = this.current.getId();
            System.out.println("========> Ingresó a editar un Supletorio: " + current);
        } else {
            System.out.println("========> Ingresó a crear una asignatura: ");
            this.current = new Supletorio();
        }
    } // Fin del método setSupletorioId

    //________________________________Métodos_____________________________________________//
//________________________Metodos para iniciar y finalizar la conversación____________________//
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en SupletorioController");
        }

    } //Fin del método beginConversation

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en SupletorioController");

        }
    }//fin del método endConversation

    //___________________________Métodos para operaciones de tipo CRUD____________________________//
    public String persist() {
        System.out.println("Antes de crear un supletorio: " + current.getNombreSupletorio());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        current.setAnioLectivo(ejbFacadeAnio.findAnioActive());
        ejbFacade.create(current);
        this.endConversation();
        System.out.println("Después de crear un supletorio: " + current.getNombreSupletorio());
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/supletorio/List?faces-redirect=true";
    } //Fin del método persist

    public String update() {
        System.out.println("Antes de actualizar un supletorio: " + current.getNombreSupletorio());
        current.setFechaActualizacion(new Date());
        current.setAnioLectivo(ejbFacadeAnio.findAnioActive());
        ejbFacade.edit(current);
        System.out.println("Después de actualizar un supletorio: " + current.getNombreSupletorio());
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/supletorio/List?faces-redirect=true";
    } // Fin del método Update

    public String delete() {
        System.out.println("Antes de eliminar un supletorio: " + current.getNombreSupletorio());
        ejbFacade.remove(current);
        System.out.println("Después de eliminar un supletorio");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/supletorio/List?faces-redirect=true";
    } // Fin del método delete

    public String cancelEdit() {
        this.endConversation();
        return "/supletorio/List?faces-redirect=true";
    }  // Fin del método Cancel Edit

    //___________________Métodos que devuelven una lista de items de tipo Supletorio___________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    //Método que muestra una lista de todas las asignaturas
    public List<Supletorio> getFindAllbyAnio() {
        return ejbFacade.findAllbyAnio();
    }

    //Métodos para navegar entre distintas páginas
    public String getOutcomeList() {
        return "/supletorio/List?faces-redirect=true";
    }

    public String getOutcomeEdit() {
        return "/supletorio/Edit?faces-redirect=true";
    }
    
    
}
