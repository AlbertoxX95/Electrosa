<%-- 
    Document   : pedidosCliente
    Created on : Apr 16, 2018, 1:52:37 PM
    Author     : alruizj
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Pedidos del cliente</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/listado.css" rel="stylesheet" media="all" type="text/css">
        <script src="../js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script>
            window.addEventListener('load', function () {
                var desplegables = document.getElementsByClassName("desplegar");
                for (var i = 0; i < desplegables.length; i++) {
                    desplegables[i].onclick = function (evt) {
                        cambiarSimbolo(evt);
                        evt.preventDefault();
                    };
                }
            });
            function cambiarSimbolo(evt) {
                var simbolo = evt.target;
                if (simbolo.textContent == "[+] ") {
                    simbolo.class = "Ocultar";
                    simbolo.textContent = "[-]";
                    simbolo.title = "Ocultar detalle del pedido";
 
                    obtenerPedido(simbolo.id);
                } else {

                    simbolo.textContent = "[+] ";
                    simbolo.title = "Desplegar detalle del pedido";
                    ocultarPedido(simbolo.id);
                }

            }
            function obtenerPedido(cp) {
                $.ajax("VerPedido", {
                    data: {'cp': cp},
                    dataType: 'json',
                    cache: false,
                    success: insertaPedido,
                    error: function (xhr, status, ex) {
                        alert("Error (" + xhr.status + "):" + status);
                    }
                });
            }
            function insertaPedido(pedido) {
                var tr = document.createElement("tr");
                var td = document.createElement("td");
                td.setAttribute("colspan", "6");
                td.setAttribute("align", "center");
                var tabla = hazHTMLPedido(pedido);
                tabla += "</table>";
                td.innerHTML = tabla;
                tr.appendChild(td);
                var fila = document.getElementById(pedido.codigo).parentNode.parentNode;
                insertaDespues(fila.parentNode, fila, tr);
            }
            function insertaDespues(padre, nodo, nuevoNodo) {
                var siguiente = nodo.nextSibling;
                if (siguiente == null) {
                    padre.appendChild(nuevoNodo);
                } else {
                    padre.insertBefore(nuevoNodo, siguiente);
                }
            }
            var hazHTMLPedido = function (pedido) {
                var html = '<table width="95%"> \
          <colgroup> \
            <col width="9%"><col width="53%"><col width="10%"> \
            <col width="10%"><col width="6%"><col width="6%"> \
          </colgroup> \
          <tr style="text-align: left"><td colspan="6">Detalle del pedido ';
                html += pedido.codigo;
                html += '</td></tr>  \
          <tr style="text-align: left"> \
            <td>Cantidad</td><td>Art&iacute;culo</td><td>P.V.P.</td> \
            <td>Su precio</td><td>F. E. des.</td><td>F. E. prev.</td> \
          </tr>'

                html = pedido.lineas.reduce(function (html, l, i) {
                    html += '      <tr ' + (i % 2 == 0 ? 'class="par"' : '') + '> \
            <td style="text-align:center">';
                    html += l.cantidad;
                    html += '</td><td style="text-align: left">';
                    html += l.articulo.codigo + ' / ' + l.articulo.nombre
                    html += '</td><td style="text-align: right">' + l.articulo.pvp + ' &euro;</td>'
                    html += '<td style="text-align: right">' + l.precioReal + ' &euro;</td>'
                    html += '<td>' + l.fechaEntregaDeseada.dayOfMonth + '/' + l.fechaEntregaDeseada.month + '/' + l.fechaEntregaDeseada.year + '</td>';
                    html += '<td>' + (!l.fechaEntregaPrevista ? 'S/D*' :
                            l.fechaEntregaPrevista.dayOfMonth + '/' + l.fechaEntregaPrevista.month + '/' + l.fechaEntregaPrevista.year) + '</td></tr>';
                    return html;
                }, html)

                return html;
            }
            function ocultarPedido(codigo) {
                var celda = document.getElementById(codigo).parentNode.parentNode.nextSibling;
                if (celda.hasChildNodes()) {
                    while (celda.childNodes.length >= 1) {
                        celda.removeChild(celda.firstChild);
                    }
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
                    Bienvenido ${cliente.getNombre()}
                </div>
                <div class="clear"></div>
                <div class="contenido">
                    <h1>Sus pedidos    </h1>
                    <a name="inicio"></a>
                    <p>Estos son sus pedidos. </p>
                    <c:if test="${pedidoRealizacion!= null}">
                        Actualmente, dispone de un <a href="PedidoRealizacion">pedido en realización</a>
                    </c:if>
                    <p>&nbsp;<span class="atajo"><a href="#comp">Completados</a> &nbsp; | &nbsp; <a href="#anul">Anulados</a></span></p>

                    <table width="95%">
                        <colgroup>
                            <col width="5%">
                            <col width="5%">
                            <col width="14%">
                            <col width="14%">
                            <col width="51%">
                            <col width="11%">
                        </colgroup>
                        <tr>
                            <td colspan="6">Listado de pedidos pendientes </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo </td>
                            <td>Fecha </td>
                            <td>Direcci&oacute;n de entrega </td>
                            <td>Importe </td>
                        </tr> 

                        <c:forEach var="p" items="${pendientes}" varStatus = "i">
                            <c:if test="${i.count%2!=0}">
                                <tr class="par">
                                </c:if>
                                <c:if test="${i.count%2==0}">
                                <tr>
                                </c:if>
                                <td style="text-align: center"><a href="GeneraPDF?cp=${p.getCodigo()}"><img src="../img/pdf.gif" title="Descargar en PDF"/></a></td>

                                <td style="text-align: center"><img src="../img/cancel.png" title="Cancelar el pedido"/></td>
                                <td> <a title="Desplegar detalle pedido" class="desplegar" href="#" id="${p.getCodigo()}">[+] </a><a href="VerPedido?cp=${p.getCodigo()}" > ${p.getCodigo()}</a></td>
                                <td><fmt:formatDate value="${p.fechaCierre.time}"/></td>
                                <td>${p.getDirEntrega()}</td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${p.importe}"/></td>    
                            </tr>
                        </c:forEach>
                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>

                    <p>&nbsp;</p>          
                    <a name="comp"></a>
                    <table width="95%">
                        <colgroup>
                            <col width="5%">
                            <col width="14%">
                            <col width="14%">
                            <col width="56%">
                            <col width="11%">
                        </colgroup>
                        <tr>
                            <td colspan="5">Listado de pedidos Completados</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo </td>
                            <td>Fecha </td>
                            <td>Direcci&oacute;n de entrega </td>
                            <td>Importe </td>
                        </tr>
                        <c:forEach var="p" items="${completados}" varStatus="i">
                            <c:if test="${i.count%2!=0}">
                                <tr class="par">
                                </c:if>
                                <c:if test="${i.count%2==0}">
                                <tr>
                                </c:if>             
                                <td style="text-align: center"><a href="GeneraPDF?cp=${p.getCodigo()}"><img src="../img/pdf.gif" title="Descargar en PDF"/></a></td>
                                <td><a title="Desplegar detalle pedido" class="desplegar" href="#" id="${p.getCodigo()}">[+] </a><a href="VerPedido?cp=${p.getCodigo()}"> ${p.getCodigo()}</a></td>
                                <td><fmt:formatDate value="${p.fechaCierre.time}"/></td>
                                <td>${p.getDirEntrega()}</td>
                                <td style="text-align: right"><fmt:formatNumber type="currency" value="${p.importe}"/> </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>

                    <p>&nbsp;</p>
                    <a name="anul"></a>
                    <table width="55%">
                        <colgroup>
                            <col width="10%">
                            <col width="26%">
                            <col width="32%">
                            <col width="32%">
                        </colgroup>
                        <tr>
                            <td colspan="4">Listado de pedidos anulados </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>C&oacute;digo</td>
                            <td>Fecha cierre</td>
                            <td>Fecha anulación</td>
                        </tr> 
                        <c:forEach var="p" items="${anulados}" varStatus="i">
                            <c:if test="${i.count%2!=0}">
                                <tr class="par">
                                </c:if>
                                <c:if test="${i.count%2==0}">
                                <tr>
                                </c:if>
                                <td style="text-align: center"><a href="GeneraPDF?cp=${p.getCodigo()}"><img src="../img/pdf.gif" title="Descargar en PDF"/></a></td>
                                <td><a href="VerPedidoAnulado?cp=${p.getCodigo()}">${p.getCodigo()}</a></td>
                                <td><fmt:formatDate value="${p.fechaCierre.time}"/></td>
                                <td><fmt:formatDate value="${p.fechaAnulacion.time}"/></td>
                            </tr>
                        </c:forEach>
                    </table>

                    <span class="atajo"><a href="#inicio">Inicio</a></span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="separa"></div>

            <%@include file="pieC.html" %>

        </div>
    </body>
</html>
