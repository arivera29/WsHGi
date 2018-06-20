/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.entidades;

import java.util.ArrayList;

/**
 *
 * @author aimer
 */
public class BuscarActaResult {

    private boolean encontrado;
    private boolean error;
    private String msgError;

    private ArrayList<ActaResult> actas;

    public BuscarActaResult() {
        this.encontrado = false;
        this.error = false;
        this.msgError = "";
        this.actas = new ArrayList<ActaResult>();
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public void setError(String error) {
        this.encontrado = false;
        this.error = true;
        this.msgError = error;

    }

    public ArrayList<ActaResult> getActas() {
        return actas;
    }

    public void setActas(ArrayList<ActaResult> actas) {
        this.actas = actas;
    }
    
    

   

}
