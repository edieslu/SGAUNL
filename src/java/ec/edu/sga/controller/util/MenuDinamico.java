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


//        model = new DefaultMenuModel();
//
//
//        Submenu submenu = new Submenu();
////        submenu.setLabel("Dynamic Submenu 1");
////
////        MenuItem item = new MenuItem();
////        item.setValue("Dynamic Menuitem 1.1");
////        item.setUrl("#");
////        submenu.getChildren().add(item);
////
////        model.addSubmenu(submenu);
//
//
//        for (Menu menu : ejbFacadeMenu.findAllOrderMenu()) {
//            System.out.println(menu.getNombre());
//            if (menu.isLengt()) {
//                MenuItem item = new MenuItem();
//                item.setValue(menu.getNombre());
//                item.setUrl("#");
//                submenu.getChildren().add(item);
//                model.addSubmenu(submenu);
//            } else {
//                submenu.setLabel(menu.getNombre());
//            }
//        }
//
////        for (Menu menu : ejbFacadeMenu.findAllOrderMenu()) {
////            System.out.println(menu.getNombre());
////
////            if (menu.isLengt()) {
////                MenuItem item = new MenuItem();
////                item.setValue(menu.getNombre());
////                item.setUrl("#");
////                submenu.getChildren().add(item);
////                menuDinamico.addSubmenu(submenu);
//            } else {
//                submenu.setLabel(menu.getNombre());
//            }
//
//
//        }


//        menuDinamico = new DefaultMenuModel();
//        int var = 0;
//
//
//
//        for (Menu menu : ejbFacadeMenu.findAll()) {
//            Submenu submenu = new Submenu();
//
//            if (menu.isLengt()) {
//                var = 1;
//            } else {
//                var = 0;
//            }
//
//            switch (var) {
//
//                case 1:
//                    MenuItem item = new MenuItem();
//                    item.setValue(menu.getNombre());
//                    item.setUrl(loginController.irA(menu.getActio()));
//                    submenu.getChildren().add(item);
//                    menuDinamico.addSubmenu(submenu);
//                default:
//                    submenu.setLabel(menu.getNombre());
//            }
//        }


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
    private void init(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        model = new DefaultMenuModel();
        Submenu submenu;
        submenu = new Submenu();
        
        for (Menu menu : ejbFacadeMenu.findAllOrderMenu()) {
            System.out.println(menu.getNombre());
            if (menu.isLengt()) {
                MenuItem item = new MenuItem();
                item.setValue(menu.getNombre());
                item.setUrl(menu.getActio());
                   //item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{menuController.current.actio}", String.class, new Class[0]));
                submenu.getChildren().add(item);
                model.addSubmenu(submenu);
            } else {
                submenu.setLabel(menu.getNombre());
            }
        }

        
//        submenu.setId("idCabecera");
//        submenu.setLabel("Operaciones");
//
//        MenuItem item = new MenuItem();
//        item.setValue("Organismos");
//        item.setId("idOrganismo");
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuarioSesion.listar2}", String.class, new Class[0]));
//        item.setAjax(false);
//        item.setAsync(false);
//        submenu.getChildren().add(item);
//
//        MenuItem item2 = new MenuItem();
//        item2.setId("idAplicacion");
//        item2.setValue("Aplicacion");
//        item2.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuarioSesion.listar1}", String.class, new Class[0]));
//        item2.setAjax(false);
//        item2.setAsync(false);
//        submenu.getChildren().add(item2);
//
//        model.addSubmenu(submenu);

    }
}
