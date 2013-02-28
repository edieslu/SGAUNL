package ec.edu.sga.controller;

import ec.edu.sga.facade.PeriodoAcademicoFacade;
import ec.edu.sga.modelo.academico.PeriodoAcademico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("periodoController")
@ConversationScoped
public class PeriodoAcademicoController implements Serializable {
//___________________________________ATRIBUTOS___________________________________

    @EJB
    private PeriodoAcademicoFacade ejbFacade;
    @Inject
    private Conversation conversation;
    private PeriodoAcademico current;
    private Long periodoId;
    private List<PeriodoAcademico> resultlist;
//_______________________________Constructor__________________________________________
    
    public PeriodoAcademicoController() {
        resultlist = new ArrayList<PeriodoAcademico>();
        current = new PeriodoAcademico();
    }

    //______________________________Getters and setters__________________________________

    public PeriodoAcademico getCurrent() {
        return current;
    }

    public void setCurrent(PeriodoAcademico current) {
        //this.beginConversation();
        this.current = current;
    }

    public Long getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Long periodoId) {
        this.beginConversation();
        current = ejbFacade.find(periodoId);
        this.periodoId = periodoId;
        
    }

    public List<PeriodoAcademico> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<PeriodoAcademico> resultlist) {
        this.resultlist = resultlist;
    }
    
    //__________________________Métodos______________________________________________

    
    //Métodos para iniciar y terminar una conversación
    public void beginConversation(){
        if(conversation.isTransient()){
            conversation.begin();
            System.out.println("iniciando la conversación");
        }
    }
    
    public void endConversation(){
        if (!conversation.isTransient()){
            conversation.end();
            System.out.println("terminando la conversación");
        }
    }
    
    //Métodos para realizar operaciones CRUD
    
    public String persist(){
        ejbFacade.create(current);
        this.endConversation();
        return "/periodo/List?faces-redirect=true";
    }
    
    
    public String update(){
        ejbFacade.edit(current);
        this.endConversation();
        return "/periodo/List?faces-redirect=true";
    }
    
    
    public String delete(){
        ejbFacade.remove(current);
        this.endConversation();
        return "/periodo/List?faces-redirect=true";
    }
    
    public String cancel(){
        this.endConversation();
        return "/periodo/List?faces-redirect=true";
    }
    
    //___________________---Métodos para realizar búsquedas_____________________
    
    public List<PeriodoAcademico> getFindAll(){
        return ejbFacade.findAll();
    }
    
}
