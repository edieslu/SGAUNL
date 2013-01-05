package ec.edu.sga.controller;

import ec.edu.sga.modelo.academico.TrabajoGrado;
import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.TrabajoGradoFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

@Named("trabajoGradoController")
@SessionScoped
public class TrabajoGradoController implements Serializable {

    private TrabajoGrado current;
    @EJB
    private TrabajoGradoFacade ejbFacade;
    private Long trabajoGradoId;
    private List<TrabajoGrado> lista;
    private String criterio;
    @Inject
    Conversation conversation;

    // Constructor de la clase
    public TrabajoGradoController() {
        current = new TrabajoGrado();
        lista = new ArrayList<TrabajoGrado>();
    }

    //******************* Setter and Gentter *************//
    public TrabajoGrado getCurrent() {
        if (current == null) {
            current = new TrabajoGrado();
        }
        return current;
    }

    public void setCurrent(TrabajoGrado current) {
        this.current = current;
    }

    public TrabajoGradoFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TrabajoGradoFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Long getTrabajoGradoId() {
        return trabajoGradoId;
    }

    public void setTrabajoGradoId(Long trabajoGradoId) {
        this.conversation.begin();
        if (trabajoGradoId != null && trabajoGradoId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(trabajoGradoId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.trabajoGradoId = current.getId();
            System.out.println("Ingreso a editar Trabajo Grado: " + current.getNombreTrabajoGrado());

        } else {
            System.out.println("Ingreso a crear un nuevo Trabajo de Grado");
            this.current = new TrabajoGrado();
        }

    }

    public List<TrabajoGrado> getLista() {
        return lista;
    }

    public void setLista(List<TrabajoGrado> lista) {
        this.lista = lista;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    // *******Metodo para Inicio y Fin de Conversacion**********+ //
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en Trabajo de Grado");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en Trabajo de Grado");
        }

    }

    // *************+ Metodos del Managed Bean******************//
    public String find() {

        if (criterio == null || criterio.equals("")) {
            SessionUtil.agregarMensajeError("mensaje.busqueda.camposVacios");

        } else {
            System.out.println("Ingreso a buscar con criterio: " + criterio);
            lista = ejbFacade.buscarPorNombre(criterio);
            if (lista.isEmpty()) {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda.noEncontrada");
            } else {
                SessionUtil.agregarMensajeInformacion("mensaje.busqueda");
            }

        }
        return "/usuario/List";
    }

    public String persist() {
        System.out.println("Ingreso a grabar el Trabajo Grado: " + current.getNombreTrabajoGrado());
        ejbFacade.create(current);
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.creacion");
        return "/index?faces-redirect=true";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getNombreTrabajoGrado());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el Trabajo Grado: " + current.getNombreTrabajoGrado());
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.actualizacion");
        return "/trabajoGrado/List?faces-redirect=true";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el Trabajo de Grado: " + current.getNombreTrabajoGrado());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el Trabajo Grado");
        this.endConversation();
        SessionUtil.agregarMensajeInformacionOtraPagina("mensaje.eliminacion");
        return "/trabajoGrado/List?faces-redirect=true";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    //**************** Metodos para obtener un select item de Trabajo Grado
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
}
