package acad.ifma.edu.campeonato_v2.domain.service;

import acad.ifma.edu.campeonato_v2.domain.dto.JogadorDto;
import acad.ifma.edu.campeonato_v2.domain.model.Jogador;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.repository.JogadorRepository;
import acad.ifma.edu.campeonato_v2.domain.repository.TimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JogadorService {

    private final JogadorRepository repository;
    private final TimeRepository timeRepository;

    @Autowired
    public JogadorService(JogadorRepository repository, TimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    public List<JogadorDto> todos() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<JogadorDto> buscarPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    @Transactional
    public JogadorDto salvar(JogadorDto dto) {
        Jogador entidade = toEntity(dto);
        // salva e atualiza id gerado
        entidade = repository.save(entidade);
        return toDto(entidade);
    }

    @Transactional
    public JogadorDto atualizar(Integer id, JogadorDto dto) {
        // garante existência
        Jogador existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado: " + id));
        Jogador atualizado = toEntity(dto);
        atualizado.setId(id);
        atualizado = repository.save(atualizado);
        return toDto(atualizado);
    }

    @Transactional
    public void excluir(Integer id) {
        repository.deleteById(id);
    }

    // --- conversões manuais ---

    private JogadorDto toDto(Jogador j) {
        return new JogadorDto(
                j.getId(),
                j.getNome(),
                j.getNascimento(),
                j.getGenero(),
                j.getAltura(),
                j.getTime() != null ? j.getTime().getNome() : null
        );
    }

    private Jogador toEntity(JogadorDto dto) {
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
}
