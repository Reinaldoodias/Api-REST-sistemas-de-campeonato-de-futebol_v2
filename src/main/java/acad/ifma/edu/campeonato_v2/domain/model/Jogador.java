package acad.ifma.edu.campeonato_v2.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Jogador {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Gera o Id automaticamente
    private int id;
    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private LocalDate nascimento;
    @Getter
    @Setter
    private String Genero;
    @Getter
    @Setter
    private float altura;

    @ManyToOne
    @JoinColumn(name="time_id")
    private Time time;

    public Jogador() {}//Padrão

    public Jogador(String nome, LocalDate nascimento, String genero, float altura, Time time) {
        this.nome = nome;
        this.nascimento = nascimento;
        Genero = genero;
        this.altura = altura;
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
