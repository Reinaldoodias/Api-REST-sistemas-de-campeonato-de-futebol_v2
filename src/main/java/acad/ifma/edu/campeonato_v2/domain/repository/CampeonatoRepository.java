package acad.ifma.edu.campeonato_v2.domain.repository;

import acad.ifma.edu.campeonato_v2.domain.model.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {
    Campeonato findByNome(String nome);
}
