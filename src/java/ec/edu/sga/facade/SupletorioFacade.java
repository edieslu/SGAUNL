/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.academico.Supletorio;
import ec.edu.sga.modelo.matriculacion.Curso;
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
public class SupletorioFacade extends AbstractFacade<Supletorio> {

    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupletorioFacade() {
        super(Supletorio.class);
    }

    public List<Supletorio> findAllbyAnio() {
        Query query = em.createNamedQuery("Supletorio.findAllbyAnio");
        return query.getResultList();
    }
}
