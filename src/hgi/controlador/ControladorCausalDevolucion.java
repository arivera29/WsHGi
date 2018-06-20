package hgi.controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import hgi.entidades.CausalDevolucion;

public class ControladorCausalDevolucion {
	private conexion conex;
	private CausalDevolucion causal;
	public conexion getConex() {
		return conex;
	}
	public void setConex(conexion conex) {
		this.conex = conex;
	}
	public CausalDevolucion getCausal() {
		return causal;
	}
	public void setCausal(CausalDevolucion causal) {
		this.causal = causal;
	}
	public ControladorCausalDevolucion(conexion conex) {
		super();
		this.conex = conex;
	}
	public ControladorCausalDevolucion(conexion conex, CausalDevolucion causal) {
		super();
		this.conex = conex;
		this.causal = causal;
	}
	
	public ArrayList<CausalDevolucion> List() throws SQLException {
		ArrayList<CausalDevolucion> lista = new ArrayList<CausalDevolucion>();
		String sql = "SELECT CaDeCodi, CaDeDesc, CaDeEsta FROM CausasDevolucionMensa ORDER BY CaDeDesc";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		java.sql.ResultSet rs = conex.Query(pst);
		while (rs.next()) {
			CausalDevolucion causal = new CausalDevolucion();
			causal.setCodigo(rs.getString("CaDeCodi"));
			causal.setDescripcion(rs.getString("CaDeDesc"));
			causal.setEstado(rs.getInt("CaDeEsta"));
			lista.add(causal);
		}
		return lista;
	}
	
	public ArrayList<CausalDevolucion> List(int estado) throws SQLException {
		ArrayList<CausalDevolucion> lista = new ArrayList<CausalDevolucion>();
		String sql = "SELECT CaDeCodi, CaDeDesc, CaDeEsta FROM CausasDevolucionMensa WHERE  CaDeEsta=? ORDER BY CaDeDesc";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setInt(1, estado);
		java.sql.ResultSet rs = conex.Query(pst);
		while (rs.next()) {
			CausalDevolucion causal = new CausalDevolucion();
			causal.setCodigo(rs.getString("CaDeCodi"));
			causal.setDescripcion(rs.getString("CaDeDesc"));
			causal.setEstado(rs.getInt("CaDeEsta"));
			lista.add(causal);
		}
		return lista;
	}


}
