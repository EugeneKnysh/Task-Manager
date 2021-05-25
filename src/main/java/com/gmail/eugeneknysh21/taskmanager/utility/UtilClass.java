package com.gmail.eugeneknysh21.taskmanager.utility;

public class UtilClass {
    public static String convertMonthIntToString(Integer monthInt) {
        String monthStr;
        if (monthInt < 10 && monthInt > 0) {
            monthStr = "0" + monthInt;
        } else {
            monthStr = monthInt.toString();
        }
        return monthStr;
    }
}
