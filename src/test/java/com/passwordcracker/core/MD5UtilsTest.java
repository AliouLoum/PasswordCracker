package com.passwordcracker.core;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {

    @Test
    void testCalculerMd5_motBasique() {
        String hashAttendu = "098f6bcd4621d373cade4e832627b4f6";
        String hashReel = MD5Utils.calculerMd5("test");
        assertEquals(hashAttendu, hashReel, "Le hash MD5 de 'test' devrait être '098f6bcd4621d373cade4e832627b4f6'");
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
        for (int i = 0; i < 100; i++) {
            assertEquals("098f6bcd4621d373cade4e832627b4f6", MD5Utils.calculerMd5("test"));
        }
    }

    @Test
    void testCalculerMd5_concurrence() throws InterruptedException {
        int nbThreads = 10;
        int iterationsParThread = 50;
        ExecutorService pool = Executors.newFixedThreadPool(nbThreads);
        AtomicInteger erreurs = new AtomicInteger(0);

        for (int t = 0; t < nbThreads; t++) {
            pool.submit(() -> {
                for (int i = 0; i < iterationsParThread; i++) {
                    if (!"098f6bcd4621d373cade4e832627b4f6".equals(MD5Utils.calculerMd5("test"))) {
                        erreurs.incrementAndGet();
                    }
                    if (!"d41d8cd98f00b204e9800998ecf8427e".equals(MD5Utils.calculerMd5(""))) {
                        erreurs.incrementAndGet();
                    }
                    if (!"f6f4061a1bddc1c04d8109b39f581270".equals(MD5Utils.calculerMd5("test0"))) {
                        erreurs.incrementAndGet();
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        assertEquals(0, erreurs.get(), "Aucune erreur de hash ne devrait survenir en concurrence");
    }
}