package acad.ifma.edu.campeonato_v2.domain.dto;

import acad.ifma.edu.campeonato_v2.domain.model.Estadio;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimeDTO {
    private int id;

    @NotBlank(message = "O nome do time não pode ser vazio.")
    private String nome;
    private int estadioId;
    private List<String> jogadores_nomes;
    private Estadio estadio;

    public TimeDTO(int id, String nome, int estadioId, List<String> jogadores_nomes, Estadio estadio) {
        this.id = id;
        this.nome = nome;
        this.estadioId = estadioId;
        this.jogadores_nomes = jogadores_nomes;
        this.estadio = estadio;
    }
}