package de.puzzles.core.util;

import de.puzzles.core.domain.CreditState;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 *         Time: 17:14
 *         To change this template use File | Settings | File Templates.
 */
public final class PuzzlesUtils {

    private PuzzlesUtils() {
    }

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

    public static CreditState getCreditStateByValue(Integer value) {
        if (value != null) {
            CreditState[] states = CreditState.values();
            if (value < states.length) {
                return states[value];
            }
        }
        return null;
    }
}
