package hgi.rest;

import hgi.controlador.ControladorCausalDevolucion;
import hgi.controlador.conexion;
import hgi.entidades.CausalDevolucion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/causaldevolucion")
public class WsCausalDevolucion {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CausalDevolucion> getCodigosCausal() throws SQLException {
		conexion conex = new conexion();
		ControladorCausalDevolucion controlador = new ControladorCausalDevolucion(conex);
		ArrayList<CausalDevolucion> lista = controlador.List(1);
		return lista;
	}
}
