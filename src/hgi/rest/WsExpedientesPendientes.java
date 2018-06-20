package hgi.rest;

import java.util.ArrayList;

import hgi.controlador.ControladorExpediente;
import hgi.controlador.conexion;
import hgi.entidades.EstadoExpediente;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/ExpedientesPendientes/")
public class WsExpedientesPendientes {

	@GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<EstadoExpediente> getExpediente(@PathParam("param") String arg) throws Exception {
			ArrayList<EstadoExpediente> lista = new ArrayList<EstadoExpediente>();

			conexion conex = new conexion();
			ControladorExpediente controlador = new ControladorExpediente(conex);
			lista = controlador.ExpedientesPendientes(arg);
			conex.Close();
		return lista;
    }
	
}
