package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;

/**
 * Stub pour la stratégie de cassage par force brute (Personne 3).
 */
public class BruteForceHashCracker implements HashCracker {

    @Override
    public String casser(String hashCible) {
        // TODO: Implémenter la logique de force brute pour la Personne 3.
        // 1. Générer toutes les combinaisons de a à z (1 à 4 caractères).
        // 2. Pour chaque combinaison, hasher avec MD5Utils.calculerMd5().
        // 3. Comparer avec hashCible.
        throw new UnsupportedOperationException("Non implémenté (Personne 3)");
    }
}
