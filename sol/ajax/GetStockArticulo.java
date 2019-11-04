/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;

/**
 *
 * @author alruizj
 */
@WebServlet(name = "GetStockArticulo", urlPatterns = {"/api/GetStockArticulo"})
public class GetStockArticulo extends HttpServlet {

    private static GestorBD gbd = new GestorBD();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        String codigo = request.getParameter("cart");
        int stock = 0;
        try {
            stock = gbd.getStockArticulo(codigo);
            response.getWriter().print(stock);
        
        } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(GetStockArticulo.class.getName()).log(Level.SEVERE, null, ex);
            }


    }
}

