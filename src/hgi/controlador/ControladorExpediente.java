package hgi.controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import hgi.entidades.EstadoExpediente;
import hgi.entidades.Expediente;

public class ControladorExpediente {
	private conexion conex;
	private Expediente expediente;
	public conexion getConex() {
		return conex;
	}
	public void setConex(conexion conex) {
		this.conex = conex;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public ControladorExpediente(conexion conex) {
		super();
		this.conex = conex;
	}
	
	public boolean FindExpediente(String arg) throws SQLException {
		boolean respuesta = false;
		String sql = "SELECT _number,nic, direccion,departamento,municipio,localidad,"
				+ "referenciaDireccion,acceso, nombreTitularContrato,"
				+ "apellido1TitularContrato,apellido2TitularContrato,"
				+ "cedulaTitularContrato, nombreReceptorVisita,"
				+ "apellido1ReceptorVisita,apellido2ReceptorVisita,"
				+ "cedulaReceptorVisita, ZonaDesc,tipoCliente "
				+ " FROM actas, zonas "
				+ " WHERE Delegacion = zonas.ZonaCodi "
				+ " AND concat(_number,nic) = ? ";
		
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setString(1, arg);
		java.sql.ResultSet rs = conex.Query(pst);

		if  (rs.next()) {
			expediente = new Expediente();
			expediente.setNumeroExpediente(arg);
			expediente.setNic(rs.getString("nic"));
			expediente.setActa(rs.getInt("_number"));
			expediente.setCliente((String)rs.getString("nombreTitularContrato") + " " + (String)rs.getString("apellido1TitularContrato") + " " + (String)rs.getString("apellido2TitularContrato"));
			expediente.setReceptor((String)rs.getString("nombreReceptorVisita") + " " + (String)rs.getString("apellido1ReceptorVisita") + " " + (String)rs.getString("apellido2ReceptorVisita"));
			expediente.setDepartamento(rs.getString("departamento"));
			expediente.setMunicipio(rs.getString("municipio"));
			expediente.setLocalidad(rs.getString("localidad"));
			expediente.setDireccion(rs.getString("direccion"));
			expediente.setDirReferencia(rs.getString("referenciaDireccion"));
			expediente.setBarrio(rs.getString("acceso"));
			expediente.setDelegacion(rs.getString("ZonaDesc"));
			expediente.setTipoCliente(rs.getString("tipoCliente"));
			
			respuesta = true;
		}
		
		
		return respuesta;
	}
	
	public EstadoExpediente ConsultaEstadoExpediente(String arg) throws SQLException {
		EstadoExpediente obj = new EstadoExpediente();
		
		String sql = "SELECT actas._number,actas.nic,Zonas.ZonaDesc,"
				+ "Mensajeria.GuiaMensajeria,Mensajeria.EstadoEntrega,"
				+ "CONCAT(_number,nic) expediente, Mensajeria.UploadImagen,"
				+ "Mensajeria.MensFesi "
				+ " FROM actas,mensajeria,zonas "
				+ "WHERE actas._number = Mensajeria.MensActa "
				+ "AND Mensajeria.Delegacion = Zonas.ZonaCodi "
				+ "AND CONCAT(_number,nic) = ?";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setString(1, arg);
		java.sql.ResultSet rs = conex.Query(pst);

		if  (rs.next()) {
			obj.setDelegacion(rs.getString("ZonaDesc"));
			obj.setExpediente(rs.getString("expediente"));
			obj.setActa(rs.getString("_number"));
			obj.setNic(rs.getString("nic"));
			obj.setFechaMensajeria(rs.getDate("MensFesi"));
			if (rs.getString("EstadoEntrega") != null) {
				obj.setEstado(rs.getString("EstadoEntrega"));
			}
			if (rs.getString("GuiaMensajeria") != null) {
				obj.setGuia(rs.getString("GuiaMensajeria"));
			}
			
			obj.setFotoGuia(rs.getInt("UploadImagen")==1?true:false);
		}
		
		return obj;
	}
	
	public ArrayList<EstadoExpediente> ExpedientesPendientes(String arg) throws SQLException {
		ArrayList<EstadoExpediente> lista = new ArrayList<EstadoExpediente>();
		
		String sql = "SELECT actas._number,actas.nic,Zonas.ZonaDesc,"
				+ "Mensajeria.GuiaMensajeria,Mensajeria.EstadoEntrega,"
				+ "CONCAT(_number,nic) expediente, Mensajeria.UploadImagen,"
				+ "Mensajeria.MensFesi"
				+ " FROM actas,mensajeria,zonas "
				+ "WHERE actas._number = Mensajeria.MensActa "
				+ "AND Mensajeria.Delegacion = Zonas.ZonaCodi "
				+ "AND Mensajeria.MensEsta=1 "
				+ "AND (EstadoEntrega = '' OR UploadImagen = 0) "
				+ "AND Mensajeria.MensOper = ? "
                                + "ORDER BY Mensajeria.MensFesi ASC";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setString(1, arg);
		java.sql.ResultSet rs = conex.Query(pst);

		while  (rs.next()) {
			EstadoExpediente obj = new EstadoExpediente();
			obj.setDelegacion(rs.getString("ZonaDesc"));
			obj.setExpediente(rs.getString("expediente"));
			obj.setActa(rs.getString("_number"));
			obj.setNic(rs.getString("nic"));
			obj.setFechaMensajeria(rs.getDate("MensFesi"));
			if (rs.getString("EstadoEntrega") != null) {
				obj.setEstado(rs.getString("EstadoEntrega"));
			}
			if (rs.getString("GuiaMensajeria") != null) {
				obj.setGuia(rs.getString("GuiaMensajeria"));
			}
			obj.setFotoGuia(rs.getInt("UploadImagen")==1?true:false);
			lista.add(obj);
		}
		
		return lista;
	}
	public ArrayList<EstadoExpediente> ExpedientesPendientes() throws SQLException {
		ArrayList<EstadoExpediente> lista = new ArrayList<EstadoExpediente>();
		
		String sql = "SELECT actas._number,actas.nic,Zonas.ZonaDesc,"
				+ "Mensajeria.GuiaMensajeria,Mensajeria.EstadoEntrega,"
				+ "CONCAT(_number,nic) expediente, Mensajeria.UploadImagen,"
				+ "Mensajeria.MensFesi"
				+ " FROM actas,mensajeria,zonas "
				+ "WHERE actas._number = Mensajeria.MensActa "
				+ "AND Mensajeria.Delegacion = Zonas.ZonaCodi "
				+ "AND Mensajeria.MensEsta=1 "
				+ "AND (EstadoEntrega = '' OR UploadImagen = 0) "
                                + "ORDER BY Mensajeria.MensFesi ASC";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		java.sql.ResultSet rs = conex.Query(pst);

		while  (rs.next()) {
			EstadoExpediente obj = new EstadoExpediente();
			obj.setDelegacion(rs.getString("ZonaDesc"));
			obj.setExpediente(rs.getString("expediente"));
			obj.setActa(rs.getString("_number"));
			obj.setNic(rs.getString("nic"));
			obj.setFechaMensajeria(rs.getDate("MensFesi"));
			if (rs.getString("EstadoEntrega") != null) {
				obj.setEstado(rs.getString("EstadoEntrega"));
			}
			if (rs.getString("GuiaMensajeria") != null) {
				obj.setGuia(rs.getString("GuiaMensajeria"));
			}
			obj.setFotoGuia(rs.getInt("UploadImagen")==1?true:false);
			lista.add(obj);
		}
		
		return lista;
	}
        
        public ArrayList<EstadoExpediente> ExpedientesPendientes(int year) throws SQLException {
		ArrayList<EstadoExpediente> lista = new ArrayList<EstadoExpediente>();
		
		String sql = "SELECT actas._number,actas.nic,Zonas.ZonaDesc,"
				+ "Mensajeria.GuiaMensajeria,Mensajeria.EstadoEntrega,"
				+ "CONCAT(_number,nic) expediente, Mensajeria.UploadImagen,"
				+ "Mensajeria.MensFesi"
				+ " FROM actas,mensajeria,zonas "
				+ "WHERE actas._number = Mensajeria.MensActa "
				+ "AND Mensajeria.Delegacion = Zonas.ZonaCodi "
				+ "AND Mensajeria.MensEsta=1 "
				+ "AND (EstadoEntrega = '' OR UploadImagen = 0) "
                                + "AND YEAR(MensFesi) = ? "
                                + "ORDER BY Mensajeria.MensFesi DESC";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setInt(1, year);
		java.sql.ResultSet rs = conex.Query(pst);

		while  (rs.next()) {
			EstadoExpediente obj = new EstadoExpediente();
			obj.setDelegacion(rs.getString("ZonaDesc"));
			obj.setExpediente(rs.getString("expediente"));
			obj.setActa(rs.getString("_number"));
			obj.setNic(rs.getString("nic"));
			obj.setFechaMensajeria(rs.getDate("MensFesi"));
			if (rs.getString("EstadoEntrega") != null) {
				obj.setEstado(rs.getString("EstadoEntrega"));
			}
			if (rs.getString("GuiaMensajeria") != null) {
				obj.setGuia(rs.getString("GuiaMensajeria"));
			}
			obj.setFotoGuia(rs.getInt("UploadImagen")==1?true:false);
			lista.add(obj);
		}
		
		return lista;
	}
        
	public boolean ExisteActa(int _number) throws SQLException {
		String sql = "SELECT _number FROM Actas WHERE _number=?";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setInt(1,_number);
		java.sql.ResultSet rs = conex.Query(pst);

		if  (rs.next()) {
			return true;
		}
		return false;
	}
        
        public long PendienteGestion () throws SQLException {
            long contador =0;
            String sql = "SELECT count(1) as contador FROM Mensajeria WHERE EstadoEntrega = '' OR EstadoEntrega IS NULL";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		java.sql.ResultSet rs = conex.Query(pst);
		if  (rs.next()) {
			contador = rs.getLong("contador");
		}
            return contador;
        }
        
        public long PendienteGestion (int year) throws SQLException {
            long contador =0;
            String sql = "SELECT count(1) as contador FROM Mensajeria WHERE (EstadoEntrega = '' OR EstadoEntrega IS NULL) AND YEAR(MensFesi) = ?";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setInt(1,year);
		java.sql.ResultSet rs = conex.Query(pst);
		if  (rs.next()) {
			contador = rs.getLong("contador");
		}
            return contador;
        }
        public long PendienteImagen () throws SQLException {
            long contador =0;
            String sql = "SELECT count(1) as contador FROM Mensajeria WHERE UploadImagen = 0";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		java.sql.ResultSet rs = conex.Query(pst);
		if  (rs.next()) {
			contador = rs.getLong("contador");
		}
            return contador;
        }
        
        public long PendienteImagen (int year) throws SQLException {
            long contador =0;
            String sql = "SELECT count(1) as contador FROM Mensajeria WHERE UploadImagen = 0 AND YEAR(MensFesi) = ?";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
                pst.setInt(1,year);
		java.sql.ResultSet rs = conex.Query(pst);
		if  (rs.next()) {
			contador = rs.getLong("contador");
		}
            return contador;
        }
}
