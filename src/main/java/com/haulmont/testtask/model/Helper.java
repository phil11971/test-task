package com.haulmont.testtask.model;

public class Helper {
    public static String getShortFio(String fio){
        String[] result = fio.split(" ");
        for (int i = 1; i < result.length; i++)
            result[i] = result[i].substring(0, 1) + ".";
        return String.join(" ", result);
    }
}
