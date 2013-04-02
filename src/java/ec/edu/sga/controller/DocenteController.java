/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaMadre;
import ec.edu.sga.modelo.usuarios.FichaMedica;
import ec.edu.sga.modelo.usuarios.FichaPadre;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaProfesional;
import ec.edu.sga.modelo.usuarios.FichaRepresentante;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.TipoUsuario;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;

/**
 *
 * @author edison
 */
@Named(value = "docenteController")
@ConversationScoped
public class DocenteController implements Serializable {

    private static final long serialVersionUID = 4166635535168356318L;
    private Usuario current;
    private String criterio;
    private Ficha ficha;
    private FichaPersonal fichaP;
    private FichaMedica fichaM;
    private FichaSocioeconomica fichaS;
    private FichaPadre fichaPadre;
    private FichaMadre fichaMadre;
    private FichaRepresentante fichaRepresentante;
    private FichaProfesional fichaProfesional;
    @EJB
    private ec.edu.sga.facade.UsuarioFacade ejbFacade;
    private List<Usuario> resultlist;
    @Inject
    Conversation conversation;
    private Long usuarioId;

// ---------------------- Constructor de la Clase ----------------------
    public DocenteController() {
        System.out.println("Constructor de Usuario Controller");
        current = new Usuario();
        ficha = new Ficha();
        current.setFicha(ficha);
        ficha.setUsuario(current);
        fichaP = new FichaPersonal();
        fichaP.setFicha(ficha);
        ficha.setFichaPersonal(fichaP);
        fichaM = new FichaMedica();
        fichaM.setFicha(ficha);
        ficha.setFichaMedica(fichaM);
        fichaS = new FichaSocioeconomica();
        fichaS.setFicha(ficha);
        ficha.setFichaSocio(fichaS);
        fichaPadre = new FichaPadre();
        fichaPadre.setFicha(ficha);
        ficha.setFichaPadre(fichaPadre);
        fichaMadre = new FichaMadre();
        fichaMadre.setFicha(ficha);
        ficha.setFichaMadre(fichaMadre);
        fichaRepresentante = new FichaRepresentante();
        fichaRepresentante.setFicha(ficha);
        ficha.setFichaRepresentante(fichaRepresentante);
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
            fichaM = new FichaMedica();
            fichaM.setFicha(ficha);
            ficha.setFichaMedica(fichaM);
            fichaS = new FichaSocioeconomica();
            fichaS.setFicha(ficha);
            ficha.setFichaSocio(fichaS);
            fichaPadre = new FichaPadre();
            fichaPadre.setFicha(ficha);
            ficha.setFichaPadre(fichaPadre);
            fichaMadre = new FichaMadre();
            fichaMadre.setFicha(ficha);
            ficha.setFichaMadre(fichaMadre);
            fichaRepresentante = new FichaRepresentante();
            fichaRepresentante.setFicha(ficha);
            ficha.setFichaRepresentante(fichaRepresentante);
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

    public FichaMedica getFichaM() {
        if (fichaM == null) {
            fichaM = new FichaMedica();

        }
        return fichaM;
    }

    public void setFichaM(FichaMedica fichaM) {
        this.fichaM = fichaM;
    }

    public FichaSocioeconomica getFichaS() {
        if (fichaS == null) {
            fichaS = new FichaSocioeconomica();

        }
        return fichaS;
    }

    public void setFichaS(FichaSocioeconomica fichaS) {
        this.fichaS = fichaS;
    }

    public FichaPadre getFichaPadre() {
        return fichaPadre;
    }

    public void setFichaPadre(FichaPadre fichaPadre) {
        this.fichaPadre = fichaPadre;
    }

    public FichaMadre getFichaMadre() {
        return fichaMadre;
    }

    public void setFichaMadre(FichaMadre fichaMadre) {
        this.fichaMadre = fichaMadre;
    }

    public FichaRepresentante getFichaRepresentante() {
        return fichaRepresentante;
    }

    public void setFichaRepresentante(FichaRepresentante fichaRepresentante) {
        this.fichaRepresentante = fichaRepresentante;
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

    public void renderTabs(ValueChangeEvent e) {

        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        TipoUsuario tipo = (TipoUsuario) e.getNewValue();
        System.out.println("Valor de idRol: " + tipo);
        System.out.println("Inicio de IF");
        RequestContext context = RequestContext.getCurrentInstance();
        //  context.execute("alert('Prueba')");
        if (tipo.getNombre().equalsIgnoreCase("estudiante")) {

            Panel panelPersonales = (Panel) uiViewRoot.findComponent("formUsuario:idDatosPersonales");
            panelPersonales.setRendered(true);
            Panel panelDatosFamilia = (Panel) uiViewRoot.findComponent("formUsuario:idDatosFamilia");
            panelDatosFamilia.setRendered(true);
            Panel panelMedicos = (Panel) uiViewRoot.findComponent("formUsuario:idDatosMedicos");
            panelMedicos.setRendered(true);
            Panel panelEconomicos = (Panel) uiViewRoot.findComponent("formUsuario:idDatosEconomicos");
            panelEconomicos.setRendered(true);
            Panel panelProfesional = (Panel) uiViewRoot.findComponent("formUsuario:idDatosProfesional");
            panelProfesional.setRendered(false);
        } else {

            if (tipo.getNombre().equalsIgnoreCase("Secretaria") || (tipo.getNombre().equalsIgnoreCase("Docente"))) {
                //context.execute("alert('PruebaAcord Tab 2')");
                Panel panelPersonales = (Panel) uiViewRoot.findComponent("formUsuario:idDatosPersonales");
                panelPersonales.setRendered(true);
                Panel panelDatosFamilia = (Panel) uiViewRoot.findComponent("formUsuario:idDatosFamilia");
                panelDatosFamilia.setRendered(false);
                Panel panelMedicos = (Panel) uiViewRoot.findComponent("formUsuario:idDatosMedicos");
                panelMedicos.setRendered(false);
                Panel panelEconomicos = (Panel) uiViewRoot.findComponent("formUsuario:idDatosEconomicos");
                panelEconomicos.setRendered(false);
                Panel panelProfesional = (Panel) uiViewRoot.findComponent("formUsuario:idDatosProfesional");
                panelProfesional.setRendered(true);
            }
        }
    }
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
            resultlist = ejbFacade.buscarPorClave(criterio);
            if (resultlist.isEmpty()) {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda.noEncontrada");
            } else {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda");
            }

        }
        return "list?faces-redirect=true";
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Usuario: " + current.getNombres());
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
        return resultlist = ejbFacade.findbyRol("docente");
    }

    public List<Usuario> getEstudiantes() {
        return resultlist = ejbFacade.findbyRol("estudiante");
    }

    public List<Usuario> getAdmin() {
        return resultlist = ejbFacade.findbyRol("admin");
    }
}
