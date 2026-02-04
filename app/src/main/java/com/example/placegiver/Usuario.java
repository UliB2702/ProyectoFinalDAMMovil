package com.example.placegiver;

import java.io.Serializable;
import java.sql.Date;

//TODO Cambiar la base de datos para que el nombre sea la clave primaria
public class Usuario implements Serializable {
    private String nombre;
    private String descripcion;
    private String email;
    private String password;
    private String fechaCreacion;

    public Usuario(String nombre, String descripcion, String email, String password, String fechaCreacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.email = email;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
    }


    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
