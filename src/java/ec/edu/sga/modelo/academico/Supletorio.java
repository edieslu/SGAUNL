/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "SupletorioGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Supletorio", initialValue = 1, allocationSize = 1)
public class Supletorio implements Serializable {
    
    
    //-------------------------ATRIBUTOS---------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="SupletorioGenerador")
    private Long id;
    private String nombreSupletorio;
    private String descripcionSupletorio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    //------------------------------CONSTRUCTORES----------------------------//

    public Supletorio() {
    }

    public Supletorio(String nombreSupletorio, String descripcionSupletorio, Date fechaCreacion, Date fechaActualizacion) {
        this.nombreSupletorio = nombreSupletorio;
        this.descripcionSupletorio = descripcionSupletorio;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSupletorio() {
        return nombreSupletorio;
    }

    public void setNombreSupletorio(String nombreSupletorio) {
        this.nombreSupletorio = nombreSupletorio;
    }

    public String getDescripcionSupletorio() {
        return descripcionSupletorio;
    }

    public void setDescripcionSupletorio(String descripcionSupletorio) {
        this.descripcionSupletorio = descripcionSupletorio;
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

    
    
    

    
    
    //--------------------------------MÉTODOS----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supletorio)) {
            return false;
        }
        Supletorio other = (Supletorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.Supletorio[ id=" + id + " ]";
    }
    
}
