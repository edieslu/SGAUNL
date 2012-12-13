/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.academico.ExpedienteAcademico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lucho
 */
@Stateless
public class ExpedienteAcademicoFacade extends AbstractFacade<ExpedienteAcademico> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExpedienteAcademicoFacade() {
        super(ExpedienteAcademico.class);
    }
    
}
