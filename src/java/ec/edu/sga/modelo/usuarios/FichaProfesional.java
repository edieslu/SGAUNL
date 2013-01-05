/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "FichaProfesionalGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "FichaProfesional", initialValue = 1, allocationSize = 1)
public class FichaProfesional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="FichaProfesionalGenerador")
    private Long id;
    private String titulo;
    private String especializacion;
    private String nivelInstruccion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngresoMagisterio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngresoUnidad;
    @OneToOne
    private Ficha ficha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getNivelInstruccion() {
        return nivelInstruccion;
    }

    public void setNivelInstruccion(String nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
    }

    public Date getFechaIngresoMagisterio() {
        return fechaIngresoMagisterio;
    }

    public void setFechaIngresoMagisterio(Date fechaIngresoMagisterio) {
        this.fechaIngresoMagisterio = fechaIngresoMagisterio;
    }

    public Date getFechaIngresoUnidad() {
        return fechaIngresoUnidad;
    }

    public void setFechaIngresoUnidad(Date fechaIngresoUnidad) {
        this.fechaIngresoUnidad = fechaIngresoUnidad;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaProfesional)) {
            return false;
        }
        FichaProfesional other = (FichaProfesional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.modelo.usuarios.FichaProfesional[ id=" + id + " ]";
    }
}
