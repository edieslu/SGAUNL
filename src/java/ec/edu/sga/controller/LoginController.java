/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.MenuFacade;
import ec.edu.sga.facade.MenuTipousuarioFacade;
import ec.edu.sga.facade.TipousuarioFacade;
import ec.edu.sga.facade.UsersFacade;
import ec.edu.sga.facade.UsuarioFacade;
import ec.edu.sga.modelo.usuarios.Menu;
import ec.edu.sga.modelo.usuarios.Tipousuario;
import ec.edu.sga.modelo.usuarios.Users;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    private TipousuarioFacade ejbTipoUsuario;
    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private MenuTipousuarioFacade ejbMenuTipoUsuario;
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
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    // --------------------- Getters y Setters ---------------------
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

    // --------------------- Métodos del Bean ---------------------
    public String index() {
        return "/faces/index.xhtml";
    } // Fin public String index

    public String acercaDe() {
        return "/home/acerca_de";
    } // Fin public String acercaDe

    public String login() {
        return "/faces/login/login.xhtml";
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
        // Cierra la sesion y la crea con el nuevo usuario logueada.
        //SessionUtil.closeSession();
        //SessionUtil.addSession(login.getId(), login.getNombres(), login.getTipousuarioId().getId(), login.getTipousuarioId().getNombre());

        return "/index";

    } // Fin public Ingresar

    public String logout() {
        SessionUtil.closeSession();
        return "/faces/login/login.xhtml";
    } // Fin public String logout

    public String cambio_clave() {
        return "/home/cambio_clave";
    } // Fin public String cambio_clave

    // Funcion para cambiar la clave del usuario.
    public String cambiarPWD() {

        // Si las claves nueva y repetida no coinciden, error.
        if (!(claveNew.equals(claveRep))) {
            SessionUtil.addErrorMessage("La clave nueva debe ser igual al campo repetida");
            return null;
        }

        // Recupera el usuario actual para conocer su clave
        Usuario current = ejbUsuario.find(SessionUtil.getUserLog());
        String claveUsr = current.getClave();

        // La clave del usuario debe ser la actual
        if (!(claveAct.equals(claveUsr))) {
            SessionUtil.addErrorMessage("La clave actual no coindide con la de su usuario");
            return null;
        }

        current.setClave(claveNew);
        ejbUsuario.edit(current);
        return "/index";

    } // Fin public String cambiarPWD

   
    public Boolean getLogueado() {
        Long userLog = SessionUtil.getUserLog();
        return !(userLog == null);

    }

    public String irA(String action) {
        return action;
    }

    public void accesoURL(Boolean ctrl, String pagina) {
        if (!tieneAcceso(ctrl, pagina)) {
            SessionUtil.redirectTo("/faces/home/acceso_denegado.xhtml");
        }
    } // Fin public void logout

    // Determina si la pagina para el tipo de usuario puede ser accedida.
    public boolean tieneAcceso(Boolean ctrl, String pagina) {

        if (!ctrl) {
            return true;
        } // Si el indicador dice que no hay que controlar, tiene acceso.

        // Si el usuario no ingreso, no hay acceso.
        Long userLog = SessionUtil.getUserLog();
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
        Tipousuario tipo = ejbTipoUsuario.find(SessionUtil.getIdUserTipoLog());
        if (tipo == null) {
            return false;
        }

        // Se controla acceso por menu (se busca en la tabla de accesos el tipo usuario y la página).
        return ejbMenuTipoUsuario.findByMenuAndTipousuario(menu, tipo);

    } // Fin private boolean tieneAcceso

    public String infoDelPie() {

        String nombre = SessionUtil.getUserNombreLog();
        //String tipo = SessionUtil.getUserTipoLog();
        String user = "";
        if (nombre != null ) {
            user = nombre;
            System.out.println("*********************"+user);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date d = new Date();
        String fechaStr = sdf.format(d);

        return user + " - " + fechaStr + " - Desarrollado con Java EE 6 - ";

    } // Fin public String infoDelPie
}
