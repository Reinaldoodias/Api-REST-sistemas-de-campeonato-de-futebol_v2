// Service EstadioService.java
package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.auxiliares.Conversoes;
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
    Conversoes conversoes = new Conversoes();


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
                .map(conversoes::estadiotoDto);
    }

    @Transactional
    public EstadioDTO salvar(EstadioDTO dto) {
        Estadio entidade = conversoes.estadiotoEntity(dto);
        Estadio salvo = estadioRepository.save(entidade);
        return conversoes.estadiotoDto(salvo);
    }

    @Transactional
    public EstadioDTO atualizar(Integer id, EstadioDTO dto) {
        Estadio existente = estadioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado: " + id));
        Estadio atualizado = conversoes.estadiotoEntity(dto);
        atualizado.setId(id);
        atualizado = estadioRepository.save(atualizado);
        return conversoes.estadiotoDto(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        estadioRepository.deleteById(id);
    }

}
