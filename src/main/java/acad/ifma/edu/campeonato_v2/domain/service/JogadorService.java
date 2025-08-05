package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.auxiliares.Conversoes;
import acad.ifma.edu.campeonato_v2.domain.dto.JogadorDto;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.JogadorRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JogadorService {

    private final JogadorRepository repository;
    private final TimeRepository timeRepository;

    Conversoes conversoes = new Conversoes();


    @Autowired
    public JogadorService(JogadorRepository repository, TimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    public List<JogadorDto> todos() {
        return repository.findAll().stream()
                .map(conversoes::jogadortoDto)
                .collect(Collectors.toList());
    }

    public Optional<JogadorDto> buscarPorId(Integer id) {
        return repository.findById(id)
                .map(conversoes::jogadortoDto);
    }

    @Transactional
    public JogadorDto salvar(JogadorDto dto) {
        Jogador entidade = conversoes.jogadortoEntity(dto);
        // salva e atualiza id gerado
        entidade = repository.save(entidade);
        return conversoes.jogadortoDto(entidade);
    }

    @Transactional
    public JogadorDto atualizar(Integer id, JogadorDto dto) {
        // garante existência
        Jogador existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado: " + id));
        Jogador atualizado = conversoes.jogadortoEntity(dto);
        atualizado.setId(id);
        atualizado = repository.save(atualizado);
        return conversoes.jogadortoDto(atualizado);
    }

    @Transactional
    public void excluir(Integer id) {
        repository.deleteById(id);
    }

}
