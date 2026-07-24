package com.passwordcracker.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire ultra-optimisé pour le calcul de hash MD5.
 * Ne contient pas d'état (stateless) et ne dépend d'aucune librairie externe lourde.
 */
public final class MD5Utils {

    // On utilise un ThreadLocal pour éviter de recréer l'instance de MessageDigest à chaque appel
    // et garantir la thread-safety de manière optimale, au cas où des stratégies parallèles seraient ajoutées.
    private static final ThreadLocal<MessageDigest> DIGEST_MD5 = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("L'algorithme MD5 n'est pas disponible dans cet environnement", exception);
        }
    });

    private static final char[] TABLEAU_HEXA = "0123456789abcdef".toCharArray();

    private MD5Utils() {
        // Empêche l'instanciation de cette classe utilitaire.
    }

    /**
     * Calcule le hash MD5 de la chaîne d'entrée et le retourne sous forme hexadécimale.
     *
     * @param entree La chaîne dont on veut calculer le hash.
     * @return Le hash MD5 en hexadécimal (minuscules, 32 caractères).
     */
    public static String calculerMd5(String entree) {
        if (entree == null) {
            throw new IllegalArgumentException("La chaîne d'entrée ne peut pas être nulle");
        }

        MessageDigest md = DIGEST_MD5.get();
        md.reset(); // Très important pour réutiliser l'instance
        
        byte[] octetsHash = md.digest(entree.getBytes());

        // Conversion performante de byte[] vers hex String sans String.format ni BigInteger
        char[] caracteresHexa = new char[octetsHash.length * 2];
        for (int i = 0; i < octetsHash.length; i++) {
            int valeur = octetsHash[i] & 0xFF;
            caracteresHexa[i * 2] = TABLEAU_HEXA[valeur >>> 4];
            caracteresHexa[i * 2 + 1] = TABLEAU_HEXA[valeur & 0x0F];
        }
        return new String(caracteresHexa);
    }
}
