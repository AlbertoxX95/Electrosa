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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.Direccion;
import paw.model.ExcepcionDeAplicacion;
import paw.util.ReCaptchaException;
import paw.util.ReCaptchaValidator;
import paw.util.UtilesString;
import paw.util.Validacion;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author alruizj
 */
public class NuevoCliente extends HttpServlet {

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
        RequestDispatcher rd = request.getRequestDispatcher("/nuevoCliente.jsp");
            rd.forward(request, response);
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
       RequestDispatcher rd = null;
       GestorBD gbd = new GestorBD();
       request.setCharacterEncoding("UTF-8");
        try {
            Direccion dir = (Direccion) UtilesServlet.populateBean("paw.model.Direccion", request);
            Cliente c = (Cliente) UtilesServlet.populateBean("paw.model.Cliente", request);
            ReCaptchaValidator capt = new ReCaptchaValidator("6Lej-lgUAAAAAKew2QuCPGKf_4yTEbHI0W9UX6tn","6Lej-lgUAAAAAHge0MOjGO_0pIBZwN4fg8DHXCFy");
            c.setDireccion(dir);
             List<String> errores = valida(c, request.getParameter("login"), request.getParameter("pwd"), request.getParameter("rpwd"), request.getParameter("privacidad") != null ? 1 : 0, request, gbd, capt);
             if(errores.isEmpty()){
                 Cliente cli = gbd.insertaCliente(c, request.getParameter("login"),request.getParameter("pwd"));
                 response.sendRedirect("clientes/AreaCliente");
                 return;
             }else{
                
                 
                     request.setAttribute("nombre", c.getNombre());
                 
                 
                     request.setAttribute("cif1", c.getCif());
                 
                 
                     request.setAttribute("email", c.getEmail());
                 

                     request.setAttribute("tfno1", c.getTfno());
                 
                 
                     request.setAttribute("dir", c.getDireccion().getCalle());
                 
                
                     request.setAttribute("ciudad1", c.getDireccion().getCiudad());
                 
                 request.setAttribute("provincia", c.getDireccion().getProvincia());
                
                     request.setAttribute("cp", c.getDireccion().getCp());
                 
                
                     request.setAttribute("login1", request.getParameter("login"));
                
                 request.setAttribute("errores", errores);
                 rd = request.getRequestDispatcher("nuevoCliente.jsp");
                 rd.forward(request, response);
                 return;
                 
             }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }

    private List<String> valida(Cliente cli,
          String usr,
          String pwd,
          String rpwd,
          int privacidadOK,
          HttpServletRequest request,
          GestorBD gbd,
          ReCaptchaValidator capt) throws ExcepcionDeAplicacion, IOException {
    List<String> errores = new ArrayList<String>();

    if (UtilesString.isVacia(cli.getNombre())
            || UtilesString.isVacia(cli.getCif())
            || UtilesString.isVacia(cli.getDireccion().getCalle())
            || UtilesString.isVacia(cli.getDireccion().getCiudad())
            || UtilesString.isVacia(cli.getDireccion().getProvincia())
            || UtilesString.isVacia(cli.getDireccion().getCp())
            || UtilesString.isVacia(cli.getEmail())
            || UtilesString.isVacia(usr)
            || UtilesString.isVacia(pwd)
            || UtilesString.isVacia(rpwd)) {
      errores.add("Debes proporcionar valor para todos los campos requeridos");
    }
    
    if (cli.getNombre() != null && cli.getNombre().length() > 50) {
      errores.add("La longitud mÃ¡xima del nombre son 50 caracteres");      
    }
    
    if (cli.getCif() != null && cli.getCif().length() > 12) {
      errores.add("La longitud mÃ¡xima del CIF son 12 caracteres");      
    }
    
    if (cli.getTfno() != null && cli.getTfno().length() > 11) {
      errores.add("La longitud mÃ¡xima del telÃ©fono son 11 caracteres");      
    }
    
    if (cli.getEmail() != null && cli.getEmail().length() > 100) {
      errores.add("La longitud mÃ¡xima del email son 100 caracteres");      
    }
    
    if (usr != null && usr.length() > 50) {
      errores.add("La longitud mÃ¡xima del userName son 50 caracteres");      
    }
    
    if (cli.getDireccion().getCalle() != null && cli.getDireccion().getCalle().length() > 50) {
      errores.add("La longitud mÃ¡xima de la calle son 50 caracteres");      
    }
    
    if (cli.getDireccion().getCiudad() != null && cli.getDireccion().getCiudad().length() > 20) {
      errores.add("La longitud mÃ¡xima de la ciudad son 20 caracteres");      
    }

    if (privacidadOK != 1) {
      errores.add("Debes leer la clÃ¡usula de privacidad y marcar la casilla correspondiente");
    }

    if (!UtilesString.isVacia(pwd) && !UtilesString.isVacia(rpwd) && !pwd.equals(rpwd)) {
      errores.add("Las constraseÃ±as son diferentes");
    }

    if (!UtilesString.isVacia(usr) && !usr.trim().equals(usr)) {
      errores.add("El nombre de usuario tiene espacios en blanco por delante o detrÃ¡s");
    }

    if (!UtilesString.isVacia(usr) && gbd.getClienteByUserName(usr) != null) {
      errores.add("Ya existe un usuario en la BD con ese nombre de usuario");
    }

    if (!UtilesString.isVacia(cli.getCif()) && gbd.getClienteByCIF(cli.getCif()) != null) {
      errores.add("Ya existe un usuario en la BD con ese CIF");
      cli.setCif(null);
    }

    if (!UtilesString.isVacia(cli.getEmail()) && !Validacion.isEmailValido(cli.getEmail())) {
      errores.add("El email no parece una direcciÃ³n de correo vÃ¡lida");
      cli.setEmail(null);
    }

    if (!UtilesString.isVacia(cli.getDireccion().getCp()) && !Validacion.isCPValido(cli.getDireccion().getCp())) {
      errores.add("El CP no parece un cÃ³digo postal vÃ¡lido");
      cli.getDireccion().setCp(null);
    }
        try {
            capt.verifyResponse(request);
        } catch (ReCaptchaException ex) {
           errores.add("Debes verificar el captcha");
        }
    return errores;
  }

}
