package de.puzzles.core.util;

import de.puzzles.core.domain.CreditState;

import java.nio.charset.Charset;

/**
 * This class provides help-methods for the webapplication.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 *
 */
public final class PuzzlesUtils {

    private PuzzlesUtils() {
    }

    /**
     * This Method generates a md5 hash of a given String.
     * @param input (String)
     * @return  (String)
     */
    public static String md5(String input) {
        String md5 = (input == null) ? "" : input;
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes(Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException while creating MD5 hash");
            System.out.println(e);
        }
        return null;
    }

    /**
     * This Method returns the enum value from a given integer value.
     * This method also checks if the integer is related to a enum value.
     * @param value
     * @return enum value
     */
    public static CreditState getCreditStateByValue(Integer value) {
        if (value != null) {
            CreditState[] states = CreditState.values();
            if (value < states.length) {
                return states[value];
            }
        }
        return null;
    }

    public static Integer parseInt(String number) {
        try {
            return Integer.valueOf(number);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
