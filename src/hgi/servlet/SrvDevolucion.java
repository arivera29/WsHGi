/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import hgi.controlador.ControladorAuditoria;
import hgi.controlador.ControladorDevolucion;
import hgi.controlador.Utilidades;
import hgi.controlador.conexion;
import java.io.BufferedReader;
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
@WebServlet("/rest/devolucion")
public class SrvDevolucion extends HttpServlet {

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
            Gson gson = new Gson();
            conexion conex = null;
            String json = "";
            try {

                StringBuilder sb = new StringBuilder();
                BufferedReader br = request.getReader();
                String str = "";

                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }

                json = sb.toString();

                if (json.equals("")) {
                    throw new Exception("Parametro json vacio");
                }

                hgi.entidades.Devolucion devolucion = gson.fromJson(json, hgi.entidades.Devolucion.class);
                
                if (devolucion == null) {
                    throw new Exception("JSON devolucion no válido");
                }
                
                if (devolucion.getNumeroOrden().equals("")) {
                    throw new Exception("Parametro NumeroOrden vacio");
                }
                if (devolucion.getNumeroGuia().equals("")) {
                    throw new Exception("Parametro NumeroGuia vacio");
                }
                if (devolucion.getNumeroExpediente().equals("")) {
                    throw new Exception("Parametro NumeroExpediente vacio");
                }
                if (devolucion.getFechaDevolucion().equals("")) {
                    throw new Exception("Parametro FechaDevolucion vacio");
                }
                if (devolucion.getCausalDevolucion().equals("")) {
                    throw new Exception("Parametro CausalDevolucion vacio");
                }
                if (devolucion.getCodigoMensajero().equals("")) {
                    throw new Exception("Parametro CodigoMensajero vacio");
                }
                if (devolucion.getToken().equals("")) {
                    throw new Exception("Parametro Token vacio");
                }
                Utilidades.AgregarLog(json, "json.txt");
                

                conex = new conexion();
                ControladorAuditoria c1 = new ControladorAuditoria(conex);
                c1.Mensajeria(devolucion.getNumeroExpediente(), json, "devolucion", "webservice");
                
                
                
                ControladorDevolucion controlador = new ControladorDevolucion(conex);
                controlador.setDevolucion(devolucion);
                if (controlador.DevolverExpediente()) {
                    out.print("OK");
                    Utilidades.AgregarLog("Gestion Devolucion Confirmada. Expediente: " + devolucion.getNumeroExpediente() , "gestion.txt");
                } else {
                    out.print("FAIL");
                    Utilidades.AgregarLog("Gestion Devolucion Error. Expediente: " + devolucion.getNumeroExpediente() , "gestion.txt");
                }

            } catch (SQLException e) {
                out.print(e.getMessage());
                Utilidades.AgregarLog(e.getMessage() + "\t" + json, "excepciones.txt");
            }catch (JsonParseException  e) {
                out.print(e.getMessage());
                Utilidades.AgregarLog(e.getMessage() + "\t" + json, "excepciones.txt");
            } catch (Exception e) {
                out.print(e.getMessage());
                Utilidades.AgregarLog(e.getMessage() + "\t" + json, "excepciones.txt");
            }finally {
                if (conex != null) {
                    try {
                        conex.Close();
                    } catch (SQLException ex) {
                        out.print(ex.getMessage());
                        Utilidades.AgregarLog(ex.getMessage(), "excepciones.txt");
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
