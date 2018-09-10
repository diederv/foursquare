package com.adyen.android.util;

import java.util.List;

public class StringUtil {

    public static String formatDist(float meters) {
        if (meters < 1000) {
            return ((int) meters) + " m";
        } else if (meters < 10000) {
            return formatDec(meters / 1000f, 1) + " km";
        } else {
            return ((int) (meters / 1000f)) + " km";
        }
    }

    private static String formatDec(float val, int dec) {
        int factor = (int) Math.pow(10, dec);

        int front = (int) (val);
        int back = (int) Math.abs(val * (factor)) % factor;

        return front + "." + back;
    }

    public static String flattenStringList(List<String> list) {
        if (list == null || list.size() == 0) return null;
        String result = "";
        for (int i=0; i< list.size(); i++) {
            result += list.get(i) + (i < list.size()-1 ? ", " : "");
        }
        return result;
    }
}
