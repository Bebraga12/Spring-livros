package com.livro.crud.livros_crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.livro.crud.livros_crud.auth.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
public class SecurityManagerTest {

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private SecurityManager subject;

    @BeforeEach
    public void setUp() {
        when(loginRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    }

    @Test
    public void testPasswordEncoder() throws Exception {
        PasswordEncoder result = subject.passwordEncoder();
        assertNotNull(result);
        assertTrue(result instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testAuthenticationProvider() throws Exception {
        AuthenticationProvider result = subject.authenticationProvider();
        assertNotNull(result);
        assertTrue(result instanceof DaoAuthenticationProvider);
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        when(config.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));
        AuthenticationManager result = subject.authenticationManager(config);
        assertNotNull(result);
    }

    @Test
    public void testUserDetailsService_UserFound() throws Exception {
        UserDetails userDetails = mock(UserDetails.class);
        when(loginRepository.findByUsername(anyString())).thenReturn(Optional.of(userDetails));
        UserDetailsService userDetailsService = subject.userDetailsService();
        UserDetails result = userDetailsService.loadUserByUsername("user");
        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    public void testUserDetailsService_UserNotFound() throws Exception {
        assertThrows(UsernameNotFoundException.class, () -> subject.userDetailsService().loadUserByUsername("x"));
    }
}
