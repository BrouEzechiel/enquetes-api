package org.smf.enquetes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartementNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(DepartementNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage()
        ));
    }
    @ExceptionHandler(EnqueteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EnqueteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage()
        ));
    }
    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSectionNotFound(SectionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(SectionNonSupprimableException.class)
    public ResponseEntity<Map<String, Object>> handleSectionNonSupprimable(SectionNonSupprimableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(DepartementNomDejaUtiliseException.class)
    public ResponseEntity<Map<String, Object>> handleDepartementNomDejaUtilise(DepartementNomDejaUtiliseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(DepartementNonSupprimableException.class)
    public ResponseEntity<Map<String, Object>> handleDepartementNonSupprimable(DepartementNonSupprimableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(EnqueteNonSupprimableException.class)
    public ResponseEntity<Map<String, Object>> handleEnqueteNonSupprimable(EnqueteNonSupprimableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(QuestionNonSupprimableException.class)
    public ResponseEntity<Map<String, Object>> handleQuestionNonSupprimable(QuestionNonSupprimableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(EmailDejaUtiliseException.class)
    public ResponseEntity<Map<String, Object>> handleEmailDejaUtilise(EmailDejaUtiliseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UtilisateurNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUtilisateurNotFound(UtilisateurNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(ReponseInvalideException.class)
    public ResponseEntity<Map<String, Object>> handleReponseInvalide(ReponseInvalideException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(
            org.springframework.security.authentication.BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "message", "Email ou mot de passe incorrect."
        ));
    }
}