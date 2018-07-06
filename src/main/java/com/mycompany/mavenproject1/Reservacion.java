/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author erquier
 */
public class Reservacion {
    private int IDreserva;
    private int IDcliente;
    private String fecha_llegada;
    private String fecha_salida;

    public Reservacion(int IDreserva, int IDcliente, String fecha_llegada, String fecha_salida) {
        this.IDreserva = IDreserva;
        this.IDcliente = IDcliente;
        this.fecha_llegada = fecha_llegada;
        this.fecha_salida = fecha_salida;
    }

    public int getIDreserva() {
        return IDreserva;
    }

    public void setIDreserva(int IDreserva) {
        this.IDreserva = IDreserva;
    }

    public int getIDcliente() {
        return IDcliente;
    }

    public void setIDcliente(int IDcliente) {
        this.IDcliente = IDcliente;
    }

    public String getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(String fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

   

    
        
    
    
}
