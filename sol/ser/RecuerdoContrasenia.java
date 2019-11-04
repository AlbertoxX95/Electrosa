/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.util.mail.DatosCorreo;
import paw.util.mail.GestorCorreo;
import paw.util.mail.conf.ConfiguracionCorreo;

/**
 *
 * @author alruizj
 */
public class RecuerdoContrasenia extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String usuario = request.getParameter("usr");
      String uuid = UUID.randomUUID().toString();
        try {
            Cliente c = gbd.getClienteByUserName(usuario);
            if(c != null){
                String servidor = request.getServerName();
                int puerto = request.getLocalPort();
                String contexto = request.getContextPath();
                String destino = gbd.getClienteByUserName(usuario).getEmail();
                String contenido = "Cambio de contraseña en electrosa.com"+"/n"+"Usa el siguiente enlace para cambiar la contraseña:"+"\n"+"http://"+servidor+":"+puerto+contexto+"/CambioContrasenia?cc="+uuid;
                DatosCorreo datoscorreo = new DatosCorreo("alruizj@unirioja.es", c.getEmail(), "Recuperación de contraseña", contenido);
                datoscorreo.setCharset("UTF-8");
                datoscorreo.setMimeType("text/plain;charset=UTF-8");
                GestorCorreo.envia(datoscorreo, ConfiguracionCorreo.getDefault());
                gbd.insertaRecuerdoContrasenia(usuario, uuid);
                response.sendRedirect("avisoEmail.html");
                return;
                
            }else{
                response.sendRedirect("avisoEmail.html");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(RecuerdoContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }   catch (MessagingException ex) {
                    Logger.getLogger(RecuerdoContrasenia.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServletException(ex);
                }
      
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
