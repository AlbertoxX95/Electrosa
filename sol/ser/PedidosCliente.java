/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.model.PedidoAnulado;
import paw.model.PedidoEnRealizacion;

/**
 *
 * @author alruizj
 */
public class PedidosCliente extends HttpServlet {
    private static GestorBDPedidos gbd = new GestorBDPedidos();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cli = (Cliente) request.getSession().getAttribute("cliente");    
        RequestDispatcher rd = null;
        try {
            request.setAttribute("pendientes", gbd.getPedidosPendientes(cli.getCodigo()));
            request.setAttribute("completados", gbd.getPedidosCompletados(cli.getCodigo()));
            request.setAttribute("anulados", gbd.getPedidosAnulados(cli.getCodigo()));
            PedidoEnRealizacion pr = (PedidoEnRealizacion) request.getSession().getAttribute("pedidoRealizacion");
            if (pr == null) {
                pr = gbd.getPedidoEnRealizacion(cli.getCodigo());
                if (pr != null) {
                    request.getSession().setAttribute("pedidoRealizacion", pr);
                }
            }
            rd = request.getRequestDispatcher("pedidosCliente.jsp");
            rd.forward(request, response);
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(PedidosCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }


}
