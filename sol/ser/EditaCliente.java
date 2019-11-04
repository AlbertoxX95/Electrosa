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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.Direccion;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.Validacion;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Alberto
 */
@WebServlet(name = "EditaCliente", urlPatterns = {"/clientes/EditaCliente"})
public class EditaCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/clientes/cambioDatosPersonales.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       RequestDispatcher rd = null;
       GestorBD gbd = new GestorBD();
       request.setCharacterEncoding("UTF-8");
       try {
       Cliente c = (Cliente) request.getSession().getAttribute("cliente");
       String nombre = request.getParameter("nombre");
       String cif = request.getParameter("cif");
       String calle = request.getParameter("calle");
       String ciudad = request.getParameter("ciudad");
       String cp = request.getParameter("cp");
       String provincia = request.getParameter("provincia");
       String tfno = request.getParameter("tfno");
       String email = request.getParameter("email");
       c.setNombre(nombre);
       c.setCif(cif);
       c.setEmail(email);
       c.setTfno(tfno);
       Direccion dir = (Direccion) UtilesServlet.populateBean("paw.model.Direccion", request);
       dir.setCalle(calle);
       dir.setCiudad(ciudad);
       dir.setCp(cp);
       dir.setProvincia(provincia);
       c.setDireccion(dir);
       List<String> errores = valida(c, request.getParameter("privacidad") != null ? 1 : 0, request, gbd);
       if(errores.isEmpty()){
           boolean editado = gbd.editaCliente(c);
           if(editado){
           response.sendRedirect("AreaCliente");
           return;
           }else{
               System.out.println("no edita");
           }
           
       }else{
           request.setAttribute("nombre", c.getNombre());
           request.setAttribute("cif1", c.getCif());
           request.setAttribute("email", c.getEmail());
           request.setAttribute("tfno1", c.getTfno());
           request.setAttribute("dir", c.getDireccion().getCalle());
           request.setAttribute("ciudad1", c.getDireccion().getCiudad());
           request.setAttribute("provincia", c.getDireccion().getProvincia());
           request.setAttribute("cp", c.getDireccion().getCp());
           request.setAttribute("errores", errores);
           rd = request.getRequestDispatcher("cambioDatosPersonales.jsp");
           rd.forward(request, response);
           return;
       }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(EditaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
private List<String> valida(Cliente cli,
          int privacidadOK,
          HttpServletRequest request,
          GestorBD gbd) throws ExcepcionDeAplicacion, IOException {
    List<String> errores = new ArrayList<String>();
    if (UtilesString.isVacia(cli.getNombre())
            || UtilesString.isVacia(cli.getCif())
            || UtilesString.isVacia(cli.getDireccion().getCalle())
            || UtilesString.isVacia(cli.getDireccion().getCiudad())
            || UtilesString.isVacia(cli.getDireccion().getProvincia())
            || UtilesString.isVacia(cli.getDireccion().getCp())
            || UtilesString.isVacia(cli.getEmail())) {
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
       
    if (cli.getDireccion().getCalle() != null && cli.getDireccion().getCalle().length() > 50) {
      errores.add("La longitud mÃ¡xima de la calle son 50 caracteres");      
    }
    
    if (cli.getDireccion().getCiudad() != null && cli.getDireccion().getCiudad().length() > 20) {
      errores.add("La longitud mÃ¡xima de la ciudad son 20 caracteres");      
    }

    if (privacidadOK != 1) {
      errores.add("Debes leer la clÃ¡usula de privacidad y marcar la casilla correspondiente");
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
    
    return errores;
  }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */

}
