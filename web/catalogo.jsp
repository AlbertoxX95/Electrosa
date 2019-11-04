<%-- 
    Document   : catalogo
    Created on : Mar 15, 2018, 10:25:55 AM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<fmt:setBundle basename="electrosaMsg"/>
<html>
    <script>
                    
                    window.onload = function(){
                    var enlace = document.getElementById("enlaceBuscador");
                    var formulario = document.getElementById("filtroCatalogo");
                    var span = document.getElementById("spanBuscador");
                    enlace.onclick = function(){
                        formulario.removeChild(span);
                        formulario.innerHTML = '<input name="nombre" id="nombre" value="" type="text" placeholder=\'Nombre\'>    <input name="codigo" id="codigo" value="" type="text" placeholder=\'Código\'>    <input name="buscar" id="buscar" type="image" title="Buscar" src="img/search25.png" alt="Buscar">';
                        }
                        buscarPartes();
                        }  
                </script>
                <script>
                        function buscarPartes(){
                            
                            tipo.onchange = function(){
                                document.getElementById("filtroCatalogo").submit();
                            }
                            fabricante.onchange = function(){
                                document.getElementById("filtroCatalogo").submit();
                            }
                            precio.onchange = function(){
                                document.getElementById("filtroCatalogo").submit();
                            }
                        }
                </script>
                
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> cat&aacute;logo</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="css/catalogoMosaico.css" rel="stylesheet" media="all" type="text/css">
    <!--    <link href="css/catalogoLista.css" rel="stylesheet" media="all" type="text/css"> -->
  </head>

  <body >
    <c:if test="${cliente != null}">
          <%@include file="clientes/cabeceraC.html" %>
      </c:if>
      <c:if test="${cliente == null}">
          <%@include file="cabecera.html" %>
      </c:if>
    <div class="sombra">
      <div class="nucleo">

        <div id="migas">
          <a href="index.html" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
          <a href="BuscarArticulos" title="Hojear catalogo">Hojear catalogo</a>	<!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>
        <div class="contenido">
          <h1>Nuestros productos</h1>
          <p>Puede buscar los productos que necesite en nuestro cat&aacute;logo. Lo hemos organizado por marcas, tipo de electrodom&eacute;stico y rango de precios. Lo precios indicados en rojo corresponden a ofertas. </p>
          <div class="filtroCatalogo">
            <form name="filtroCatalogo" id="filtroCatalogo" action="BuscarArticulos">
                <span id="spanBuscador">
              <label for="tipo">Tipo: </label>
              <select name="tipo" id="tipo">
              <option value="-1">- Cualquiera -</option>
              <c:forEach var="t" items="${tiposArticulos}">
                <option value="${t}" ${(t == tipo ? 'selected' : '')} >${t}</option>
              </c:forEach>
              </select>
              <label for="fabricante">Fabricante: </label>
              <select name="fabricante" id="fabricante">
              <option value="-1">- Cualquiera -</option>
              <c:forEach var="f" items="${fabricantes}">
                <option value="${f}" ${(f == fabricante ? 'selected' : '')} >${f}</option>
              </c:forEach>                 
              </select>

              <label for="precio">Precio: </label>
              <select name="precio" id="precio">
                <option value="-1">- Cualquiera -</option>
                <option value="10-50">10-50 &euro;</option>
                <option value="50-100">50-100 &euro;</option>
                <option value="100-200">100-200 &euro;</option>
                <option value="200-500">200-500 &euro;</option>
                <option value="500-1000">500-1000 &euro;</option>
                <option value="1000">Mas de 1000 &euro;</option>
              </select>
              <input name="buscar" id="buscar" type="image" title="Buscar" src="img/search25.png" alt="Buscar">
              <p>
              <a href="#" id="enlaceBuscador">Búsqueda por nombre o código</a></p>
              </span>
                
                
             

            </form>

            <div class="modovisual">
              <a href="catalogo.html">Mosaico</a> &nbsp; | &nbsp; <a href="catalogo.html">Lista</a>
            </div>
            <div class="clear"></div>
          </div>
          
          <c:if test="${not empty paginador}">
          <div class="resumResul redondeo">
            Encontrados ${paginador.numRegistros} artículos.
            <c:if test="${paginador.numRegistros>0}">Mostrando página ${pag} de ${paginador.numPaginas}.
            <span class="paginador">                
                <c:if test="${pag > 1}"><a href="BuscarArticulos?p=${paginador.anterior(pag)}&tipo=${tipo}&fabricante=${fabricante}&precio=${precio}&nombre=${nombre}&codigo=${codigo}">Anterior</a></c:if>
                <c:forEach var="ady" items="${paginador.adyacentes(pag)}">
                    <c:if test="${pag != ady}"><a href="BuscarArticulos?p=${ady}&tipo=${tipo}&fabricante=${fabricante}&precio=${precio}&nombre=${nombre}&codigo=${codigo}">${ady}</a></c:if>
                    <c:if test="${pag == ady}">${ady}</c:if>
                </c:forEach>
                <c:if test="${pag < paginador.numPaginas}"><a href="BuscarArticulos?p=${paginador.siguiente(pag)}&tipo=${tipo}&fabricante=${fabricante}&precio=${precio}&nombre=${nombre}&codigo=${codigo}">Siguiente</a></c:if>
            </span>
            </c:if>
            </c:if>
          </div>

          <c:if test="${paginador.numRegistros != null && paginador.numRegistros > 0}">
                    <ul class="resultBusqueda">
                        <c:forEach var="a" items="${articulos}" varStatus="c">
                            <li class="item redondeo">
                                <div class="foto">
                                    <a href="fichaArticulo.jsp?cart=${a.codigo}"><img src="img/fotosElectr/${a.foto}" alt="${a.codigo}" longdesc="${a.descripcion}" width="80"></a>
                                </div>
                                <div class="datos">
                                    <span>${a.nombre}</span>
                                    <div class="precio">
                                        <c:if test="${c.index %3==0}">
                                            <span class="oferta">${a.pvp} &euro;</span>
                                        </c:if>
                                        <c:if test="${c.index %3!=0}">
                                            ${a.pvp} &euro;
                                        </c:if>
                                    </div>
                                    <div class="carro">
                                        <a href="clientes/GestionaPedido?accion=Comprar&ca=${a.codigo}"><img src="img/shopcartadd_16x16.png" title="Añadir a mi carro de la compra"></a>
                                    </div>
                                </div>			  
                                <div class="codigo"><a href="fichaArticulo.jsp?cart=${a.codigo}">${a.codigo}</a></div>
                            </li>
                        </c:forEach> 			
                    </ul>
                </c:if>			
          <div class="clear"></div>

        </div>
      </div>

      <div class="separa"></div>

      <%@include file="pie.html" %> 

    </div>
  </body>
</html>
