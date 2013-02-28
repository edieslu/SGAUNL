/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "PeriodoAcademicoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "PeriodoAcademico", initialValue = 1, allocationSize = 1)
@NamedQueries(value={
    @NamedQuery(name="Periodo.findAllByAnioActivo", query="SELECT p FROM PeriodoAcademico p WHERE p.anioLectivo.estado='true'")
})
public class PeriodoAcademico implements Serializable {
    //--------------------------------ATRIBUTOS--------------------------------//

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PeriodoAcademicoGenerador")
    private Long id;
    private String nombre;
    private Boolean estado;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @OneToOne
    private RegistroCalificaciones registroCalificaciones;
    @ManyToOne
    private AnioLectivo anioLectivo;
    

    //------------------------------CONSTRUCTORES----------------------------//
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistroCalificaciones getRegistroCalificaciones() {
        return registroCalificaciones;
    }

    public void setRegistroCalificaciones(RegistroCalificaciones registroCalificaciones) {
        this.registroCalificaciones = registroCalificaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public AnioLectivo getAnioLectivo() {
        return anioLectivo;
    }

    public void setAnioLectivo(AnioLectivo anioLectivo) {
        this.anioLectivo = anioLectivo;
    }

    
    
    

    //--------------------------------MÉTODOS---------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoAcademico)) {
            return false;
        }
        PeriodoAcademico other = (PeriodoAcademico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.Quimestre[ id=" + id + " ]";
    }
}
