package com.uco.ucopetcommercialprocessor.util;

public class Constants {

    private Constants(){
        throw new IllegalStateException("Contants class");
    }
    public static final String TXT_PATTER_NUMBER = "[0-9]+";
    public static final String TXT_ONLY_NUMBERS = "Only numbers in the field were expected %s";
    public static final String TXT_NO_LENGTH_REQUIERED = "No length required %s";
    public static final String TXT_EXPECT_VALUE = "Expect value in the field %s";
    public static final String TXT_PATTER_EMAIL = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String TXT_BAD_EMAIL = "Email is not valid";
    public static final String TXT_ERROR_CREAR = "No puede crear una marca ya existente!!!";

}
