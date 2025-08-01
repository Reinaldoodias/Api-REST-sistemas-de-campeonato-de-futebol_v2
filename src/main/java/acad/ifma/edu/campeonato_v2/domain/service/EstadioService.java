// Service EstadioService.java
package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.EnderecoDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.EstadioDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Endereco;
import acad.ifma.edu.campeonato_v2.domain.model.Estadio;
import acad.ifma.edu.campeonato_v2.domain.repository.EstadioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadioService {

    private final EstadioRepository estadioRepository;

    @Autowired
    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public List<String> listarNomes() {
        return estadioRepository.findAll()
                .stream()
                .map(e -> e.getNome())
                .collect(Collectors.toList());
    }

    public Optional<EstadioDTO> buscarPorId(Integer id) {
        return estadioRepository.findById(id)
                .map(this::toDto);
    }

    @Transactional
    public EstadioDTO salvar(EstadioDTO dto) {
        Estadio entidade = toEntity(dto);
        Estadio salvo = estadioRepository.save(entidade);
        return toDto(salvo);
    }

    @Transactional
    public EstadioDTO atualizar(Integer id, EstadioDTO dto) {
        Estadio existente = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado: " + id));
        Estadio atualizado = toEntity(dto);
        atualizado.setId(id);
        atualizado = estadioRepository.save(atualizado);
        return toDto(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        estadioRepository.deleteById(id);
    }

    // Métodos auxiliares de conversão manual

    private EstadioDTO toDto(Estadio e) {
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

    private Estadio toEntity(EstadioDTO dto) {
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
}
