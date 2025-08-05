package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.api.controller.auxiliares.NotFoundException;
import acad.ifma.edu.campeonato_v2.domain.auxiliares.Conversoes;
import acad.ifma.edu.campeonato_v2.domain.dto.CampeonatoDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.TimeDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Campeonato;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Partida;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.CampeonatoRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final TimeRepository timeRepository;

    Conversoes conversoes = new Conversoes();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CampeonatoService(CampeonatoRepository campeonatoRepository, TimeRepository timeRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.timeRepository = timeRepository;
    }

    public List<CampeonatoDTO> listarTodos() {
        List<Campeonato> campeonatos = campeonatoRepository.findAll();
        return campeonatos.stream()
                .map(conversoes::campeonatotoDto)
                        .collect(Collectors.toList());
    }

    public Optional<CampeonatoDTO> buscarPorId(int id) {
        return campeonatoRepository.findById(id)
                .map(conversoes::campeonatotoDto);
    }

    @Transactional
    public CampeonatoDTO salvar(CampeonatoDTO campeonatoDTO) {
        Campeonato campeonato = conversoes.campeonatotoEntity(campeonatoDTO);
        campeonatoRepository.save(campeonato);
        return conversoes.campeonatotoDto(campeonato);
    }

    @Transactional
    public CampeonatoDTO atualizar(int id, CampeonatoDTO campeonatoDTO) {
        Campeonato existente = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado: " + id));
        Campeonato atualizado = conversoes.campeonatotoEntity(campeonatoDTO);
        atualizado.setId(id);
        atualizado = campeonatoRepository.save(atualizado);
        return conversoes.campeonatotoDto(atualizado);
    }

    @Transactional
    public boolean deletar(int id) {
        campeonatoRepository.deleteById(id);
        return true;
    }

    public List<TimeDTO> listarTimes(int campeonatoId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(()-> new NotFoundException("Campeonato com ID:" + campeonatoId + " Não encontrado."));


        List<Time> times = campeonato.getTimes();

        List<TimeDTO> timeDTOs = times
                .stream()
                .map(conversoes::timetoDto)
                .collect(Collectors.toList());

        return timeDTOs;

    }

}
