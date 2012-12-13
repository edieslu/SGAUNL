/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.Menu;
import ec.edu.sga.modelo.usuarios.MenuTipousuario;
import ec.edu.sga.modelo.usuarios.Tipousuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lucho
 */
@Stateless
public class MenuTipousuarioFacade extends AbstractFacade<MenuTipousuario> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuTipousuarioFacade() {
        super(MenuTipousuario.class);
    }
    
    public Boolean findByMenuAndTipousuario(Menu menu, Tipousuario tipo) {
        try {
            Query query = em.createNamedQuery("MenuTipousuario.findByMenuAndTipousuario");
            query.setParameter("menu", menu);
            query.setParameter("tipo", tipo);
            query.getSingleResult();
            return true;
        }
        catch (Exception e) { return false; }
    } // Fin public Boolean findByMenuAndTipousuario
    
}
