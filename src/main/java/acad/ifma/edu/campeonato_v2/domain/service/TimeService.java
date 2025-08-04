package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.TimeDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Estadio;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.EstadioRepository;
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
    private final EstadioRepository estadioRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository, EstadioRepository estadioRepository) {
        this.timeRepository = timeRepository;
        this.estadioRepository = estadioRepository;
    }

    public List<TimeDTO> listarTodos() {

        return timeRepository.findAll()
                .stream()
                .map(t -> new TimeDTO(
                        t.getId(),
                        t.getNome(),
                        t.getEstadio()!=null? t.getEstadio().getId():-1,
                        t.getJogadores().stream().map(Jogador::getNome).collect(Collectors.toList()),
                        t.getEstadio()
                ))
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
                        t.getEstadio().getId(),
                        t.getJogadores().stream().map(Jogador::getNome).collect(Collectors.toList()),
                        t.getEstadio()

                )).collect(Collectors.toList());
    }


    // --- conversões manuais ---

    private TimeDTO toDto(Time time) {
        List<String> nomesJogadores = time.getJogadores() != null
                ? time.getJogadores().stream()
                .map(Jogador::getNome)
                .collect(Collectors.toList())
                : List.of();
        int estadioId = -1;
        if(time.getEstadio() != null) {
            estadioId = time.getEstadio().getId();
        }

        TimeDTO timeDto = new TimeDTO(
                time.getId(),
                time.getNome(),
                estadioId,
                nomesJogadores,
                time.getEstadio()
        );
        timeDto.setEstadio(time.getEstadio());
        return timeDto;
    }

    /*FUNÇÕES AUXILIARES*/

    private Time toEntity(TimeDTO dto) {
        Time time = new Time();


        time.setNome(dto.getNome());

        //Encontra estádio
        Estadio estadio = buscarEstadioPorId(dto.getEstadioId());
        time.setEstadio(estadio);

        return time;
    }

    private Estadio buscarEstadioPorId(Integer idEstadio) {
        Optional<Estadio> estadio = estadioRepository.findById(idEstadio);
        if (estadio.isPresent()) {
            return estadio.get();
        }
        return null;
    }


}
