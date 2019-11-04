<%-- 
    Document   : error.jsp
    Created on : Mar 21, 2018, 12:19:11 PM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Error de aplicación</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">
    <link href="<%= request.getContextPath() %>/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
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
          <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>

        <div class="contenido">
		  <div style="float:left"><img src="<%= request.getContextPath() %>/img/alerta.png"></div>
		  <div class="error">
		    <div>${empty requestScope['javax.servlet.error.message'] ? 'Error de aplicación' :
                            requestScope['javax.servlet.error.message']}
                    </div>
		    <div class="errorb"><a href="${link != null ? link : "index.html"}">Salir de aqui</a></div>
		  </div>
		  <div class="clear"></div>
          
        </div>
      </div>

      <div class="separa"></div>

      <%@include file="pie.html" %>

    </div>
  </body>
</html>
