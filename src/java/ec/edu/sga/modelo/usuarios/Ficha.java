/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "FichaGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Ficha", initialValue = 1, allocationSize = 1)
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "FichaGenerador")
    private Long id;
    @OneToOne
    private Usuario usuario;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private FichaPersonal fichaPersonal;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaMedica fichaMedica;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaSocioeconomica fichaSocio;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaPadre fichaPadre;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaMadre fichaMadre;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaRepresentante fichaRepresentante;
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL, orphanRemoval = true)
    private FichaProfesional fichaProfesional;

    public Ficha() {
//        this.usuario= new Usuario();
//        
//        this.fichaPersonal= new FichaPersonal();
//        this.fichaMedica= new FichaMedica();
//        this.fichaSocio= new FichaSocioeconomica();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {

        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FichaPersonal getFichaPersonal() {
        return fichaPersonal;
    }

    public void setFichaPersonal(FichaPersonal fichaPersonal) {
        this.fichaPersonal = fichaPersonal;
    }

    public FichaMedica getFichaMedica() {
        return fichaMedica;
    }

    public void setFichaMedica(FichaMedica fichaMedica) {
        this.fichaMedica = fichaMedica;
    }

    public FichaSocioeconomica getFichaSocio() {
        return fichaSocio;
    }

    public void setFichaSocio(FichaSocioeconomica fichaSocio) {
        this.fichaSocio = fichaSocio;
    }

    public FichaPadre getFichaPadre() {
        return fichaPadre;
    }

    public void setFichaPadre(FichaPadre fichaPadre) {
        this.fichaPadre = fichaPadre;
    }

    public FichaMadre getFichaMadre() {
        return fichaMadre;
    }

    public void setFichaMadre(FichaMadre fichaMadre) {
        this.fichaMadre = fichaMadre;
    }

    public FichaRepresentante getFichaRepresentante() {
        return fichaRepresentante;
    }

    public void setFichaRepresentante(FichaRepresentante fichaRepresentante) {
        this.fichaRepresentante = fichaRepresentante;
    }

    public FichaProfesional getFichaProfesional() {
        return fichaProfesional;
    }

    public void setFichaProfesional(FichaProfesional fichaProfesional) {
        this.fichaProfesional = fichaProfesional;
    }

    public void add(FichaPersonal fichaPersonal) {
        if (!fichaPersonal.equals(fichaPersonal)) {
            this.fichaPersonal = fichaPersonal;
            fichaPersonal.setFicha(this);
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
        if (!(object instanceof Ficha)) {
            return false;
        }
        Ficha other = (Ficha) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.modelo.user.Ficha[ id=" + id + " ]";
    }
}
