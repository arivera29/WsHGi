package hgi.controlador;

import java.sql.SQLException;

import hgi.entidades.Devolucion;
import hgi.entidades.Expediente;
import java.text.SimpleDateFormat;

public class ControladorDevolucion {
	private conexion conex;
	private Devolucion devolucion;
	public conexion getConex() {
		return conex;
	}
	public void setConex(conexion conex) {
		this.conex = conex;
	}
	public Devolucion getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(Devolucion devolucion) {
		this.devolucion = devolucion;
	}
	public ControladorDevolucion(conexion conex) {
		super();
		this.conex = conex;
	}
	public ControladorDevolucion(conexion conex, Devolucion devolucion) {
		super();
		this.conex = conex;
		this.devolucion = devolucion;
	}
	
	public boolean DevolverExpediente() throws Exception {
		boolean respuesta = false;
		
		if (devolucion == null) {
			throw new Exception("Tipo de dato no valido. Null");
		}
		
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                
		String expediente = devolucion.getNumeroExpediente();
		ControladorExpediente controlador = new ControladorExpediente(conex);
		if (controlador.FindExpediente(expediente)) {
			Expediente exp = controlador.getExpediente();
			String sql = "UPDATE Mensajeria SET FechaEntregaExpe=?, CodigoMensajero=?,"
					+ "Latitud=?, Longitud=?, GuiaMensajeria=?, FechaUpdate=SYSDATETIME(),"
					+ "modoUpdate='webservice',estadoEntrega='D',OsMensajeria=?, MensCaDe=? "
					+ "WHERE MensActa=?";
			java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(formatter.parse(devolucion.getFechaDevolucion()).getTime()));
			pst.setString(2, devolucion.getCodigoMensajero());
			pst.setString(3, devolucion.getLatitud());
			pst.setString(4, devolucion.getLongitud());
			pst.setString(5, devolucion.getNumeroGuia());
			pst.setString(6, devolucion.getNumeroOrden());
			pst.setString(7, devolucion.getCausalDevolucion());
			pst.setInt(8, exp.getActa() );
			
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
