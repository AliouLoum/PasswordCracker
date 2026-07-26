package com.passwordcracker.factory;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.strategy.DictionaryHashCracker;
import com.passwordcracker.strategy.BruteForceHashCracker;

/**
 * Fabrique simple responsable de la création des différentes stratégies
 * de cassage de mot de passe (Simple Factory).
 */
public class HashCrackerFactory {

    /**
     * Crée l'instance de HashCracker correspondant à la méthode demandée.
     *
     * @param method "DICO" pour une attaque par dictionnaire,
     *               "BRUTE" pour une attaque par force brute
     * @return une instance de HashCracker
     * @throws IllegalArgumentException si la méthode est inconnue
     */
    public static HashCracker create(String method) {
        if (method == null) {
            throw new IllegalArgumentException("La méthode ne peut pas être nulle.");
        }

        switch (method.toUpperCase()) {
            case "DICO":
                return new DictionaryHashCracker();
            case "BRUTE":
                return new BruteForceHashCracker();
            default:
                throw new IllegalArgumentException("Méthode inconnue : " + method
                        + " (attendu : DICO ou BRUTE)");
        }
    }
}
