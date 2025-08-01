package acad.ifma.edu.campeonato_v2.api.controller;

import acad.ifma.edu.campeonato_v2.domain.dto.EstadioDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.JogadorDto;
import acad.ifma.edu.campeonato_v2.domain.service.EstadioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estadios")
public class EstadioController {

    private final EstadioService estadioService;

    @Autowired
    public EstadioController(EstadioService estadioService) {
        this.estadioService = estadioService;
    }

    @GetMapping
    public ResponseEntity<List<String>> listarNomes() {
        List<String> nomes = estadioService.listarNomes();
        return ResponseEntity.ok(nomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioDTO> buscarPorId(@PathVariable Integer id) {
        return estadioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadioDTO> adicionar(@Valid @RequestBody EstadioDTO dto) {
                   EstadioDTO salvo = estadioService.salvar(dto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(salvo.getId())
                    .toUri();
            return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioDTO> atualizar(@PathVariable int id, @RequestBody EstadioDTO dto) {
        if (!estadioService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        EstadioDTO atualizado = estadioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        estadioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
