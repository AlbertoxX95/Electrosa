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
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;

/**
 *
 * @author alruizj
 */
public class PedidoRealizacion extends HttpServlet {
    private static GestorBDPedidos gbd = new GestorBDPedidos();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PedidoEnRealizacion pr = (PedidoEnRealizacion) request.getSession().getAttribute("pedidoRealizacion");
        Cliente cli = (Cliente) request.getSession().getAttribute("cliente");
        RequestDispatcher rd = null;
        try {
            if (pr == null) {
                pr = gbd.getPedidoEnRealizacion(cli.getCodigo());
                if (pr != null) {
                    request.getSession().setAttribute("pedidoRealizacion", pr);

                }
            }
            rd = request.getRequestDispatcher("pedidoRealizacion.jsp");
            rd.forward(request, response);
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(PedidoRealizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }
}
