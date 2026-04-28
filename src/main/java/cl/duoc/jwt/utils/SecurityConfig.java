package cl.duoc.jwt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter filtro;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf -> es una medida de seguridad que se utiliza para proteger las aplicaciones web contra ataques de tipo CSRF (Cross-Site Request Forgery), que son ataques en los que un atacante engaña a un usuario autenticado para que realice una acción no deseada en una aplicación web en la que el usuario está autenticado.  
        //  Se desactiva el csrf para configurar nuestrar propias reglas con respecto a la seguridad 
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .addFilterBefore(filtro, UsernamePasswordAuthenticationFilter.class))
                .build();

    }

}
