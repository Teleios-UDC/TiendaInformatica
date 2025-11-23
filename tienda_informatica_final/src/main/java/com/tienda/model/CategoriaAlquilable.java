package com.tienda.model;


public class CategoriaAlquilable {
    
    private int idCategoriaAlquilable;
    private int idCategoria;


    private Categoria categoria;

    public CategoriaAlquilable() {
    }

    public CategoriaAlquilable(int idCategoriaAlquilable, int idCategoria) {
        this.idCategoriaAlquilable = idCategoriaAlquilable;
        this.idCategoria = idCategoria;
    }

    // Getters y Setters

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdCategoriaAlquilable() {
        return idCategoriaAlquilable;
    }

    public void setIdCategoriaAlquilable(int idCategoriaAlquilable) {
        this.idCategoriaAlquilable = idCategoriaAlquilable;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}