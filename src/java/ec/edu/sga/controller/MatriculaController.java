package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.facade.CursoFacade;
import ec.edu.sga.facade.EspecialidadFacade;
import ec.edu.sga.facade.MatriculaFacade;
import ec.edu.sga.facade.NivelFacade;
import ec.edu.sga.facade.ParaleloFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Especialidad;
import ec.edu.sga.modelo.matriculacion.Matricula;
import ec.edu.sga.modelo.matriculacion.Nivel;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import ec.edu.sga.modelo.matriculacion.TipoNivel;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("matriculaController")
@ConversationScoped
public class MatriculaController implements Serializable {

    private Matricula current;
    private Long matriculaId;
    private Long usuarioId;
    private Long nivelId;
    private Long especialidadId;
    private Long cursoId;
    private List<Matricula> matriculas;
    private Curso curso;
    private Nivel nivel;
    private Paralelo paralelo;
    private Especialidad especialidad;
    @EJB
    private ec.edu.sga.facade.MatriculaFacade ejbFacade;
    @EJB
    private ec.edu.sga.facade.AnioLectivoFacade ejbFacadeAnioLectivo;
    @EJB
    private UsuarioFacade ejbFacadeUsuario;
    @EJB
    private NivelFacade ejbFacadeNivel;
    @EJB
    private EspecialidadFacade ejbFacadeEspecialidad;
    @EJB
    private CursoFacade ejbFacadeCurso;
    @EJB
    private ParaleloFacade ejbFacadeParalelo;
    @Inject
    Conversation conversation;

    //_______________________CONSTRUCTORES______________________________//
    public MatriculaController() {
        current = new Matricula();
        matriculas = new ArrayList<Matricula>();
        curso = new Curso();
        nivel = new Nivel();
        paralelo = new Paralelo();
        especialidad = new Especialidad();

    }

    //_____________________SETTERS AND GETTERS_____________________________//
    public Matricula getCurrent() {
        return current;
    }

    public void setCurrent(Matricula current) {
        System.out.println("Ingreso a fijar matricula" + this.current.getCurso());
        this.beginConversation();
        this.current = current;
    }

    public Long getMatriculaId() {
        if (current != null) {
            this.matriculaId = current.getId();
            return this.matriculaId;
        }
        return null;
    }

    public void setMatriculaId(Long matriculaId) {
        conversation.begin();
        if (matriculaId != null && matriculaId.longValue() > 0) {
            this.current = ejbFacade.find(matriculaId);
            this.matriculaId = current.getId();
            System.out.println("Ingreso a editar matriculas: " + current.getTipoMatricula());

        } else {
            System.out.println("Ingreso a crear una nueva matrícula");
            this.current = new Matricula();
        }
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public MatriculaFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(MatriculaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public AnioLectivoFacade getEjbFacadeAnioLectivo() {
        return ejbFacadeAnioLectivo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Long getNivelId() {
        return nivelId;
    }

    public void setNivelId(Long nivelId) {
        this.nivelId = nivelId;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    //_____________________________MÉTODOS_____________________________//
    // Metodo que permite buscar un usuario para poder matricular
    public void findUserEnrollment() {
        beginConversation();
        if (usuarioId != null && usuarioId.longValue() > 0) {
            Usuario u = ejbFacadeUsuario.find(usuarioId);
            current.setUsuario(u);
        }
    }

    public String persist() {
        System.out.println("Ingreso a grabar la matrícula: " + current.getTipoMatricula());
        current.setParalelo(paralelo);
        current.setCurso(curso);
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getTipoMatricula());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé la matrícula: " + current.getTipoMatricula());
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/matricula/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar la matrícula: " + current.getTipoMatricula());
        ejbFacade.remove(current);
        System.out.println("ya eliminé la matrícula");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/matricula/List";
    }

    public String cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();
        return "/index?faces-redirect=true";

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en matrícula");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en matrícula");
        }

    }

    //___________________________MÉTODOS DE BÚSQUEDA___________________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    //Method that return the Anio that are activate == true
    public SelectItem[] getItemAnioActivo() {
        return SessionUtil.getSelectItem(ejbFacadeAnioLectivo.findAnioActivate(Boolean.TRUE));
    }

