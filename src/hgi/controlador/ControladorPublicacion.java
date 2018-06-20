/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.controlador;

import hgi.entidades.Publicacion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aimer
 */
public class ControladorPublicacion {
    private boolean error;
    private String msgError;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public ControladorPublicacion() {
        this.error = false;
        this.msgError = "";
    }
    
    

    public ArrayList<Publicacion> obtenerPublicaciones(String nic) {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
        conexion conex = null;

        try {
            conex = new conexion();

            String sql = "SELECT _number, nic, Mensajeria.departamento,Mensajeria.municipio,"
                    + "Mensajeria.localidad,CONVERT(date,_clientCloseTS) _clientCloseTS, "
                    + "CONVERT(date,fechaEntregaExpe) fechaEntregaExpe, uploadImagen "
                    + " FROM Actas,Mensajeria "
                    + " WHERE nic = ? "
                    + " AND Mensajeria.MensActa = _number "
                    + " AND Mensajeria.estadoEntrega = 'D' "
                    + " AND MensCaDe NOT IN (4,12,14) "
                    + " AND DATEDIFF(day,fechaEntregaExpe,SYSDATETIME()) <=  60 "
                    + " AND uploadImagen = 1";

            java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
            pst.setString(1, nic);
            java.sql.ResultSet rs = conex.Query(pst);
            while (rs.next()) {
                Publicacion pub = new Publicacion();
                pub.setActa(rs.getInt("_number"));
                pub.setNic(rs.getString("nic"));
                pub.setDepartamento(rs.getString("departamento"));
                pub.setMunicipio(rs.getString("municipio"));
                pub.setLocalidad(rs.getString("localidad"));
                pub.setNumeroFR("");
                pub.setFecha(rs.getString("_clientCloseTS"));
                pub.setFechaDevolucion(rs.getString("fechaEntregaExpe"));

                publicaciones.add(pub);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControladorPublicacion.class.getName()).log(Level.SEVERE, null, ex);
            this.error = true;
            this.msgError = ex.getMessage();
        }

        return publicaciones;
    }

}
