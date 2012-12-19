package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author edison
 */
// select m.nombre from Menu m, MenuTipoUsuario mt 
//where mt.menu_id=m.id and mt.menutipousuario_id = :param
@Entity
@TableGenerator(name = "MenuTipousuarioGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "MenuTipousuario", initialValue = 1, allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "MenuTipoUsuario.findAll", query = "SELECT m FROM MenuTipoUsuario m"),
    @NamedQuery(name = "MenuTipoUsuario.findById", query = "SELECT m FROM MenuTipoUsuario m WHERE m.id = :id"),
    @NamedQuery(name = "MenuTipoUsuario.findByMenuId", query = "SELECT m FROM MenuTipoUsuario m WHERE m.menu = :menu"),
    @NamedQuery(name = "MenuTipoUsuario.findByTipousuarioId", query = "SELECT m FROM MenuTipoUsuario m WHERE m.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "MenuTipoUsuario.findTipousuarioByMenu", query = "SELECT m.tipoUsuario FROM MenuTipoUsuario m WHERE m.menu = :menu"),
    @NamedQuery(name = "MenuTipoUsuario.findByMenuAndTipousuario", query = "SELECT m FROM MenuTipoUsuario m WHERE m.menu = :menu AND m.tipoUsuario = :tipo"),
    @NamedQuery(name = "MenuTipoUsuario.findByFechaCreacion", query = "SELECT m FROM MenuTipoUsuario m WHERE m.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "MenuTipoUsuario.findByFechaActualizacion", query = "SELECT m FROM MenuTipoUsuario m WHERE m.fechaActualizacion = :fechaActualizacion")})
public class MenuTipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MenuTipousuarioGenerador")
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @NotNull(message = "Debe seleccionar un Tipo Usuario")
    @ManyToOne
    private TipoUsuario tipoUsuario;
    @NotNull(message = "Debe seleccionar un Menu")
    @ManyToOne
    private Menu menu;

    // -------------------------- Contructores de la Clase --------------------------
    public MenuTipoUsuario() {
    }

    public MenuTipoUsuario(Long id) {
        this.id = id;
    }

    // -------------------------- Getters y Setters --------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
        if (!(object instanceof MenuTipoUsuario)) {
            return false;
        }
        MenuTipoUsuario other = (MenuTipoUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoUsuario.getNombre() + " - " + menu.getNombre();
    }
}
