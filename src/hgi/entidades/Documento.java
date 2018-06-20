package hgi.entidades;

public class Documento {
	private int codigo;
	private int acta;
	private int tipo;
	private String url;
	private String usuarioCarga;
	private String fechaCarga;
	private String ubicacion;
	private int sincronizado;
	private int verificado;
	private String usuarioVerifica;
	private String fechaVerifica;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getActa() {
		return acta;
	}
	public void setActa(int acta) {
		this.acta = acta;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsuarioCarga() {
		return usuarioCarga;
	}
	public void setUsuarioCarga(String usuarioCarga) {
		this.usuarioCarga = usuarioCarga;
	}
	public String getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getSincronizado() {
		return sincronizado;
	}
	public void setSincronizado(int sincronizado) {
		this.sincronizado = sincronizado;
	}
	public int getVerificado() {
		return verificado;
	}
	public void setVerificado(int verificado) {
		this.verificado = verificado;
	}
	public String getUsuarioVerifica() {
		return usuarioVerifica;
	}
	public void setUsuarioVerifica(String usuarioVerifica) {
		this.usuarioVerifica = usuarioVerifica;
	}
	public String getFechaVerifica() {
		return fechaVerifica;
	}
	public void setFechaVerifica(String fechaVerifica) {
		this.fechaVerifica = fechaVerifica;
	}
	
	

}
