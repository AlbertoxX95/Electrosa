/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.scene.paint.Color;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.Linea;
import paw.model.Pedido;

/**
 *
 * @author Alberto
 */
@WebServlet(name = "GeneraPDF", urlPatterns = {"/clientes/GeneraPDF"})
public class GeneraPDF extends HttpServlet {
    private static GestorBDPedidos gbp = new GestorBDPedidos();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String codigo = request.getParameter("cp");
        String nombrePdf = "Pedido_"+ codigo;
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombrePdf + ".pdf");
        OutputStream out = response.getOutputStream();
        Cliente c = null;
        try {
            try{
               
                Pedido p = gbp.getPedido(codigo);
                c = (Cliente) request.getSession().getAttribute("cliente");
                Document documento = new Document();
                PdfWriter.getInstance(documento, out);
                documento.open();
                Image logoElectrosa;
                logoElectrosa = Image.getInstance(request.getServletContext().getRealPath("img\\LogoElectrosa200.png"));
                logoElectrosa.setAlignment(Element.ALIGN_CENTER);
                logoElectrosa.scaleToFit(100,100);
                documento.add(logoElectrosa);
                
                
                
                PdfPTable tabla0 = new PdfPTable(1);
                PdfPCell celda01 = new PdfPCell(new Paragraph("Hoja de pedido", FontFactory.getFont("Arial", 18, Font.BOLD, new BaseColor(92, 126, 112))));
                celda01.setBackgroundColor(BaseColor.WHITE);
                celda01.setBorder(0);
                tabla0.addCell(celda01);
                documento.add(tabla0);
                
                //Tabla espacio blanco
                
                PdfPTable tEspacio = new PdfPTable(1);
                PdfPCell celdaEsp = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 1, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp.setBorder(0);
                celdaEsp.setMinimumHeight(5);
                tEspacio.addCell(celdaEsp);
                documento.add(tEspacio);
                
                PdfPTable tabla1 = new PdfPTable(2);
                PdfPCell celda1 = new PdfPCell(new Paragraph("Electrosa - Hoja de pedido", FontFactory.getFont("Arial", 16, Font.BOLD, BaseColor.WHITE)));
                celda1.setBackgroundColor(BaseColor.BLACK);
                PdfPCell celda2 = new PdfPCell(new Paragraph("Ref.Pedido: "+ codigo, FontFactory.getFont("Arial", 16, Font.BOLD, BaseColor.WHITE)));
                celda2.setBackgroundColor(BaseColor.BLACK);
                celda2.setHorizontalAlignment(2);
                tabla1.addCell(celda1);
                tabla1.addCell(celda2);
                documento.add(tabla1);
                
                PdfPTable tEspacio2 = new PdfPTable(1);
                PdfPCell celdaEsp2 = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 14, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp2.setBackgroundColor(BaseColor.WHITE);
                celdaEsp2.setBorder(0);
                celdaEsp2.setMinimumHeight(3);
                tEspacio2.addCell(celdaEsp2);
                documento.add(tEspacio2);
                
                PdfPTable tabla2 = new PdfPTable(1);
                PdfPCell celda21 = new PdfPCell(new Paragraph("Identificacion del cliente", FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.WHITE)));
                celda21.setBackgroundColor(new BaseColor(92, 126, 112));
                celda21.setBorder(0);
                tabla2.addCell(celda21);
                documento.add(tabla2);
                
                PdfPTable tEspacio3 = new PdfPTable(1);
                PdfPCell celdaEsp3 = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 14, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp3.setBackgroundColor(BaseColor.WHITE);
                celdaEsp3.setBorder(0);
                celdaEsp3.setMinimumHeight(3);
                tEspacio3.addCell(celdaEsp3);
                documento.add(tEspacio3);
                
                Date fechaCierre = p.getFechaCierre().getTime();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fechaCierreS = format.format(fechaCierre);
                
                PdfPTable tabla3 = new PdfPTable(2);
                PdfPCell celda31 = new PdfPCell(new Paragraph("Cliente: " + c.getCodigo() + " " + c.getNombre(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                PdfPCell celda32 = new PdfPCell(new Paragraph("CIF: " + c.getCif() + " " + "Fecha: "+ fechaCierreS, FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda31.setBorder(0);
                celda32.setBorder(0);
                celda31.setBackgroundColor(new BaseColor(230, 255, 255));
                celda32.setBackgroundColor(new BaseColor(230, 255, 255));
                celda32.setHorizontalAlignment(2);
                tabla3.addCell(celda31);
                tabla3.addCell(celda32);
                documento.add(tabla3);
                
                PdfPTable tEspacio4 = new PdfPTable(1);
                PdfPCell celdaEsp4 = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 14, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp4.setBackgroundColor(BaseColor.WHITE);
                celdaEsp4.setBorder(0);
                celdaEsp4.setMinimumHeight(3);
                tEspacio4.addCell(celdaEsp4);
                documento.add(tEspacio4);
                
                PdfPTable tabla4  = new PdfPTable(4);
                PdfPCell celda41 = new PdfPCell(new Paragraph("A entregar en: ", FontFactory.getFont("Arial", 10, BaseColor.BLACK)));
                PdfPCell celda42 = new PdfPCell(new Paragraph(c.getDireccion().getCalle() + "\n" + c.getDireccion().getCiudad() + "\n" + c.getDireccion().getCp() + "-" + c.getDireccion().getProvincia(), FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK)));
                celda42.setBackgroundColor(new BaseColor(230, 255, 255));
                celda41.setBorder(0);
                celda42.setBorder(0);
                tabla4.addCell(celda41);
                tabla4.addCell(celda42);
                PdfPCell celda43 = new PdfPCell();
                PdfPCell celda44 = new PdfPCell();
                celda43.setBorder(0);
                celda44.setBorder(0);
                tabla4.addCell(celda43);
                tabla4.addCell(celda44);
                documento.add(tabla4);
                
                PdfPTable tEspacio5 = new PdfPTable(1);
                PdfPCell celdaEsp5 = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 14, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp5.setBackgroundColor(BaseColor.WHITE);
                celdaEsp5.setBorder(0);
                celdaEsp5.setMinimumHeight(3);
                tEspacio5.addCell(celdaEsp5);
                documento.add(tEspacio5);
                
                PdfPTable tabla5 = new PdfPTable(1);
                PdfPCell celda51 = new PdfPCell(new Paragraph("Detalle del pedido", FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.WHITE)));
                celda51.setBackgroundColor(new BaseColor(92, 126, 112));
                celda51.setBorder(0);
                tabla5.addCell(celda51);
                documento.add(tabla5);
                
                PdfPTable tEspacio6 = new PdfPTable(1);
                PdfPCell celdaEsp6 = new PdfPCell(new Paragraph("", FontFactory.getFont("Arial", 14, Font.BOLD, new BaseColor(92, 126, 112))));
                celdaEsp6.setBackgroundColor(BaseColor.WHITE);
                celdaEsp6.setBorder(0);
                celdaEsp6.setMinimumHeight(3);
                tEspacio6.addCell(celdaEsp6);
                documento.add(tEspacio6);
                
                PdfPTable tabla6 = new PdfPTable(6);
                float[] medidaCeldas = {0.65f, 2.00f, 0.65f, 0.65f,0.70f,0.70f};
                tabla6.setWidths(medidaCeldas);
                PdfPCell celda61 = new PdfPCell(new Paragraph("Cantidad", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                PdfPCell celda62 = new PdfPCell(new Paragraph("Artículo", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                PdfPCell celda63 = new PdfPCell(new Paragraph("P.V.P", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                PdfPCell celda64 = new PdfPCell(new Paragraph("Su precio", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                PdfPCell celda65 = new PdfPCell(new Paragraph("F.Entrega deseada", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                PdfPCell celda66 = new PdfPCell(new Paragraph("F.Entrega prevista", FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                celda61.setBorderColor(BaseColor.WHITE);
                celda62.setBorderColor(BaseColor.WHITE);
                celda63.setBorderColor(BaseColor.WHITE);
                celda64.setBorderColor(BaseColor.WHITE);
                celda65.setBorderColor(BaseColor.WHITE);
                celda66.setBorderColor(BaseColor.WHITE);
                celda61.setBackgroundColor(BaseColor.BLACK);
                celda62.setBackgroundColor(BaseColor.BLACK);
                celda63.setBackgroundColor(BaseColor.BLACK);
                celda64.setBackgroundColor(BaseColor.BLACK);
                celda65.setBackgroundColor(BaseColor.BLACK);
                celda66.setBackgroundColor(BaseColor.BLACK);
                tabla6.addCell(celda61);
                tabla6.addCell(celda62);
                tabla6.addCell(celda63);
                tabla6.addCell(celda64);
                tabla6.addCell(celda65);
                tabla6.addCell(celda66);
                documento.add(tabla6);
                
                PdfPTable tabla7 = new PdfPTable(6);
                
                tabla7.setWidths(medidaCeldas);

                List<Linea> lineas = p.getLineas();
                int i = 0;
                for (Linea l : lineas){
                NumberFormat formatter = new DecimalFormat("#0.00");
                String precio =  formatter.format(l.getPrecioBase());
                String precioTot = formatter.format(l.getCantidad()*l.getPrecioBase());
                PdfPCell celda71 = new PdfPCell(new Paragraph(""+l.getCantidad(), FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                PdfPCell celda72 = new PdfPCell(new Paragraph(l.getArticulo().getNombre(), FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                PdfPCell celda73 = new PdfPCell(new Paragraph(precio, FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                PdfPCell celda74 = new PdfPCell(new Paragraph(precioTot, FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                Date fechaDeseada = l.getFechaEntregaDeseada().getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
                String fechaDeseadaS = format1.format(fechaDeseada);
                String fechaPrevistaS = "";
                if(l.getFechaEntregaPrevista() != null){
                        Date fechaPrevista = l.getFechaEntregaPrevista().getTime();
                        fechaPrevistaS = format1.format(fechaPrevista);
                    }else{
                        fechaPrevistaS = "Por decidir";
                    }    
                PdfPCell celda75 = new PdfPCell(new Paragraph(fechaDeseadaS, FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                PdfPCell celda76 = new PdfPCell(new Paragraph(fechaPrevistaS, FontFactory.getFont("Arial", 10,Font.NORMAL, BaseColor.BLACK)));
                   
                    //Date fechaPrevista = l.getFechaEntregaPrevista().getTime();
                    //SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yy");
                    //String fechaPrevistaS = format2.format(fechaPrevista);
                    if(i%2 == 0){
                        celda71.setBackgroundColor(new BaseColor(230, 255, 255));
                        celda72.setBackgroundColor(new BaseColor(230, 255, 255));
                        celda73.setBackgroundColor(new BaseColor(230, 255, 255));
                        celda74.setBackgroundColor(new BaseColor(230, 255, 255));
                        celda75.setBackgroundColor(new BaseColor(230, 255, 255));
                        celda76.setBackgroundColor(new BaseColor(230, 255, 255));
                        
                    }else{
                        celda71.setBackgroundColor(BaseColor.WHITE);
                        celda72.setBackgroundColor(BaseColor.WHITE);
                        celda73.setBackgroundColor(BaseColor.WHITE);
                        celda74.setBackgroundColor(BaseColor.WHITE);
                        celda75.setBackgroundColor(BaseColor.WHITE);
                        celda76.setBackgroundColor(BaseColor.WHITE);
                    }
                    celda71.setBorder(0);
                    celda72.setBorder(0);
                    celda73.setBorder(0);
                    celda74.setBorder(0);
                    celda75.setBorder(0);
                    celda76.setBorder(0);
                   
                    
                    i++;
                    tabla7.addCell(celda71);
                    tabla7.addCell(celda72);
                    tabla7.addCell(celda73);
                    tabla7.addCell(celda74);
                    tabla7.addCell(celda75);
                    tabla7.addCell(celda76);
                    
                }
                
                documento.add(tabla7);
                
                
               
                PdfPTable tabla8 = new PdfPTable(1);
                PdfPCell celda81 = new PdfPCell(new Paragraph("Total: " + p.getImporte(),FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.WHITE)));
                celda81.setBackgroundColor(new BaseColor(92, 126, 112));
                celda81.setHorizontalAlignment(2);
                tabla8.addCell(celda81);
                documento.add(tabla8);
                
               
                
                PdfPTable tabla9 = new PdfPTable(1);
                PdfPCell celda91 = new PdfPCell(new Paragraph("* S/D sin disponibilidad. Recibirá una notificación de entrega en el momento en que podamos atender su petición",FontFactory.getFont("Arial", 10,Font.BOLD, BaseColor.GRAY)));
                celda91.setBackgroundColor(BaseColor.WHITE);
                celda91.setBorder(0);
                tabla9.addCell(celda91);
                documento.add(tabla9);
              
                
                
                
                documento.close();
            }catch(Exception ex){
                ex.getMessage();
            }
        }finally{
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
