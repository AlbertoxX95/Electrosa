/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.LineaEnRealizacion;
import paw.model.PedidoEnRealizacion;
import paw.util.UtilesString;
import paw.util.servlet.ParameterParser;

/**
 *
 * @author alruizj
 */
public class GestionaPedido extends HttpServlet {

  private static GestorBDPedidos gbp = new GestorBDPedidos();
  private static GestorBD gbd = new GestorBD();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession sesion = request.getSession();
       RequestDispatcher rd = null;
       try {
       PedidoEnRealizacion pr = (PedidoEnRealizacion) sesion.getAttribute("pedidoRealizacion");
       if(pr == null){
               Cliente cli = (Cliente) sesion.getAttribute("cliente");
               pr = gbp.getPedidoEnRealizacion(cli.getCodigo());
               if(pr ==null){
                   pr = new PedidoEnRealizacion(cli);
               }
       }
       sesion.setAttribute("pedidoRealizacion", pr);
       String accion = request.getParameter("accion");
       List<String> acciones = new ArrayList<String>();
       acciones.add("Comprar");
       acciones.add("Seguir comprando");
       acciones.add("Guardar pedido");
       acciones.add("Quitar");
       acciones.add("Cerrar pedido");
       acciones.add("Cancelar");
       if(UtilesString.isVacia(accion)){
           response.sendRedirect("PedidoRealizacion");
           return;
       }else{
           if(!acciones.contains(accion)){
               response.sendRedirect("PedidoRealizacion");
               return;
           }
       }
       if(accion.equals("Comprar")){
           String codArt = (String) request.getParameter("ca");
           if(UtilesString.isVacia(codArt)){
               request.setAttribute("link", "PedidoRealizacion");
               response.sendError(HttpServletResponse.SC_FORBIDDEN, "No hay código de artículo");
               return;
           }
           if(gbd.getArticulo(codArt)==null){
               request.setAttribute("link", "PedidoRealizacion");
               response.sendError(HttpServletResponse.SC_NOT_FOUND, "Codigo de articulo erroneo");
               return;
           }
           pr.addLinea(gbd.getArticulo(codArt));        
           response.sendRedirect("pedidoRealizacion.jsp");
           return;
       }
       if(accion.equals("Seguir comprando")){
           procesaParams(pr,request);
           response.sendRedirect("../BuscarArticulos");
       }
	          if (accion.equals("Guardar pedido")) {
               procesaParams(pr, request);
               try {
                   gbp.grabaPedidoEnRealizacion(pr);
               } catch (ExcepcionDeAplicacion ex) {
                   Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
               }
               response.sendRedirect("AreaCliente");
               return;
           }
       if(accion.equals("Cerrar pedido")){
           procesaParams(pr,request);
           sesion.setAttribute("pedidoACerrar", pr);
           request.setAttribute("msg", "Se va a proceder a cerrar su pedido en realización. ¿Está usted seguro?");
           request.setAttribute("siLink", "CierraPedido?accion=cerrar");
           request.setAttribute("noLink", "CierraPedido?accion=cancelar");
           rd = request.getRequestDispatcher("../clientes/confirmacion.jsp");
           rd.forward(request, response);
       }
       
           } catch (ExcepcionDeAplicacion ex) {
               Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
               throw new ServletException(ex);
           }
       }
    

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession sesion = request.getSession();
       RequestDispatcher rd = null;
       try {
       PedidoEnRealizacion pr = (PedidoEnRealizacion) sesion.getAttribute("pedidoRealizacion");
       if(pr == null){
               Cliente cli = (Cliente) sesion.getAttribute("cliente");
               pr = gbp.getPedidoEnRealizacion(cli.getCodigo());
               if(pr ==null){
                   pr = new PedidoEnRealizacion(cli);
               }
       }
       sesion.setAttribute("pedidoRealizacion", pr);
       String accion = request.getParameter("accion");
       List<String> acciones = new ArrayList<String>();
       acciones.add("Comprar");
       acciones.add("Seguir comprando");
       acciones.add("Guardar pedido");
       acciones.add("Quitar");
       acciones.add("Cerrar pedido");
       acciones.add("Cancelar");
       if(UtilesString.isVacia(accion)){
           response.sendRedirect("PedidoRealizacion");
           return;
       }else{
           if(!acciones.contains(accion)){
               response.sendRedirect("PedidoRealizacion");
               return;
           }
       }
       if(accion.equals("Comprar")){
           String codArt = (String) request.getParameter("ca");
           if(UtilesString.isVacia(codArt)){
               request.setAttribute("link", "PedidoRealizacion");
               response.sendError(HttpServletResponse.SC_FORBIDDEN, "No hay código de artículo");
               return;
           }
           if(gbd.getArticulo(codArt)==null){
               request.setAttribute("link", "PedidoRealizacion");
               response.sendError(HttpServletResponse.SC_NOT_FOUND, "Codigo de articulo erroneo");
               return;
           }
           pr.addLinea(gbd.getArticulo(codArt));        
           response.sendRedirect("pedidoRealizacion.jsp");
           return;
       }
       if(accion.equals("Seguir comprando")){
           procesaParams(pr,request);
           response.sendRedirect("../BuscarArticulos");
       }
	   if(accion.equals("Guardar pedido")){
		   procesaParams(pr,request);
		   try{
			    gbp.grabaPedidoEnRealizacion(pr);
		   }catch(ExcepcionDeAplicacion ex){
				Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
		   }
		   response.sendRedirect("AreaCliente");
		   return;
	   }
       if(accion.equals("Cerrar pedido")){
           procesaParams(pr,request);
           sesion.setAttribute("pedidoACerrar", pr);
           request.setAttribute("msg", "Se va a proceder a cerrar su pedido en realización. ¿Está usted seguro?");
           request.setAttribute("siLink", "CierraPedido?accion=cerrar");
           request.setAttribute("noLink", "CierraPedido?accion=cancelar");
           rd = request.getRequestDispatcher("../clientes/confirmacion.jsp");
           rd.forward(request, response);
       }
           } catch (ExcepcionDeAplicacion ex) {
               Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
               throw new ServletException(ex);
           }
       }
    private void procesaParams(PedidoEnRealizacion pr, HttpServletRequest req) {
    ParameterParser pp = new ParameterParser(req);
    Enumeration<String> pnames = req.getParameterNames();
    while (pnames.hasMoreElements()) {
      String pn = pnames.nextElement();
      if (pn.startsWith("C_")) {
	String cl = pn.substring(2);
	LineaEnRealizacion lr = pr.getLinea(cl);
	int cantidad = pp.getIntParameter(pn, 1);
	lr.setCantidad(cantidad);
      } else if (pn.startsWith("F_")) {
	String cl = pn.substring(2);
	LineaEnRealizacion lr = pr.getLinea(cl);
	Calendar fe = pp.getCalendarParameter(pn, "dd/MM/yyyy", Calendar.getInstance());
	lr.setFechaEntregaDeseada(fe);
      }
    }
  }
    }
