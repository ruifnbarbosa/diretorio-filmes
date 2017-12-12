package com.example.a029265.diretorio_filmes.ClassesAssistentes;

import java.io.Serializable;

public class Pesquisa implements Serializable {
    protected String id, nome;

    public Pesquisa(String id) {
        this.id = id;
    }

    public Pesquisa() {
        this.id = "-1";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
