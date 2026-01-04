package ec.edu.ups.icc.fundamentos01.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ec.edu.ups.icc.fundamentos01.exceptions.base.ApplicationException;
import ec.edu.ups.icc.fundamentos01.exceptions.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                ex.getStatus(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage())
          );

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Datos de entrada inválidos",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;


// @RestControllerAdvice
// public class GlobalExceptionHandler {


// @ExceptionHandler(MethodArgumentNotValidException.class)
// public ResponseEntity<Map<String, Object>> handleValidationErrors(
//         MethodArgumentNotValidException ex) {

//     java.util.List<String> mensajes = ex.getBindingResult()
//             .getFieldErrors()
//             .stream()
//             .map(err -> err.getDefaultMessage())
//             .toList();

//     Map<String, Object> response = new HashMap<>();
//     response.put("status", HttpStatus.BAD_REQUEST.value()); // ← AQUÍ
//     response.put("error", "Error de validación");
//     response.put("mensajes", mensajes);

//     return ResponseEntity
//             .status(HttpStatus.BAD_REQUEST)
//             .body(response);
// }

//     @ExceptionHandler(IllegalArgumentException.class)
//     public ResponseEntity<Map<String, String>> handleIllegalArgument(
//             IllegalArgumentException ex) {

//         Map<String, String> error = new HashMap<>();
//         error.put("error", "Error de validación");
//         error.put("mensaje", ex.getMessage());

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//     }
// }