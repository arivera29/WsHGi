/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import hgi.controlador.ControladorExpediente;
import hgi.controlador.conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aimer
 */
@WebServlet("/rest/ResumenPendientes")
public class SrvResumenPendientes extends HttpServlet {

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
            conexion conex = null;
            try {

                String year = "";
                if (request.getParameter("year") != null) {
                    year = (String)request.getParameter("year");
                }
                    
                conex = new conexion();
                ControladorExpediente controlador = new ControladorExpediente(conex);
                Resumen resumen = new Resumen();
                if (year.equals("")) {
                    resumen.setGestion(controlador.PendienteGestion());
                    resumen.setImagen(controlador.PendienteImagen());
                } else {
                    resumen.setGestion(controlador.PendienteGestion(Integer.parseInt(year)));
                    resumen.setImagen(controlador.PendienteImagen(Integer.parseInt(year)));
                }
                
                
                out.print(gson.toJson(resumen));

            } catch (SQLException e) {
                out.print(e.getMessage());
            } catch (Exception e) {
                out.print(e.getMessage());
            } finally {
                if (conex != null) {
                    try {
                        conex.Close();
                    } catch (SQLException ex) {
                        out.print(ex.getMessage());
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

    private class Resumen {
        private long gestion;
        private long imagen;

        public long getGestion() {
            return gestion;
        }

        public void setGestion(long gestion) {
            this.gestion = gestion;
        }

        public long getImagen() {
            return imagen;
        }

        public void setImagen(long imagen) {
            this.imagen = imagen;
        }

        public Resumen() {
            gestion = 0;
            imagen = 0;
        }
        
    }
}
