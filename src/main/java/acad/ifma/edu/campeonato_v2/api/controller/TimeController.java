package acad.ifma.edu.campeonato_v2.api.controller;

import acad.ifma.edu.campeonato_v2.domain.dto.TimeDTO;
import acad.ifma.edu.campeonato_v2.domain.service.TimeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeDTO>> listarTodos() {
        List<TimeDTO> list = timeService.listarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeDTO> buscarPorId(@PathVariable Integer id) {
        return timeService.buscarPorId(id)
                .map(ResponseEntity::ok) // Se encontrou, retorna 200 OK com o time.
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TimeDTO> adicionar(@RequestBody TimeDTO timeDTO, UriComponentsBuilder uriBuilder) {
        final TimeDTO dto = timeService.salvar(timeDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(location).body(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody TimeDTO timeDTO) {
        // O serviço de update deve verificar se o ID existe antes de atualizar.
        TimeDTO timeAtualizado = timeService.atualizar(id, timeDTO);
        if (timeAtualizado != null) {
            return ResponseEntity.ok(timeAtualizado); // Retorna 200 OK com o time atualizado.
        }
        return ResponseEntity.notFound().build(); // Se o ID não for encontrado, retorna 404.
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // O serviço de delete deve verificar se o ID existe.
        if (timeService.deletar(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content, indicando sucesso sem corpo.
        }
        return ResponseEntity.notFound().build(); // Se o ID não for encontrado, retorna 404.
    }



}
