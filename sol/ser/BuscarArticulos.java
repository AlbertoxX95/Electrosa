/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.CriteriosArticulo;
import paw.bd.GestorBD;
import paw.bd.Paginador;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;


/**
 *
 * @author alruizj
 */
public class BuscarArticulos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static int tamanioPagina = 15;
    GestorBD gbd = new GestorBD();
    public void init() throws ServletException {
        try {
             tamanioPagina = Integer.parseInt(this.getInitParameter("tamanioPagina"));
        } catch (Exception ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.WARNING,null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            String tipo = request.getParameter("tipo");
            String fabricante = request.getParameter("fabricante");
            String precio = request.getParameter("precio");
            String nombre = request.getParameter("nombre");
            String codigo = request.getParameter("codigo");
            
           try{ 
            if(!UtilesString.isVacia(tipo) && !UtilesString.isVacia(fabricante) && !UtilesString.isVacia(precio) || !UtilesString.isVacia(codigo) || !UtilesString.isVacia(nombre)){
                CriteriosArticulo cri = new CriteriosArticulo();
                cri.setTipo(tipo);
                cri.setFabricante(fabricante);
                cri.setPrecio(precio);
                cri.setNombre(nombre);
                cri.setCodigo(codigo);
              
                Paginador paginador = gbd.getPaginadorArticulos(cri, tamanioPagina);
                
                String pag = request.getParameter("p");
                int p = 1;
                try {
                    p = Integer.parseInt(pag);
                } catch (NumberFormatException e) { 
                }
                if(paginador.getNumPaginas() > 0 && (p < 1 || p > paginador.getNumPaginas())){
                    if(p < 1){
                        p = 1;
                    }else{
                        p = paginador.getNumPaginas();
                    }
                    String url = "BuscarArticulos?p="+p+"&tipo="+(tipo != null ? tipo:"")+"&fabricante="+(fabricante != null ? fabricante:"")+"&precio="+(precio != null ? precio:"")+"&nombre="+(nombre != null ? nombre:"")+"&codigo="+(codigo != null ? codigo:"");
                    response.sendRedirect(url);
                    return;
                }
                List<Articulo> articulos = new ArrayList<Articulo>();
                articulos = gbd.getArticulos(cri,p,tamanioPagina);
                request.setAttribute("articulos", articulos);
                request.setAttribute("paginador", paginador);
                request.setAttribute("tipo", tipo);
                request.setAttribute("fabricante", fabricante);
                request.setAttribute("precio", precio);
                request.setAttribute("pag", p);
                request.setAttribute("nombre", nombre);
                request.setAttribute("codigo", codigo);
                if(articulos.size() == 1){
                    response.sendRedirect("fichaArticulo.jsp?cart="+articulos.get(0).getCodigo());
                    return;
                    
                }
        }else{
              Paginador paginador = gbd.getPaginadorArticulos(tamanioPagina);
                
                String pag = request.getParameter("p");
                int p = 1;
                try {
                    p = Integer.parseInt(pag);
                } catch (NumberFormatException e) { 
                }
                if(paginador.getNumPaginas() > 0 && (p < 1 || p > paginador.getNumPaginas())){
                    if(p < 1){
                        p = 1;
                    }else{
                        p = paginador.getNumPaginas();
                    }
                    String url = "BuscarArticulos?p="+p+"&tipo="+(tipo != null ? tipo:"")+"&fabricante="+(fabricante != null ? fabricante:"")+"&precio="+(precio != null ? precio:"");
                    response.sendRedirect(url);
                    return;
                }
                request.setAttribute("articulos", gbd.getArticulos(p,tamanioPagina));
                request.setAttribute("paginador", paginador);
                request.setAttribute("tipo", tipo);
                request.setAttribute("fabricante", fabricante);
                request.setAttribute("precio", precio);
                request.setAttribute("pag", p);  
            }
            request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
            request.setAttribute("fabricantes", gbd.getFabricantes());
            RequestDispatcher rd = request.getRequestDispatcher("/catalogo.jsp");
            rd.forward(request, response);
            
           }catch(ExcepcionDeAplicacion ex){
               Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE,null,ex);
               request.setAttribute("link", "index.html");
               throw new ServletException(ex);
           }
                
        
    
    
    

    }
}
