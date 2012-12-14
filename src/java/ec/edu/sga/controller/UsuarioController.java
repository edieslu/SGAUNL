/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.controller.util.PaginationHelper;
import ec.edu.sga.facade.RolFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.FichaMedica;
import ec.edu.sga.modelo.usuarios.FichaPersonal;
import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import ec.edu.sga.modelo.usuarios.Rol;
import ec.edu.sga.modelo.usuarios.TipoRol;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;

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
    private Rol rol;
    private DataModel items = null;
    @EJB
    private ec.edu.sga.facade.UsuarioFacade ejbFacade;
    @EJB
    private ec.edu.sga.facade.RolFacade ejbFacadeRol;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Usuario> resultlist;
    @Inject
    Conversation conversation;
    private Long usuarioId;

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

    public String index() {
        return "/usuario/index";
    } // Fin public String index

    public List<Usuario> listado() {
        return ejbFacade.findAll();
    } // Fin public List<Usuario> listado

    public String create() {
        current = new Usuario();
        return "/usuario/new";
    } // Fin public String create

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

    public void renderTabs(ValueChangeEvent e) {

        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = fc.getViewRoot();
        Rol rols = (Rol) e.getNewValue();
        System.out.println("Valor de idRol: " + rols);
        System.out.println("Inicio de IF");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("alert('Prueba')");
        if (rols.getTipoRol().equals(TipoRol.ESTUDIANTE)) {
//            System.out.println("FIN de IF");
            context.execute("alert('PruebaAcordion')");
//            UIPanel tabPersonal = (UIPanel) uiViewRoot.findComponent("formUsuario:idPanel");
//            tabPersonal.setRendered(true);

            AccordionPanel accPersonal = (AccordionPanel) uiViewRoot.findComponent("formUser:idAcordion");
            accPersonal.setRendered(true);
            Tab acPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal");
            acPersonal.setRendered(true);

        } else {

            if (rols.getTipoRol().equals(TipoRol.ADMINISTRADOR)) {
                context.execute("alert('PruebaAcord Tab 2')");
                AccordionPanel accPersonal = (AccordionPanel) uiViewRoot.findComponent("formUser:idAcordion");
                accPersonal.setRendered(true);
                Tab acPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal2");
                acPersonal.setRendered(true);
                Tab aPersonal = (Tab) uiViewRoot.findComponent("formUser:idAcordion:idTabPersonal");
                aPersonal.setRendered(false);
            }
        }
    }

    public void addRol() {
        Rol r = new Rol();
        current.add(r);
    }

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
        System.out.println("========> INGRESO a Crear Instance estudiante: " + current.getNombres());
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
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Estudiante: " + current.getNombres());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

      SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");

        return "/usuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Estudiante: " + current.getNombres());
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

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        System.out.println("========> INGRESO a fijar Estudiante: " + current);
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
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public FichaPersonal getFichaP() {
        return fichaP;
    }

    public void setFichaP(FichaPersonal fichaP) {
        this.fichaP = fichaP;
    }

    public FichaMedica getFichaM() {
        return fichaM;
    }

    public void setFichaM(FichaMedica fichaM) {
        this.fichaM = fichaM;
    }

    public FichaSocioeconomica getFichaS() {
        return fichaS;
    }

    public void setFichaS(FichaSocioeconomica fichaS) {
        this.fichaS = fichaS;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public List<Usuario> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Usuario> resultlist) {
        this.resultlist = resultlist;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public RolFacade getEjbFacadeRol() {
        return ejbFacadeRol;
    }

    public void setEjbFacadeRol(RolFacade ejbFacadeRol) {
        this.ejbFacadeRol = ejbFacadeRol;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public SelectItem[] getItemsRol() {
        return SessionUtil.getSelectItems(ejbFacadeRol.findAll(), false);
    }
}
