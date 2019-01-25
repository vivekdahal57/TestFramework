package com.vh.mi.automation.impl.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author nimanandhar
 */
public class NumberParser {
    private static final NumberFormat integerInstance = NumberFormat.getIntegerInstance(Locale.US);


    public static int parseInteger(String string) {
        try {
            return integerInstance.parse(string).intValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
