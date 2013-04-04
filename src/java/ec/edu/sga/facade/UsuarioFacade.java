/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author edison
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorClave(String clave) {
        Query query = em.createNamedQuery("Usuario.buscarPorClave");
        query.setParameter("clave", clave);
        return query.getResultList();
    }

    public Usuario buscarPorId(Long id) {

        Query query = em.createNamedQuery("Usuario.buscarPorId");
        query.setParameter("id", id);

        return (Usuario) query.getSingleResult();
    }

    public Usuario getLogin(String login, String clave) {
        try {
            Query query = em.createNamedQuery("Usuario.findLogin");
            query.setParameter("login", login);
            query.setParameter("clave", clave);
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> findEstudiantes(String clave) {
        Query query = em.createNamedQuery("Usuario.buscarPorEstudiantes");
        query.setParameter("clave", clave);
        return query.getResultList();
    }

    public List<Usuario> findDocentes(String clave) {
        Query query = em.createNamedQuery("Usuario.buscarPorDocentes");
        query.setParameter("clave", clave);
        return query.getResultList();
    }

    public List<Usuario> findAdmin() {
        Query query = em.createNamedQuery("Usuario.findAdmin");
        return query.getResultList();
    }
}
