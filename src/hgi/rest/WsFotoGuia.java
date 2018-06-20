package hgi.rest;

import hgi.controlador.Base64;
import hgi.controlador.ControladorDocumento;
import hgi.controlador.conexion;
import hgi.entidades.Documento;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Path("/uploadFotoGuia")
public class WsFotoGuia {
	
	@POST
	@Consumes
	public Response upload(InputStream incomingData) throws IOException, DocumentException, SQLException {
		String result = "FAIL";
		
		DataInputStream in = new DataInputStream(incomingData);
		String acta = in.readUTF();
		String filename = in.readUTF();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		StringBuffer buf = new StringBuffer();
		String line;
		
		while ((line = r.readLine()) != null) {
			buf.append(line);
		}
		
		String directorio = "H:\\HGI2\\Documentos\\GuiaEnvioMensajeria\\";
		File dir = new File(directorio);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String name = AhoraToString() + "_GUIA_" + acta + "_" + filename;
		String fnameImage = directorio +  name ;
		String fnamePDF = fnameImage + ".pdf";
		String s = buf.toString();
		
        
        FileOutputStream fos = new FileOutputStream(fnameImage);
        byte[] bytes = Base64.decode(s);
        fos.write(bytes);
        fos.close();
        
        
        createPdf(fnamePDF, fnameImage);
        
        File f1 = new File(fnameImage);
        File f2 = new File(fnamePDF);
        
        if (f2.exists() && f2.length() > 0) {
	        Documento documento = new Documento();
	        documento.setActa(Integer.parseInt(acta));
	        documento.setTipo(14); // Guia de envio
	        documento.setVerificado(1);
	        documento.setSincronizado(1);
	        documento.setUsuarioCarga("webservice");
	        documento.setUsuarioVerifica("webservice");
	        documento.setUrl("");
	        documento.setUbicacion("File/Documentos/GuiaEnvioMensajeria/" + name + ".pdf");
	        conexion conex = new conexion();
	        ControladorDocumento controlador = new ControladorDocumento(conex);
	        if (controlador.Add(documento)) {
	        	result = "OK";
	        }
        }
        
        f1.delete();  // Se borra el archivo de Imagen
        
        
		
		return Response.status(201).entity(result).build();
	}
	
	private String AhoraToString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		java.util.Date fechaActual = new java.util.Date();
		String fecha = dateFormat.format(fechaActual);
		return fecha;
	}
	
	public void createPdf(String filename, String fileFoto) throws IOException, DocumentException
    {        
        Document document = new Document(PageSize.LETTER , 36, 36, 54, 36);
        Paragraph parrafo;
        Image imagen = Image.getInstance(fileFoto); 
        imagen.scaleToFit(400, 400); // Scale
        
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();
        //Creamos una cantidad significativa de paginas para probar el encabezado
        parrafo = new Paragraph("FOTO GUIA");
        parrafo.setAlignment(Element.ALIGN_CENTER);

        imagen.setAlignment(Element.ALIGN_CENTER);

        document.add(parrafo);
        document.add(imagen);
        
        document.close();        
    }

}
