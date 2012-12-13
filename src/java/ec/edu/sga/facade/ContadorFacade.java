/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

/**
 *
 * @author lucho
 */

import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
/**
* CounterBean is a simple singleton session bean that records the number
* of hits to a web page.
*/
@Singleton
@ConcurrencyManagement(CONTAINER)
public class ContadorFacade {
private int hits = 1;
// Increment and return the number of hits
@Lock(LockType.WRITE)
public int getHits() {
return hits++;
}
}

