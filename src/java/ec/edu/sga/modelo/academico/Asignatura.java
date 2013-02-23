/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import ec.edu.sga.modelo.horarios.CargaHoraria;
import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "AsignaturaGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Asignatura", initialValue = 1, allocationSize = 1)
@NamedQueries(value={
@NamedQuery(name = "Asignatura.findAllbyAnio", query = "select a from Asignatura a WHERE a.anioLectivo.estado = 'true' "),
   
})
public class Asignatura implements Serializable {

    @ManyToOne
    private MallaCurricular mallaCurricular;
    @ManyToOne
    private Docente docente;
    //-----------------------ATRIBUTOS----------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AsignaturaGenerador")
    private Long id;
    private String nombreAsignatura;
    private String tipoAsignatura; //obligatoria, optativa, a discreción del centro educativo
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @ManyToOne
    private CargaHoraria cargaHoraria;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private AnioLectivo anioLectivo;

    //----------------------------CONSTRUCTORES---------------------------//
    public Asignatura() {
    }

    public Asignatura(MallaCurricular mallaCurricular, Docente docente, String nombreAsignatura, String tipoAsignatura, CargaHoraria cargaHoraria, Date fechaCreacion, Date fechaActualizacion) {
        this.mallaCurricular = mallaCurricular;
        this.docente = docente;
        this.nombreAsignatura = nombreAsignatura;
        this.tipoAsignatura = tipoAsignatura;
        this.cargaHoraria = cargaHoraria;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    //------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getTipoAsignatura() {
        return tipoAsignatura;
    }

    public void setTipoAsignatura(String tipoAsignatura) {
        this.tipoAsignatura = tipoAsignatura;
    }

    public CargaHoraria getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(CargaHoraria cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public MallaCurricular getMallaCurricular() {
        return mallaCurricular;
    }

    public void setMallaCurricular(MallaCurricular mallaCurricular) {
        this.mallaCurricular = mallaCurricular;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AnioLectivo getAnioLectivo() {
        return anioLectivo;
    }

    public void setAnioLectivo(AnioLectivo anioLectivo) {
        this.anioLectivo = anioLectivo;
    }
    
    

    //-------------------------METODOS------------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.modelo.academico.Materia[ id=" + id + " ]";
    }
}
