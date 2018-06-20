/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import hgi.controlador.conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aimer
 */
@WebServlet(name = "SrvDescargarAll", urlPatterns = {"/SrvDescargarAll"})
public class SrvDescargarAll extends HttpServlet {

    static final String PATH_DOCUMENTO = "I:\\HGI2\\Documentos";
    static final String PATH_DOCUMENTO4 = "E:\\HGI2\\Documentos";
    static final String PATH_DOCUMENTO2 = "F:\\HGI2\\Documentos";
    static final String PATH_DOCUMENTO3 = "H:\\HGI2\\Documentos";
    static final String PATH_GUIAS = "G:\\Guias";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String id = (String) request.getParameter("id");
            conexion conex = null;
            String sql = "SELECT d.DocuCodi, d.DocuUrLo, d.DocuPath, d.DocuTiDo, t.TiDoDocu"
                    + " FROM Documentos d "
                    + " INNER JOIN TipoDocumento t ON d.DocuTiDo = t.TiDoCodi "
                    + " WHERE d.DocuActa= ?  "
                    + " AND (t.TiDoMens = 1 OR t.TiDoCodi=14);";

            try {
                conex = new conexion();
                List<InputStream> archivos = new ArrayList<InputStream>();
                java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setString(1, id);
                java.sql.ResultSet rs = conex.Query(pst);
                while (rs.next()) {
                    String path = rs.getString("DocuPath");
                    //path = this.parserPath(path);
                    File file = new File(path);
                    if (file.exists()) {
                        archivos.add(new FileInputStream(path));
                    }
                }

                if (archivos.size() > 0) {
                    String mimeType = "application/octet-stream";
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String fname = "documentos.pdf";
                    String headerValue = String.format("attachment; filename=\"%s\"", fname);
                    response.setHeader(headerKey, headerValue);
                    generatePDF(archivos, response, false);
                }

            } catch (SQLException ex) {
                Logger.getLogger(SrvDescargarAll.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(SrvDescargarAll.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    private String parserPath(String path) {
        String ruta = "";
        ruta = path.replace("~", "");
        ruta = ruta.replace("/", "\\");

        if (ruta.contains("\\File\\Documentos\\")) {
            ruta = ruta.replace("\\File\\Documentos", PATH_DOCUMENTO);
        }

        if (ruta.contains("\\File\\Documentos2\\")) {
            ruta = ruta.replace("\\File\\Documentos2", PATH_DOCUMENTO2);
        }

        if (ruta.contains("\\File\\Documentos3\\")) {
            ruta = ruta.replace("\\File\\Documentos3", PATH_DOCUMENTO3);
        }
        
        if (ruta.contains("\\File\\Documentos3\\")) {
            ruta = ruta.replace("\\File\\Documentos4", PATH_DOCUMENTO4);
        }
        
        if (ruta.contains("File\\Guias\\")) {
            ruta = ruta.replace("File\\Guias", PATH_GUIAS);
        }
        return ruta;
    }

    private void generatePDF(List<InputStream> archivos, HttpServletResponse response, boolean paginate) 
            throws IOException, DocumentException {
        Document document = new Document();

        List<InputStream> pdfs = archivos;
        List<PdfReader> readers = new ArrayList<PdfReader>();
        int totalPages = 0;
        Iterator<InputStream> iteratorPDFs = pdfs.iterator();
        UUID identificador = UUID.randomUUID();
        String filename = identificador + ".pdf";
        OutputStream outputStream = response.getOutputStream();
        
        while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
        
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
 
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
 
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
 
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
 
                    Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
                    document.setPageSize(rectangle);
                    document.newPage();
 
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    switch (rectangle.getRotation()) {
                    case 0:
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                        break;
                    case 90:
                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
                                .getPageSizeWithRotation(1).getHeight());
                        break;
                    case 180:
                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
                        break;
                    case 270:
                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
                                .getPageSizeWithRotation(1).getWidth(), 0);
                        break;
                    default:
                        break;
                    }
                    if (paginate) {
                        cb.beginText();
                        cb.getPdfDocument().getPageSize();
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();

    }

}
