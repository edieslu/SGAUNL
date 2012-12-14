/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "MatriculaGenerador", table = "GeneradorIdentificador",
pkColumnName = "nombre", pkColumnValue = "matricula",
valueColumnName = "valor", initialValue = 1, allocationSize = 1)
public class Matricula implements Serializable {

    //-----------------------------------ATRIBUTOS-------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MatriculaGenerador")
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fechaMatricula;
    private int numeroMatricula;
    private int folioMatricula;
    @Enumerated(EnumType.STRING)
    private TipoMatricula tipoMatricula;
    private String observaciones;
    private int segundaTerceraMatricula;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private AnioLectivo anioLectivo;
    
    @ManyToOne
    private Curso curso;
    @ManyToOne
    private Paralelo paralelo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    
    //--------------------COSTRUCTORES-----------------------//
    public Matricula() {
        fechaMatricula = new Date();

    }

    public Matricula(Date fechaMatricula, int numeroMatricula, int folioMatricula, TipoMatricula tipoMatricula, String observaciones, int segundaTerceraMatricula, Usuario usuario, AnioLectivo anioLectivo, Curso curso, Paralelo paralelo, Date fechaCreacion, Date fechaActualizacion) {
        this.fechaMatricula = fechaMatricula;
        this.numeroMatricula = numeroMatricula;
        this.folioMatricula = folioMatricula;
        this.tipoMatricula = tipoMatricula;
        this.observaciones = observaciones;
        this.segundaTerceraMatricula = segundaTerceraMatricula;
        this.usuario = usuario;
        this.anioLectivo = anioLectivo;
        this.curso = curso;
        this.paralelo = paralelo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    

    //----------------------GETTERS AND SETTERS------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public int getFolioMatricula() {
        return folioMatricula;
    }

    public void setFolioMatricula(int folioMatricula) {
        this.folioMatricula = folioMatricula;
    }

    public TipoMatricula getTipoMatricula() {
        return tipoMatricula;
    }

    public void setTipoMatricula(TipoMatricula tipoMatricula) {
        this.tipoMatricula = tipoMatricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public AnioLectivo getAnioLectivo() {
        return anioLectivo;
    }

    public void setAnioLectivo(AnioLectivo anioLectivo) {
        this.anioLectivo = anioLectivo;
    }

    public Curso getCurso() {
        return curso;
    }
   
    public void setCurso(Curso curso) {
        System.out.println("CLASE MATRICULA-SETCURSO: "+curso);
        this.curso = curso;
    }

    public int getSegundaTerceraMatricula() {
        return segundaTerceraMatricula;
    }

    public void setSegundaTerceraMatricula(int segundaTerceraMatricula) {
        this.segundaTerceraMatricula = segundaTerceraMatricula;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
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
    
    
    
    

    //------------------------METODOS--------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoMatricula+"";
    }
}
