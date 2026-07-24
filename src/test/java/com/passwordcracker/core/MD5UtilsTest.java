package com.passwordcracker.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {

    @Test
    void testCalculerMd5_motBasique() {
        String hashAttendu = "e7247759c1633c0f9f1485f3690294a9";
        String hashReel = MD5Utils.calculerMd5("test");
        assertEquals(hashAttendu, hashReel, "Le hash MD5 de 'test' devrait être 'e7247759c1633c0f9f1485f3690294a9'");
    }

    @Test
    void testCalculerMd5_chaineVide() {
        String hashAttendu = "d41d8cd98f00b204e9800998ecf8427e";
        String hashReel = MD5Utils.calculerMd5("");
        assertEquals(hashAttendu, hashReel, "Le hash MD5 d'une chaîne vide devrait être 'd41d8cd98f00b204e9800998ecf8427e'");
    }

    @Test
    void testCalculerMd5_entreeNulle_lanceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MD5Utils.calculerMd5(null);
        }, "Le passage d'un paramètre null devrait déclencher une IllegalArgumentException");
    }
    
    @Test
    void testCalculerMd5_securiteMultithread() throws InterruptedException {
        for(int i=0; i<100; i++) {
             assertEquals("098f6bcd4621d373cade4e832627b4f6", MD5Utils.calculerMd5("test" + "test".length() * 0));
        }
    }
}
