/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller.util;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author lucho
 */
@Named(value = "validadorController")
@SessionScoped
public class ValidadorController implements Serializable {
    
    public ValidadorController() {
    }
    
    public void validaNumeroCedula(FacesContext context, UIComponent uiComponent, Object valor) {
        int numeroProvincias = 24;
        String cedula = (String) valor;

        //verifica que tenga 10 dígitos y que contenga solo valores numéricos
        if (!((cedula.length() == 10) && cedula.matches("^[0-9]{10}$"))) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cédula: El número de cédula no es válido", "Ingrese solo numeros - 10  en total");
            context.addMessage(uiComponent.getClientId(context), message);
            ((UIInput) uiComponent).setValid(false);
                             
                     
            //((InputText) uiComponent).setStyleClass("ui-state-error");
            
             //RequestContext rc = RequestContext.getCurrentInstance();
//            rc.execute("alert('Hola')");
            return;
        }

        //verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
        int prov = Integer.parseInt(cedula.substring(0, 2));
        
        if (!((prov > 0) && (prov <= numeroProvincias))) {
            FacesMessage message = new FacesMessage("El número de cédula no es válido!");
            context.addMessage(uiComponent.getClientId(context), message);
            ((UIInput) uiComponent).setValid(false);
            return;
        }

        //verifica que el último dígito de la cédula sea válido
        int[] d = new int[10];

        //Asignamos el string a un array
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }
        
        int imp = 0;
        int par = 0;

        //sumamos los duplos de posición impar
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        //sumamos los digitos de posici�n par
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        //Sumamos los dos resultados
        int suma = imp + par;

        //Restamos de la decena superior
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1)
                + "0") - suma;

        //Si es diez el décimo dígito es cero        
        d10 = (d10 == 10) ? 0 : d10;

        //si el d�cimo d�gito calculado es igual al digitado la cédula es correcta
        if (d10 != d[9]) {
            FacesMessage message = new FacesMessage("El número de cédula no es válido!");
            context.addMessage(uiComponent.getClientId(context), message);
            ((UIInput) uiComponent).setValid(false);
        }
    }
    
    public void validarFecha(FacesContext context, UIComponent uiComponent, Object valor) {
        Date fecha = new Date();
        Date fechaNacimiento = (Date) valor;
        if (fecha.before(fechaNacimiento)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha: la fecha ingresada es mayor a la fecha actual", "La fecha ingresada no puede ser mayor a la fecha actual");
            context.addMessage(uiComponent.getClientId(context), message);
            ((UIInput) uiComponent).setValid(false);
        }
        
    }
}
