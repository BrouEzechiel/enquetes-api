package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.AuthRequestDTO;
import org.smf.enquetes.dtos.response.AuthResponseDTO;
import org.smf.enquetes.entities.Utilisateur;
import org.smf.enquetes.exceptions.UtilisateurNotFoundException;
import org.smf.enquetes.repositories.UtilisateurRepository;
import org.smf.enquetes.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO request) {
        // 1. Authentification via Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.motDePasse())
        );

        // 2. Récupération des détails de l'utilisateur
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);

        // 3. Récupérer l'utilisateur en base pour extraire son rôle et ses infos
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.email())
                .orElseThrow(() -> new UtilisateurNotFoundException(request.email()));

        // 4. Retourner le token et les informations de session
        AuthResponseDTO response = new AuthResponseDTO(
                token,
                "Bearer",
                utilisateur.getEmail(),
                utilisateur.getRole()
        );

        return ResponseEntity.ok(response);
    }
}