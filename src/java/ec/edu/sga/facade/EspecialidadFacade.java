/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.matriculacion.Especialidad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lucho
 */
@Stateless
public class EspecialidadFacade extends AbstractFacade<Especialidad> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EspecialidadFacade() {
        super(Especialidad.class);
    }
    
    
    
    public List<Especialidad> findEspecialidadesByNivelId(Long id){
        Query query= em.createNamedQuery("Especialidad.findEspecialidadesByNivelId");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
