function ayudasVisuales(elemento){
     var elementtolista=$(elemento).parentNode;
     var ayuda = $(document).getElementById("ayuda");
     if(ayuda != undefined){
         ayuda.parentNode.removeChild(ayuda);
      }
    elementtolista.innerHTML+= "<span class='ayuda' id='ayuda'>Prueba</span> " ;
}


function revisarObligatorio(elemento) {
    
    var texto = $(elemento).val();
    if (texto=="") {
        $(elemento).addClass("ui-state-error ui-state-focus");
    } else {
        $(elemento).removeClass("ui-state-error ui-state-focus");	
    }
}


function revisarNumerico(elemento) {
    var texto= $(elemento).val();
    if (texto!="") {
		
        if (isNaN(texto)) {
            $(elemento).addClass("ui-state-error ui-state-focus");	
        } else {
            $(elemento).removeClass("ui-state-error ui-state-focus");
        }
    }
}

function validarFormulario(){
    var bandera=true;
    var camposObligatorios = $(".campos-obligatorios");
    var numeros = $(".numeros");
    
    if(camposObligatorios==""){
        bandera = false;
    }
    
    if(isNaN(numeros)){
        bandera = false;
    }
        
        
    if(bandera==false){
//        $("#msg").click(function() { 
//            $(this).feedback("This is a sample info message", {
//                duration: 3000, 
//                right: true
//            });
//        }); 
        
        alert("Revisa los campos, están vacíos");
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Validar Campos", "PrimeFaces makes no mistakes"));  
        $(".campos-obligatorios").addClass("ui-state-error");
        $(".numeros").addClass("ui-state-error");
            
    }
        
        
    return bandera;
}
    

//<f:validateRegex pattern="[\w\.-]*[a-zA-Z0-9_]@[\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]"/>

