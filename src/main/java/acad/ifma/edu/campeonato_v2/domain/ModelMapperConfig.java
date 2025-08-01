package acad.ifma.edu.campeonato_v2.domain;
import acad.ifma.edu.campeonato_v2.domain.dto.JogadorDto;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }
}
