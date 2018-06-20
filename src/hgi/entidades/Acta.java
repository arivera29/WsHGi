/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.entidades;

import java.util.ArrayList;

/**
 *
 * @author aimerrivera
 */
public class Acta {
    private int numeroActa;
    private String nic;
    private String fechaActa;
    private String numeroFR;
    private ArrayList<Archivo> documentos;

    public int getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(int numeroActa) {
        this.numeroActa = numeroActa;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFechaActa() {
        return fechaActa;
    }

    public void setFechaActa(String fechaActa) {
        this.fechaActa = fechaActa;
    }

    public String getNumeroFR() {
        return numeroFR;
    }

    public void setNumeroFR(String numeroFR) {
        this.numeroFR = numeroFR;
    }

    public ArrayList<Archivo> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Archivo> documentos) {
        this.documentos = documentos;
    }
    
    
    
}
