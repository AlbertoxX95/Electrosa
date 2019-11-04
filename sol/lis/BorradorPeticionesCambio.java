/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.lis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import paw.bd.ConnectionManager;

/**
 * Web application lifecycle listener.
 *
 * @author alruizj
 */
public class BorradorPeticionesCambio implements ServletContextListener {
    private ScheduledExecutorService planificador;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       planificador = Executors.newSingleThreadScheduledExecutor();
       planificador.scheduleAtFixedRate(new Runnable(){
           public void run(){
               borrar();
           }
       }, 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        planificador.shutdownNow();
    }
    public void borrar(){
        try {
            Connection con = null;
            con = ConnectionManager.getConnection();
            String sql = "delete from recuerdocontrasenia where fecha < DATE_ADD(now(), INTERVAL '-5' MINUTE)";
            PreparedStatement ps = con.prepareStatement(sql);
            int filas = ps.executeUpdate();
            Logger.getLogger(BorradorPeticionesCambio.class.getName()).log(Level.SEVERE, "El numero de filas borradas es: ", filas);
        } catch (SQLException ex) {
            Logger.getLogger(BorradorPeticionesCambio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
