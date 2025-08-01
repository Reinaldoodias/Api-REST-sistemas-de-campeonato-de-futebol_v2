package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.EnderecoDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.EstadioDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.PartidaDTO;
import acad.ifma.edu.campeonato_v2.domain.model.*;
import acad.ifma.edu.campeonato_v2.domain.repository.CampeonatoRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.EstadioRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.PartidaRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    public PartidaService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    public List<PartidaDTO> listarTodas() {
        return partidaRepository.findAll()
                .stream().map(p -> new PartidaDTO(
                        p.getId(),
                        p.getData(),
                        p.getMandante().getNome(),
                        p.getVisitante().getNome(),
                        p.getEstadio().getNome(),
                        p.getCampeonato().getNome(),
                        p.getResultado()))
                .collect(Collectors.toList());
    }

    public Optional<PartidaDTO> buscarPorId(Integer id) {
        return partidaRepository.findById(id)
                .map(this::toDto);
    }


    @Transactional
    public PartidaDTO salvar(PartidaDTO partidaDto) {
        Partida partida = toEntity(partidaDto);
        partidaRepository.save(partida);
        return toDto(partida);

    }

    @Transactional
    public PartidaDTO atualizar(Integer id, PartidaDTO partidaDto) {
        Partida existente = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrado: " + id));
        Partida atualizada = toEntity(partidaDto);
        atualizada.setId(id);
        atualizada = partidaRepository.save(atualizada);
        return toDto(atualizada);
    }

    @Transactional
    public Boolean deletar(Integer id) {
        partidaRepository.deleteById(id);
        return true;
    }


    /*Métodos auxiliares*/

    private PartidaDTO toDto(Partida p) {
        PartidaDTO dto = new PartidaDTO(p.getId(),
                p.getData(),
                p.getMandante().getNome(),
                p.getVisitante().getNome(),
                p.getEstadio().getNome(),
                p.getCampeonato().getNome(),
                p.getResultado());

        return dto;
    }

    private Partida toEntity(PartidaDTO dto) {
        Partida e = new Partida();
        e.setId(dto.getId());
        e.setData(dto.getData());
        e.setResultado(dto.getResultado());

        //Acha time Mandante
        if (dto.getMandante() != null) {
            Time m = timeRepository.findByNome(dto.getMandante())
                    .orElseThrow(() -> new RuntimeException("Time não encontrado: " + dto.getMandante()));
            e.setMandante(m);
        }
        //acha Time visitante
        if (dto.getVisitante() != null) {
            Time v = timeRepository.findByNome(dto.getVisitante())
                    .orElseThrow(() -> new RuntimeException("Time não encontrado: " + dto.getVisitante()));
            e.setVisitante(v);
        }
        //Acha Estadio
        if (dto.getEstadio() != null) {
            Estadio estadio = estadioRepository.findByNome(dto.getEstadio());
            e.setEstadio(estadio);
        }
        //Acha campeonato
        if (dto.getCampeonato() != null) {
            Campeonato camp = campeonatoRepository.findByNome(dto.getCampeonato());
            e.setCampeonato(camp);
        }
        return e;
    }
}
