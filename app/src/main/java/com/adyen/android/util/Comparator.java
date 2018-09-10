package com.adyen.android.util;

import com.adyen.android.foursquare.Venue;

public class Comparator implements java.util.Comparator<Venue> {

    @Override
    public int compare(Venue o1, Venue o2) {
        long d1 = o1.getLocation().getDistance();
        long d2 = o2.getLocation().getDistance();
        return d1 > d2 ? 1 : d1 < d2 ? -1 : 0;
    }
}
