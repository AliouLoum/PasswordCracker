package com.passwordcracker.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DictionaryHashCrackerTest {

    private final DictionaryHashCracker cracker = new DictionaryHashCracker();

    @Test
    void testMotExistant() {
        String hashTest = "098f6bcd4621d373cade4e832627b4f6";
        String resultat = cracker.crack(hashTest);
        assertEquals("test", resultat, "Le dictionnaire contient 'test', son hash doit être retrouvé");
    }

    @Test
    void testMotExistant_password() {
        String hashPassword = "5f4dcc3b5aa765d61d8327deb882cf99";
        String resultat = cracker.crack(hashPassword);
        assertEquals("password", resultat);
    }

    @Test
    void testMotNonExistant() {
        String hashInexistant = "357f5c155c9da6842b84ad1066996928";
        String resultat = cracker.crack(hashInexistant);
        assertNull(resultat, "Un hash inconnu doit retourner null");
    }

    @Test
    void testHashNull() {
        String resultat = cracker.crack(null);
        assertNull(resultat, "Un hash null doit retourner null");
    }
}