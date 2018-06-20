/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hgi.controlador.ControladorExpediente;
import hgi.controlador.conexion;
import hgi.entidades.EstadoExpediente;
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
@WebServlet("/rest/ExpedientesPendientes")
public class SrvExpedientesPendientes extends HttpServlet {

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
        response.sendError(500, "Servicio no disponible");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            conexion conex = null;
            try {
                String year="";
                if (request.getParameter("year") != null) {
                    year = (String)request.getParameter("year");
                }
                    

                conex = new conexion();
                ControladorExpediente controlador = new ControladorExpediente(conex);
                ArrayList<EstadoExpediente> lista = new ArrayList<EstadoExpediente> (); 
                if (year.equals("")) {
                    lista = controlador.ExpedientesPendientes();
                }else {
                    lista = controlador.ExpedientesPendientes(Integer.parseInt(year));
                }
                out.print(gson.toJson(lista));

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

}
