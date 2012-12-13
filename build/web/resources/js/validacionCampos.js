var expresion = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
 
//<span class="ui-messages-error-icon"></span><ul><li><span class="ui-messages-error-summary">Apellidos: Error de validación: se necesita un valor.</span></li></ul>
//<div class="ui-messages-error ui-corner-all"><span class="ui-messages-error-icon"></span><ul><li><span class="ui-messages-error-summary">Apellidos: Error de validación: se necesita un valor.</span></li></ul></div>

//
//<input aria-multiline="false" aria-readonly="false" aria-disabled="false" role="textbox" id="formCreateUsuario:j_idt54:apellidos" name="formCreateUsuario:j_idt54:apellidos" value="" title="Apellidos" class="ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-state-error" type="text">
function inicializar(){
    
alert("Entro a inicializar");
var x;
x=$(document);
x.ready(accederformulario);
}

function accederformulario(){
    alert("Entro a acceder a formulario");
    var x;
    x=$("#idguardar");
    x.click(validarCampos);
}

function validarCampos(){
    alert("Entro a validar");
    var nombres = ("#nombres").val();
        if(nombres==""){
            $("#mensajeNombres").fadeIn();
            alert("Entro al if");
            return false;
        } 
        else 
        {           
            alert("Entro al else");
            $("#mensajeNombres").fadeIn();
            return true;
         }

}






//x.ready(function(){
//                    $("#idguardar").click(function(){
//                        
//                        var nombres = (".nombres").val();
//                                       
//                        if(nombres==""){
//                            $("#mensajeNombres").fadeIn();
//                            return;
//                        } 
//                        
//                        else 
//                        {
//                            $("#mensajeNombres").fadeIn();
//                            
////                            if(email == "" || !expresion.test(email)){
////                                $(".mensajes").fadeIn();
////                                return false
////                                
////                                
////                            }
//                        }
//                        
//                    });
//                    
//                });