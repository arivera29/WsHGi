package hgi.rest;

import hgi.controlador.ControladorExpediente;
import hgi.controlador.conexion;
import hgi.entidades.EstadoExpediente;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/estadoExpediente/")
public class WsEstadoExpediente {

	@GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public EstadoExpediente getExpediente(@PathParam("param") String arg) throws Exception {
		EstadoExpediente expediente = new EstadoExpediente();

			conexion conex = new conexion();
			ControladorExpediente controlador = new ControladorExpediente(conex);
			expediente = controlador.ConsultaEstadoExpediente(arg);
			conex.Close();
		return expediente;
    }
	
}
