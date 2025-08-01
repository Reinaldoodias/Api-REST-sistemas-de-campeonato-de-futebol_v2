package acad.ifma.edu.campeonato_v2.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate data;

    @OneToOne
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;

    @ManyToOne
    private Time mandante;
    @ManyToOne
    private Time visitante;
    @ManyToOne
    private Campeonato campeonato;

    @ManyToOne
    private Estadio estadio;

    public Partida(LocalDate data, Resultado resultado, Time mandante, Time visitante, Campeonato campeonato, Estadio estadio) {
        this.data = data;
        this.resultado = resultado;
        this.mandante = mandante;
        this.visitante = visitante;
        this.campeonato = campeonato;
        this.estadio = estadio;
    }

    public Partida() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public Time getMandante() {
        return mandante;
    }

    public void setMandante(Time mandante) {
        this.mandante = mandante;
    }

    public Time getVisitante() {
        return visitante;
    }

    public void setVisitante(Time visitante) {
        this.visitante = visitante;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }
}
