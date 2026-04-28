package cl.duoc.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET = "clave-secreta-super-larga-de-minimo-32-caracteres";
    // Se coloca el tiempo en formato numerico en milisegundo 1 segundo es igual a 1000
    private final long EXPIRACION = 1000 * 60 * 15;

    /**
     *
     * @return devuelve la key cifrada con el estandar de ecriptacion de UTF-8
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(EXPIRACION + System.currentTimeMillis()))
                .signWith(getKey())
                .compact();
    }

    /*
        public String extraerUsername(String token) {
        return Jwts.parserBuilder(getKey()).;
    } 
     */
    public String extraerUsername(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();

    }


    public boolean esValido(String token) {
        try {
            extraerUsername(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
