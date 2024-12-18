package pinecone.springsecuritydemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import pinecone.springsecuritydemo.model.enums.Role;
import pinecone.springsecuritydemo.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class TokenUtils {

    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final UserRepository userRepository;

    public TokenUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer extractUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey((Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (Integer) claims.get("userId");
    }

    public String generateToken(Integer userId, Role role) {
        Map<String, Object> claims = Map.of("userId", userId, "role", role);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 ))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}