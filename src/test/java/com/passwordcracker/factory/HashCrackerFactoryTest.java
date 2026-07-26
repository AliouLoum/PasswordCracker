package com.passwordcracker.factory;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.strategy.DictionaryHashCracker;
import com.passwordcracker.strategy.BruteForceHashCracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashCrackerFactoryTest {

    @Test
    void testCreateDico() {
        HashCracker cracker = HashCrackerFactory.create("DICO");
        assertNotNull(cracker);
        assertInstanceOf(DictionaryHashCracker.class, cracker);
    }

    @Test
    void testCreateBrute() {
        HashCracker cracker = HashCrackerFactory.create("BRUTE");
        assertNotNull(cracker);
        assertInstanceOf(BruteForceHashCracker.class, cracker);
    }

    @Test
    void testCreateDicoIgnoreCase() {
        HashCracker cracker = HashCrackerFactory.create("dico");
        assertInstanceOf(DictionaryHashCracker.class, cracker);
    }

    @Test
    void testCreateBruteIgnoreCase() {
        HashCracker cracker = HashCrackerFactory.create("brute");
        assertInstanceOf(BruteForceHashCracker.class, cracker);
    }

    @Test
    void testCreateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            HashCrackerFactory.create(null);
        });
    }

    @Test
    void testCreateMethodeInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            HashCrackerFactory.create("XYZ");
        });
    }
}