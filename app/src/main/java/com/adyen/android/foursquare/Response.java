
package com.adyen.android.foursquare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.adyen.android.util.Comparator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("venues")
    @Expose
    private List<Venue> venues = null;
    @SerializedName("confident")
    @Expose
    private Boolean confident;

    public List<Venue> getVenues() {
        if (venues == null) {
            return new ArrayList<>();
        }
        Collections.sort(venues, new Comparator());
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

}
