package acad.ifma.edu.campeonato_v2.domain.repository;

import acad.ifma.edu.campeonato_v2.domain.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadioRepository extends JpaRepository<Estadio, Integer> {
    List<Estadio> findByNomeIn(List<String> nome);
    Estadio findByNome(String nome);
}
