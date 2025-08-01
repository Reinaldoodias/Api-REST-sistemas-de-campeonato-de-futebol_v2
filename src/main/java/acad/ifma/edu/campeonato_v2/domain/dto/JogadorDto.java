package acad.ifma.edu.campeonato_v2.domain.dto;


import acad.ifma.edu.campeonato_v2.domain.model.Time;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JogadorDto {

    private int id;

    @NotBlank
    private String nome;
    private LocalDate nascimento;
    private String genero;
    private float altura;
    private String time;


    public JogadorDto(int id, String nome, LocalDate nascimento, String genero, float altura, String time) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.genero = genero;
        this.altura = altura;
        this.time = time;
    }

}
