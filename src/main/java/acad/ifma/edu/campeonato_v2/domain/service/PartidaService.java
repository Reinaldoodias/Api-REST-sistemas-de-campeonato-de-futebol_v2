package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.auxiliares.Conversoes;
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

    Conversoes conversoes = new Conversoes();


    @Autowired
    public PartidaService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }


    public List<PartidaDTO> listarTodas() {
        return partidaRepository.findAll()
                .stream().map(conversoes::partidatoDto)
                .collect(Collectors.toList());
    }

    public Optional<PartidaDTO> buscarPorId(Integer id) {
        return partidaRepository.findById(id)
                .map(conversoes::partidatoDto);
    }


    @Transactional
    public PartidaDTO salvar(PartidaDTO partidaDto) {
        Partida partida = conversoes.partidatoEntity(partidaDto);
        partidaRepository.save(partida);
        return conversoes.partidatoDto(partida);

    }

    @Transactional
    public PartidaDTO atualizar(Integer id, PartidaDTO partidaDto) {
        Partida existente = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrado: " + id));
        Partida atualizada = conversoes.partidatoEntity(partidaDto);
        atualizada.setId(id);
        atualizada = partidaRepository.save(atualizada);
        return conversoes.partidatoDto(atualizada);
    }

    @Transactional
    public Boolean deletar(Integer id) {
        partidaRepository.deleteById(id);
        return true;
    }

}
