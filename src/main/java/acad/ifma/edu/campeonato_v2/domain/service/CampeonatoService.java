package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.CampeonatoDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Campeonato;
import acad.ifma.edu.campeonato_v2.domain.model.Partida;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.CampeonatoRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final TimeRepository timeRepository;

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
                .map(c -> new CampeonatoDTO(
                        c.getId(),
                        c.getNome(),
                        c.getAno(),
                        c.getTimes().stream()
                                .map(Time::getNome)
                                .collect(Collectors.toList())

                ))
                        .collect(Collectors.toList());
    }

    public Optional<CampeonatoDTO> buscarPorId(int id) {
        return campeonatoRepository.findById(id)
                .map(this::toDto);
    }

    @Transactional
    public CampeonatoDTO salvar(CampeonatoDTO campeonatoDTO) {
        Campeonato campeonato = toEntity(campeonatoDTO);
        campeonatoRepository.save(campeonato);
        return toDto(campeonato);
    }

    @Transactional
    public CampeonatoDTO atualizar(int id, CampeonatoDTO campeonatoDTO) {
        Campeonato existente = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado: " + id));
        Campeonato atualizado = toEntity(campeonatoDTO);
        atualizado.setId(id);
        atualizado = campeonatoRepository.save(atualizado);
        return toDto(atualizado);
    }

    @Transactional
    public boolean deletar(int id) {
        campeonatoRepository.deleteById(id);
        return true;
    }

    // MÉTODOS AUXILIARES

    private CampeonatoDTO toDto(Campeonato c) {
        CampeonatoDTO dto = new CampeonatoDTO(
                c.getId(),
                c.getNome(),
                c.getAno(),
                c.getTimes().stream().map(Time::getNome).collect(Collectors.toList())
        );
        return dto;
    }


    private Campeonato toEntity(CampeonatoDTO dto) {
        Campeonato camp = new Campeonato();
        camp.setId(dto.getId());
        camp.setNome(dto.getNome());
        camp.setAno(dto.getAno());

        if (dto.getTimesNomes() != null) {
            List<Time> times = timeRepository.findByNomeIn(dto.getTimesNomes());
            camp.setTimes(times);
        }

        return camp;
    }

}
