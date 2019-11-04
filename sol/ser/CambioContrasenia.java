/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.ExcepcionDeAplicacion;

/**
 *
 * @author alruizj
 */
public class CambioContrasenia extends HttpServlet {
    static GestorBD gbd = new GestorBD();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = null;
        try {
            String codigo = request.getParameter("cc");
            if (codigo != null && codigo.trim().length() > 0){
                String nomUsuario = gbd.getUserNameDeRecuerdo(codigo);
                if(nomUsuario != null && nomUsuario.trim().length() > 0){
                    rd = request.getRequestDispatcher("cambioContrasenia.html");
                    rd.forward(request, response);
                    return;
                }
            }
           response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Petición de cambio de contraseña inválida. Es posible que el código  de cambio haya expirado. Vuelva a solicitar el cambio.");
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String pwd = request.getParameter("pwd");
            String rpwd = request.getParameter("rpwd");
            String url = request.getHeader("Referer");
            String codigo = url.substring(url.lastIndexOf("=")+1);
            String usuario = gbd.getUserNameDeRecuerdo(codigo);
            request.setCharacterEncoding("UTF-8");
            if(pwd != null && rpwd != null){
                if(pwd.length() == rpwd.length() && pwd.length() > 0){
                    gbd.cambiaContrasenia(usuario, pwd);
                    response.sendRedirect("clientes/AreaCliente");
                    return;
                }
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Petición de cambio de contraseña erronea");
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
      
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   

}
