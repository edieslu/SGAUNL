/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import ec.edu.sga.modelo.academico.ExpedienteAcademico;
import ec.edu.sga.modelo.academico.RegistroCalificaciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "EstudianteGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Estudiante", initialValue = 1, allocationSize = 1)
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    //-------------------------ATRIBUTOS---------------------------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="EstudianteGenerador")
    private Long id;
    private String dni;
    private String libretaMilitar;
    private String nombres;
    private String apellidos;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    private String ciudadNacimiento;
    private String cantonNacimiento;
    private String provinciaNacimiento;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    @Enumerated
    private TipoGenero genero;
    private String nacionalidad;
    private String foto;
//    @OneToMany(mappedBy = "estudiante")
//    private List<Matricula> matriculas;
//    @OneToOne(mappedBy = "estudiante")
//    private RegistroCalificaciones registroCalificaciones;
//    @OneToOne(mappedBy = "estudiante")
//    private ExpedienteAcademico expedienteAcademico;

    //---------------------CONTRUCTORES-----------------------------//
    public Estudiante() {
//        matriculas = new ArrayList<Matricula>();
        fechaNacimiento = new Date();

    }

    //--------------------GETERS AND SETERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLibretaMilitar() {
        return libretaMilitar;
    }

    public void setLibretaMilitar(String libretaMilitar) {
        this.libretaMilitar = libretaMilitar;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getCantonNacimiento() {
        return cantonNacimiento;
    }

    public void setCantonNacimiento(String cantonNacimiento) {
        this.cantonNacimiento = cantonNacimiento;
    }

    public String getProvinciaNacimiento() {
        return provinciaNacimiento;
    }

    public void setProvinciaNacimiento(String provinciaNacimiento) {
        this.provinciaNacimiento = provinciaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoGenero getGenero() {
        return genero;
    }

    public void setGenero(TipoGenero genero) {
        this.genero = genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

//    public List<Matricula> getMatriculas() {
//        return matriculas;
//    }
//
//    public void setMatriculas(List<Matricula> matriculas) {
//        this.matriculas = matriculas;
//    }
//
//    public RegistroCalificaciones getRegistroCalificaciones() {
//        return registroCalificaciones;
//    }
//
//    public void setRegistroCalificaciones(RegistroCalificaciones registroCalificaciones) {
//        this.registroCalificaciones = registroCalificaciones;
//    }
//
//    public ExpedienteAcademico getExpedienteAcademico() {
//        return expedienteAcademico;
//    }
//
//    public void setExpedienteAcademico(ExpedienteAcademico expedienteAcademico) {
//        this.expedienteAcademico = expedienteAcademico;
//    }

    //--------------------------MÉTODOS-----------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}
