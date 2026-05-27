package com.livro.crud.livros_crud.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.livro.crud.livros_crud.auth.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.io.Decoders;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
public class JwtServiceGeneratorTest {

    @InjectMocks
    private JwtServiceGenerator jwtServiceGenerator;

    @Mock
    private Usuario usuario;

    @Test
    public void testGerarPayload() throws Exception {
        when(usuario.getUsername()).thenReturn("testUser");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRole()).thenReturn("ROLE_USER");
        Map<String, Object> payload = jwtServiceGenerator.gerarPayload(usuario);
        assertEquals("testUser", payload.get("username"));
        assertEquals("1", payload.get("id"));
        assertEquals("ROLE_USER", payload.get("role"));
        assertEquals("teste", payload.get("outracoisa"));
    }

    @Test
    public void testGenerateToken() throws Exception {
        when(usuario.getUsername()).thenReturn("testUser");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRole()).thenReturn("ROLE_USER");
        String token = jwtServiceGenerator.generateToken(usuario);
        assertNotNull(token);
        assertTrue(token.contains("testUser"));
        assertTrue(token.contains("1"));
        assertTrue(token.contains("ROLE_USER"));
    }

    @Test
    public void testIsTokenValid() throws Exception {
        when(usuario.getUsername()).thenReturn("testUser");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRole()).thenReturn("ROLE_USER");
        String token = jwtServiceGenerator.generateToken(usuario);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");
        assertTrue(jwtServiceGenerator.isTokenValid(token, userDetails));
    }

    @Test
    public void testExtractUsername() throws Exception {
        when(usuario.getUsername()).thenReturn("testUser");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRole()).thenReturn("ROLE_USER");
        String token = jwtServiceGenerator.generateToken(usuario);
        assertEquals("testUser", jwtServiceGenerator.extractUsername(token));
    }

    @Test
    public void testExtractClaim() throws Exception {
        when(usuario.getUsername()).thenReturn("testUser");
        when(usuario.getId()).thenReturn(1L);
        when(usuario.getRole()).thenReturn("ROLE_USER");
        String token = jwtServiceGenerator.generateToken(usuario);
        assertEquals("testUser", jwtServiceGenerator.extractClaim(token, Claims::getSubject));
    }
}
