package hgi.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class srvXmlMedidores
 */
@WebServlet("/srvXmlMedidores")
public class srvXmlMedidores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public srvXmlMedidores() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void procesar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<?xml version=\"1.0\" ?>");
        if (request.getParameter("fecha_inicio") == null) {
        	out.println("<Medidores>");
        	out.println("<error>1</error>");
			out.println("<type>Parameter</type>");
			out.println("<code>-1</code>");
			out.println("<description>Falta parametro fecha_inicio</description>");
			out.println("</Medidores>");
			return;
        }
        
        if (request.getParameter("fecha_fin") == null) {
        	out.println("<Medidores>");
        	out.println("<error>1</error>");
			out.println("<type>Parameter</type>");
			out.println("<code>-1</code>");
			out.println("<description>Falta parametro fecha_fin</description>");
			out.println("</Medidores>");
			return;
        }
        if (request.getParameter("empresa") == null) {
        	out.println("<Medidores>");
        	out.println("<error>1</error>");
			out.println("<type>Parameter</type>");
			out.println("<code>-1</code>");
			out.println("<description>Falta parametro empresa</description>");
			out.println("</Medidores>");
			return;
        }
        
        String fecha_inicio = (String)request.getParameter("fecha_inicio");
        String fecha_fin = (String)request.getParameter("fecha_fin");
        String empresa = (String)request.getParameter("empresa");
        conexion conex;
		try {
			conex = new conexion();
			if (conex.getConnection() != null) {
				String sql = "SELECT Delegacion,Zonadesc,codContrata,empresaOperario,"
						+ "'' SemiDirecta, '' EsPci, nic,Actas._number ActaAnomalia, Actas.nic,"
						+ " Actas._number ordenServicio, envioLabNumCustodia selloCustodia,"
						+ " Actas.cedulaOperario, Actas.nombreOperario, Actas.apellido1Operario,"
						+ " Actas.apellido2Operario, Acta_Medidor.numero,Acta_Medidor.marca, "
						+ " Acta_Medidor.tipoMedidor, Acta_Medidor.tecnologia, Acta_Medidor.nFases,"
						+ " Actas._clientCloseTs fechaRetiro "
						+ " FROM Actas, Zonas, Acta_Medidor "
						+ " WHERE Delegacion = ZonaCodi"
						+ " AND Actas._number = Acta_Medidor._number "
						+ " AND Actas.protocolo != 0"
						+ " AND _clientCloseTs BETWEEN ? AND ? ";
				
				if (!empresa.equals("all")) {
					sql += " AND codigoEmpresa = '" + empresa + "'";
				}
			
	           java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
			   pst.setString(1, fecha_inicio);
			   pst.setString(2, fecha_fin);
			   java.sql.ResultSet rs = conex.Query(pst);
			   out.println("<Medidores>");
			   while (rs.next()) {
				   out.println("<Medidor>");
				   out.println("<codigoDelegacion>" + format(rs.getString("Delegacion")) + "</codigoDelegacion>");
				   out.println("<nombreDelegacion>" + format(rs.getString("ZonaDesc")) + "</nombreDelegacion>");
				   out.println("<codigoEmpresa>" + format(rs.getString("codContrata")) + "</codigoEmpresa>");
				   out.println("<nombreEmpresa>" + format(rs.getString("empresaOperario")) + "</nombreEmpresa>");
				   out.println("<semiDirecta>" + format(rs.getString("SemiDirecta")) + "</semiDirecta>");
				   out.println("<esPci>" + format(rs.getString("EsPci")) + "</esPci>");
				   out.println("<actaAnomalia>" + format(rs.getString("ActaAnomalia")) + "</actaAnomalia>");
				   out.println("<ordenServicio>" + format(rs.getString("ordenServicio")) + "</ordenServicio>");
				   out.println("<nic>" + format(rs.getString("nic")) + "</nic>");
				   out.println("<selloCustodia>" + format(rs.getString("selloCustodia")) + "</selloCustodia>");
				   out.println("<cedulaOperario>" + format(rs.getString("cedulaOperario")) + "</cedulaOperario>");
				   out.println("<nombreOperario>" + format(rs.getString("nombreOperario")) + "</nombreOperario>");
				   out.println("<apellido1Operario>" + format(rs.getString("apellido1Operario")) + "</apellido1Operario>");
				   out.println("<apellido2Operario>" + format(rs.getString("apellido2Operario")) + "</apellido2Operario>");
				   out.println("<numeroMedidor>" + format(rs.getString("numero")) + "</numeroMedidor>");
				   out.println("<marcaMedidor>" + format(rs.getString("marca")) + "</marcaMedidor>");
				   out.println("<tipoMedidor>" + format(rs.getString("tipoMedidor")) + "</tipoMedidor>");
				   out.println("<tecnologiaMedidor>" + format(rs.getString("tecnologia")) + "</tecnologiaMedidor>");
				   out.println("<fasesMedidor>" + format(rs.getString("nFases")) + "</fasesMedidor>");
				   out.println("<fechaRetiro>" + format(rs.getString("fechaRetiro")) + "</fechaRetiro>");
				   out.println("</Medidor>");
			   }
			   out.println("<error>0</error>");
			   out.println("<type></type>");
			   out.println("<code></code>");
			   out.println("<description></description>");
			   
			   out.println("</Medidores>");
			   
			   
				
	    		
	    	}else {
	    		out.println("<Medidores>");
	    		out.println("<error>1</error>");
				out.println("<type>SQL</type>");
				out.println("<code>E01</code>");
				out.println("<description>Error conenexion con el servidor SQL</description>");
				out.println("</Medidores>");
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("<Medidores>");
			out.println("<error>1</error>");
			out.println("<type>SQL</type>");
			out.println("<code>" + e.getErrorCode() + "</code>");
			out.println("<description>" + e.getMessage()  +"</description>");
			out.println("</Medidores>");
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
	
	private String format(String cadena) {
		if (cadena == null) {
			return "";
		}
		cadena = cadena.replace("<", "");
		cadena = cadena.replace(">", "");
		cadena = cadena.replace("[", "");
		cadena = cadena.replace("]", "");
		cadena = cadena.replace("&", "");
		cadena = cadena.replace("\t", "");
		cadena = cadena.replace("\r", "");
		cadena = cadena.replace("\n", "");
		
		return cadena;
	}

}
