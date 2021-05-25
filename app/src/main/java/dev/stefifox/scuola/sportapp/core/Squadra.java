package dev.stefifox.scuola.sportapp.core;

public class Squadra {

    private Sport sport;
    private int id;
    private String nome;
    private String codNazione;

    public Squadra(int id, Sport sport, String nome, String codNazione){
        this.id = id;
        this.sport = sport;
        this.nome = nome;
        this.codNazione = codNazione;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
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

    public String getCodNazione() {
        return codNazione;
    }

    public void setCodNazione(String codNazione) {
        this.codNazione = codNazione;
    }
}
