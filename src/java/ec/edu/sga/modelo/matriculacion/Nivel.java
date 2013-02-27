/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "NivelGenerador", table = "GeneradorIdentificador",
pkColumnName = "nombre", valueColumnName = "valor",
pkColumnValue = "nivel", initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Nivel.findAllNiveles", query = "select n from Nivel n")
})
public class Nivel implements Serializable {

    //----------------------ATRIBUTOS--------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NivelGenerador")
    private Long id;
    @NotNull
    @Size(min=1,message="Debe ingresar un nombre")
    private String nombreNivel;
    private String descripcionNivel;
    @Enumerated(EnumType.STRING)
    private TipoNivel tipoNivel;
    @OneToMany(mappedBy = "nivel")
    private List<Curso> cursos;
    @OneToMany(mappedBy = "nivel")
    private List<Especialidad> especialidades;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    //---------------------CONTRUCTORES--------------------------//
    public Nivel() {
        cursos = new ArrayList<Curso>();
        especialidades=new ArrayList<Especialidad>();
        
    }

    public Nivel(String nombreNivel, String descripcionNivel, List<Curso> cursos) {
        this.nombreNivel = nombreNivel;
        this.descripcionNivel = descripcionNivel;
        this.cursos = cursos;
    }
    
    

    //-----------------GETTERS AND SETTERS------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        System.out.println("CLASE NIVEL - SET NOMBRENIVEL: "+ nombreNivel);
        this.nombreNivel = nombreNivel;
    }

    public TipoNivel getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(TipoNivel tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    
    
    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
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
    
    

    //---------------------------MÉTODOS---------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nivel)) {
            return false;
        }
        Nivel other = (Nivel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreNivel;
    }
}
