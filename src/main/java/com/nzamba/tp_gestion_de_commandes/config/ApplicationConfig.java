package com.nzamba.tp_gestion_de_commandes.config;

import com.nzamba.tp_gestion_de_commandes.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UtilisateurRepository utilisateurRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> utilisateurRepository.findByUsername(username)
                .map(utilisateur -> org.springframework.security.core.userdetails.User.builder()
                        .username(utilisateur.getUsername())
                        .password(utilisateur.getPassword())
                        // Si votre string en BDD est "ROLE_USER", utilisez .authorities()
                        .authorities(utilisateur.getRole()) 
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}