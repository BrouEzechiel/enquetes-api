package org.smf.enquetes;

import io.jsonwebtoken.Jwts;
import java.util.Base64;
import javax.crypto.SecretKey;

public class GenerateurCle {
    public static void main(String[] args) {
        // Génère une clé sécurisée pour l'algorithme HS256
        SecretKey key = Jwts.SIG.HS256.key().build();

        // Encode la clé en Base64 pour pouvoir la stocker facilement en texte
        String cleBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Votre clé secrète JWT :");
        System.out.println(cleBase64);
    }
}