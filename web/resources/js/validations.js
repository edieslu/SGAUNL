function validarCampos(){
    $j = jQuery.noConflict();
    $j(document).ready(function(){
        $j('.numeros').numeric();
        $j('.letras').alpha({allow:" "});
        $j('.mayusculas').alpha({allcaps:true});
        $j('.email').alphanumeric({allow:"._-"});
                    
    });
}