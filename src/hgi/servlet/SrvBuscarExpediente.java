package hgi.servlet;

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
 * Servlet implementation class SrvBuscarExpediente
 */
@WebServlet("/SrvBuscarExpediente")
public class SrvBuscarExpediente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SrvBuscarExpediente() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void procesar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String expediente = (String)request.getParameter("expediente");
        //String imei = (String)request.getParameter("imei");
        conexion conex;
		try {
			conex = new conexion();
			if (conex.getConnection() != null) {
				
				
				String sql = "SELECT nic, direccion,departamento,municipio,localidad,"
						+ "referenciaDireccion,acceso, nombreTitularContrato,"
						+ "apellido1TitularContrato,apellido2TitularContrato,"
						+ "cedulaTitularContrato, nombreReceptorVisita,"
						+ "apellido1ReceptorVisita,apellido2ReceptorVisita,"
						+ "cedulaReceptorVisita, ZonaDesc "
						+ " FROM actas, zonas "
						+ " WHERE Delegacion = zonas.ZonaCodi "
						+ " AND concat(_number,nic) = ? ";
				
				java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
				pst.setString(1, expediente);
				java.sql.ResultSet rs = conex.Query(pst);
				String cadena = "{";
				if  (rs.next()) {
					cadena +=  String.format("\"nic\":\"%s\",",(String)rs.getString("nic"));
					cadena +=  String.format("\"direccion\":\"%s\",",(String)rs.getString("direccion"));
					cadena +=  String.format("\"departamento\":\"%s\",",(String)rs.getString("departamento"));
					cadena +=  String.format("\"municipio\":\"%s\",",(String)rs.getString("municipio"));
					cadena +=  String.format("\"localidad\":\"%s\",",(String)rs.getString("localidad"));
					cadena +=  String.format("\"direccionReferencia\":\"%s\",",(String)rs.getString("referenciaDireccion"));
					cadena +=  String.format("\"acceso\":\"%s\",",(String)rs.getString("acceso"));
					cadena +=  String.format("\"delegacion\":\"%s\",",(String)rs.getString("ZonaDesc"));
					cadena +=  String.format("\"nombreTitular\":\"%s %s %s\",",(String)rs.getString("nombreTitularContrato"),(String)rs.getString("apellido1TitularContrato"),(String)rs.getString("apellido2TitularContrato"));
					cadena +=  String.format("\"nombreReceptor\":\"%s %s %s\",",(String)rs.getString("nombreReceptorVisita"),(String)rs.getString("apellido1ReceptorVisita"),(String)rs.getString("apellido2ReceptorVisita"));
					
					
					
				}
				cadena +=  "\"error\":false,\"mensaje\":\"\"";
				cadena += "}";
				
				out.print(cadena);
				
				conex.Close();
			}
		
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			String cadena = String.format("{\"error\":true,\"mensaje\":%s\"}",e.getMessage());
			out.print(cadena);
		}
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		procesar(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		procesar(request,response);
	}

}
