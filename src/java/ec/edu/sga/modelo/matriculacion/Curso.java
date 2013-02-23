/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import ec.edu.sga.modelo.academico.MallaCurricular;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@TableGenerator(name = "CursoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Curso", initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Curso.findAll", query = "select c from Curso c"),
    @NamedQuery(name = "Curso.findAllbyAnio", query = "select c from Curso c WHERE c.anioLectivo.estado = 'true' "),
    @NamedQuery(name = "findCursosAndParalelos",
    query = "select distinct e from Curso e left join fetch"
    + " e.paralelos where e.id = :id"),
    @NamedQuery(name = "Curso.findAllCursosbyNivelId",
    query = "select c from Curso c where c.nivel.id =:id"),
    @NamedQuery(name="Curso.countCursos",query="SELECT count(c) FROM Curso c join c.anioLectivo p WHERE p.estado='true'"),
    @NamedQuery(name = "Curso.findCursosByEspecialidadId", query = "SELECT c FROM Curso c WHERE c.especialidad.id=:id ")
})
public class Curso implements Serializable {

    //-----------------------ATRIBUTOS----------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CursoGenerador")
    private Long id;
    
    @NotNull
    @Size(min=1,message="Debe ingresar un nombre")
    private String nombreCurso;
    @ManyToOne
    private Especialidad especialidad;
    @ManyToOne
    private Nivel nivel;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Matricula> matriculas;
    @OneToOne(mappedBy = "curso")
    private MallaCurricular mallaCurricular;
    private Integer orden;
    @OneToMany(mappedBy = "curso", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Paralelo> paralelos;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @ManyToOne
    private AnioLectivo anioLectivo;
    

    //----------------------constructores--------------------------------------//
    public Curso() {
        paralelos = new ArrayList<Paralelo>();
    }

    public Curso(String nombreCurso, Especialidad especialidad, Nivel nivel, List<Matricula> matriculas, MallaCurricular mallaCurricular, List<Paralelo> paralelos) {
        this.nombreCurso = nombreCurso;
        this.especialidad = especialidad;
        this.nivel = nivel;
        this.matriculas = matriculas;
        this.mallaCurricular = mallaCurricular;
        this.paralelos = paralelos;
    }

    //----------------------MÉTODOS SET AND GET---------------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        System.out.println("CLASE CURSO-SET ESPECIALIDAD: " + especialidad);
        this.especialidad = especialidad;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        System.out.println("CLASE CURSO-SET NIVEL: " + nivel);
        this.nivel = nivel;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {

        this.matriculas = matriculas;
    }

    public MallaCurricular getMallaCurricular() {
        return mallaCurricular;
    }

    public void setMallaCurricular(MallaCurricular mallaCurricular) {
        this.mallaCurricular = mallaCurricular;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    
    
    public List<Paralelo> getParalelos() {
        return paralelos;
    }

    public void setParalelos(List<Paralelo> paralelos) {
        this.paralelos = paralelos;
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

    public AnioLectivo getAnioLectivo() {
        return anioLectivo;
    }

    public void setAnioLectivo(AnioLectivo anioLectivo) {
        this.anioLectivo = anioLectivo;
    }

    
    

    
    
    
    //----------------------MÉTODOS---------------------------------//
    public void add(Paralelo paralelo) {
        if (!paralelos.contains(paralelo)) {
            paralelos.add(paralelo);
            paralelo.setCurso(this);
        }


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
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreCurso;
    }
}
