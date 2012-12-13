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
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "NotaGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Nota", initialValue = 1, allocationSize = 1)
public class Nota implements Serializable {
    
    
    //----------------------------------ATRIBUTOS-----------------------------//
    
    
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator="NotaGenerador")
    private Long id;
    private int notaExamenParcial;
    private int notaExamenQuimestral;
    private int notaTotalQuimestre; 
    private int notaComportamiento;
    @ManyToOne
    private RegistroCalificaciones registroCalificaciones;
    

    
    
    
    
     //------------------------------CONSTRUCTORES----------------------------//
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNotaExamenParcial() {
        return notaExamenParcial;
    }

    public void setNotaExamenParcial(int notaExamenParcial) {
        this.notaExamenParcial = notaExamenParcial;
    }

    public int getNotaExamenQuimestral() {
        return notaExamenQuimestral;
    }

    public void setNotaExamenQuimestral(int notaExamenQuimestral) {
        this.notaExamenQuimestral = notaExamenQuimestral;
    }

    public int getNotaTotalQuimestre() {
        return notaTotalQuimestre;
    }

    public void setNotaTotalQuimestre(int notaTotalQuimestre) {
        this.notaTotalQuimestre = notaTotalQuimestre;
    }

    public int getNotaComportamiento() {
        return notaComportamiento;
    }

    public void setNotaComportamiento(int notaComportamiento) {
        this.notaComportamiento = notaComportamiento;
    }

    public RegistroCalificaciones getRegistroCalificaciones() {
        return registroCalificaciones;
    }

    public void setRegistroCalificaciones(RegistroCalificaciones registroCalificaciones) {
        this.registroCalificaciones = registroCalificaciones;
    }

    
    
    
    
    
    //-------------------------------METODOS--------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.Nota[ id=" + id + " ]";
    }
    
}
