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
@Entity
@TableGenerator(name = "MenuGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Menu", initialValue = 1, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findAllOrderMenu", query = "SELECT m FROM Menu m order by m.raiz , m.orden"),
    @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id"),
    @NamedQuery(name = "Menu.findByNombre", query = "SELECT m FROM Menu m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Menu.findByImagen", query = "SELECT m FROM Menu m WHERE m.imagen = :imagen"),
    @NamedQuery(name = "Menu.findByUrl", query = "SELECT m FROM Menu m WHERE m.url = :url"),
    @NamedQuery(name = "Menu.findByAction", query = "SELECT m FROM Menu m WHERE m.actio = :action"),
    @NamedQuery(name = "Menu.findByRaiz", query = "SELECT m FROM Menu m WHERE m.raiz = :raiz"),
    @NamedQuery(name = "Menu.findByOrden", query = "SELECT m FROM Menu m WHERE m.orden = :orden"),
    @NamedQuery(name = "Menu.findByCreated", query = "SELECT m FROM Menu m WHERE m.created = :created"),
    @NamedQuery(name = "Menu.findByUpdated", query = "SELECT m FROM Menu m WHERE m.updated = :updated")})
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
    @Size(max = 100)
    @Column
    private String imagen;
    @Size
    @Column(name = "URL")
    private String url;
    @Size
    @Column
    private String actio;
    @Column(name = "RAIZ")
    @NotNull(message = "Debe ingresar Raiz")
    private Integer raiz;
    @Column(name = "ORDEN")
    @NotNull(message = "Debe ingresar Orden")
    private Integer orden;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    // -------------------------- Contructores de la Clase --------------------------
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActio() {
        return actio;
    }

    public void setActio(String actio) {
        this.actio = actio;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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
        return actio.length() > 0;

        }

    @Override
    public String toString() {
        return nombre;
    }
}