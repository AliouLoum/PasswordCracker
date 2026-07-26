package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.core.MD5Utils;

/**
 * Stratégie de cassage par force brute.
 * Génère de façon exhaustive toutes les combinaisons possibles sur
 * l'alphabet [a-z], de longueur croissante (1 à MAX_LENGTH caractères),
 * calcule leur hash MD5 via MD5Utils.calculerMd5(), et les compare au
 * hash recherché.
 *
 * Personne 3 — package com.passwordcracker.strategy
 */
public class BruteForceHashCracker implements HashCracker {

    /** Alphabet utilisé pour générer les combinaisons. */
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /** Longueur maximale des combinaisons testées. */
    private static final int MAX_LENGTH = 4;

    /** Nombre de tentatives effectuées lors du dernier appel à crack(). */
    private long attempts = 0;

    /**
     * Tente de retrouver le mot de passe correspondant au hash MD5 fourni,
     * en testant exhaustivement toutes les combinaisons de 1 à MAX_LENGTH
     * caractères sur l'alphabet a-z.
     *
     * @param hash le hash MD5 recherché (hexadécimal, en minuscules)
     * @return le mot de passe trouvé, ou null si aucune combinaison ne correspond
     */
    @Override
    public String crack(String hash) {
        attempts = 0;

        if (hash == null) {
            return null;
        }

        for (int length = 1; length <= MAX_LENGTH; length++) {
            char[] buffer = new char[length];
            String found = tryCombinations(buffer, 0, hash);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Génère récursivement toutes les combinaisons de la longueur du buffer,
     * teste chacune contre le hash recherché, et s'arrête dès qu'une
     * correspondance est trouvée.
     *
     * @param buffer  tableau de caractères en cours de remplissage
     * @param index   position courante dans le buffer
     * @param target  hash MD5 recherché
     * @return le mot trouvé, ou null si aucune correspondance dans cette branche
     */
    private String tryCombinations(char[] buffer, int index, String target) {
        if (index == buffer.length) {
            String candidate = new String(buffer);
            attempts++;
            String candidateHash = MD5Utils.calculerMd5(candidate);
            if (candidateHash.equals(target)) {
                return candidate;
            }
            return null;
        }

        for (int i = 0; i < ALPHABET.length(); i++) {
            buffer[index] = ALPHABET.charAt(i);
            String result = tryCombinations(buffer, index + 1, target);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Retourne le nombre de tentatives effectuées lors du dernier appel à crack().
     * Utile pour les statistiques / le rapport de résultats (section "Résultats
     * obtenus" du README, à destination de Personne 4).
     *
     * @return le nombre de hash calculés lors de la dernière recherche
     */
    public long getAttempts() {
        return attempts;
    }
}
