package hgi.controlador;

import java.sql.SQLException;

import hgi.entidades.Entrega;
import hgi.entidades.Expediente;
import java.text.SimpleDateFormat;

public class ControladorEntrega {
	private conexion conex;
	private Entrega entrega;

	public conexion getConex() {
		return conex;
	}

	public void setConex(conexion conex) {
		this.conex = conex;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public ControladorEntrega(conexion conex) {
		super();
		this.conex = conex;
	}

	public ControladorEntrega(conexion conex, Entrega entrega) {
		super();
		this.conex = conex;
		this.entrega = entrega;
	}
	
	public boolean EntregarExpediente() throws Exception {
		boolean respuesta = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (entrega == null) {
			throw new Exception("Tipo de dato no valido. Null");
		}
		
		String expediente = entrega.getNumeroExpediente();
		ControladorExpediente controlador = new ControladorExpediente(conex);
		if (controlador.FindExpediente(expediente)) {
			Expediente exp = controlador.getExpediente();
			String sql = "UPDATE Mensajeria SET FechaEntregaExpe=?, CodigoMensajero=?,"
					+ "Latitud=?, Longitud=?, GuiaMensajeria=?, FechaUpdate=SYSDATETIME(),"
					+ "modoUpdate='webservice',estadoEntrega='E',OsMensajeria=? "
					+ "WHERE MensActa=?";
			java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(formatter.parse(entrega.getFechaEntrega()).getTime()));
			pst.setString(2, entrega.getCodigoMensajero());
			pst.setString(3, entrega.getLatitud());
			pst.setString(4, entrega.getLongitud());
			pst.setString(5, entrega.getNumeroGuia());
			pst.setString(6, entrega.getNumeroOrden());
			pst.setInt(7, exp.getActa() );
			
			if (conex.Update(pst) > 0) {
				conex.Commit();
				respuesta = true;
			}
			
			
		}else {
			throw new SQLException("Expediente no existe");
		}
		return respuesta;
	}
	
	public boolean EntregarExpediente(Entrega entrega) throws Exception {
		boolean respuesta = false;
		
		if (entrega == null) {
			throw new Exception("Tipo de dato no valido. Null");
		}
		
		String expediente = entrega.getNumeroExpediente();
		ControladorExpediente controlador = new ControladorExpediente(conex);
		if (controlador.FindExpediente(expediente)) {
			Expediente exp = controlador.getExpediente();
			String sql = "UPDATE Mensajeria SET FechaEntregaExpe=?, CodigoMensajero=?,"
					+ "Latitud=?, Longitud=?, GuiaMensajeria=?, FechaUpdate=SYSDATETIME(),"
					+ "modoUpdate='webservice',estadoEntrega=1,OsMensajeria=? "
					+ "WHERE MensActa=?";
			java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
			pst.setString(1, entrega.getFechaEntrega());
			pst.setString(2, entrega.getCodigoMensajero());
			pst.setString(3, entrega.getLatitud());
			pst.setString(4, entrega.getLongitud());
			pst.setString(5, entrega.getNumeroGuia());
			pst.setString(6, entrega.getNumeroOrden());
			pst.setInt(7, exp.getActa() );
			
			if (conex.Update(pst) > 0) {
				conex.Commit();
				respuesta = true;
			}
			
			
		}else {
			throw new SQLException("Expediente no existe");
		}
		return respuesta;
	}

}
