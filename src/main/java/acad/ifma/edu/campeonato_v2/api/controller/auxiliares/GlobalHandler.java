package acad.ifma.edu.campeonato_v2.api.controller.auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {


    /**
     * Handler para capturar a exceção NotFoundException.
     * Quando ela for lançada em qualquer lugar da aplicação, este método será chamado.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex) {
        // Cria um corpo de resposta de erro padronizado
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Recurso Não Encontrado");
        body.put("message", ex.getMessage()); // Pega a mensagem da exceção ("Campeonato com ID X não encontrado.")

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}