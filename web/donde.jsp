<%-- 
    Document   : donde
    Created on : 05-may-2018, 19:56:05
    Author     : Alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="paw.model.Almacen"%>
<%@page import="java.util.List"%>
<%@page import="paw.bd.GestorBD"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Donde estamos</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDVy9cY5FmVFxok7-y2KqEDhGmJJTkkkPE"></script>
        <% GestorBD gbd = new GestorBD();
            List<Almacen> almacenes = gbd.getAlmacenes();
            Almacen central = gbd.getAlmacenDeCP("28001");
        %>
        <script type="text/javascript">
            var map;
            function inicializa() {
                var centro = new google.maps.LatLng(40.307682, -3.729762);
                var myOptions = {
                    zoom: 6,
                    center: centro,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);
                // Añade un marcador al mapa
               <% for (int i = 0; i < almacenes.size(); i++) {%>
            new google.maps.Marker({
                    position: new google.maps.LatLng(<%= almacenes.get(i).getCoordX()%>, <%= almacenes.get(i).getCoordY()%>),
                    map: map,
                    title: "Zona <%= almacenes.get(i).getZona()%>"
                });
            <%}%>
                // Puedes añadir más marcadores de la misma manera
            }
            google.maps.event.addDomListener(window, 'load', inicializa);
        </script>
        <script language="javascript">
            function obtener_localizacion() {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(muestraPosicion);
                } else {
                    alert('Su navegador no soporta la API de geolocalizacion');
                }
            }
            function muestraPosicion(posicion) {
                var latitud = posicion.coords.latitude;

                var longitud = posicion.coords.longitude;

                var miPos = new google.maps.Marker({
                    position: new google.maps.LatLng(posicion.coords.latitude, posicion.coords.longitude),
                    map: map,
                    icon: "img/home.png",
                    animation: google.maps.Animation.DROP,
                    title: "Su situación"
                });
            }
        </script>
    </head>

    <body >
        <%@include file="cabecera.html" %>
        
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.jsp" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>
                <div class="contenido">
                    <h1>Donde estamos</h1>
                    <div class="centro">
                        <h2>Oficinas centrales </h2>
                        <p><strong>Electrosa S.L. </strong><br>
                            C/ Luis de Ulloa, s.n. 
                            26004 - Logro&ntilde;o <br>
                            La Rioja - Spain <br><br>
                            Tfno: (+34) 941 000 000 - FAX:  (+34) 941 000 001</p>
                        <h2>Almacenes de zona </h2>
                        <p>Para  facilitar la gesti&oacute;n ELECTROSA considera la superficie nacional dividida en  cinco zonas: centro, norte, sur, este y oeste. En cada zona dispone de un  almac&eacute;n (almac&eacute;n de referencia de la zona). Puede visitarlos en las siguientes direcciones:</p>
                        <% for (int i = 0; i < almacenes.size(); i++) {%>
                        <div class="dirAlmacen"><strong>Zona <%= almacenes.get(i).getZona()%> </strong><br>
                            <%= almacenes.get(i).getDireccion().getCalle()%>, s.n.  <br>
                            <%= almacenes.get(i).getDireccion().getCp()%> - <%= almacenes.get(i).getDireccion().getCiudad()%> <br>
                            <%= almacenes.get(i).getDireccion().getProvincia()%> Spain <br>
                        </div>
                        <%}%>      
                        <div class="clear"></div>
                        <p class="centro"></p>
                        <button onclick="obtener_localizacion()">Dime dónde estoy</button>
                        <p></p>
                    </div>
                        <div id="map_canvas" style="margin: auto;width: 512px;height: 512px;"></div>
                </div>
            </div>

            <div class="separa"></div>

            <%@include file="pie.html" %>

        </div>
    </body>
</html>
