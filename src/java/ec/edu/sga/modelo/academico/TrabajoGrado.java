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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "TrabajoGradoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "TrabajoGrado", initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "TrabajoGrado.buscarPorNombre", 
        query = "SELECT t FROM TrabajoGrado t WHERE t.nombreTrabajoGrado =:param")})
public class TrabajoGrado implements Serializable {

    //--------------------------------ATRIBUTOS-------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TrabajoGradoGenerador")
    private Long id;
    private String nombreTrabajoGrado;
    private String tipoTrabajoGrado;
    private String nombreDirectorTrabajo;
    private String descripcionTrabajo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEntrega;

    //------------------------------CONSTRUCTORES----------------------------//
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTrabajoGrado() {
        return nombreTrabajoGrado;
    }

    public void setNombreTrabajoGrado(String nombreTrabajoGrado) {
        this.nombreTrabajoGrado = nombreTrabajoGrado;
    }

    public String getTipoTrabajoGrado() {
        return tipoTrabajoGrado;
    }

    public void setTipoTrabajoGrado(String tipoTrabajoGrado) {
        this.tipoTrabajoGrado = tipoTrabajoGrado;
    }

    public String getNombreDirectorTrabajo() {
        return nombreDirectorTrabajo;
    }

    public void setNombreDirectorTrabajo(String nombreDirectorTrabajo) {
        this.nombreDirectorTrabajo = nombreDirectorTrabajo;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    //------------------------------MÉTODOS-----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrabajoGrado)) {
            return false;
        }
        TrabajoGrado other = (TrabajoGrado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreTrabajoGrado;
    }
}
