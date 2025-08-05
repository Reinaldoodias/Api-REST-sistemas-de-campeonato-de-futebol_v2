package acad.ifma.edu.campeonato_v2.api.controller.auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Anotação para que o Spring retorne o status 404 Not Found por padrão quando esta exceção for lançada
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}