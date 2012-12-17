package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.TipoUsuarioFacade;
import ec.edu.sga.modelo.usuarios.TipoUsuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "tipoUsuarioController")
@ConversationScoped
public class TipoUsuarioController implements Serializable {

    private TipoUsuario current;
    @EJB
    private TipoUsuarioFacade ejbFacade;
    private Long tipoUsuarioId;
    @Inject
    Conversation conversation;

    // ---------------------- Constructor de la Clase ----------------------
    public TipoUsuarioController() {
        System.out.println("Ingreso a Tipo Usuario controller");
        current = new TipoUsuario();
    }

    //-------------------------------------------GETTERS AND SETTER------------------------------------------------
    public TipoUsuario getCurrent() {
        if (current == null) {
            current = new TipoUsuario();
        }
        return current;
    }

    public void setCurrent(TipoUsuario current) {
        System.out.println("========> INGRESO a fijar Tipo Usuario: " + current);
        this.beginConversation();
        this.current = current;
    }

    public TipoUsuarioFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TipoUsuarioFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Long getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Long tipoUsuarioId) {
        System.out.println("========> Ingreso a fijar el id de Tipo Usuario: " + tipoUsuarioId);
        this.beginConversation();
        if (tipoUsuarioId != null && tipoUsuarioId.longValue() > 0) {
            current = ejbFacade.find(tipoUsuarioId);
            this.tipoUsuarioId = this.current.getId();
            System.out.println("========> INGRESO a Editar un Tipo Usuario: " + current.getNombre());
        } else {
            System.out.println("========> INGRESO a Crear un Tipo Usuario: ");
            this.current = new TipoUsuario();
        }

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
    // ---------------------- Métodos del Managed Bean ----------------------

       public List<TipoUsuario> getListado() {
        return ejbFacade.findAll();
    }

   
    //************** METODOS PARA PERSISTIR OBJETOS******************//
    
    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Tipo de Usuario: " + current.getNombre());
        current.setFechaCreacion(new Date());
        current.setFechaActualizacion(new Date());
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "tipousuario/List?faces-redirect=true";


    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Tipo Usuario: " + current.getNombre());
        current.setFechaActualizacion(new Date());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "tipousuario/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Tipo Usuario: " + current.getNombre());
        ejbFacade.remove(current);

        //cambia este método por uno implementado con búsqueda por criteria
        //  this.findAll();

        this.endConversation();

        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");

        return "tipousuario/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
       return "tipousuario/List?faces-redirect=true";
    }
    // --------------------- Métodos de Ayuda para acceder al Bean por otras Clases ---------------------

    public SelectItem[] getItemsAvailableSelectOne() {
        return getSelectItems(ejbFacade.findAll(), true);
    }

    // Genera una lista con los items seleccionados (uno o muchos según selectOne). Para tablas relacionadas.
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
}
