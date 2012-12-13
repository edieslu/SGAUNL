/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.util.Date;

/**
 *
 * @author lucho
 */
public interface Timestamped {

    public Date getCreatedOn();

    public void setCreatedOn(Date createdOn);

    public Date getUpdatedOn();

    public void setUpdatedOn(Date updatedOn);
}
