/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.academico.RegistroCalificaciones;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lucho
 */
@Stateless
public class RegistroCalificacionesFacade extends AbstractFacade<RegistroCalificaciones> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroCalificacionesFacade() {
        super(RegistroCalificaciones.class);
    }
    
}