    public SelectItem[] getItemsNiveles() {
        return SessionUtil.getSelectItems(ejbFacadeNivel.findAll(), false);
    }
    private SelectItem[] itemsCursosByNivelId;
    private SelectItem[] itemsEspecialidadesByNivelId;
    private SelectItem[] itemsCursosByNivelIdOrEspecialidadId;
    private SelectItem[] itemsParalelosByCursoId;

    public SelectItem[] getItemsCursosByNivelIdOrEspecialidadId() {
        return itemsCursosByNivelIdOrEspecialidadId;
    }

    public void setItemsCursosByNivelIdOrEspecialidadId(SelectItem[] itemsCursosByNivelIdOrEspecialidadId) {
        this.itemsCursosByNivelIdOrEspecialidadId = itemsCursosByNivelIdOrEspecialidadId;
    }

    public SelectItem[] getItemsEspecialidadesByNivelId() {
        return itemsEspecialidadesByNivelId;
    }

    public void setItemsEspecialidadesByNivelId(SelectItem[] itemsEspecialidadesByNivelId) {
        this.itemsEspecialidadesByNivelId = itemsEspecialidadesByNivelId;
    }

    public SelectItem[] getItemsCursosByNivelId() {
        return itemsCursosByNivelId;
    }

    public void setItemsCursosByNivelId(SelectItem[] itemsCursosByNivelId) {
        this.itemsCursosByNivelId = itemsCursosByNivelId;
    }

    public SelectItem[] getItemsParalelosByCursoId() {
        return itemsParalelosByCursoId;
    }

    public void setItemsParalelosByCursoId(SelectItem[] itemsParalelosByCursoId) {
        this.itemsParalelosByCursoId = itemsParalelosByCursoId;
    }

    public void prueba1(ValueChangeEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        Nivel n = (Nivel) e.getNewValue();
        UIOutput labelEspecialidadInputText = (UIOutput) uiViewRoot.findComponent("formEditMatricula:idLabelEspecialidad");
        UIInput especialidadInputText = (UIInput) uiViewRoot.findComponent("formEditMatricula::idEspecialidad");

        if (n.getTipoNivel().equals(TipoNivel.BACHILLERATO)) {
            labelEspecialidadInputText.setRendered(true);
            especialidadInputText.setRendered(true);
            itemsEspecialidadesByNivelId = SessionUtil.getSelectItems(ejbFacadeEspecialidad.findEspecialidadesByNivelId(n.getId()), false);
        } else {
            labelEspecialidadInputText.setRendered(false);
            especialidadInputText.setRendered(false);
            itemsCursosByNivelIdOrEspecialidadId = SessionUtil.getSelectItems(ejbFacadeCurso.findAllCursosbyNivelId(n.getId()), false);
        }
    }

    public void pruebaRender(ValueChangeEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        Nivel n = (Nivel) e.getNewValue();
        UIOutput labelEspecialidadInputText = (UIOutput) uiViewRoot.findComponent("formUsuario:idAcordion:idTabAsignacion:idTabMaterias:idTabAsignacion1:idLabelEspecialidad");
        UIInput especialidadInputText = (UIInput) uiViewRoot.findComponent("formUsuario:idAcordion:idTabAsignacion:idTabMaterias:idTabAsignacion1:idLabelEspecialidad");

        if (n.getTipoNivel().equals(TipoNivel.BACHILLERATO)) {
            labelEspecialidadInputText.setRendered(true);
            especialidadInputText.setRendered(true);
            itemsEspecialidadesByNivelId = SessionUtil.getSelectItems(ejbFacadeEspecialidad.findEspecialidadesByNivelId(n.getId()), false);
        } else {
            labelEspecialidadInputText.setRendered(false);
            especialidadInputText.setRendered(false);
            itemsCursosByNivelIdOrEspecialidadId = SessionUtil.getSelectItems(ejbFacadeCurso.findAllCursosbyNivelId(n.getId()), false);
        }
    }

    public void prueba2(ValueChangeEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        Especialidad n = (Especialidad) e.getNewValue();
        itemsCursosByNivelIdOrEspecialidadId = SessionUtil.getSelectItems(ejbFacadeCurso.findAllCursosbyEspecialidadId(n.getId()), false);
    }

    public void prueba3(ValueChangeEvent e) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        Curso c = (Curso) e.getNewValue();
        itemsParalelosByCursoId = SessionUtil.getSelectItems(ejbFacadeParalelo.findAllParalelosByCursoId(c.getId()), false);
    }
}
