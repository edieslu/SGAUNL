package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.ParaleloFacade;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("paraleloController")
@ConversationScoped
public class ParaleloController implements Serializable {

    private List<Paralelo> resultlist;
    @EJB
    private ParaleloFacade ejbFacade;
    private Paralelo current;
    private Long paraleloId;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public ParaleloController() {
        System.out.println("Llamando al constructor de Paralelo");
        resultlist = new ArrayList<Paralelo>();
        current = new Paralelo();
    }

    //______________________________get and set________________________________
    public List<Paralelo> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Paralelo> resultlist) {
        this.resultlist = resultlist;
    }

    public ParaleloFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ParaleloFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Paralelo getCurrent() {
        return current;
    }

    public void setCurrent(Paralelo current) {
        System.out.println("Ingreso a fijar paralelo" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getParaleloId() {
        if (current != null) {
            this.paraleloId = current.getId();
            return this.paraleloId;
        }
        return null;
    }

    public void setParaleloId(Long paraleloId) {
        conversation.begin();
        if (paraleloId != null && paraleloId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(paraleloId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.paraleloId = current.getId();
             System.out.println("Ingreso a editar paralelo: " +current.getNombreParalelo());

        } else {
            System.out.println("Ingreso a crear un nuevo paralelo");
            this.current = new Paralelo();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar el paralelo: " + current.getNombreParalelo());
        ejbFacade.create(current);
        this.endConversation();
         String summary = ResourceBundle.getBundle("/Bundle").getString("CursoCreated");
        SessionUtil.agregarMensajeInformacion(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getNombreParalelo());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el paralelo: " + current.getNombreParalelo());
        this.endConversation();
        return "/paralelo/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el paralelo: " + current.getNombreParalelo());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el paralelo");
        this.endConversation();
        return "/paralelo/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en paralelos");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en Paralelos");
        }

    }

    public String findAllParalelos() {
       resultlist = ejbFacade.findAll();
        for (Paralelo object : resultlist) {
            System.out.println("paralelos: "+ object);
            
        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        return "paralelo/List";
    }
    
     // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    
    
    
}
