package com.uco.ucopetcommercialprocessor.util;

import java.util.List;

public final class UtilStrings {

    public static void requiresPattern (String s, String pattern, String message) {
        if (!s.matches(pattern)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requieresLength(String s, int lengthMin, int lengthMax, String message) {
        if (!(s.length() >= lengthMin && s.length() <= lengthMax)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean isEmptyOrNull(String val) {
        if (val == null || val.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static void requieresNoNullOrNoEmpty(String val, String mensaje) {
        if (isEmptyOrNull(val)) {
            throw new IllegalArgumentException(mensaje);
        }

    }
    public  static String arrayToStringSql(List<String> listaParametros){

        String parametros = "";
        listaParametros.forEach(parametro -> {
            parametros.concat(parametro + ",");
        });
        System.out.println(parametros);
        return  parametros;
    }

}
