/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.RoleFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaProfesional;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import prueba.ciclodevida.DepuracionListener;

/**
 *
 * @author edison
 */
@Named(value = "docenteController")
@ConversationScoped
public class DocenteController implements Serializable {
    
    private static final Log log = LogFactory.getLog(DocenteController.class);

    private static final long serialVersionUID = 4166635535168356318L;
    private Usuario current;
    private String criterio;
    private Ficha ficha;
    private FichaPersonal fichaP;
    private FichaProfesional fichaProfesional;
    @EJB
    private ec.edu.sga.facade.UsuarioFacade ejbFacade;
    private List<Usuario> resultlist;
    @Inject
    Conversation conversation;
    private Long usuarioId;
    @EJB
    private RoleFacade ejbFacadeRole;

// ---------------------- Constructor de la Clase ----------------------
    public DocenteController() {
        log.info("Constructor de Usuario Controller");
        current = new Usuario();
        ficha = new Ficha();
        current.setFicha(ficha);
        ficha.setUsuario(current);
        fichaP = new FichaPersonal();
        fichaP.setFicha(ficha);
        ficha.setFichaPersonal(fichaP);
        fichaProfesional = new FichaProfesional();
        fichaProfesional.setFicha(ficha);
        ficha.setFichaProfesional(fichaProfesional);
        resultlist = new ArrayList<Usuario>();
    }
//*********** GETTER AND SETTER***********//
    
    

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        System.out.println("========> INGRESO a Fijar Estudiante: " + usuarioId);
        this.beginConversation();
        if (usuarioId != null && usuarioId.longValue() > 0) {
            //this.current = ejbFacade.buscarPorId(estudianteId);
            this.current = ejbFacade.find(usuarioId);
//            List<Contacto> res= ejbFacade.buscarContactos(estudianteId);
//            this.current.setContactos(res);
            System.out.println("========> INGRESO a Editar Usuario: " + current.getNombres());
            System.out.println("========> INGRESO a Editar Usuario: " + current.getTipoUsuario().getNombre());
        } else {
            System.out.println("========> INGRESO a Crear Estudiante: ");
            this.current = new Usuario();
            ficha = new Ficha();
            current.setFicha(ficha);
            ficha.setUsuario(current);
            fichaP = new FichaPersonal();
            fichaP.setFicha(ficha);
            ficha.setFichaPersonal(fichaP);
            fichaProfesional = new FichaProfesional();
            fichaProfesional.setFicha(ficha);
            ficha.setFichaProfesional(fichaProfesional);
        }
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        System.out.println("========> INGRESO a fijar Usuario: " + current);
        this.beginConversation();
        this.current = current;
    }

    public UsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public Ficha getFicha() {
        if (ficha == null) {
            ficha = new Ficha();

        }
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public FichaPersonal getFichaP() {
        if (fichaP == null) {
            fichaP = new FichaPersonal();

        }
        return fichaP;
    }

    public void setFichaP(FichaPersonal fichaP) {
        this.fichaP = fichaP;
    }

    public FichaProfesional getFichaProfesional() {
        return fichaProfesional;
    }

    public void setFichaProfesional(FichaProfesional fichaProfesional) {
        this.fichaProfesional = fichaProfesional;
    }

    public List<Usuario> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Usuario> resultlist) {
        this.resultlist = resultlist;
    }

//******************Metodos del managed Bean************************//
    public List<Usuario> getListado() {
        return ejbFacade.findAll();
    } // Fin public List<Usuario> listado

    // METODOS DE INICIO Y FINALIZACION DE LA CONVERSACION*******//
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

//****** METODOS PARA EL MANEJO DE APLICACIONES CRUD******************//    
    public String find() {

        if (criterio == null || criterio.equals("")) {
            SessionUtil.agregarMensajeError("mensaje.busqueda.camposVacios");

        } else {
            System.out.println("Ingreso a buscar con criterio: " + criterio);
            log.info("Ingreso a buscar con criterio: " + criterio);
            resultlist = ejbFacade.findDocentes(criterio);
            log.info("Encontre Docente***********:" + resultlist.size());
            for (Usuario usuario : resultlist) {
                log.info("Docente encontrado " + usuario.getNombres());
                
            }
            if (resultlist.isEmpty()) {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda.noEncontrada");
            } else {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda");
            }

        }
        return "list?faces-redirect=true";
    }

    public String persist() {
        System.out.println("========> INGRESO a Grabar nuevo Docente: " + current.getNombres());
        current.setRole(ejbFacadeRole.find(Long.parseLong("3")));
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        //FacesContext.getCurrentInstance().getMessageList().clear();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "list?faces-redirect=true";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Usuario: " + current.getNombres());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "list?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Usuario: " + current.getNombres());
        ejbFacade.remove(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "list?faces-redirect=true";
    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "list?faces-redirect=true";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public List<Usuario> getDocentes() {
        return resultlist;
    }

    public List<Usuario> getEstudiantes() {
        return resultlist;
    }

    public List<Usuario> getAdmin() {
        return resultlist;
    }
}
