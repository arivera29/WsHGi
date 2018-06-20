package hgi.rest;

import hgi.controlador.ControladorDevolucion;
import hgi.controlador.conexion;
import hgi.entidades.Devolucion;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/devolucion")
public class WsDevolucion {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response getExpediente(Devolucion devolucion) throws Exception {
		String result = "";
		conexion conex = new conexion();
		ControladorDevolucion controlador = new ControladorDevolucion(conex);
		controlador.setDevolucion(devolucion);
		if (controlador.DevolverExpediente()) {
			result = "OK";
		}else {
			result = "FAIL";
		}
		return Response.status(201).entity(result).build();
    }
}
