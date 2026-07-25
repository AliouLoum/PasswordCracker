package com.passwordcracker.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BruteForceHashCrackerTest {

    private final BruteForceHashCracker cracker = new BruteForceHashCracker();

    @Test
    void testMotExistant_test() {
        // hash MD5 de "test", donné dans le sujet
        String hashTest = "098f6bcd4621d373cade4e832627b4f6";
        String resultat = cracker.crack(hashTest);
        assertEquals("test", resultat, "La force brute doit retrouver 'test' (4 caractères, dans l'alphabet a-z)");
    }

    @Test
    void testMotExistant_courtUneLettre() {
        // hash MD5 de "a"
        String hashA = "0cc175b9c0f1b6a831c399e269772661";
        String resultat = cracker.crack(hashA);
        assertEquals("a", resultat);
    }

    @Test
    void testMotInexistant_horsEspaceDeRecherche() {
        String hashTest5 = "0dcc44e39cbf9c33d3cff37f61959b1d";
        String resultat = cracker.crack(hashTest5);
        assertNull(resultat, "Un mot de plus de 4 caractères ne peut pas être retrouvé, doit retourner null");
    }

    @Test
    void testHashNull() {
        String resultat = cracker.crack(null);
        assertNull(resultat, "Un hash null doit retourner null");
    }

    @Test
    void testCompteurTentatives() {
        String hashTest = "098f6bcd4621d373cade4e832627b4f6";
        cracker.crack(hashTest);
        assertTrue(cracker.getAttempts() > 0, "Le compteur de tentatives doit être incrémenté pendant la recherche");
    }
}
