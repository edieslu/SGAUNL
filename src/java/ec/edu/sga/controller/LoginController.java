/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuFacade;
import ec.edu.sga.facade.MenuTipoUsuarioFacade;
import ec.edu.sga.facade.TipoUsuarioFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Menu;
import ec.edu.sga.modelo.usuarios.TipoUsuario;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;

/**
 *
 * @author edison
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @Inject
    SessionBean sessionbean;
    @EJB
    private TipoUsuarioFacade ejbTipoUsuario;
    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private MenuTipoUsuarioFacade ejbMenuTipoUsuario;
    @EJB
    private MenuFacade ejbMenu;
    @Size(min = 1, message = "Debe ingresar un usuario")
    private String usuario;
    @Size(min = 1, message = "Debe ingresar la clave")
    private String clave;
    @Size(min = 1, message = "Debe ingresar la clave actual")
    private String claveAct;
    @Size(min = 1, message = "Debe ingresar la clave nueva")
    private String claveNew;
    @Size(min = 1, message = "Debe ingresar la clave repetida")
    private String claveRep;

    /**
     * ********************* CONSTRUCTOR **********************
     */
    public LoginController() {
    }

    //_________________________GETTERS AND SETTERS___________________________________-//
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClaveAct() {
        return claveAct;
    }

    public void setClaveAct(String claveAct) {
        this.claveAct = claveAct;
    }

    public String getClaveNew() {
        return claveNew;
    }

    public void setClaveNew(String claveNew) {
        this.claveNew = claveNew;
    }

    public String getClaveRep() {
        return claveRep;
    }

    public void setClaveRep(String claveRep) {
        this.claveRep = claveRep;
    }

    public void setSessionbean(SessionBean sessionbean) {
        this.sessionbean = sessionbean;
    }

    public SessionBean getSessionbean() {
        return sessionbean;
    }

    //________________________________Métodos_____________________________________________//
    //--------- Metodos del Login Controller-----
    public String index() {
        return "/faces/login/login.xhtml";
    } // Fin public String index

    public String acercaDe() {
        return "/home/acerca_de.xhtml";
    } // Fin public String acercaDe

    public String login() {
        return "/login/login.xhtml";
    } // Fin public String login

    // Funcion de ingreso al sistema
    public String ingresar() {
        Usuario login = ejbUsuario.getLogin(usuario, clave);
        // Si el login no es correcto, se queda en la página actual.
        if (login == null) {
            SessionUtil.addErrorMessage("Usuario o Claves incorrectos");
            return null;
        }
        sessionbean.setUsuarioLogeado(login);
        return "/index";

    } // Fin public Ingresar

    //Cerrar Sesion
    public static void closeSession() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
    }

    @SuppressWarnings("static-access")
    public String logout() {
        this.closeSession();
        return this.index();

    } // Fin public String logout

    public String cambio_clave() {
        return "/faces/usuario/CambioClave.xhtml";
    } // Fin public String cambio_clave

    // Funcion para cambiar la clave del usuario.
    public String cambiarPWD() {

        // Si las claves nueva y repetida no coinciden, error.
        if (!(claveNew.equals(claveRep))) {
            SessionUtil.addErrorMessage("La clave nueva debe ser igual al campo repetida");
            return null;
        }

        // Recupera el usuario actual para conocer su clave
        Usuario current = ejbUsuario.find(sessionbean.getUsuarioLogeado().getId());
        String claveUsr = current.getClave();

        // La clave del usuario debe ser la actual
        if (!(claveAct.equals(claveUsr))) {
            SessionUtil.addErrorMessage("La clave actual no coindide con la de su usuario");
            return null;
        }

        current.setClave(claveNew);
        ejbUsuario.edit(current);
        return "/index.xhtml";

    } // Fin public String cambiarPWD

    public Boolean getLogueado() {
        Long userLog = sessionbean.getUsuarioLogeado().getId();
        return !(userLog == null);

    }

    public String irA(String action) {
        return action;
    }

    public void accesoURL(Boolean ctrl, String pagina) {
        if (!tieneAcceso(ctrl, pagina)) {
            LoginController.redirectTo("/faces/login/AccesoDenegado.xhtml");
        }
    } // Fin public void logout

    // Para redirecionar a una pagina cuando el usuario no tenga acceso
    public static void redirectTo(String url) {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        try {
            ctx.redirect(ctxPath + url);
        } catch (IOException ex) {
        }
    }

    // Determina si la pagina para el tipo de usuario puede ser accedida.
    public boolean tieneAcceso(Boolean ctrl, String pagina) {

        if (!ctrl) {
            return true;
        } // Si el indicador dice que no hay que controlar, tiene acceso.

        // Si el usuario no ingreso, no hay acceso.
        Long userLog = sessionbean.getUsuarioLogeado().getId();
        if (userLog == null) {
            return false;
        }

        // El usuario ingreso, si la página está en blanco, hay acceso.
        // Página en blanco indica que solo se requiere está logueado).
        if (pagina.equals("")) {
            return true;
        }

        // Si la opción de menú no existe, no hay acceso.
        Menu menu = ejbMenu.findByAction(pagina);
        if (menu == null) {
            return false;
        }

        // No debería pasar, pero si el tipo no existe, no hay acceso.
        TipoUsuario tipo = ejbTipoUsuario.find(sessionbean.getUsuarioLogeado().getTipousuarioId().getId());
        if (tipo == null) {
            return false;
        }

        // Se controla acceso por menu (se busca en la tabla de accesos el tipo usuario y la página).
        return ejbMenuTipoUsuario.findByMenuAndTipousuario(menu, tipo);

    } // Fin private boolean tieneAcceso

    public String infoDelPie() {

        String nombre = sessionbean.getUsuarioLogeado().getNombres();
        //String tipo = SessionUtil.getUserTipoLog();
        String user = "";
        if (nombre != null) {
            user = nombre;
            System.out.println("*********************" + user);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date d = new Date();
        String fechaStr = sdf.format(d);

        return user + " - " + fechaStr + " - Desarrollado con Java EE 6 - ";

    } // Fin public String infoDelPie
}
