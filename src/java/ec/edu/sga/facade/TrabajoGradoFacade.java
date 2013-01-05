/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.academico.TrabajoGrado;
import ec.edu.sga.modelo.usuarios.Usuario;
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
public class TrabajoGradoFacade extends AbstractFacade<TrabajoGrado> {

    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrabajoGradoFacade() {
        super(TrabajoGrado.class);
    }

    public List<TrabajoGrado> buscarPorNombre(String param) {
        Query query = em.createNamedQuery("TrabajoGrado.buscarPorNombre");
        query.setParameter("param", param);
        return query.getResultList();
    }
}
