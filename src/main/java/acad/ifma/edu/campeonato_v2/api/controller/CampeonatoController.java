package acad.ifma.edu.campeonato_v2.api.controller;

import acad.ifma.edu.campeonato_v2.domain.dto.CampeonatoDTO;
import acad.ifma.edu.campeonato_v2.domain.dto.TimeDTO;
import acad.ifma.edu.campeonato_v2.domain.model.Time;
import acad.ifma.edu.campeonato_v2.domain.service.CampeonatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    @Autowired
    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping
    public ResponseEntity<List<CampeonatoDTO>> listarTodos() {
        List<CampeonatoDTO> lista = campeonatoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> buscarPorId(@PathVariable Integer id) {
        return campeonatoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CampeonatoDTO> adicionar(@Valid @RequestBody CampeonatoDTO campeonatoDTO, UriComponentsBuilder uriBuilder) {
        CampeonatoDTO dto = campeonatoService.salvar(campeonatoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody CampeonatoDTO campeonatoDTO) {
        CampeonatoDTO atualizado = campeonatoService.atualizar(id, campeonatoDTO);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (campeonatoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    //Lista times
    @GetMapping("/{id}/times")
    public ResponseEntity<List<TimeDTO>> listarTimes(@PathVariable Integer id) {
        List<TimeDTO> times = campeonatoService.listarTimes(id);
        return ResponseEntity.ok(times);
    }
}
