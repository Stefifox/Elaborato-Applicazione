package dev.stefifox.scuola.sportapp.core;

public class Sport {

    private int id;
    private String nome;
    private String descizione;

    public Sport(int id, String nome, String descizione){
        this.id = id;
        this.nome = nome;
        this.descizione = descizione;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescizione() {
        return descizione;
    }

    public void setDescizione(String descizione) {
        this.descizione = descizione;
    }
}
