package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "TipousuarioGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Tipousuario", initialValue = 1, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t"),
    @NamedQuery(name = "TipoUsuario.findById", query = "SELECT t FROM TipoUsuario t WHERE t.id = :id"),
    @NamedQuery(name = "TipoUsuario.findByNombre", query = "SELECT t FROM TipoUsuario t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoUsuario.findByFechaCreacion", query = "SELECT t FROM TipoUsuario t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "TipoUsuario.findByFechaActualizacion", query = "SELECT t FROM TipoUsuario t WHERE t.fechaActualizacion = :fechaActualizacion")})
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TipousuarioGenerador")
    @Basic(optional = false)
    private Long id;
    @Size(min = 1, max = 100, message = "Debe ingresar el Nombre")
    private String nombre;
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    // -------------------------- Contructores de la Clase --------------------------
    public TipoUsuario() {
    }

    public TipoUsuario(Long id) {
        this.id = id;
    }

    // -------------------------- Getters y Setters --------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    // -------------------------- Métodos de la Clase --------------------------
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
