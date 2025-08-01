package acad.ifma.edu.campeonato_v2.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int ano;
    private String nome;

    @ManyToMany
    private List<Time> times;

    @OneToMany(mappedBy = "campeonato")
    private List<Partida> partidas;

    public Campeonato(int ano, String nome, List<Time> times, List<Partida> partidas) {
        this.ano = ano;
        this.nome = nome;
        this.times = times;
        this.partidas = partidas;
    }

    public Campeonato() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
}


