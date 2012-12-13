/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.FichaSocioeconomica;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author edison
 */
@Stateless
public class FichaSocioeconomicaFacade extends AbstractFacade<FichaSocioeconomica> {

    @PersistenceContext(unitName="SGAUNLPU")
    private EntityManager em;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FichaSocioeconomicaFacade() {
        super(FichaSocioeconomica.class);
    }
    
    
}
