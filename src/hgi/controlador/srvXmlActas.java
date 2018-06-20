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
 * Servlet implementation class srvXmlActas
 */
@WebServlet("/srvXmlActas")
public class srvXmlActas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public srvXmlActas() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void procesar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<?xml version=\"1.0\" ?>");
        if (request.getParameter("fecha_inicio") == null) {
        	out.println("<Actas>");
        	out.println("<error>1</error>");
			out.println("<type>Parameter</type>");
			out.println("<code>-1</code>");
			out.println("<description>Falta parametro fecha_inicio</description>");
			out.println("</Actas>");
			return;
        }
        
        if (request.getParameter("fecha_fin") == null) {
        	out.println("<Actas>");
        	out.println("<error>1</error>");
			out.println("<type>Parameter</type>");
			out.println("<code>-1</code>");
			out.println("<description>Falta parametro fecha_fin</description>");
			out.println("</Actas>");
			return;
        }
        String fecha_inicio = (String)request.getParameter("fecha_inicio");
        String fecha_fin = (String)request.getParameter("fecha_fin");
        conexion conex;
		try {
			conex = new conexion();
			if (conex.getConnection() != null) {
				String sql = "SELECT _number,Id,numeroLote,codigoEmpresa,"
						+ "tipoOrdenServicio,tipoServicio,comentario1,comentario2,"
						+ "direccion,codigoTarifa,nic,tipoCliente,estrato,"
						+ "cargaContratada,departamento,municipio,localidad,"
						+ "tipoVia,calle,numeroPuerta,duplicador,piso,"
						+ "referenciaDireccion,acceso,numeroCircuito,"
						+ "matriculaCT,nombreTitularContrato,apellido1TitularContrato,"
						+ "apellido2TitularContrato,cedulaTitularContrato,"
						+ "telefonoFijoTitularContrato,telefonoMovilTitularContrato,"
						+ "emailTitularContrato,relacionReceptorVisita,"
						+ "solicitaTecnicoReceptorVisita,aportaTestigo,"
						+ "nombreReceptorVisita,apellido1ReceptorVisita,"
						+ "apellido2ReceptorVisita,cedulaReceptorVisita,"
						+ "telefonoFijoReceptorVisita,telefonoMovilReceptorVisita,"
						+ "emailReceptorVisita,modoPagoTipo,fechaInicioIrregularidad,"
						+ "residuosRecolectados,clasificacionResiduos,ordenAseo,"
						+ "recibidoQuejas,atendidoQuejas,observaciones,EstadoActa,"
						+ "EstadoAnteriorActa,Bandeja,Asegurada,nombreTecnico,"
						+ "apellido1Tecnico,apellido2Tecnico,cedulaTecnico,"
						+ "comteTecnico,nombreTestigo,apellido1Testigo,apellido2Testigo,"
						+ "cedulaTestigo,Delegacion,codContrata,protocolo,"
						+ "BandejaAnterior,fechaCarga,usuarioCarga,obsAnomalia,"
						+ "tipoCenso,censoCargaInstalada,ValorTarifa,ValorEcdf,"
						+ "osResuelta,MedRetirado,FRAnticipado,EnergiaAnticipada,"
						+ "medidaAnomaliaVR,medidaAnomaliaVS,medidaAnomaliaVT,"
						+ "medidaAnomaliaIR,medidaAnomaliaIS,medidaAnomaliaIT,"
						+ "medidaAnomaliaTipo,nombreOperario,apellido1Operario,"
						+ "apellido2Operario,cedulaOperario,empresaOperario,"
						+ "_clientCloseTs,fechaModificacionActa,fechaOrden,"
						+ "porcentajeError,conAnomalia,subnormal "
						+ " FROM Actas "
						+ " WHERE _clientCloseTs between ? and ? ";
			
	           java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
			   pst.setString(1, fecha_inicio);
			   pst.setString(2, fecha_fin);
			   java.sql.ResultSet rs = conex.Query(pst);
			   out.println("<Actas>");
			   while (rs.next()) {
				   out.println("<Acta>");
				   out.println("<NumActa>" + format(rs.getString("_number")) + "</NumActa>");
				   out.println("<Id>" + format(rs.getString("Id")) + "</Id>");
				   out.println("<numeroLote>" + format(rs.getString("numeroLote")) + "</numeroLote>");
				   out.println("<codigoEmpresa>" + format(rs.getString("codigoEmpresa")) + "</codigoEmpresa>");
				   out.println("<tipoOrdenServicio>" + format(rs.getString("tipoOrdenServicio")) + "</tipoOrdenServicio>");
				   out.println("<tipoServicio>" + format(rs.getString("tipoServicio")) + "</tipoServicio>");
				   out.println("<comentario1>" + format(rs.getString("comentario1")) + "</comentario1>");
				   out.println("<comentario2>" + format(rs.getString("comentario2")) + "</comentario2>");
				   out.println("<direccion>" + format(rs.getString("direccion")) + "</direccion>");
				   out.println("<codigoTarifa>" + format(rs.getString("codigoTarifa")) + "</codigoTarifa>");
				   out.println("<nic>" + format(rs.getString("nic")) + "</nic>");
				   out.println("<tipoCliente>" + format(rs.getString("tipoCliente")) + "</tipoCliente>");
				   out.println("<estrato>" + format(rs.getString("estrato")) + "</estrato>");
				   out.println("<cargaContratada>" + format(rs.getString("cargaContratada")) + "</cargaContratada>");
				   out.println("<departamento>" + format(rs.getString("departamento")) + "</departamento>");
				   out.println("<municipio>" + format(rs.getString("municipio")) + "</municipio>");
				   out.println("<localidad>" + format(rs.getString("localidad")) + "</localidad>");
				   out.println("<tipoVia>" + format(rs.getString("tipoVia")) + "</tipoVia>");
				   out.println("<calle>" + format(rs.getString("calle")) + "</calle>");
				   out.println("<numeroPuerta>" + format(rs.getString("numeroPuerta")) + "</numeroPuerta>");
				   out.println("<duplicador>" + format(rs.getString("duplicador")) + "</duplicador>");
				   out.println("<piso>" + format(rs.getString("piso")) + "</piso>");
				   out.println("<referenciaDireccion>" + format(rs.getString("referenciaDireccion")) + "</referenciaDireccion>");
				   out.println("<acceso>" + format(rs.getString("acceso")) + "</acceso>");
				   out.println("<numeroCircuito>" + format(rs.getString("numeroCircuito")) + "</numeroCircuito>");
				   out.println("<matriculaCT>" + format(rs.getString("matriculaCT")) + "</matriculaCT>");
				   out.println("<nombreTitularContrato>" + format(rs.getString("nombreTitularContrato")) + "</nombreTitularContrato>");
				   out.println("<apellido1TitularContrato>" + format(rs.getString("apellido1TitularContrato")) + "</apellido1TitularContrato>");
				   out.println("<apellido2TitularContrato>" + format(rs.getString("apellido2TitularContrato")) + "</apellido2TitularContrato>");
				   out.println("<cedulaTitularContrato>" + format(rs.getString("cedulaTitularContrato")) + "</cedulaTitularContrato>");
				   out.println("<telefonoFijoTitularContrato>" + format(rs.getString("telefonoFijoTitularContrato")) + "</telefonoFijoTitularContrato>");
				   out.println("<telefonoMovilTitularContrato>" + format(rs.getString("telefonoMovilTitularContrato")) + "</telefonoMovilTitularContrato>");
				   out.println("<emailTitularContrato>" + format(rs.getString("emailTitularContrato")) + "</emailTitularContrato>");
				   out.println("<relacionReceptorVisita>" + format(rs.getString("relacionReceptorVisita")) + "</relacionReceptorVisita>");
				   out.println("<solicitaTecnicoReceptorVisita>" + format(rs.getString("solicitaTecnicoReceptorVisita")) + "</solicitaTecnicoReceptorVisita>");
				   out.println("<aportaTestigo>" + format(rs.getString("aportaTestigo")) + "</aportaTestigo>");
				   out.println("<nombreReceptorVisita>" + format(rs.getString("nombreReceptorVisita")) + "</nombreReceptorVisita>");
				   out.println("<apellido1ReceptorVisita>" + format(rs.getString("apellido1ReceptorVisita")) + "</apellido1ReceptorVisita>");
				   out.println("<apellido2ReceptorVisita>" + format(rs.getString("apellido2ReceptorVisita")) + "</apellido2ReceptorVisita>");
				   out.println("<cedulaReceptorVisita>" + format(rs.getString("cedulaReceptorVisita")) + "</cedulaReceptorVisita>");
				   out.println("<telefonoFijoReceptorVisita>" + format(rs.getString("telefonoFijoReceptorVisita")) + "</telefonoFijoReceptorVisita>");
				   out.println("<telefonoMovilReceptorVisita>" + format(rs.getString("telefonoMovilReceptorVisita")) + "</telefonoMovilReceptorVisita>");
				   out.println("<emailReceptorVisita>" + format(rs.getString("emailReceptorVisita")) + "</emailReceptorVisita>");
				   out.println("<modoPagoTipo>" + format(rs.getString("modoPagoTipo")) + "</modoPagoTipo>");
				   out.println("<fechaInicioIrregularidad>" + format(rs.getString("fechaInicioIrregularidad")) + "</fechaInicioIrregularidad>");
				   out.println("<residuosRecolectados>" + format(rs.getString("residuosRecolectados")) + "</residuosRecolectados>");
				   out.println("<clasificacionResiduos>" + format(rs.getString("clasificacionResiduos")) + "</clasificacionResiduos>");
				   out.println("<ordenAseo>" + format(rs.getString("ordenAseo")) + "</ordenAseo>");
				   out.println("<recibidoQuejas>" + format(rs.getString("recibidoQuejas")) + "</recibidoQuejas>");
				   out.println("<atendidoQuejas>" + format(rs.getString("atendidoQuejas")) + "</atendidoQuejas>");
				   out.println("<observaciones>" + format(rs.getString("observaciones")) + "</observaciones>");
				   out.println("<EstadoActa>" + format(rs.getString("EstadoActa")) + "</EstadoActa>");
				   out.println("<EstadoAnteriorActa>" + format(rs.getString("EstadoAnteriorActa")) + "</EstadoAnteriorActa>");
				   out.println("<Bandeja>" + format(rs.getString("Bandeja")) + "</Bandeja>");
				   out.println("<Asegurada>" + format(rs.getString("Asegurada")) + "</Asegurada>");
				   out.println("<nombreTecnico>" + format(rs.getString("nombreTecnico")) + "</nombreTecnico>");
				   out.println("<apellido1Tecnico>" + format(rs.getString("apellido1Tecnico")) + "</apellido1Tecnico>");
				   out.println("<apellido2Tecnico>" + format(rs.getString("apellido2Tecnico")) + "</apellido2Tecnico>");
				   out.println("<cedulaTecnico>" + format(rs.getString("cedulaTecnico")) + "</cedulaTecnico>");
				   out.println("<comteTecnico>" + format(rs.getString("comteTecnico")) + "</comteTecnico>");
				   out.println("<nombreTestigo>" + format(rs.getString("nombreTestigo")) + "</nombreTestigo>");
				   out.println("<apellido1Testigo>" + format(rs.getString("apellido1Testigo")) + "</apellido1Testigo>");
				   out.println("<apellido2Testigo>" + format(rs.getString("apellido2Testigo")) + "</apellido2Testigo>");
				   out.println("<cedulaTestigo>" + format(rs.getString("cedulaTestigo")) + "</cedulaTestigo>");
				   out.println("<Delegacion>" + format(rs.getString("Delegacion")) + "</Delegacion>");
				   out.println("<codContrata>" + format(rs.getString("codContrata")) + "</codContrata>");
				   out.println("<protocolo>" + format(rs.getString("protocolo")) + "</protocolo>");
				   out.println("<BandejaAnterior>" + format(rs.getString("BandejaAnterior")) + "</BandejaAnterior>");
				   out.println("<fechaCarga>" + format(rs.getString("fechaCarga")) + "</fechaCarga>");
				   out.println("<usuarioCarga>" + format(rs.getString("usuarioCarga")) + "</usuarioCarga>");
				   out.println("<obsAnomalia>" + format(rs.getString("obsAnomalia")) + "</obsAnomalia>");
				   out.println("<tipoCenso>" + format(rs.getString("tipoCenso")) + "</tipoCenso>");
				   out.println("<censoCargaInstalada>" + format(rs.getString("censoCargaInstalada")) + "</censoCargaInstalada>");
				   out.println("<ValorTarifa>" + format(rs.getString("ValorTarifa")) + "</ValorTarifa>");
				   out.println("<ValorEcdf>" + format(rs.getString("ValorEcdf")) + "</ValorEcdf>");
				   out.println("<osResuelta>" + format(rs.getString("osResuelta")) + "</osResuelta>");
				   out.println("<MedRetirado>" + format(rs.getString("MedRetirado")) + "</MedRetirado>");
				   out.println("<FRAnticipado>" + format(rs.getString("FRAnticipado")) + "</FRAnticipado>");
				   out.println("<EnergiaAnticipada>" + format(rs.getString("EnergiaAnticipada")) + "</EnergiaAnticipada>");
				   out.println("<medidaAnomaliaVR>" + format(rs.getString("medidaAnomaliaVR")) + "</medidaAnomaliaVR>");
				   out.println("<medidaAnomaliaVS>" + format(rs.getString("medidaAnomaliaVS")) + "</medidaAnomaliaVS>");
				   out.println("<medidaAnomaliaVT>" + format(rs.getString("medidaAnomaliaVT")) + "</medidaAnomaliaVT>");
				   out.println("<medidaAnomaliaIR>" + format(rs.getString("medidaAnomaliaIR")) + "</medidaAnomaliaIR>");
				   out.println("<medidaAnomaliaIS>" + format(rs.getString("medidaAnomaliaIS")) + "</medidaAnomaliaIS>");
				   out.println("<medidaAnomaliaIT>" + format(rs.getString("medidaAnomaliaIT")) + "</medidaAnomaliaIT>");
				   out.println("<medidaAnomaliaTipo>" + format(rs.getString("medidaAnomaliaTipo")) + "</medidaAnomaliaTipo>");
				   out.println("<nombreOperario>" + format(rs.getString("nombreOperario")) + "</nombreOperario>");
				   out.println("<apellido1Operario>" + format(rs.getString("apellido1Operario")) + "</apellido1Operario>");
				   out.println("<apellido2Operario>" + format(rs.getString("apellido2Operario")) + "</apellido2Operario>");
				   out.println("<cedulaOperario>" + format(rs.getString("cedulaOperario")) + "</cedulaOperario>");
				   out.println("<empresaOperario>" + format(rs.getString("empresaOperario")) + "</empresaOperario>");
				   out.println("<fechaCierre>" + format(rs.getString("_clientCloseTs")) + "</fechaCierre>");
				   out.println("<fechaModificacionActa>" + format(rs.getString("fechaModificacionActa")) + "</fechaModificacionActa>");
				   out.println("<fechaOrden>" + format(rs.getString("fechaOrden")) + "</fechaOrden>");
				   out.println("<porcentajeError>" + format(rs.getString("porcentajeError")) + "</porcentajeError>");
				   out.println("<conAnomalia>" + format(rs.getString("conAnomalia")) + "</conAnomalia>");
				   out.println("<subnormal>" + format(rs.getString("subnormal")) + "</subnormal>");
				   out.println("</Acta>");
			   }
			   out.println("<error>0</error>");
			   out.println("<type></type>");
			   out.println("<code></code>");
			   out.println("<description></description>");
			   
			   out.println("</Actas>");
			   
			   
				
	    		
	    	}else {
	    		out.println("<Actas>");
	    		out.println("<error>1</error>");
				out.println("<type>SQL</type>");
				out.println("<code>E01</code>");
				out.println("<description>Error conenexion con el servidor SQL</description>");
				out.println("</Actas>");
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("<Actas>");
			out.println("<error>1</error>");
			out.println("<type>SQL</type>");
			out.println("<code>" + e.getErrorCode() + "</code>");
			out.println("<description>" + e.getMessage()  +"</description>");
			out.println("</Actas>");
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
