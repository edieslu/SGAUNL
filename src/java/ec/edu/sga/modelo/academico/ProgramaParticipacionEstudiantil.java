/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "ProgramaParticipacionEstudiantilGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "ProgramaParticipacionEstudiantil", initialValue = 1, allocationSize = 1)
public class ProgramaParticipacionEstudiantil implements Serializable {
   
    //------------------------------ATRIBUTOS---------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="ProgramaParticipacionEstudiantilGenerador")
    private Long id;

    private String nombrePrograma;
    private String descripciónPrograma;
    private int duracionPrograma; 
    private int notaPrograma;
    private Boolean estadoAprobacion;
    
    
     //------------------------------CONSTRUCTORES----------------------------//
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getDescripciónPrograma() {
        return descripciónPrograma;
    }

    public void setDescripciónPrograma(String descripciónPrograma) {
        this.descripciónPrograma = descripciónPrograma;
    }

    public int getDuracionPrograma() {
        return duracionPrograma;
    }

    public void setDuracionPrograma(int duracionPrograma) {
        this.duracionPrograma = duracionPrograma;
    }

    public int getNotaPrograma() {
        return notaPrograma;
    }

    public void setNotaPrograma(int notaPrograma) {
        this.notaPrograma = notaPrograma;
    }

    public Boolean getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Boolean estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }
    
    

    //--------------------------------METODOS----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramaParticipacionEstudiantil)) {
            return false;
        }
        ProgramaParticipacionEstudiantil other = (ProgramaParticipacionEstudiantil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.ProgramaParticipacionEstudiantil[ id=" + id + " ]";
    }
    
}
