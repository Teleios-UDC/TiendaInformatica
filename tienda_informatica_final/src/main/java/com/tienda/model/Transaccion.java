package com.tienda.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaccion {
    
    private int idTransaccion;
    private LocalDateTime FechaHora;
    private double precio;
    private String tipoPago;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, LocalDateTime fechaHora, double precio, String tipoPago) {
        this.idTransaccion = idTransaccion;
        this.FechaHora = fechaHora; 
        this.precio = precio;
        this.tipoPago = tipoPago;
    }

    // Getters y Setters

    public LocalDateTime getFechaHora() {
        return FechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        FechaHora = fechaHora;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}