/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.Menu;
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
public class MenuFacade extends AbstractFacade<Menu> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
    public List<Menu> findAllOrderMenu() {
        try {
            Query query = em.createNamedQuery("Menu.findAllOrderMenu");
            return query.getResultList();
        }
        catch (Exception e) { return null; }
    } // Fin public List<Menu> findAllOrderMenu

    public Menu findByAction(String action) {
        try {
            Query query = em.createNamedQuery("Menu.findByAction");
            query.setParameter("actio", action);
            return (Menu)query.getSingleResult();
        }
        catch (Exception e) { return null; }
    } // Fin public Menu findByAction
    
}
