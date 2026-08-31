package org.smf.enquetes.security;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.entities.Utilisateur;
import org.smf.enquetes.repositories.UtilisateurRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // On associe le rôle de l'utilisateur (ex: "ADMIN", "MEDECIN") sous forme d'autorité Spring Security
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name());

        return new User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                List.of(authority)
        );
    }
}