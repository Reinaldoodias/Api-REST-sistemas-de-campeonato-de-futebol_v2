package acad.ifma.edu.campeonato_v2.domain.dto;
import acad.ifma.edu.campeonato_v2.domain.model.Campeonato;
import acad.ifma.edu.campeonato_v2.domain.model.Estadio;
import acad.ifma.edu.campeonato_v2.domain.model.Resultado;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartidaDTO {
    private int id;

    private LocalDate data;

    private String mandante;
    private String visitante;
    private String estadio;
    private Resultado resultado;

    @NotBlank
    private String Campeonato;

    public PartidaDTO(int id, LocalDate data, String mandante, String visitante, String estadio, String campeonato, Resultado resultado) {
        this.id = id;
        this.data = data;
        this.mandante = mandante;
        this.visitante = visitante;
        this.estadio = estadio;
        Campeonato = campeonato;
    }
}
