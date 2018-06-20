/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aimer
 */
public class Utilidades {

    public static void AgregarLog(String log, String filename) {
        FileWriter fichero = null;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            fichero = new FileWriter("C:\\Temp\\" + filename, true);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (fichero != null) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(fichero);
                java.util.Date fecha = new Date();
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                pw.append(df.format(fecha) + "\t" + log + "\r\n");
                fichero.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
