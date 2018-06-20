/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import hgi.controlador.conexion;
import hgi.entidades.Acta;
import hgi.entidades.Archivo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aimer
 */
@WebServlet(name = "SrvExpediente", urlPatterns = {"/SrvExpediente"})
public class SrvExpediente extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String criterio = (String) request.getParameter("criterio");
            String sql = "SELECT a._number,a.nic,a._clientCloseTs, p.AnLaProce "
                    + " FROM actas a"
                    + " INNER JOIN ProcesoSimpli p ON a._number = p.NoAcProc "
                    + " WHERE a.estadoActa=11 "
                    + " AND ( a.nic = ? OR p.AnLaProce = ?) ";
            conexion conex = null;
            ArrayList<Acta> expedientes = new ArrayList<Acta>();
            try {
                conex = new conexion();
                java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setString(1, criterio);
                pst.setString(2, criterio);
                java.sql.ResultSet rs = conex.Query(pst);

                while (rs.next()) {
                    Acta exp = new Acta();
                    exp.setFechaActa(rs.getString("_clientCloseTs"));
                    exp.setNic(rs.getString("nic"));
                    exp.setNumeroActa(rs.getInt("_number"));
                    exp.setNumeroFR(rs.getString("AnLaProce"));

                    ArrayList<Archivo> documentos = new ArrayList<Archivo>();
                    sql = "SELECT d.DocuCodi, d.DocuUrLo, d.DocuTiDo, t.TiDoDocu"
                            + " FROM Documentos d "
                            + " INNER JOIN TipoDocumento t ON d.DocuTiDo = t.TiDoCodi "
                            + " WHERE d.DocuActa= ? "
                            + " AND (t.TiDoMens = 1 OR t.TiDoCodi=14 );";
                    java.sql.PreparedStatement pst2 = conex.getConnection().prepareStatement(sql);
                    pst2.setInt(1, rs.getInt("_number"));
                    java.sql.ResultSet rs2 = conex.Query(pst2);
                    while (rs2.next()) {
                        Archivo doc = new Archivo();
                        doc.setIdDocumento(rs2.getInt("DocuCodi"));
                        doc.setNumeroActa(rs.getInt("_number"));
                        doc.setTipoDocumento(rs2.getString("TiDoDocu"));

                        documentos.add(doc);

                    }

                    exp.setDocumentos(documentos);

                    expedientes.add(exp);

                }

                Gson gson = new Gson();
                String json = gson.toJson(expedientes);
                out.print(json);

            } catch (SQLException ex) {
                out.print(ex.getMessage());
            } finally {
                if (conex != null) {
                    try {
                        conex.Close();
                    } catch (SQLException ex) {
                        out.print(ex.getMessage());
                    }
                }
            }

        } finally {
            out.close();

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

}
