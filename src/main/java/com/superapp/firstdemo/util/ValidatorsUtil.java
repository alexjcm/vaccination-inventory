package com.superapp.firstdemo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorsUtil {

    /**
     * E-mail validation permitted by RFC 5322
     *
     * @param email
     * @return
     */
    public static boolean emailIsValid(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param identCard
     * @return
     */
    public static boolean identCardIsValid(String identCard) {
        byte sum = 0;
        try {
            if (identCard.trim().length() != 10) {
                return false;
            }
            String[] data = identCard.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24) {
                return false;
            }
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++)
                digits[i] = Byte.parseByte(data[i]);
            if (digits[2] > 6) {
                return false;
            }
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9) {
                        verifier = (byte) (verifier - 9);
                    }
                } else {
                    verifier = (byte) (digits[i] * 1);
                }
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9]) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param sameNumber
     * @return
     */
    public static boolean isNumeric(String sameNumber) {
        try {
            double d = Double.parseDouble(sameNumber);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * @param name
     * @return
     */
    public static boolean namesAreValid(String name) {
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
