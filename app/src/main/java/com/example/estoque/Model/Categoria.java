package com.example.estoque.Model;

import java.util.Objects;

public class Categoria {

    private int idCategoria;
    private String nomeCategoria;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String toString(){
        return  this.nomeCategoria;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria == categoria.idCategoria;
    }


}
