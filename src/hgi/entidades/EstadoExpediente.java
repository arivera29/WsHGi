package hgi.entidades;

public class EstadoExpediente {
	String expediente;
	String acta;
	String nic;
	java.util.Date fechaMensajeria;
	String estado;
	String guia;
	String delegacion;
	boolean fotoGuia;
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getGuia() {
		return guia;
	}
	public void setGuia(String guia) {
		this.guia = guia;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public boolean isFotoGuia() {
		return fotoGuia;
	}
	public void setFotoGuia(boolean fotoGuia) {
		this.fotoGuia = fotoGuia;
	}
	
	

	public String getActa() {
		return acta;
	}
	public void setActa(String acta) {
		this.acta = acta;
	}
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public java.util.Date getFechaMensajeria() {
		return fechaMensajeria;
	}
	public void setFechaMensajeria(java.util.Date fechaMensajeria) {
		this.fechaMensajeria = fechaMensajeria;
	}
	public EstadoExpediente() {
		this.delegacion="";
		this.guia="";
		this.expediente="";
		this.estado="";
		this.fotoGuia=false;
		this.acta = "";
		this.nic = "";
	}
	
}
