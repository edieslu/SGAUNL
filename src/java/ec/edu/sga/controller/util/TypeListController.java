/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller.util;

import ec.edu.sga.modelo.matriculacion.TipoMatricula;
import ec.edu.sga.modelo.matriculacion.TipoNivel;
import ec.edu.sga.modelo.usuarios.TipoRol;
import ec.edu.sga.modelo.usuarios.TipoSangre;
import ec.edu.sga.modelo.usuarios.TipoSexo;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "typeListController")

public class TypeListController {

    /**
     * Creates a new instance of TypeListController
     */
    public TypeListController() {
    }
    
    public TipoSexo[] getTypeListSexo(){
        return TipoSexo.values();
    }
    
    public TipoSangre[] getTypeListSangre(){
        return TipoSangre.values();
    }
    
    public TipoMatricula[] getTypeTipoMatriculas(){
        return TipoMatricula.values();
    }
    
    public TipoNivel[] getTypeTipoNivel(){
        return TipoNivel.values();
    }
    public TipoRol[] getTipoRol(){
        return TipoRol.values();
    }
            
}
