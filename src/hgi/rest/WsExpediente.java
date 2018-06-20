package hgi.rest;

import hgi.controlador.ControladorExpediente;
import hgi.controlador.conexion;
import hgi.entidades.Expediente;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/expediente")
public class WsExpediente {

	@GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Expediente getExpediente(@PathParam("param") String arg) throws Exception {
        Expediente expediente = new Expediente();

			conexion conex = new conexion();
			ControladorExpediente controlador = new ControladorExpediente(conex);
			if (controlador.FindExpediente(arg)) {
				expediente = controlador.getExpediente();
			}else {
				throw new Exception("FAIL, No existe expediente");
			}
		return expediente;
    }
	
}
