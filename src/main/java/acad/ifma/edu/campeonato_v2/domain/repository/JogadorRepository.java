package acad.ifma.edu.campeonato_v2.domain.repository;

import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {
    List<Jogador> findByNome(String nome);
}
