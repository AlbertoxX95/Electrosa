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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.bd.GestorBDPedidos;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.model.PedidoEnRealizacion;

/**
 *
 * @author alruizj
 */
public class CierraPedido extends HttpServlet {
    private static GestorBDPedidos gbp = new GestorBDPedidos();
    private static GestorBD gbd = new GestorBD();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        PedidoEnRealizacion pr = (PedidoEnRealizacion) sesion.getAttribute("pedidoACerrar");
         try {
            if (pr != null) {
                String accion = (String) request.getParameter("accion");
                if (accion.equals("cerrar")) {
                    sesion.removeAttribute("pedidoACerrar");
                    sesion.removeAttribute("pedidoRealizacion");
                    Pedido pedidoCerrado = gbp.cierraPedido(pr, pr.getCliente().getDireccion());
                    response.sendRedirect("VerPedido?cp="+pedidoCerrado.getCodigo());
                    return;
                } else {
                    sesion.removeAttribute("pedidoACerrar");
                    response.sendRedirect("PedidoRealizacion");
                    return;
                }
            } else {
                request.setAttribute("link", "../AreaClientes");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La aplicacion no puede determinar el pedido a cerrar");
                return;
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CierraPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
