package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author edison
 */
// SELECT p FROM FidPersonaEmpresa p JOIN p.fidTrabajador t  WHERE
//p.nombres like :nombres and p.id = t.fidPersonaEmpresaId
@Entity
@TableGenerator(name = "MenuGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Menu", initialValue = 1, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findAllOrderMenu", query = "SELECT m FROM Menu m order by m.raiz , m.orden"),
    @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id"),
    @NamedQuery(name = "Menu.findByNombre", query = "SELECT m FROM Menu m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Menu.findBySrc", query = "SELECT m FROM Menu m WHERE m.src = :src"),
    @NamedQuery(name = "Menu.findByRaiz", query = "SELECT m FROM Menu m WHERE m.raiz = :raiz"),
    @NamedQuery(name = "Menu.findByOrden", query = "SELECT m FROM Menu m WHERE m.orden = :orden"),
    @NamedQuery(name = "Menu.findByFechaCreacion", query = "SELECT m FROM Menu m WHERE m.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Menu.findByFechaActualizacion", query = "SELECT m FROM Menu m WHERE m.fechaActualizacion = :fechaActualizacion")})

public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MenuGenerador")
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(min = 1, max = 100, message = "Debe ingresar el Nombre")
    @Column
    private String nombre;
    @Size
    @Column
    private String src;
    @Column(name = "RAIZ")
    @NotNull(message = "Debe ingresar Raiz")
    private Integer raiz;
    @Column(name = "ORDEN")
    @NotNull(message = "Debe ingresar Orden")
    private Integer orden;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    // -------------------------- Contructor de la Clase --------------------------
    public Menu() {
    }

    public Menu(Long id) {
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getRaiz() {
        return raiz;
    }

    public void setRaiz(Integer raiz) {
        this.raiz = raiz;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean isLengt() {
        return src.length() > 0;

    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
