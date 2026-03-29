package com.example.placegiver;

import java.io.Serializable;
import java.sql.Date;


/**
 * Object that contains an user's data
 * */
public class Usuario implements Serializable {
    private String nombre;
    private String descripcion;
    private String email;
    private String password;
    private String fechaCreacion;

    /**
     * Creates an Usuario object filling all the variables
     * @param nombre Name of the user
     * @param descripcion Description of the user
     * @param email Email of the user
     * @param password Password od the user's account
     * @param fechaCreacion The user account's creation date
     */
    public Usuario(String nombre, String descripcion, String email, String password, String fechaCreacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.email = email;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Returns the creation's date of the user
     * @return Creation's date of the user
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    /**
     * Sets a new creation's date for the user
     * @param fechaCreacion New creation's date for the user
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    /**
     * Returns the password of the user
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets a new password for the user
     * @param password New password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Returns the email of the user
     * @return Email of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets a new email for the user
     * @param email New email for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns the description of the user
     * @return Description of the user
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Sets a new description for the user
     * @param descripcion New description for the user
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Returns the name of the user
     * @return Name of the user
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Sets a new name for the user
     * @param nombre New name for the user
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
