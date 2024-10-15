package com.example.boubyantask.services;

import com.example.boubyantask.config.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder encoder;


    @Override
    public TokenResponse auth(String request) {
        if (request != null && request.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = request.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            System.err.println("cred " + values[0] + " " + values[1]);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(values[0], values[1]));
            return generateToken(authentication);
        } else {
            return null;
        }
    }


    public TokenResponse generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Access token
        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))  // short-lived
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        String accessToken = this.encoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        // Refresh token
        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))  // longer expiration for refresh token
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("type", "refresh_token")  // indicate that this is a refresh token
                .build();

        String refreshToken = this.encoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        // Return both tokens in a custom response object
        return new TokenResponse(accessToken, refreshToken);
    }
    @Override
    public TokenResponse refreshToken(String refreshToken) {
        Instant now = Instant.now();


        // Access token
        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))  // short-lived
                .build();

        String accessToken = this.encoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();

        // Return both tokens in a custom response object
        return new TokenResponse(accessToken, refreshToken);
    }
}
