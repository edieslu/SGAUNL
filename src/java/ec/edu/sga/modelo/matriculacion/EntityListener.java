/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author lucho
 */
public class EntityListener {
    @PrePersist
public void onPrePersist(Object o) {
populateTimestamp(o);
}

@PreUpdate
public void onPreUpdate(Object o) {

populateTimestamp(o);

}

 

protected void populateTimestamp(Object o) {

if (o instanceof Timestamped) {

Timestamped ts = (Timestamped) o;

if (ts.getCreatedOn() == null) {

ts.setCreatedOn(new Date());

} else {

ts.setUpdatedOn(new Date());

}

}

}
}
