package com.passwordcracker.factory;

import com.passwordcracker.core.HashCracker;

/**
 * Stub de la fabrique pour instancier la bonne stratégie (Personne 4).
 */
public class HashCrackerFactory {

    /**
     * Crée et retourne la bonne implémentation de HashCracker.
     *
     * @param methode La méthode demandée ("DICO" ou "BRUTE").
     * @return Une instance de HashCracker.
     */
    public static HashCracker creer(String methode) {
        // TODO: Instancier DictionaryHashCracker ou BruteForceHashCracker selon le paramètre (Personne 4).
        throw new UnsupportedOperationException("Non implémenté (Personne 4)");
    }
}
