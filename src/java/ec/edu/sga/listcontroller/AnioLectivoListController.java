/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.listcontroller;

import ec.edu.sga.facade.AnioLectivoFacade;
import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author lucho
 */

@Named
@RequestScoped
public class AnioLectivoListController extends LazyDataModel<AnioLectivo> {

    private static final long serialVersionUID = 3822900416268191502L;
    private static final Log log = LogFactory.getLog(AnioLectivoListController.class);
    
    @EJB
    private AnioLectivoFacade ejbFacade;
    private List<AnioLectivo> resultlist;
    private AnioLectivo[] anios;
    private AnioLectivo anioLectivoSelected;

    public AnioLectivoListController() {
        resultlist = new ArrayList<AnioLectivo>();
    }

    public List<AnioLectivo> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<AnioLectivo> resultlist) {
        this.resultlist = resultlist;
    }

    public AnioLectivo getAnioLectivoSelected() {
        return anioLectivoSelected;
    }

    public void setAnioLectivoSelected(AnioLectivo anioLectivoSelected) {
        this.anioLectivoSelected = anioLectivoSelected;
    }
    
    
    @Override  
    public List<AnioLectivo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
    
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
