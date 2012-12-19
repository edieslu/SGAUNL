package ec.edu.sga.controller.util;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    
    
    public static void addSession(Long userId, String userNombre, Long tipo, String userTipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);

        sesion.setAttribute("userLog", userId);
        sesion.setAttribute("userNombre", userNombre);
        sesion.setAttribute("userTipoId", tipo);
        sesion.setAttribute("userTipo", userTipo);
    }
    
    // Se cierra la sesion.
    public static void closeSession() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
    }

    // Recupera el código del usuario logueado.
    public static Long getUserLog() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        Long userLog = (Long)sesion.getAttribute("userLog");
        return userLog;
    }

    // Recupera el nombre del usuario logueado.
    public static String getUserNombreLog() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("userNombre");
        return nombre;
    }

    // Recupera el tipo de usuario del usuario logueado (el ID).
    public static Integer getIdUserTipoLog() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        Integer tipo = (Integer)sesion.getAttribute("userTipoId");
        return tipo;
    }

    // Recupera el tipo de usuario del usuario logueado (el nombre).
    public static String getUserTipoLog() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String tipo = (String)sesion.getAttribute("userTipo");
        return tipo;
    }

    // Nombre de la página actual que se está visitando
    public static String getPagina() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        return ctx.getRequestPathInfo();
    }

    // Método para redirigir a página del Sitio.
    public static void redirectTo(String url) {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        try { ctx.redirect(ctxPath + url); }
        catch (IOException ex) {  }
    }
    
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static SelectItem[] getSelectItem(Object entities) {

        SelectItem[] items = new SelectItem[1];
        items[0] = new SelectItem(entities, entities.toString());
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            agregarMensajeError(msg);
        } else {
            agregarMensajeError(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            agregarMensajeError(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void agregarMensajeError(String mensajeBundle) {
        String summary = ResourceBundle.getBundle("/Bundle").getString(mensajeBundle);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void  agregarMensajeErrorOtraPagina(String mensajeBundle){
        String summary = ResourceBundle.getBundle("/Bundle").getString(mensajeBundle);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
    
    

    public static void agregarMensajeInformacion(String mensajeBundle) {
        String summary = ResourceBundle.getBundle("/Bundle").getString(mensajeBundle);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
         
    }
    
    public static void agregarMensajeInformacionOtraPagina(String mensajeBundle) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().clear();
        String summary = ResourceBundle.getBundle("/Bundle").getString(mensajeBundle);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
         FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
         
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = SessionUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }
}