package com.passwordcracker.cli;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.factory.HashCrackerFactory;
import com.passwordcracker.strategy.BruteForceHashCracker;

/**
 * Point d'entrée en ligne de commande de PasswordCracker.
 *
 * Usage :
 *   passwordCracker -m BRUTE -h e7247759c1633c0f9f1485f3690294a9
 *   passwordCracker -m DICO  -h e7247759c1633c0f9f1485f3690294a9
 */
public class Main {

    public static void main(String[] args) {
        String method = null;
        String hash = null;

        for (int i = 0; i < args.length - 1; i++) {
            if ("-m".equals(args[i])) {
                method = args[i + 1];
            } else if ("-h".equals(args[i])) {
                hash = args[i + 1];
            }
        }

        if (method == null || hash == null) {
            System.out.println("Usage: passwordCracker -m [DICO|BRUTE] -h <hashMD5>");
            return;
        }

        try {
            HashCracker cracker = HashCrackerFactory.create(method);

            long start = System.currentTimeMillis();
            String result = cracker.crack(hash);
            long elapsed = System.currentTimeMillis() - start;

            if (result != null) {
                System.out.println("Password found: " + result);
            } else {
                System.out.println("Password not found");
            }
            System.out.println("Temps d'exécution : " + elapsed + " ms");

            // Affiche le nombre de tentatives uniquement pour la force brute
            if (cracker instanceof BruteForceHashCracker) {
                long attempts = ((BruteForceHashCracker) cracker).getAttempts();
                System.out.println("Nombre de tentatives : " + attempts);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
