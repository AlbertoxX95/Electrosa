/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import com.google.gson.Gson;
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
import paw.model.Pedido;
import paw.util.UtilesString;

/**
 *
 * @author alruizj
 */
public class VerPedido extends HttpServlet {
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
                Pedido p = gbd.getPedido(codigo);
                if(p == null){
                     request.setAttribute("link", "PedidosCliente");
                     response.sendError(HttpServletResponse.SC_NOT_FOUND, "Código de pedido inválido");
                }else{
                    Cliente clis = (Cliente) request.getSession().getAttribute("cliente");
                    if(clis.equals(p.getCliente())){
                        String ajax = request.getHeader("X-Requested-With");
                        if(!UtilesString.isVacia(ajax) && ajax.equals("XMLHttpRequest")){
                            response.setContentType("application/json");
                            Gson gson = new Gson();
                            String json = gson.toJson(p);
                            response.getWriter().print(json);
                        }else{
                        request.setAttribute("pedido", p);
                        rd = request.getRequestDispatcher("verPedido.jsp");
                        rd.forward(request, response);
                        }
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

   
}
