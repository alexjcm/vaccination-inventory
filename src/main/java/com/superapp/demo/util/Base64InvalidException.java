package com.superapp.demo.util;

/**
 * Excepción lanzada cuando no se puede decodificar un cadena de texto en
 * Base64.
 */
public class Base64InvalidException extends Exception {

    private static final long serialVersionUID = 4433183404356913624L;

    public Base64InvalidException(IllegalArgumentException cause) {
        super(cause);
    }
}

