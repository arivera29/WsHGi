/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.entidades;

/**
 *
 * @author Aimer
 */
public class IndicadorMensajeria {
    private int peticionPCG;
    private int peticionPG;
    private int peticionUploadImagen;
    private int peticionEntrega;
    private int peticionDevolucion;

    public int getPeticionPCG() {
        return peticionPCG;
    }

    public void setPeticionPCG(int peticionPCG) {
        this.peticionPCG = peticionPCG;
    }

    public int getPeticionPG() {
        return peticionPG;
    }

    public void setPeticionPG(int peticionPG) {
        this.peticionPG = peticionPG;
    }

    public int getPeticionUploadImagen() {
        return peticionUploadImagen;
    }

    public void setPeticionUploadImagen(int peticionUploadImagen) {
        this.peticionUploadImagen = peticionUploadImagen;
    }

    public int getPeticionEntrega() {
        return peticionEntrega;
    }

    public void setPeticionEntrega(int peticionEntrega) {
        this.peticionEntrega = peticionEntrega;
    }

    public int getPeticionDevolucion() {
        return peticionDevolucion;
    }

    public void setPeticionDevolucion(int peticionDevolucion) {
        this.peticionDevolucion = peticionDevolucion;
    }

    public IndicadorMensajeria() {
        this.peticionPCG=0;
        this.peticionPG=0;
        this.peticionUploadImagen=0;
        this.peticionEntrega=0;
        this.peticionDevolucion=0;
    
    }
    
    
    
}
