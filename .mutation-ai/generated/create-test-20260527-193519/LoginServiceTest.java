package com.livro.crud.livros_crud.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.livro.crud.livros_crud.config.JwtServiceGenerator;
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
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private LoginRepository repository;

    @Mock
    private JwtServiceGenerator jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginService subject;

    @Test
    public void testLogar_Success() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        Usuario user = new Usuario();
        user.setUsername("user");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Act
        String result = subject.logar(login);
        // Assert
        assertEquals("jwtToken", result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByUsername(login.getUsername());
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testLogar_RepositoryThrowsException() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenThrow(new RuntimeException());
        // Act & Assert
        assertThrows(RuntimeException.class, () -> subject.logar(login));
    }

    @Test
    public void testLogar_JwtServiceThrowsException() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        Usuario user = new Usuario();
        user.setUsername("user");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenThrow(new RuntimeException());
        // Act & Assert
        assertThrows(RuntimeException.class, () -> subject.logar(login));
    }

    @Test
    public void testGerarToken_Success() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        Usuario user = new Usuario();
        user.setUsername("user");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Act
        String result = subject.gerarToken(login);
        // Assert
        assertEquals("jwtToken", result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByUsername(login.getUsername());
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testGerarToken_RepositoryThrowsException() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenThrow(new RuntimeException());
        // Act & Assert
        assertThrows(RuntimeException.class, () -> subject.gerarToken(login));
    }

    @Test
    public void testGerarToken_JwtServiceThrowsException() throws Exception {
        // Arrange
        Login login = new Login();
        login.setUsername("user");
        login.setPassword("password");
        Usuario user = new Usuario();
        user.setUsername("user");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(login.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenThrow(new RuntimeException());
        // Act & Assert
        assertThrows(RuntimeException.class, () -> subject.gerarToken(login));
    }
}
