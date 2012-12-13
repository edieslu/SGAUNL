/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.matriculacion.Paralelo;
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
public class ParaleloFacade extends AbstractFacade<Paralelo> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParaleloFacade() {
        super(Paralelo.class);
    }
    
    public List<Paralelo> findAllParalelos(){
        Query query = em.createNamedQuery("Paralelo.findAllParalelos");
        return query.getResultList();
    }
    
    public List<Paralelo> findAllParalelosByCursoId(Long cursoId){
        Query query = em.createNamedQuery("Paralelo.findAllParalelosByCursoId");
        query.setParameter("cursoId", cursoId);
        return query.getResultList();
    }
    
}
