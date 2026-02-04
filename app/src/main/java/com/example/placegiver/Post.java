package com.example.placegiver;

import java.io.Serializable;

public class Post implements Serializable {

    private int id;
    private String texto;
    private String fechaPublicacion;
    private String usuario;
    private int idCategoria;

    public Post(int id, String texto, String fechaPublicacion, String usuario, int idCategoria) {
        this.id = id;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario = usuario;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getUsuario() {
        return usuario;

    }

    public void setUsuario(String idUsuario) {
        this.usuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
