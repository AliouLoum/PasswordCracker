package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;

/**
 * Stub pour la stratégie de cassage par dictionnaire (Personne 2).
 */
public class DictionaryHashCracker implements HashCracker {

    @Override
    public String casser(String hashCible) {
        // TODO: Implémenter la logique de recherche dans le dictionnaire pour la Personne 2.
        // 1. Charger dictionary.txt
        // 2. Parcourir chaque mot
        // 3. Hasher avec MD5Utils.calculerMd5()
        // 4. Comparer avec hashCible
        throw new UnsupportedOperationException("Non implémenté (Personne 2)");
    }
}
