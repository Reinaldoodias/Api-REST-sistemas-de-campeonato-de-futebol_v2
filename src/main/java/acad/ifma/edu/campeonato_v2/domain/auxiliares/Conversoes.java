package acad.ifma.edu.campeonato_v2.domain.auxiliares;

import acad.ifma.edu.campeonato_v2.domain.dto.*;
import acad.ifma.edu.campeonato_v2.domain.model.*;
import acad.ifma.edu.campeonato_v2.domain.repository.CampeonatoRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.EstadioRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Conversoes {


    private final TimeRepository timeRepository;
    private final EstadioRepository estadioRepository;
    private final CampeonatoRepository campeonatoRepository;


    public Conversoes() {
        timeRepository = null;
        estadioRepository = null;
        campeonatoRepository = null;
    }

    /*TIME*/
    public TimeDTO timetoDto(Time time) {
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

    public Time timetoEntity(TimeDTO dto) {
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


    /*JOGADOR*/

    public JogadorDto jogadortoDto(Jogador j) {
        return new JogadorDto(
                j.getId(),
                j.getNome(),
                j.getNascimento(),
                j.getGenero(),
                j.getAltura(),
                j.getTime() != null ? j.getTime().getNome() : null
        );
    }

    public Jogador jogadortoEntity(JogadorDto dto) {
        Jogador j = new Jogador();
        // campos simples
        j.setNome(dto.getNome());
        j.setNascimento(dto.getNascimento());
        j.setGenero(dto.getGenero());
        j.setAltura(dto.getAltura());

        // busca o Time pelo nome
        if (dto.getTime() != null) {
            Time t = timeRepository.findByNome(dto.getTime())
                    .orElseThrow(() -> new RuntimeException("Time não encontrado: " + dto.getTime()));
            j.setTime(t);
        }

        return j;
    }

    /*CAMPEONATO*/

    public CampeonatoDTO campeonatotoDto(Campeonato c) {
        CampeonatoDTO dto = new CampeonatoDTO(
                c.getId(),
                c.getNome(),
                c.getAno(),
                c.getTimes().stream().map(Time::getNome).collect(Collectors.toList())
        );
        return dto;
    }


    public Campeonato campeonatotoEntity(CampeonatoDTO dto) {
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


    /*ESTADIO*/
    // Métodos auxiliares de conversão manual

    public EstadioDTO estadiotoDto(Estadio e) {
        Endereco end = e.getEndereco();
        EnderecoDTO enderecoDto = new EnderecoDTO();
        if (end != null) {
            enderecoDto.setCep(end.getCep());
            enderecoDto.setLogradouro(end.getLogradouro());
            enderecoDto.setNumero(end.getNumero());
            enderecoDto.setComplemento(end.getComplemento());
            enderecoDto.setBairro(end.getBairro());
            enderecoDto.setCidade(end.getCidade());
            enderecoDto.setUf(end.getUf());
        }

        EstadioDTO dto = new EstadioDTO();
        dto.setId(e.getId());
        dto.setNome(e.getNome());
        dto.setEndereco(enderecoDto);
        return dto;
    }

    public Estadio estadiotoEntity(EstadioDTO dto) {
        Estadio e = new Estadio();
        e.setNome(dto.getNome());
        EnderecoDTO ed = dto.getEndereco();
        if (ed != null) {
            Endereco end = new Endereco();
            end.setCep(ed.getCep());
            end.setLogradouro(ed.getLogradouro());
            end.setNumero(ed.getNumero());
            end.setComplemento(ed.getComplemento());
            end.setBairro(ed.getBairro());
            end.setCidade(ed.getCidade());
            end.setUf(ed.getUf());
            e.setEndereco(end);
        }
        return e;
    }


    /*PARTIDAS*/

    public PartidaDTO partidatoDto(Partida p) {
        PartidaDTO dto = new PartidaDTO(p.getId(),
                p.getData(),
                p.getMandante().getNome(),
                p.getVisitante().getNome(),
                p.getEstadio().getNome(),
                p.getCampeonato().getNome(),
                p.getResultado());

        return dto;
    }

    public Partida partidatoEntity(PartidaDTO dto) {
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
