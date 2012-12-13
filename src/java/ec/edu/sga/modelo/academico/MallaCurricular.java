/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import ec.edu.sga.modelo.matriculacion.Curso;
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
@TableGenerator(name = "MallaCurricularGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Asignatura", initialValue = 1, allocationSize = 1)
public class MallaCurricular implements Serializable {
    
    //------------------------ATRIBUTOS-------------------------------//
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="MallaCurricularGenerador")
    private Long id;
    
   
    @OneToMany(mappedBy = "mallaCurricular")
    private List<Asignatura> asignaturas;
    
    
    @OneToOne
    private Curso curso;

    
    
    
    //--------------------------CONTRUCTORES-----------------------------//

    public MallaCurricular() {
        asignaturas = new ArrayList<Asignatura>();
    }
    
    
    
    
    
    
    //------------------------SETTERS AND GETTERS------------------------//
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
    
    
    //----------------------------MÉTODOS-------------------------------//

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MallaCurricular)) {
            return false;
        }
        MallaCurricular other = (MallaCurricular) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.modelo.academico.MallaCurricular[ id=" + id + " ]";
    }
    
}
