package com.livro.crud.livros_crud.config;

import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler subject;

    @Mock
    private MethodArgumentNotValidException mave;

    @Test
    public void testHandle01_withFieldError() throws Exception {
        org.springframework.validation.BindingResult br = mock(org.springframework.validation.BindingResult.class);
        FieldError fe = mock(FieldError.class);
        when(fe.getField()).thenReturn("field");
        when(fe.getDefaultMessage()).thenReturn("error");
        when(br.getFieldErrors()).thenReturn(java.util.List.of(fe));
        when(mave.getBindingResult()).thenReturn(br);
        ResponseEntity<?> r = subject.handle01(mave);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        Map<String, String> body = (Map<String, String>) r.getBody();
        assertEquals("error", body.get("field"));
    }

    @Test
    public void testHandle02_withConstraintViolation() throws Exception {
        ConstraintViolation<?> v = mock(ConstraintViolation.class, Answers.RETURNS_DEEP_STUBS);
        when(v.getPropertyPath().toString()).thenReturn("field");
        when(v.getMessage()).thenReturn("error");
        ConstraintViolationException cve = mock(ConstraintViolationException.class);
        when(cve.getConstraintViolations()).thenReturn(java.util.Set.of(v));
        ResponseEntity<?> r = subject.handle02(cve);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        Map<String, String> body = (Map<String, String>) r.getBody();
        assertEquals("error", body.get("field"));
    }

    @Test
    public void testHandle03_withException() throws Exception {
        Exception ex = mock(Exception.class);
        when(ex.getMessage()).thenReturn("exception message");
        ResponseEntity<?> r = subject.handle03(ex);
        assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
        assertEquals("exception message", r.getBody());
    }
}
