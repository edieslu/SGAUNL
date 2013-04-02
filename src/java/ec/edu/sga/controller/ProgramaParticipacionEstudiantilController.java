package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.ProgramaParticipacionEstudiantilFacade;
import ec.edu.sga.modelo.academico.ProgramaParticipacionEstudiantil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("programaController")
@ConversationScoped
public class ProgramaParticipacionEstudiantilController implements Serializable {

    private ProgramaParticipacionEstudiantil current;
    private Long programaId;
    @Inject
    private Conversation conversation;
    @EJB
    private ProgramaParticipacionEstudiantilFacade ejbFacade;

   //_________________________________Constructores_________________________________________//
    public ProgramaParticipacionEstudiantilController(){
        System.out.println("Llamando al constructor de ProgramaParticipacion");
        current = new ProgramaParticipacionEstudiantil();
    }

    //_________________________GETTERS AND SETTERS___________________________________-//
    public ProgramaParticipacionEstudiantil getCurrent() {
        return current;
    }

    public void setCurrent(ProgramaParticipacionEstudiantil current) {
        System.out.println("Ingreso a fijar un Programa en current" + this.current);
        System.out.println("Inicio la conversación desde setCurrent de Programa");
        this.beginConversation();
        this.current = current;
    }

    public Long getProgramaId() {
        if (current != null) {
            programaId = current.getId();
            return programaId;
        }
        return null;
    }

        public void setProgramaId(Long programaId) {
        System.out.println("========> Ingreso a fijar el id de un Programa: " + programaId);
        this.beginConversation();
        if (programaId != null && programaId.longValue() > 0) {
            current = ejbFacade.find(programaId);
            System.out.println("Este es el valor de current.getId: " + current.getId());
            this.programaId = this.current.getId();
            System.out.println("========> Ingresó a editar un Programa: " + current.getNombrePrograma());
        } else {
            System.out.println("========> Ingresó a crear un Programa: ");
            this.current = new ProgramaParticipacionEstudiantil();
        }
    } // Fin del método setProgramaId

    //________________________________Métodos_____________________________________________//
//________________________Metodos para iniciar y finalizar la conversación____________________//
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en ProgramaController");
        }

    } //Fin del método beginConversation

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en ProgramaController");

        }
    }//fin del método endConversation

    //___________________________Métodos para operaciones de tipo CRUD____________________________//
    public String persist() {
        System.out.println("Antes de crear un Programa: " + current.getNombrePrograma());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        System.out.println("Después de crear un Programa: " + current.getNombrePrograma());
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "list?faces-redirect=true";
    } //Fin del método persist

    public String update() {
        System.out.println("Antes de actualizar un Programa: " + current.getNombrePrograma());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("Después de actualizar un Programa: " + current.getNombrePrograma());
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "list?faces-redirect=true";
    } // Fin del método Update

    public String delete() {
        System.out.println("Antes de eliminar un Programa: " + current.getNombrePrograma());
        ejbFacade.remove(current);
        System.out.println("Después de eliminar un Programa");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "list?faces-redirect=true";
    } // Fin del método delete

    public String cancelEdit() {
        this.endConversation();
        return "list?faces-redirect=true";
    }  // Fin del método Cancel Edit

    //___________________Métodos que devuelven una lista de items de tipo Programa___________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    //Método que muestra una lista de todas los Programas
    public List<ProgramaParticipacionEstudiantil> getFindAll() {
        return ejbFacade.findAll();
    }

    //Métodos para navegar entre distintas páginas
    public String getOutcomeList() {
        return "list?faces-redirect=true";
    }

    public String getOutcomeEdit() {
        return "/programa/Edit?faces-redirect=true";
    }
   
}
