package com.monopatines.entities;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monopatines")
public class Monopatin {

    @Id
    private int id;

    private String estado;
    private double kmRecorridos;
    private double longitud;
    private double latitud;
    private int tiempoUso;
    private int tiempoPausa;
    private String idParada;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public double getKmRecorridos() {return kmRecorridos;}

    public void setKmRecorridos(double kmRecorridos) {this.kmRecorridos = kmRecorridos;}

    public double getLongitud() {return longitud;}

    public void setLongitud(double longitud) {this.longitud = longitud;}

    public double getLatitud() {return latitud;}

    public void setLatitud(double latitud) {this.latitud = latitud;}

    public int getTiempoUso() {return tiempoUso;}

    public void setTiempoUso(int tiempoUso) {this.tiempoUso = tiempoUso;}

    public int getTiempoPausa() {return tiempoPausa;}

    public void setTiempoPausa(int tiempoPausa) {this.tiempoPausa = tiempoPausa;}

    public String getIdParada() {return idParada;}

    public void setIdParada(String idParada) {this.idParada = idParada;}

}
