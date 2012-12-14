package ec.edu.sga.controller;

import ec.edu.sga.controller.util.JsfUtil;
import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Nivel;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import ec.edu.sga.modelo.matriculacion.TipoNivel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("cursoController")
@ConversationScoped
public class CursoController implements Serializable {

    private Curso current;
    private List<Curso> resultlist;
    @EJB
    private ec.edu.sga.facade.CursoFacade ejbFacade;
    private Long cursoId;
    @Inject
    Conversation conversation;

    //_____________________________________CONSTRUCTOR______________________________//
    public CursoController() {
        resultlist = new ArrayList();
        current = new Curso();
    }

    //____________________________________getters and setters______________________//
    public Curso getCurrent() {
        return current;
    }

    public void setCurrent(Curso current) {
        System.out.println("Ingreso a fijar curso: " + current);
        this.beginConversation();
        this.current = current;
    }

    public List<Curso> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Curso> resultlist) {
        this.resultlist = resultlist;
    }

    public Long getCursoId() {
        if (current != null) {
            cursoId = current.getId();
            return cursoId;
        }

        return null;
    }

    public void setCursoId(Long cursoId) {

        System.out.println("========> Ingreso a fijar el id de un Curso: " + cursoId);
        this.beginConversation();
        if (cursoId != null && cursoId.longValue() > 0) {



            //Method that return an Course whith all their paralels 
            this.current = ejbFacade.findCursoByCursoId(cursoId);
            this.cursoId = this.current.getId();


            System.out.println("========> INGRESO a Editar un Curso: " + current.getNombreCurso());
        } else {
            System.out.println("========> INGRESO a Crear un Curso: ");
            this.current = new Curso();
        }


    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    //--------------------------------------MÉTODOS--------------------------------//
    //Encuentra todos los cursos y los presenta en una tabla
    public String findAll() {
        resultlist = ejbFacade.findAll();
        return "curso/List";
    }

    public void addParalelos() {
        Paralelo p = new Paralelo();
        current.add(p);
    }

    //________________MÉTODOS PARA INICIALIZAR Y FINALIZAR LA CONVERSACIÓN_________//
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

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/curso/List?faces-redirect=true";
    }

    //_______________________PERSISTIR OBJETOS________________________________//
    public String createInstance() {

        System.out.println("========> INGRESO a Crear una instancia de curso: " + current.getNombreCurso());
        this.current = new Curso();
        return "/curso/Edit?faces-redirect=true";

    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Curso: " + current.getNombreCurso());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.curso.creado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/curso/List?faces-redirect=true";


    }

    public String update() {
        System.out.println("========> INGRESO a Actualizar al Curso: " + current.getNombreCurso());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.curso.actualizado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "/curso/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Curso: " + current.getNombreCurso());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
        //this.findAll();
        this.endConversation();
        String summary = ResourceBundle.getBundle("/Bundle").getString("mensaje.curso.eliminado");
        JsfUtil.addInformacionMessage(summary);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "/curso/List?faces-redirect=true";

    }

    // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    //Método que muestra la especialidad dependiendo del nivel elegido
    public void mostrarEspecialidad(ValueChangeEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot ui = fc.getViewRoot();
        Nivel n = (Nivel) e.getNewValue();
        UIInput inputEspecialidad = (UIInput) ui.findComponent("formCursoEdit:idEspecialidad");
        UIOutput labelEspecialidad = (UIOutput) ui.findComponent("formCursoEdit:idLabelEspecialidad");
        if (n.getTipoNivel().equals(TipoNivel.BACHILLERATO)) {
            inputEspecialidad.setRendered(true);
            labelEspecialidad.setRendered(true);

        } else {
            inputEspecialidad.setRendered(false);
            labelEspecialidad.setRendered(false);

        }

    }
}
