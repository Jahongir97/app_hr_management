package uz.pdp.app_hr_management.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.app_hr_management.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
   private static final long expireDate=1000*60*60*24;
   private static final String kalitsoz="secretkalit";

    public String generateToken(String username, Set<Role> roles){
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, kalitsoz)
                .compact();
        return token;

    }

    public String getUserName(String token){
        try {

            String username = Jwts
                    .parser()
                    .setSigningKey(kalitsoz)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            return username;

        }catch (Exception exception){
            return null;
        }

    }
}
