<%-- 
    Document   : fichaArticulo
    Created on : Mar 7, 2018, 6:38:07 PM
    Author     : alruizj
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="paw.model.Articulo"%>
<%@page import="paw.bd.GestorBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
             if (request.getParameter("cart") == null) {
                response.sendRedirect("index.html");
                return;
            }
            if (request.getParameter("cart").trim().length() == 0) {
                response.sendRedirect("index.html");
                return;
            }
            String codigo = request.getParameter("cart");
            GestorBD gbd = new GestorBD();
            Articulo a = gbd.getArticulo(codigo);
            if (a == null){
                request.setAttribute("link", "index.html");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo "+codigo+" no existe");
                return;
            }
 
  
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title><%= a.getCodigo()%></title>
        <meta name="description" content="La descripcion del producto aqui !!" lang="es-ES">
        <meta name="keywords" content="fabricante y tipo de electrodomésticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/fichaProducto.css" rel="stylesheet" media="all" type="text/css">
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script>
            window.addEventListener('load',function(){
                document.getElementById("ImgStock").addEventListener('click',function(){
                    pideStock();
                });
            });
               function pideStock() {
                    $.ajax("api/GetStockArticulo", {
                    data : {'cart':'<%= a.getCodigo()%>'},
                    dataType: 'text',
                    cache : false,
                    success : actualizaStock,
                    error : function (xhr, status, ex) {
                       alert("Error ("+xhr.status+"):"+ status);
                    }
                 });
                }
               function actualizaStock(data, status, xhr) {
                    var stElto = document.getElementById("stock");
                    stElto.innerHTML = data + ' unidades';
                }

        </script>
    </head>

    <body >
        <jsp:include page="cabecera.html"/> 

        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
                    <a href="BuscarArticulos" title="Ojear catalogo">Hojear cat&aacute;logo</a>&nbsp; | &nbsp; 
                    	<!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>

                <div class="contenido">

                    <h1>Ficha t&eacute;cnica de <%= a.getCodigo()%> </h1>
                    <div class="fotoDetalle">
                        <img src="img/fotosElectr/<%= a.getFoto()%> " alt="<%= a.getCodigo()%> " longdesc="<%= a.getNombre()%> ">
                    </div>
                    <div class="datosDetalle">
                        <h2><%= a.getNombre()%></h2>
                        <p><%= a.getDescripcion()%></p>
                        <div class="precio">
                            <span>Precio: <%= a.getPvp()%></span>	
                        </div>
                        <div class="stockArt">
                            <img id="ImgStock" src="img/Almacen30x30.png"  title="Ver stock disponible">&nbsp;
                            <span id="stock"><!-- x unidades --></span><br/>
                        </div>
                        <div class="carroDetalle" >
                            <a href="clientes/GestionaPedido?accion=Comprar&ca=<%= a.getCodigo()%>"><img src="img/AddCart2-50.png" title="Añadir a mi pedido en realización"></a>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>

            <div class="separa"></div>

            <%@include file="pie.html" %>

        </div>
    </body>
</html>
