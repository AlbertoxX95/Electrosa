<%-- 
    Document   : nuevoArticulo
    Created on : May 2, 2018, 11:58:23 AM
    Author     : alruizj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Nuevo art&iacute;culo</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">
    <script src="../js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/formulario.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/estilomenu.css" rel="stylesheet" media="all" type="text/css" />
    <script src="../js/ponFoco.js" type="text/javascript"></script>
  </head>

  <body >
    <div class="logo"><a href="../index.html"><img src="../img/LogoElectrosa200.png" border="0"></a></div>


    <div class="sombra">
      <div class="nucleo">
        <div id="lang">
          <a href="index.html">Español</a> &nbsp; | &nbsp; <a href="index.html">English</a>
        </div>
      </div>
    </div>  

    <div class="barra_menus">
      <div class="pestanias">
        <div class="grupoPestanias">
          <div class="pestaniaNoSel"><a href="../index.html">Para usuarios</a></div>
          <div class="pestaniaSel">Intranet</div>
        </div>
      </div>

      <div id="cssmenu">
        <ul>
          <li class='has-sub'><a href="index.html">Art&iacute;culos</a>
            <ul>
              <li><a href="listadoArticulos.jsp">Listar</a></li>
              <li><a href="NuevoArticulo">A&ntilde;adir</a> </li>
              <li><a href="HazEstadistica">Estadísticas</a> </li>
            </ul>
          </li>
          <li class='has-sub'><a href="index.html">Clientes</a>	
            <ul>
              <li><a href="index.html">Listar</a> </li>
              <li><a href="index.html">A&ntilde;adir</a> </li>
            </ul>    
          </li>
          <li><a href="index.html">Pedidos</a></li>
          <li class='last'><a href="index.html">Stocks</a></li>
        </ul>
        <div style="clear: left;"></div>
      </div>
    </div> 

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="index.html" title="Inicio" >Inicio</a>
        </div>

        <div class="clear"></div>

        <div class="contenido">

          <h1>A&ntilde;adir un art&iacute;culo    </h1>
          <p>Utilice el siguiente formulario.   </p>

       
            <c:if test="${errores!= null && !errores.isEmpty()}">
                        <div class="alerta">
                            <c:forEach var="e" items="${errores}">
                                ${e}<br>
                            </c:forEach >
                        </div> 
                    </c:if>
           

          <form id="fArtic" name="fArtic" action="NuevoArticulo" method="post" enctype="multipart/form-data">
            <fieldset> 
              <legend>Datos del art&iacute;culo </legend> 
              <div class="field">
                <label for="nombre">Nombre:
                  <span class='Requerido'>${articulo.getNombre() != null ? "" : "Requerido"}</span>
                </label>
                <input type="text" name="nombre" id="nombre" size="55" value="${articulo.getNombre()}" required>
              </div>
              <div class="field">
                <label for="pvp">P.V.P:
                  <span class='Requerido'>${articulo.getPvp() != null ? "" : "Requerido"}</span>
                </label>
                <input type="text" name="pvp" id="pvp" size="15" value="${articulo.getPvp()}" required>
              </div>
              <div>
                <div class="left">
                  <div class="field">
                    <label for="tipo">Tipo:
                      <span class='Requerido'>${articulo.getTipo() != null ? "" : "Requerido"}</span>
                    </label>
                    <select name="tipo" id="tipo" required>
                      <option value="">- Elige -</option>                      
                      <option value="Aspirador" ${articulo.getTipo() == "Aspirador" ? "Selected" : ""}>Aspirador</option>                      
                      <option value="Campana" ${articulo.getTipo() == "Campana" ? "Selected" : ""}>Campana</option>                      
                      <option value="Cocina" ${articulo.getTipo() == "Cocina" ? "Selected" : ""}>Cocina</option>                      
                      <option value="Frigorifico" ${articulo.getTipo() == "Frigorifico" ? "Selected" : ""}>Frigorifico</option>                      
                      <option value="Horno" ${articulo.getTipo() == "Horno" ? "Selected" : ""}>Horno</option>                      
                      <option value="Lavadora" ${articulo.getTipo() == "Lavadora" ? "Selected" : ""}>Lavadora</option>                      
                      <option value="Lavavajillas" ${articulo.getTipo() == "Lavavajillas" ? "Selected" : ""}>Lavavajillas</option>                      
                      <option value="Microondas" ${articulo.getTipo() == "Microondas" ? "Selected" : ""}>Microondas</option>                      
                      <option value="Placa" ${articulo.getTipo() == "Placa" ? "Selected" : ""}>Placa</option>                      
                    </select>       
                    <input id="otroTip" type="checkbox" name="" value="" title="Introduce otro tipo" disabled/>               
                    Otro 
                    <div id="otrotipoCont"><!--<label>&nbsp;</label><input class="text" type="text" name="tipo" id="tipo" value="" >--></div>
                  </div>
                </div>
                <div class="right">
                  <div class="field">
                    <label for="fabricante">Fabricante:
                      <span class='Requerido'>${articulo.getFabricante() != null ? "" : "Requerido"}</span>
                    </label>
                    <select name="fabricante" id="fabricante" required>
                      <option value="">- Elige -</option>                       
                      <option value="Edesa" ${articulo.getFabricante() == "Edesa" ? "Selected" : ""}>Edesa</option>                      
                      <option value="Fagor" ${articulo.getFabricante() == "Fagor" ? "Selected" : ""}>Fagor</option>                      
                      <option value="Miele" ${articulo.getFabricante() == "Miele" ? "Selected" : ""}>Miele</option>                      
                    </select>
                    <input id="otroFab" type="checkbox" name="" value=""  title="Introduce otro fabricante" disabled/>
                    Otro 
                    <div id="otrofabricanteCont"><!--<label>&nbsp;</label><input class="text" type="text" name="fabricante" id="fabricante" value="" >--></div>
                  </div>
                </div>		
              </div>

              <div class="field">
                <label for="descripcion">
                  Descripci&oacute;n:
                </label>
                <textarea name="descripcion" id="descripcion" cols="70" rows="3"></textarea>
              </div>

              <div class="field">
                <label for="fichFoto">
                  Fichero fotograf&iacute;a:
                </label>
                <input type="file" name="fichFoto" id="fichFoto">
              </div>


            </fieldset>


            <fieldset class="submit"> 
              <div class="right">
                <div class="field">
                  <input class="submitb" type="submit"  value="Enviar los datos" >  
                </div>
              </div>
            </fieldset>  
          </form>

        </div>
      </div>

      <div class="separa"></div>

      <div class="pie">
        <span class="pie_izda">
          <a href="mailto:francisco.garcia@unirioja.es">Contacto</a> &nbsp; | &nbsp; <a href="../mapa.html">Mapa</a> </span>
        <span class="pie_dcha">
          &copy; 2012 Francisco J. García Izquierdo  </span>
        <div class="clear"></div>  
      </div>

    </div>
  </body>
</html>
