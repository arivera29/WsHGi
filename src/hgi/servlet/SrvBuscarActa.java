/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import hgi.controlador.conexion;
import hgi.entidades.ActaResult;
import hgi.entidades.BuscarActaResult;
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
 * @author aimer
 */
@WebServlet("/rest/buscarActa")
public class SrvBuscarActa extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            
            
            Gson gson = new Gson();
            BuscarActaResult result = new BuscarActaResult();
            
            String nic = (String)request.getParameter("nic");
            
            if (nic.equals("")) {
                
                result.setError("Parametro NIC no válido");
                out.print(gson.toJson(result));
                return;
            }
            
            conexion conex = null;
            
            try {
                conex = new conexion();
                String sql = "SELECT _number, nic, departamento,CONVERT(date,_clientCloseTs) _clientCloseTs,"
                        + " Confirmada, ActaManual, subnormal "
                        + " FROM Actas WHERE nic=? "
                        + " ORDER BY _clientCloseTS DESC";
                
                java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setString(1, nic);
                
                java.sql.ResultSet rs = conex.Query(pst);
                if (rs.next()) {
                    result.setEncontrado(true);
                    result.setError(false);
                    result.setMsgError("");
                    
                    do {
                        if (rs.getInt("subnormal")==1 || rs.getInt("ActaManual")==1) {
                            if (rs.getInt("confirmada")==0) {
                                continue;
                            }
                        }
                        
                        ActaResult acta = new ActaResult();
                        acta.setActa(rs.getInt("_number"));
                        acta.setDepartamento(rs.getString("departamento"));
                        acta.setFecha(rs.getString("_clientCloseTS"));
                        acta.setNic(rs.getString("nic"));
                        
                        result.getActas().add(acta);
                        
                        
                    }while(rs.next());
                    
                }else {
                    result.setError("No se encuentra(n) acta(s) para el NIC " + nic);
                    
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(SrvBuscarActa.class.getName()).log(Level.SEVERE, null, ex);
                result.setError(ex.getMessage());
            }finally {
                if (conex != null){
                    try {
                        conex.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SrvBuscarActa.class.getName()).log(Level.SEVERE, null, ex);
                        result.setError(ex.getMessage());
                    }
                }
                
            }
            
            
            out.print(gson.toJson(result));
            
            
            
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
