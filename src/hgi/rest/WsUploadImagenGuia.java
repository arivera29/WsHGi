package hgi.rest;

import hgi.controlador.ControladorExpediente;
import hgi.controlador.ControladorImagenGuia;
import hgi.controlador.conexion;
import hgi.entidades.ImagenGuia;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/uploadImagenGuia")
public class WsUploadImagenGuia {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response getImagenGuia(ImagenGuia infoImagen) throws Exception {
		String result = "";
		if (!infoImagen.getActa().equals("") && !infoImagen.getImagenBase64().equals("")) {
			conexion conex = new conexion();
			ControladorExpediente c1 = new ControladorExpediente(conex);
			if (c1.ExisteActa(Integer.parseInt(infoImagen.getActa()))) {
				ControladorImagenGuia controlador = new ControladorImagenGuia(conex);
				controlador.setInfoImagen(infoImagen);
				if (controlador.UploadImgen()) {
					result = "OK";
				}else {
					result = "FAIL";
				}
			}else {
				result = "ACTA NO EXISTE";
			}
		}else {
			result = "PARAMETROS INCORRECTOS";
		}
		return Response.status(201).entity(result).build();
    }

}
