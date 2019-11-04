<%-- 
    Document   : pedidoRealizacion
    Created on : Apr 17, 2018, 11:45:08 AM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Pedido en realización</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/pedidoR.css" rel="stylesheet" media="all" type="text/css">
    <link href="../codebase/dhtmlxcalendar.css" rel="stylesheet" type="text/css"/>
    <script src="../codebase/dhtmlxcalendar.js" type="text/javascript"></script>
    <script src="../js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script>
        
window.onload = function() {
                initCalendars();
                
            };
            
            function initCalendars() {
                var ids = [];
                <c:forEach var="l" items="${pedidoRealizacion.lineas}">
                                ids.push("id_${l.codigo}");
                </c:forEach>
                var calendarios = new dhtmlXCalendarObject(ids);
                calendarios.setDateFormat("%d/%m/%Y");
                calendarios.hideTime();
                calendarios.setInsensitiveRange(null, new Date());
            }
            window.addEventListener('load',function(){
                var cantidades = document.getElementsByClassName("cantidad");
                for(var i = 0; i<cantidades.length;i++){
                    cantidades[i].onchange = function (evt){
                        pideStock(evt);
                    };
        }
            },false);
            function pideStock(evt) {
                  input = evt.target;
                 
                    $.ajax("../api/GetStockArticulo", {
                    data : {'cart':input.id},
                    dataType: 'text',
                    cache : false,
                    success : comprobarStock,
                    error : function (xhr, status, ex) {
                       alert("Error ("+xhr.status+"):"+ status);
                    }
                 });
                }
            function comprobarStock(data, status, xhr ){
               
                if(parseInt(data) < parseInt(input.value)){
                    input.style.color = "#C02C11";
                }else{
                    input.style.color = "#050201";
                }
            }
        
        </script>

  </head>

  <body >
    <%@include file="cabeceraC.html" %>

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="../index.html" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
          <a href="AreaCliente" title="Área de cliente">Área de cliente</a>
        </div>
        <div id="cliente">
          Bienvenido, ${cliente.nombre}
        </div>
        <div class="clear"></div>
        <div class="contenido">
          <h1>Contenido de su  pedido    </h1>
          <p>Pedido iniciado el <fmt:formatDate pattern="dd/MM/yyyy" value="${pedidoRealizacion.getInicio().time}"/> a las <fmt:formatDate pattern="HH:mm" value="${pedidoRealizacion.getInicio().time}"/>.</p>
          <form action="GestionaPedido" method="post">
            <table width="95%" cellspacing="0" id="tabla">
              <tr>
                <td colspan="2"><img src="../img/AddCartb.png" style="vertical-align:middle;margin-bottom:3px; margin-left:15px">&nbsp; Art&iacute;culos del pedido</td>
                <td width="10%">P.V.P.</td>
                <td width="10%">Cantidad</td>
                <td width="16%">Fecha de entrega (dd/mm/yyyy)</td>
              </tr>
              <c:forEach var="l" items="${pedidoRealizacion.getLineas()}">
              <tr >
                <td width="6%" style="text-align:center"><a href="GestionaPedido?accion=quitar&cl=L-87601"><img src="../img/cancel.png" alt="Quitar del pedido" border="0" title="Quitar del pedido"></a></td>
                <td width="58%"><span class="codigo">${l.getCodigo()}</span> - <br/><span class="descr">${l.getArticulo().getNombre()}</span></td>
                <td><fmt:formatNumber type="currency" value="${l.getPrecio()}"/></td>
                <td>
                    <input id="${l.articulo.getCodigo()}" class="cantidad" type="number" min="1" width="40px" name="textfield" size="3" value="${l.getCantidad()}">
                </td>
                <td>
                  <input type="text" id="id_${l.codigo}"  name="textfield" size="10" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${l.fechaEntregaDeseada.time}"/>">			  
                </td>
              </tr>
              </c:forEach>
            </table>
            <input class="submitb" type="submit" name="accion" value="Seguir comprando">
            <input class="submitb" type="submit" name="accion" value="Guardar pedido">
            <input class="submitb cerrar" type="submit" name="accion" value="Cerrar pedido">	
            <input class="submitb cancelar" type="submit" name="accion" value="Cancelar">
          </form>
        </div>
        <div class="clear"></div>
      </div>

      <div class="separa"></div>

      <%@include file="pieC.html" %>

    </div>
  </body>
</html>
