package com.superapp.demo.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * Clase utilitaria para procesar formato Base64.
 */
public class Base64Util {

    private static final Decoder DECODER = Base64.getDecoder();
    private static final Encoder ENCODER = Base64.getEncoder();

    public static byte[] decode(String base64) throws Base64InvalidException {
        try {
            return DECODER.decode(base64);
        } catch (IllegalArgumentException e) {
            throw new Base64InvalidException(e);
        }
    }

    public static String encode(byte[] data) {
        return ENCODER.encodeToString(data);
    }
}

