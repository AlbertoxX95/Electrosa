<%-- 
    Document   : index
    Created on : Apr 25, 2018, 7:16:42 PM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"></fmt:setLocale>
</c:if>
<fmt:setBundle basename="electrosaMsg"/>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Bienvenido a Electrosa</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
  </head>

  <body>
    <jsp:include page="cabeceraINT.jsp"/> 
    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="index.jsp" title="Inicio" ><fmt:message key="lnk.home"/></a><!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>
        <div class="contenido">
          <h1><fmt:message key="home.tit"/> </h1>
          <p><fmt:message key="home.txt"/></p>
        </div>
        <div class="centro">
          <ul>
            <li class="columna">  
              <ul>  
                <li class="celda">
                  <div class="parteCelda">
                    <a href="generica.hml"><img src="img/motif_b2c_1_194.jpg" alt="" title=""></a>
                  </div>
                  <div class="parteCelda2">
                    <h3><a href="generica.html"><fmt:message key="home.secc.sat.tit"/></a></h3>
                    <fmt:message key="home.secc.sat.txt"/>
                    <div style="float:right; padding:10px 5px">
                      <a style="padding:5px 8px 5px 10px;background:#5C7E70;color:#FFF" href="generica.html" ><span>Sepa por qu&eacute; ...</span> </a>
                    </div>
                  </div>
                  <div class="clear"></div>
                </li> 
                <li class="celda">
                  <div class="parteCelda">
                    <a class="parteCelda" href="generica.html"><img src="img/imag_teaser_encuentre-solucion.jpg" alt="" title="" ></a>
                  </div>
                  <div class="parteCelda2">
                    <h3><a href="generica.html"><fmt:message key="home.secc.sol.tit"/></a></h3>
                    <fmt:message key="home.secc.sol.txt"/>
                    <div style="float:right; padding:10px 5px">
                      <a style="padding:5px 8px 5px 10px;background:#5C7E70;color:#FFF" href="generica.html" ><span>Comenzar ...</span> </a>
                    </div>
                  </div>
                  <div class="clear"></div>
                </li> 
              </ul>
            </li>

            <li class="columna"> 
              <ul>  
                <li class="celda">
                  <div class="parteCelda">
                    <a href="generica.html"><img src="img/registre-garantia.jpg" alt="" title=""></a>
                  </div>
                  <div class="parteCelda2">
                    <h3 ><a href="generica.html"><fmt:message key="home.secc.garant.tit"/></a></h3>
                    <fmt:message key="home.secc.garant.txt"/>
                    <div style="float:right; padding:10px 5px">
                      <a style="padding:5px 8px 5px 10px;background:#5C7E70;color:#FFF" href="generica.html" ><span>Registrar ...</span> </a>
                    </div>
                  </div>
                  <div class="clear"></div>
                </li> 
                <li class="celda">
                  <div class="parteCelda">
                    <a href="generica.html"><img src="img/atencion-cliente.jpg" alt="" title=""></a>
                  </div>
                  <div class="parteCelda2">
                    <h3><a href="generica.html"><fmt:message key="home.secc.atn.tit"/></a></h3>
                    <fmt:message key="home.secc.atn.txt"/><br><strong><fmt:message key="home.secc.atn.tfn"/></strong>
                    <div style="float:right; padding:10px 5px">
                      <a style="padding:5px 8px 5px 10px;background:#5C7E70;color:#FFF" href="generica.html" ><span>M&aacute;s ...</span> </a>
                    </div>
                  </div>
                  <div class="clear"></div>
                </li> 			
              </ul>
            </li>
          </ul>

        </div>
        <div class="clear"></div>
      </div>

      <div class="separa"></div>

      <jsp:include page="pie.html"/> 

    </div>
  </body>
</html>
