package com.passwordcracker.core;

/**
 * Interface définissant le contrat pour toutes les stratégies de cassage de mot de passe.
 */
public interface HashCracker {
    /**
     * Tente de retrouver le mot de passe original correspondant à un hash MD5.
     *
     * @param hash Le hash MD5 cible (hexadécimal 32 caractères).
     * @return Le mot de passe original s'il est trouvé, sinon {@code null}.
     */
    String crack(String hash);
}
