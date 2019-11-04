/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.model.Cliente;

/**
 *
 * @author alruizj
 */
public class Fautenticador implements Filter {
    
        public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse resp = (HttpServletResponse) response;
       HttpSession sesion = req.getSession();
       Cliente cliente = (Cliente) sesion.getAttribute("cliente");
       if(cliente == null){
           String returnURL = null;
           if(req.getQueryString() != null){
               returnURL = req.getRequestURL()+"?"+ req.getQueryString();
               
           }else{
               returnURL = req.getRequestURL()+"";
           }
               sesion.setAttribute("returnURL", returnURL);
               resp.sendRedirect(req.getContextPath()+"/Login");
       }else{
           chain.doFilter(request, response);
       }
    }
    public void destroy() {        
    }

   
    public void init(FilterConfig filterConfig) {        
        
    }

   
    
}
