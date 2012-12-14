package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.EspecialidadFacade;
import ec.edu.sga.modelo.matriculacion.Especialidad;
import ec.edu.sga.modelo.matriculacion.Nivel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("especialidadController")
@ConversationScoped
public class EspecialidadController implements Serializable {

    private List<Especialidad> resultlist;
    @EJB
    private EspecialidadFacade ejbFacade;
    private Especialidad current;
    private Long especialidadId;
    private Nivel nivel;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public EspecialidadController() {
        System.out.println("Llamando al constructor de Especialidad");
        resultlist = new ArrayList<Especialidad>();
        current = new Especialidad();
        nivel = new Nivel();
        current.setNivel(nivel);
    }

    //______________________________get and set________________________________
    public List<Especialidad> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Especialidad> resultlist) {
        this.resultlist = resultlist;
    }

    public Especialidad getCurrent() {
        return current;
    }

    public void setCurrent(Especialidad current) {
        System.out.println("Ingreso a fijar especialidad" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getEspecialidadId() {
        if (current != null) {
            this.especialidadId = current.getId();
            return this.especialidadId;
        }
        return null;
    }

    public void setEspecialidadId(Long especialidadId) {
        conversation.begin();
        if (especialidadId != null && especialidadId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(especialidadId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.especialidadId = current.getId();
            System.out.println("Ingreso a editar especialidad: " + current.getNombreEspecialidad());

        } else {
            System.out.println("Ingreso a crear una nueva especialidad");
            this.current = new Especialidad();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar el paralelo: " + current.getNombreEspecialidad());
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getNombreEspecialidad());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé la especialidad: " + current.getNombreEspecialidad());
        this.endConversation();
          SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/especialidad/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar la especialidad: " + current.getNombreEspecialidad());
        ejbFacade.remove(current);
        System.out.println("ya eliminé la especialidad");
        this.endConversation();
          SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/especialidad/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en especialidad");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en Especialidad");
        }

    }

    public String findAllEspecialidad() {
        resultlist = ejbFacade.findAll();
        for (Especialidad object : resultlist) {
            System.out.println("especialidades: " + object);

        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        return "especialidad/List";
    }

    // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }
}
