package com.example.demo.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter; // ton filtre JWT à créer
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Auth libre pour inscription/login si tu veux (à adapter)
                .requestMatchers("/auth/**").permitAll()

                // ADMIN uniquement : CRUD user
                .requestMatchers("/users/**").hasRole("ADMIN")

                // ADMIN et SUPERVISOR : CRUD tasks
                .requestMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("ADMIN", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/tasks/**").hasAnyRole("ADMIN", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/tasks/**").hasAnyRole("ADMIN", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasAnyRole("ADMIN", "SUPERVISOR")


                .requestMatchers(HttpMethod.GET, "/tasks/user/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/tasks/{id}/done/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/tasks/{id}/notdone/**").hasRole("USER")

                // Le reste est interdit
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
