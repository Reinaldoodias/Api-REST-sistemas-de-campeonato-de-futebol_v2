package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.auxiliares.Conversoes;
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
    Conversoes conversoes = new Conversoes();

    @Autowired
    public TimeService(TimeRepository timeRepository, EstadioRepository estadioRepository) {
        this.timeRepository = timeRepository;
        this.estadioRepository = estadioRepository;
    }

    public List<TimeDTO> listarTodos() {

        return timeRepository.findAll()
                .stream()
                .map(conversoes::timetoDto)
                .collect(Collectors.toList());
    }

    public Optional<TimeDTO> buscarPorId(Integer id) {
        return timeRepository.findById(id)
                .map(conversoes::timetoDto);
    }

    @Transactional
    public TimeDTO salvar(TimeDTO dto) {
        Time entidade = conversoes.timetoEntity(dto);
        Time salvo = timeRepository.save(entidade);
        return conversoes.timetoDto(salvo);
    }

    @Transactional
    public TimeDTO atualizar(Integer id, TimeDTO dto) {
        Time existente = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado: " + id));
        Time atualizado = conversoes.timetoEntity(dto);
        atualizado.setId(id);
        atualizado = timeRepository.save(atualizado);
        return conversoes.timetoDto(atualizado);
    }

    @Transactional
    public boolean deletar(Integer id) {
        timeRepository.deleteById(id);
        return true;
    }





}
