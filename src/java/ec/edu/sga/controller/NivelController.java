package ec.edu.sga.controller;

import ec.edu.sga.controller.util.SessionUtil;
import ec.edu.sga.facade.NivelFacade;
import ec.edu.sga.modelo.matriculacion.Nivel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("nivelController")
@ConversationScoped
public class NivelController implements Serializable {

    private List<Nivel> resultlist;
    @EJB
    private NivelFacade ejbFacade;
    private Nivel current;
    private Long nivelId;
    @Inject
    Conversation conversation;

    //_________________________CONSTRUCTORES______________________________//
    public NivelController() {
        System.out.println("Llamando al constructor de Nivel");
        resultlist = new ArrayList<Nivel>();
        current = new Nivel();
    }

    //______________________________get and set________________________________

    public List<Nivel> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<Nivel> resultlist) {
        this.resultlist = resultlist;
    }
    
    
    

    public Nivel getCurrent() {
        return current;
    }

    public void setCurrent(Nivel current) {
        System.out.println("Ingreso a fijar nivel" + this.current);
        this.beginConversation();
        this.current = current;
    }

    public Long getNivelId() {
        if (current != null) {
            this.nivelId = current.getId();
            return this.nivelId;
        }
        return null;
    }

    public void setNivelId(Long nivelId) {
        conversation.begin();
        if (nivelId != null && nivelId.longValue() > 0) { //Verifica que el id no sea vacío
            this.current = ejbFacade.find(nivelId);//BUsca un paralelo de acuerdo al ID y lo asigna a current
            this.nivelId = current.getId();
             System.out.println("Ingreso a editar niveles: " +current.getNombreNivel());

        } else {
            System.out.println("Ingreso a crear un nuevo nivel");
            this.current = new Nivel();
        }


    }

    //____________________________MÉTODOS_______________________________
    public String persist() {
        System.out.println("Ingreso a grabar el nivel: " + current.getNombreNivel());
        ejbFacade.create(current);
        this.endConversation();
         String summary = ResourceBundle.getBundle("/Bundle").getString("NivelCreated");
        SessionUtil.agregarMensajeInformacion(summary);
       
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/index";
    }

    public String update() {
        System.out.println("Ingreso a actualizar: " + current.getNombreNivel());
        ejbFacade.edit(current);
        System.out.println("Ya actualicé el nivel: " + current.getNombreNivel());
        this.endConversation();
        return "/nivel/List";
    }

    public String delete() {
        System.out.println("Ingreso a eliminar el nivel: " + current.getNombreNivel());
        ejbFacade.remove(current);
        System.out.println("ya eliminé el nivel");
        this.endConversation();
        return "/nivel/List";
    }

    public void cancelEdit() {
        System.out.println("Terminando la conversación, cancelando el evento");
        this.endConversation();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("Iniciando la conversación en niveles");
        }

    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("Finalizando la conversación en niveles");
        }

    }

//    public String findAllNiveles() {
//       resultlist = ejbFacade.findAll();
//        for (Nivel object : resultlist) {
//            System.out.println("niveles: "+ object);
//            
//        }
//        String summary = "Encontrado Correctamente!";
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
//        return "nivel/List";
//    }
    
     // ______________________MÉTODOS PARA DEVOLVER UNA LISTA DE CURSOS_______________________//
    public SelectItem[] getItemsAvailableSelectMany() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return SessionUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    
    
    
    
}
