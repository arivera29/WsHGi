package hgi.rest;

import hgi.controlador.ControladorEntrega;
import hgi.controlador.conexion;
import hgi.entidades.Entrega;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/entrega")
public class WsEntrega {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response getEntregarExpediente(Entrega entrega) throws Exception {
		String result = "";
		conexion conex = new conexion();
		ControladorEntrega controlador = new ControladorEntrega(conex);
		controlador.setEntrega(entrega);
		if (controlador.EntregarExpediente()) {
			result = "OK";
		}else {
			result = "FAIL";
		}
		return Response.status(201).entity(result).build();
    }

}
