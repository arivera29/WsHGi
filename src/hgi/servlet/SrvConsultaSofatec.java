/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import hgi.controlador.conexion;
import hgi.entidades.InfoActa;
import java.io.IOException;
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
 * @author aimerrivera
 */
@WebServlet(name = "SrvConsultaSofatec", urlPatterns = {"/SrvConsultaSofatec"})
public class SrvConsultaSofatec extends HttpServlet {

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
        response.setContentType("text/json;charset=UTF-8");
        String codigo = (String)request.getParameter("acta");
        try (PrintWriter out = response.getWriter()) {
           Gson gson = new Gson();
           conexion conex = null;
           
            try {
                conex = new conexion();
                String sql = "SELECT a._number ACTA, a.fechaInicioIrregularidad F_IRREG," +
                    " a._clientCloseTs F_CIERRE," +
                    " a.conAnomalia ANOMALIA," +
                    " a.obsAnomalia OBS_ANOMALIA," +
                    " ISNULL(l.AcLiVaC1,0) ECDF," +
                    " ISNULL(p.AnLaProce,'') FR," +
                    " ISNULL(t.CodigoAccion,'') ACCION_RI," +
                    " a.estadoActa ESTADO," +
                    " e.EsAcDesc DESC_ESTADO" +    
                    " FROM  Actas a" +
                    " INNER JOIN EstadosActas e ON e.EsAcCodi = a.estadoActa" +    
                    " LEFT JOIN Acta_Liquidacion l ON l.AcLiActa = a._number" +
                    " LEFT JOIN ProcesoSimpli p ON p.NoAcProc = a._number" +
                    " LEFT JOIN TrabajosEjecutados t ON t.Acta = a._number AND t.CodigoPaso = 'RI'" +
                    " WHERE _number = ?" ;
                java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setString(1, codigo);
                java.sql.ResultSet rs = conex.Query(pst);
                InfoActa acta = new InfoActa();
                if (rs.next()) {
                    acta.setNumActa(rs.getInt("ACTA"));
                    if (rs.getInt("ANOMALIA") == 1) {
                        acta.setAnomalia("SI");
                    }else {
                        acta.setAnomalia("NO");
                    }
                    acta.setAccionIrregularidad(rs.getString("ACCION_RI"));
                    acta.setECDF(rs.getDouble("ECDF"));
                    acta.setFR(rs.getString("FR"));
                    acta.setObservacion(rs.getString("OBS_ANOMALIA"));
                    acta.setFechaCierre(new java.util.Date(rs.getDate("F_CIERRE").getTime()) );
                    acta.setFechaIrregularidad(new java.util.Date(rs.getDate("F_IRREG").getTime()) );
                    acta.setEstado(rs.getInt("ESTADO"));
                    acta.setDescEstado(rs.getString("DESC_ESTADO"));
                    
                }
                
                out.print(gson.toJson(acta));
                
                
            } catch (SQLException ex) {
                Logger.getLogger(SrvConsultaSofatec.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                if (conex != null) {
                    try {
                        conex.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SrvConsultaSofatec.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
           
           
           
           
           
            
            
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
