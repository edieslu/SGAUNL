/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import ec.edu.sga.modelo.matriculacion.Estudiante;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "ExpedienteAcademicoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "ExpedienteAcademico", initialValue = 1, allocationSize = 1)
public class ExpedienteAcademico implements Serializable {
   
    
    //--------------------------------ATRIBUTOS------------------------------//
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="ExpedienteAcademicoGenerador")
    private Long id;
    
    @OneToMany(mappedBy = "expedienteAcademico")
    private List<RegistroCalificaciones> registrocalificaciones;
    
    @OneToOne
    private Usuario usuario;
    
    
    //------------------------------CONSTRUCTORES----------------------------//

    public ExpedienteAcademico() {
        registrocalificaciones = new ArrayList<RegistroCalificaciones>();
    }
    
    
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RegistroCalificaciones> getRegistrocalificaciones() {
        return registrocalificaciones;
    }

    public void setRegistrocalificaciones(List<RegistroCalificaciones> registrocalificaciones) {
        this.registrocalificaciones = registrocalificaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

    
    
    //-------------------------------MÉTODOS----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpedienteAcademico)) {
            return false;
        }
        ExpedienteAcademico other = (ExpedienteAcademico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.ExpedienteAcademico[ id=" + id + " ]";
    }
    
}
