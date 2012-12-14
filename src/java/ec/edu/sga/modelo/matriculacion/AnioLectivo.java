/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "AnioLectivoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "AnioLectivo", initialValue = 1, allocationSize = 1)
@NamedQueries(value={@NamedQuery(name="AnioLectivo.findAnioActivo",query="select a from AnioLectivo a where a.estado=:parameter"),
@NamedQuery(name="AnioLectivo.findByCriterio", query="SELECT a FROM AnioLectivo a WHERE a.fechaInicio=:criterio")
})
public class AnioLectivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="AnioLectivoGenerador")
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    @OneToMany(mappedBy = "anioLectivo")
    private List<Matricula> matriculas;
    private Long duracion;
    private Boolean estado;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    

    //---------------------------CONSTRUCTORES--------------------------//
    public AnioLectivo() {
        //Fechas comentadas para que no se inicialice el p:calendar
//        fechaInicio = new Date();
//        fechaFin = new Date();
        matriculas = new ArrayList<Matricula>();
    }

    //----------------------------GETERS AND SETERS--------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    
    

    //----------------------------MÉTODOS-------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnioLectivo)) {
            return false;
        }
        AnioLectivo other = (AnioLectivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy").format(fechaInicio);
    }
}
