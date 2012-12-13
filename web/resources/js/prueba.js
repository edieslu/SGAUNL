var x;
x=$(document);
x.ready(inicializar);

function inicializar(){
    var x;
    x=$("#hola");
    x.click(clickhecho);
}

function clickhecho(){
    var x;
    x=$("#hola");
    x.css("color", "green");
}

