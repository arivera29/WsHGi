/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import hgi.controlador.conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "SrvDescargar", urlPatterns = {"/SrvDescargar"})
public class SrvDescargar extends HttpServlet {

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
            try {
                conex = new conexion();
                String sql = "SELECT DocuPath FROM documentos WHERE DocuCodi=?";
                java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(id));
                java.sql.ResultSet rs = conex.Query(pst);
                if (rs.next()) {
                    String path = rs.getString("DocuPath");
                    //path = this.parserPath(path);
                    File file = new File(path);
                    if (file.exists()) {
                        FileInputStream inStream = new FileInputStream(file);
                        String mimeType = "application/octet-stream";
                        // modifies response
                        response.setContentType(mimeType);
                        response.setContentLength((int) file.length());

                        // forces download
                        String headerKey = "Content-Disposition";
                        String fname = file.getName();
                        String headerValue = String.format("attachment; filename=\"%s\"", fname);
                        response.setHeader(headerKey, headerValue);

                        // obtains response's output stream
                        OutputStream outStream = response.getOutputStream();

                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;

                        while ((bytesRead = inStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }

                        inStream.close();
                        outStream.close();

                    }else {
                        
                        PrintWriter out = response.getWriter();
                        out.println(String.format("Archivo %s no se encuentra", path));
                        out.close();
                    } 

                }

            } catch (SQLException ex) {
                Logger.getLogger(SrvDescargar.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conex.Close();
                } catch (SQLException ex) {
                    Logger.getLogger(SrvDescargar.class.getName()).log(Level.SEVERE, null, ex);
                }
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
}
