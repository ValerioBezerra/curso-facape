package br.facape.controlefinanceiropessoal.model;

import java.io.Serializable;

/**
 * Created by valerio on 09/12/15.
 */
public class Categoria implements Serializable {
    private int id;
    private String descricao;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
