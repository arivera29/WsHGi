package hgi.controlador;

import java.sql.SQLException;

import hgi.entidades.Documento;

public class ControladorDocumento {
	private conexion conex;
	private Documento documento;
	public conexion getConex() {
		return conex;
	}
	public void setConex(conexion conex) {
		this.conex = conex;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public ControladorDocumento(conexion conex, Documento documento) {
		super();
		this.conex = conex;
		this.documento = documento;
	}
	public ControladorDocumento(conexion conex) {
		super();
		this.conex = conex;
	}
	
	public boolean Add(Documento documento) throws SQLException {
		boolean resultado = false;
		String sql = "INSERT INTO Documentos (DocuActa,DocuTiDo,DocuUrRe,DocuUsCa,DocuFeCa,DocuUrLo,DocuSincro,DocuVeri,DocuUsVe,DocuFeVe) "
				+ "VALUES (?,?,?,?,SYSDATETIME(),?,?,?,?,SYSDATETIME())";
		java.sql.PreparedStatement pst = conex.getConnection().prepareStatement(sql);
		pst.setInt(1, documento.getActa());
		pst.setInt(2, documento.getTipo());
		pst.setString(3, documento.getUrl());
		pst.setString(4, documento.getUsuarioCarga());
		pst.setString(5, documento.getUbicacion());
		pst.setInt(6, documento.getSincronizado());
		pst.setInt(7, documento.getVerificado());
		pst.setString(8, documento.getUsuarioVerifica());
		if (conex.Update(pst) > 0) {
			sql = "UPDATE Mensajeria SET uploadImagen=1, fechaUploadImagen=SYSDATETIME() WHERE MensActa=?";
			pst = conex.getConnection().prepareStatement(sql);
			pst.setInt(1, documento.getActa());
			if (conex.Update(pst) > 0) {
				conex.Commit();
				resultado = true;
			}else {
				conex.Rollback();
			}
		}
		
		
		return resultado;
	}
	

}
