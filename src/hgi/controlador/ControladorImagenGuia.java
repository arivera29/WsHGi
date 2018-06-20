package hgi.controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import hgi.entidades.Documento;
import hgi.entidades.ImagenGuia;

public class ControladorImagenGuia {

    private conexion conex;
    private ImagenGuia infoImagen;

    public conexion getConex() {
        return conex;
    }

    public void setConex(conexion conex) {
        this.conex = conex;
    }

    public ImagenGuia getInfoImagen() {
        return infoImagen;
    }

    public void setInfoImagen(ImagenGuia infoImagen) {
        this.infoImagen = infoImagen;
    }

    public ControladorImagenGuia(conexion conex) {
        super();
        this.conex = conex;
    }

    public ControladorImagenGuia(conexion conex, ImagenGuia infoImagen) {
        super();
        this.conex = conex;
        this.infoImagen = infoImagen;
    }

    public boolean UploadImgen() throws IOException, DocumentException, SQLException {
        if (infoImagen != null) {
            String acta = infoImagen.getActa();
            String contenido = infoImagen.getImagenBase64();

            String directorio = "G:\\Guias\\";
            File dir = new File(directorio);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String name = AhoraToString() + "_GUIA_" + acta + ".jpg";
            String fnameImage = directorio + name;
            String fnamePDF = fnameImage + ".pdf";

            FileOutputStream fos = new FileOutputStream(fnameImage);
            byte[] bytes = Base64.decode(contenido);
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
                documento.setUbicacion("File/Guias/" + name + ".pdf");
                conexion conex = new conexion();
                ControladorDocumento controlador = new ControladorDocumento(conex);
                if (controlador.Add(documento)) {
                    f1.delete();  // Se borra el archivo de Imagen
                    return true;
                } else {
                    
                    f2.delete();
                    throw new SQLException("Error al agregar el documento");
                }
            }

            f1.delete();  // Se borra el archivo de Imagen

        }
        return false;
    }

    private String AhoraToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        java.util.Date fechaActual = new java.util.Date();
        String fecha = dateFormat.format(fechaActual);
        return fecha;
    }

    public void createPdf(String filename, String fileFoto) throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER, 36, 36, 54, 36);
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
