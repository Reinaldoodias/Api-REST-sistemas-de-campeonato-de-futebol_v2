package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.TimeDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeDTO> listarTodos() {
        return timeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TimeDTO> buscarPorId(Integer id) {
        return timeRepository.findById(id)
                .map(this::toDto);
    }

    @Transactional
    public TimeDTO salvar(TimeDTO dto) {
        Time entidade = toEntity(dto);
        Time salvo = timeRepository.save(entidade);
        return toDto(salvo);
    }

    @Transactional
    public TimeDTO atualizar(Integer id, TimeDTO dto) {
        Time existente = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado: " + id));
        Time atualizado = toEntity(dto);
        atualizado.setId(id);
        atualizado = timeRepository.save(atualizado);
        return toDto(atualizado);
    }

    @Transactional
    public boolean deletar(Integer id) {
        timeRepository.deleteById(id);
        return true;
    }

    //Listar por campeonato
    public List<TimeDTO> listarPorCampeonato(Integer campeonatoId) {
        return timeRepository.findByCampeonatoId(campeonatoId)
                .stream()
                .map(t -> new TimeDTO(
                        t.getId(),
                        t.getNome(),
                        t.getEstadio(),
                        t.getJogadores().stream().map(Jogador::getNome).collect(Collectors.toList())

                )).collect(Collectors.toList());
    }


    // --- conversões manuais ---

    private TimeDTO toDto(Time time) {
        List<String> nomesJogadores = time.getJogadores() != null
                ? time.getJogadores().stream()
                .map(Jogador::getNome)
                .collect(Collectors.toList())
                : List.of();
        String estadioNome = time.getEstadio() != null ? time.getEstadio().getNome() : null;
        return new TimeDTO(
                time.getId(),
                time.getNome(),
                time.getEstadio(),
                nomesJogadores
        );
    }

    private Time toEntity(TimeDTO dto) {
        Time time = new Time();
        time.setNome(dto.getNome());
        // se precisar associar estádio pelo nome, injete EstadioRepository e faça lookup aqui
        // jogadores não são criados via TimeService
        return time;
    }
}
