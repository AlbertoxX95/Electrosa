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
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;

/**
 *
 * @author alruizj
 */
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("entrada.jsp");
            rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = null;
        request.setCharacterEncoding("UTF-8");
        try {
            String usuario = request.getParameter("usr");
            String password = request.getParameter("pwd");
            HttpSession sesion = request.getSession();
            
            GestorBD gbd = new GestorBD();
            
            if(UtilesString.isVacia(usuario) || UtilesString.isVacia(password)){
                request.setAttribute("aviso", "Usuario o contraseña no pueden ser nulos");
                rd = request.getRequestDispatcher("entrada.jsp");
                rd.forward(request, response);
            }
            if(gbd.comprobarLogin(usuario, password) == true){
                sesion.setAttribute("cliente", gbd.getClienteByUserName(usuario));         
                String returnURL = (String)sesion.getAttribute("returnURL");
                if(returnURL != null){
                    sesion.removeAttribute("returnURL");
                    response.sendRedirect(returnURL);
                }else{
                    response.sendRedirect("clientes/AreaClientes");
                }
            }else{
                sesion.invalidate();        
                request.setAttribute("aviso", "usuario o contraseña incorrecta");
                rd = request.getRequestDispatcher("entrada.jsp");
                rd.forward(request, response);
                return;
            }} catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }
       
       
       
    }


}
