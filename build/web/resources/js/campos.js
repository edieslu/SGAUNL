var x;
x = $(document);
x.ready(function(){
    $(".campos-obligatorios").bind("keyup mouseenter blur click", function(){
        revisarObligatorio(this);
        ayudasVisuales(this);
    });
                    
                    
    $(".numeros").bind("keyup mouseenter blur keypress", function(){
        revisarNumerico(this);
        ayudasVisuales(this);
    });
                    
                    
    $("#idguardar").click(function(){
        $(this).feedback("This is a sample info message", {
                duration: 3000, 
                right: true
            });
        var formulario=validarFormulario();
        if(formulario){
            $("#formCreateUsuario").submit();
        }
        return false;
    });
                    
});
            
            
            
           
