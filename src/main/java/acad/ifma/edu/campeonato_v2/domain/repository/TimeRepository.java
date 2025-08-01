package acad.ifma.edu.campeonato_v2.domain.repository;

import acad.ifma.edu.campeonato_v2.domain.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time, Integer> {
    Optional<Time> findByNome(String nome);

    List<Time> findByNomeIn(List<String> nomes);

    List<Time> findByCampeonatoId(Integer campeonatoId);

}
