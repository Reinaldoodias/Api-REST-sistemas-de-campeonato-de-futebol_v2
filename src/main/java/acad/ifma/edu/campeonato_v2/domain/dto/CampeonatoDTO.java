package acad.ifma.edu.campeonato_v2.domain.dto;

import acad.ifma.edu.campeonato_v2.domain.model.Partida;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CampeonatoDTO {


    private int id;

    @NotBlank
    private String nome;

    private int ano;

    private List<String> timesNomes;
    private List<Partida> partida;

    public CampeonatoDTO(int id, String nome, int ano, List<String> timesNomes) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.timesNomes = timesNomes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public List<String> getTimesNomes() {
        return timesNomes;
    }

    public void setTimesNomes(List<String> timesNomes) {
        this.timesNomes = timesNomes;
    }

    public List<Partida> getPartida() {
        return partida;
    }

    public void setPartida(List<Partida> partida) {
        this.partida = partida;
    }
}
