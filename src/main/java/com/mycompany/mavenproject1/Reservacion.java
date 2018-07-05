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
    private int IdCliente;
    private String FechaDeLlegada;
    private String FechaDeSalida;

    public Reservacion(int IdCliente, String FechaDeLlegada, String FechaDeSalida) {
        this.IdCliente = IdCliente;
        this.FechaDeLlegada = FechaDeLlegada;
        this.FechaDeSalida = FechaDeSalida;
    }

    public int getIdcliente() {
        return IdCliente;
    }

    public void setIdcliente(int Idcliente) {
        this.IdCliente = Idcliente;
    }

    public String getFechaDeLlegada() {
        return FechaDeLlegada;
    }

    public void setFechaDeLlegada(String FechaDeLlegada) {
        this.FechaDeLlegada = FechaDeLlegada;
    }

    public String getFechaDeSalida() {
        return FechaDeSalida;
    }

    public void setFechaDeSalida(String FechaDeSalida) {
        this.FechaDeSalida = FechaDeSalida;
    }
    
    
    
}
