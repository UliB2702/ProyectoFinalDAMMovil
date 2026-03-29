package com.example.placegiver;

import java.io.Serializable;

/**
 * Object that contains a Post's data
 * */
public class Post implements Serializable {

    private int id;
    private String texto;
    private String fechaPublicacion;
    private String usuario;
    private int idCategoria;

    /**
     * Creates a Post object filling all the variables
     * @param id Id of the Post
     * @param texto Text of the post
     * @param fechaPublicacion The post's creation date
     * @param usuario Name of the user who made the post
     * @param idCategoria Category's id where the post belongs to
     */
    public Post(int id, String texto, String fechaPublicacion, String usuario, int idCategoria) {
        this.id = id;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario = usuario;
        this.idCategoria = idCategoria;
    }

    /**
     * Returns the Id of the post
     * @return Id of the post
     */
    public int getId() {
        return id;
    }


    /**
     * Sets a new Id for the post
     * @param id New Id for the post
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the text of the post
     * @return Text of the post
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets a new text for the post
     * @param texto A new text for the post
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Returns the creation's date of the post
     * @return Creation's date of the post
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Sets a new creation's date for the post
     * @param fechaPublicacion A new creation's date for the post
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    /**
     * Returns the user who created the post
     * @return User who created the post
     */
    public String getUsuario() {
        return usuario;

    }
    /**
     * Sets a new user name who created the post
     * @param idUsuario A new user
     */
    public void setUsuario(String idUsuario) {
        this.usuario = idUsuario;
    }
    /**
     * Returns the category's id where the post belongs to
     * @return Category's id where the post belongs to
     */
    public int getIdCategoria() {
        return idCategoria;
    }
    /**
     * Sets a new category's id where the post belongs to
     * @param idCategoria New category where the post belongs to
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
