<%-- 
    Document   : verPedido
    Created on : Apr 16, 2018, 3:27:17 PM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Hoja de pedido</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/pedido.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/listado.css" rel="stylesheet" media="all" type="text/css">
  </head>

  <body >
    <%@include file="cabeceraC.html" %> 

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="../index.html" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
          <a href="AreaCliente" title="Área de cliente">Área de cliente</a>&nbsp; | &nbsp; 
          <a href="PedidosCliente" title="Mis pedidos">Mis pedidos</a>
        </div>
        <div id="cliente">
          Bienvenido ${cliente.nombre}
        </div>
        <div class="clear"></div>
        <div class="contenido">
          <h1>Hoja de pedido    </h1>
          <div class="cabPedido"> <span class="izda">ELECTROSA - Hoja de pedido</span> <span class="dcha">Ref. Pedido: P-0007-99</span>
            <div class="clear"></div>
          </div>
          <div class="cabIdCliente">Identificación del cliente</div>
          <div class="detIdCliente">
            <div class="izda">Cliente: ${cliente.codigo}</div>
            <div class="izda">${cliente.nombre}</div>
            <div class="dcha">Fecha: <fmt:formatDate value="${pedido.fechaCierre.time}"/></div>
            <div class="dcha">Cif: ${cliente.getCif()}</div>
            <div class="clear"></div>
          </div>

          <div class="izda direccionEntregaPedLabel">A entregar en:</div>
          <div class="izda direccionEntregaPed">${pedido.getDirEntrega().getCalle()}<br>${pedido.getDirEntrega().getCiudad()}<br>${pedido.getDirEntrega().getCp()} -${pedido.getDirEntrega().getProvincia()}</div>
          <div class="clear"></div>

          <table width="95%">
            <colgroup>
              <col width="9%">
              <col width="53%">
              <col width="10%">
              <col width="10%">
              <col width="9%">
              <col width="9%">
            </colgroup>
            <tr>
              <td colspan="6">Detalle del pedido</td>
            </tr>
            <tr>
              <td>Cantidad</td>
              <td>Art&iacute;culo</td>
              <td>P.V.P.</td>
              <td>Su precio</td>
              <td>F. Entrega deseada</td>
              <td>F. Entrega prevista</td>
            </tr>
            <c:forEach var="l" items="${pedido.getLineas()}" varStatus="i">
                <c:if test="${i.count%2!=0}">
                <tr class="par">
                </c:if>
                <c:if test="${i.count%2==0}">
                <tr>
                </c:if>
              <td style="text-align:center">${l.getCantidad()}</td>
              <td>${l.getArticulo().getCodigo()} / ${l.getArticulo().getNombre()}</td>
              <td style="text-align: right"><fmt:formatNumber type="currency" value="${l.getPrecioBase()}"/></td>
              <td style="text-align: right"><fmt:formatNumber type="currency" value="${l.getPrecioReal()}"/></td>
              <td><fmt:formatDate value="${l.getFechaEntregaDeseada().time}"/></td>
              <td><fmt:formatDate value="${l.getFechaEntregaPrevista().time}"/></td>
            </tr>
            </c:forEach>
          </table>

          <div class="resPedido">
            <span>TOTAL: <fmt:formatNumber type="currency" value="${pedido.getImporte()}"/></span>
            <div class="clear"></div>
          </div>  

          <div>
            * S.D.: sin disponibilidad. Recibirá una notificación de entrega en el momento en que podamos atender su petición.
          </div>   
        </div>
            <c:if test="${pedido.isCursado() == false}">
          <div  class="anulab">
            <a href="...">ANULAR PEDIDO</a>
          </div> 
          </c:if>
          <div  class="NOanulab">
            <a href="PedidosCliente">Volver a mis pedidos</a>
          </div> 
          <div class="clear"></div>
      </div>

      <div class="separa"></div>

     <%@include file="pieC.html" %>

    </div>
  </body>
</html>
