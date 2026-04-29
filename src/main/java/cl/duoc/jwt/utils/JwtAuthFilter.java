package cl.duoc.jwt.utils;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.duoc.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService servicio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            // startsWith: Hay que colocarle una frase para que no diga este no es el token respectivo al cual vamos a extraer
            String token = header.substring(7);

            if (servicio.esValido(token)) {
                String username = servicio.extraerUsername(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
                // List.of() -> es una forma de crear una lista vacia, ya que no tenemos roles ni permisos en este ejemplo
                // auth -> es el objeto que se va a colocar en el contexto de seguridad de Spring, para que pueda ser utilizado en toda la aplicacion
                // El contexto de seguridad de Spring es un objeto que se utiliza para almacenar la informacion de seguridad de la aplicacion, como el usuario autenticado, los roles, los permisos, etc.
                // El contexto de seguridad de Spring es un objeto que se utiliza para almacenar la informacion de
                // seguridad de la aplicacion, como el usuario autenticado, los roles, los permisos, etc.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            /* 
              Si el token es valido, se extrae el username del token y se crea un objeto de autenticacion con el username y una lista vacia de roles,
               ya que no tenemos roles ni permisos en este ejemplo. 
              Luego, se coloca el objeto de autenticacion en el contexto de seguridad de Spring, para que pueda ser utilizado en toda la aplicacion. 

             */
        }

        filterChain.doFilter(request, response);

    }

}
