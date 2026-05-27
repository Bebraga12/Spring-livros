package com.livro.crud.livros_crud.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController subject;

    @Test
    public void testLogar_Success() throws Exception {
        // Arrange
        Login login = new Login();
        String token = "testToken";
        when(loginService.logar(login)).thenReturn(token);
        // Act
        ResponseEntity<String> response = subject.logar(login);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
    }

    @Test
    public void testLogar_Failure() throws Exception {
        // Arrange
        Login login = new Login();
        when(loginService.logar(login)).thenReturn(null);
        // Act
        ResponseEntity<String> response = subject.logar(login);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
