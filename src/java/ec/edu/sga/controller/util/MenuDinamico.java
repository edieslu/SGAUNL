/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller.util;

import ec.edu.sga.facade.MenuFacade;
import ec.edu.sga.modelo.usuarios.Menu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 *
 * @author lucho
 */
@Named(value = "menuDinamico")
@SessionScoped
public class MenuDinamico implements Serializable {

    private MenuModel model;
    private MenuDinamico current;
    @EJB
    private MenuFacade ejbFacadeMenu;
    private List<Menu> list = new ArrayList<Menu>();

//    @Inject
//    private MenuController menuController;
////    @Inject
////    private LoginController loginController;
    public MenuDinamico() {
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public MenuDinamico getCurrent() {
        return current;
    }

    public void setCurrent(MenuDinamico current) {
        this.current = current;
    }

    public List<Menu> getList() {
        return list;
    }

    public void setList(List<Menu> list) {

        this.list = list;
    }

    public void lista() {
        this.list = ejbFacadeMenu.findAllOrderMenu();
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        model = new DefaultMenuModel();
        //  Submenu submenu = new Submenu();

        List<Menu> submenus = new ArrayList<Menu>();
        List<Menu> menuitems = new ArrayList<Menu>();

        for (Menu menu : ejbFacadeMenu.findAllOrderMenu()) {
            System.out.println(menu.getNombre());
            if (menu.isLengt()) {
                menuitems.add(menu);
            } else {
                submenus.add(menu);
            }
        }

        for (Menu subm : submenus) {
            Submenu sm = new Submenu();
            sm.setLabel(subm.getNombre());
            for (Menu menu : menuitems) {
                if (subm.getRaiz() == menu.getRaiz()) {
                    MenuItem m = new MenuItem();
                    m.setValue(menu.getNombre());
                    m.setUrl(menu.getSrc());
                    sm.getChildren().add(m);
                }
            }
            model.addSubmenu(sm);

        }
    }
}
