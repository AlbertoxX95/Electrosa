/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import paw.bd.GestorBD;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author alruizj
 */
@WebServlet(name = "NuevoArticulo", urlPatterns = {"/admin/NuevoArticulo"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1,maxFileSize = 1024 * 1024 * 1,maxRequestSize = 1024 * 1024 * 1 + 10 * 1024)
public class NuevoArticulo extends HttpServlet {
    private static GestorBD gbd = new GestorBD();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("nuevoArticulo.jsp");
        rd.forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       RequestDispatcher rd = null;
       try {
            Articulo a = (Articulo) UtilesServlet.populateBean(Articulo.class, request);
            Part imagen = request.getPart("fichFoto");
            List<String> errores = valida(a, imagen);
            if (errores.isEmpty()) {
                String cd = imagen.getHeader("content-disposition");
                String fName = cd.substring(cd.indexOf("filename=") + 10, cd.lastIndexOf("\""));
                a.setFoto(a.getTipo() + "/" + fName);
                ServletContext sc = request.getServletContext();
                a.setCodigo(null);
                Articulo articuloBD = gbd.insertaArticulo(a);
                try{
                imagen.write(sc.getRealPath("/img/fotosElectr/"+a.getFoto()));
                }catch(IOException ex){
                    gbd.borraArticulo(articuloBD.getCodigo(), true);
                }
                
                rd = request.getRequestDispatcher("listadoArticulos.jsp");
                rd.forward(request, response);
                return;      
            }else{
                request.setAttribute("errores", errores);
                request.setAttribute("articulo", a);
                request.setAttribute("fichFoto", imagen);
                rd = request.getRequestDispatcher("nuevoArticulo.jsp");
                rd.forward(request, response);
                return;
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(NuevoArticulo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private List<String> valida(Articulo a, Part imagen){
        List<String> errores = new ArrayList<String>();
        if(UtilesString.isVacia(a.getNombre())){
            errores.add("El campo del nombre no puede ser nulo");
            a.setNombre(null);
        }
        if(UtilesString.isVacia(a.getTipo())){
            errores.add("El campo del tipo no puede ser nulo");
            a.setTipo(null);
        }
        if(UtilesString.isVacia(a.getFabricante())){
            errores.add("El campo fabrincante  no puede ser nulo");
        }
        if(a.getPvp()<=0){
            errores.add("El precio debe ser mayor que cero");
            a.setPvp(-1);
        }
        String cab = imagen.getContentType();
        String ext = cab.substring(0, cab.indexOf("/"));
        if(ext.compareToIgnoreCase("image")!=0){
            errores.add("La extension de el archivo no pertenece a una imagen");
            imagen =  null;
        }
        return errores;
    }
}
