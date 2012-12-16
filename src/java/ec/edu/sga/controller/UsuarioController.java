/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaMedica;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "usuarioController")
@ConversationScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private String criterio;
    private Ficha ficha;
    private FichaPersonal fichaP;
    private FichaMedica fichaM;
    private FichaSocioeconomica fichaS;
    @EJB
    private ec.edu.sga.facade.UsuarioFacade ejbFacade;
    private List<Usuario> resultlist;
    @Inject
    Conversation conversation;
    private Long usuarioId;

// ---------------------- Constructor de la Clase ----------------------
    public UsuarioController() {
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
        resultlist = new ArrayList<Usuario>();
    }
//*********** GETTER AND SETTER***********//

    public Long getUsuarioId() {
        if (this.current != null) {
            return this.current.getId();
        } else {
            return null;
        }
    }

    public void setUsuarioId(Long usuarioId) {
        System.out.println("========> INGRESO a Fijar Estudiante: " + usuarioId);
        this.beginConversation();
        if (usuarioId != null && usuarioId.longValue() > 0) {
            //this.current = ejbFacade.buscarPorId(estudianteId);
            this.current = ejbFacade.find(usuarioId);
//            List<Contacto> res= ejbFacade.buscarContactos(estudianteId);
//            this.current.setContactos(res);
            System.out.println("========> INGRESO a Editar Estudiante: " + current.getNombres());
            System.out.println("========> INGRESO a Editar Estudiante: " + current.getFicha().getFichaPersonal().getCiudadNacimiento());
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

    public List<Usuario> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Usuario> resultlist) {
        this.resultlist = resultlist;
    }

//******************Metodos del managed Bean************************//
    public String index() {
        return "/usuario/index";
    } // Fin public String index

    public List<Usuario> listado() {
        return ejbFacade.findAll();
    } // Fin public List<Usuario> listado

    public String agregar() {
        Date d = new Date();
        current.setCreated(d);
        current.setUpdated(d);
        ejbFacade.create(current);
        return "/usuario/index";
    } // Fin public String agregar

    public String edit(int codigo) {
        current = ejbFacade.find(codigo);
        return "/usuario/edit";
    } // Fin public Tipousuario edit

    public String guardar() {
        Date d = new Date();
        current.setUpdated(d);
        ejbFacade.edit(current);
        return "/usuario/index";
    } // Fin public String guardar

    public String eliminar(int codigo) {
        current = ejbFacade.find(codigo);
        ejbFacade.remove(current);
        return "/usuario/index";
    } // Fin public String eliminar

//    public void renderTabs(ValueChangeEvent e) {
//
//        FacesContext fc = FacesContext.getCurrentInstance();
//        UIViewRoot uiViewRoot = fc.getViewRoot();
//        Rol rols = (Rol) e.getNewValue();
//        System.out.println("Valor de idRol: " + rols);
//        System.out.println("Inicio de IF");
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("alert('Prueba')");
//        if (rols.getTipoRol().equals(TipoRol.ESTUDIANTE)) {
////            System.out.println("FIN de IF");
//            context.execute("alert('PruebaAcordion')");
////            UIPanel tabPersonal = (UIPanel) uiViewRoot.findComponent("formUsuario:idPanel");
////            tabPersonal.setRendered(true);
//
//            AccordionPanel accPersonal = (AccordionPanel) uiViewRoot.findComponent("formUser:idAcordion");
//            accPersonal.setRendered(true);
//            Tab acPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal");
//            acPersonal.setRendered(true);
//
//        } else {
//
//            if (rols.getTipoRol().equals(TipoRol.ADMINISTRADOR)) {
//                context.execute("alert('PruebaAcord Tab 2')");
//                AccordionPanel accPersonal = (AccordionPanel) uiViewRoot.findComponent("formUser:idAcordion");
//                accPersonal.setRendered(true);
//                Tab acPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal2");
//                acPersonal.setRendered(true);
//                Tab aPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal");
//                aPersonal.setRendered(false);
//            }
//        }
//    }
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
        System.out.println("Ingreso a buscar con criterio: " + criterio);
        resultlist = ejbFacade.buscarPorClave(criterio);

        for (Usuario usuario : resultlist) {
            System.out.println(usuario);

        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        //puedo hacer retorjava.util.concurrent.Executorsnar a la pagina q se quiera
        return "/usuario/List";

    }

    public String createInstance() {
        //return "/vehicle/Edit?faces-redirect=true";
        System.out.println("========> INGRESO a Crear Instance usuario: " + current.getNombres());
        this.current = new Usuario();
        return "/usuario/Create?faces-redirect=true";
        //return "/vehicle/BrandEdit";
    }

    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Usuario: " + current.getNombres());
        ejbFacade.create(current);
        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/index?faces-redirect=true";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Usuario: " + current.getNombres());
        ejbFacade.edit(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/usuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Usuario: " + current.getNombres());
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
