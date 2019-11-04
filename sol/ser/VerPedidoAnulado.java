package sol.ser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.model.PedidoAnulado;
import paw.util.UtilesString;

/**
 *
 * @author alruizj
 */
@WebServlet(urlPatterns = {"/clientes/VerPedidoAnulado"})
public class VerPedidoAnulado extends HttpServlet {

   private static GestorBDPedidos gbd = new GestorBDPedidos();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String codigo = request.getParameter("cp");
        RequestDispatcher rd = null;
        if(UtilesString.isVacia(codigo)){
            response.sendRedirect("/clientes/PedidosCliente");
        }else{
            try {
                PedidoAnulado p = gbd.getPedidoAnulado(codigo);
                if(p == null){
                     request.setAttribute("link", "PedidosCliente");
                     response.sendError(HttpServletResponse.SC_NOT_FOUND, "Código de pedido inválido");
                }else{
                    Cliente clis = (Cliente) request.getSession().getAttribute("cliente");
                    if(clis.equals(p.getCliente())){
                        request.setAttribute("pedidoAnulado", p);
                        rd = request.getRequestDispatcher("verPedidoAnulado.jsp");
                        rd.forward(request, response);
                    }else{
                        request.setAttribute("link", "../Salir");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN,"Usted no está autorizado para consultar esta información");
                    }
                }
            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(VerPedido.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException(ex);
            }
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
    

}
