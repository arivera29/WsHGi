/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgi.entidades;

/**
 *
 * @author aimer
 */
public class ActaResult {
    private int acta;
        private String fecha;
        private String nic;
        private String departamento;

        public ActaResult() {
            this.acta = -1;
            this.fecha = "";
            this.nic = "";
            this.departamento = "";
        }
        
        

        public int getActa() {
            return acta;
        }

        public void setActa(int acta) {
            this.acta = acta;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getNic() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic = nic;
        }

        public String getDepartamento() {
            return departamento;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }
    
    
}
