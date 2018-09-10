/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adyen.android.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.adyen.android.foursquare.detail.Category;
import com.adyen.android.BR;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.adyen.android.foursquare.FoursquareServiceFactory;
import com.adyen.android.foursquare.detail.Venue;
import com.adyen.android.SnackbarTextMessage;
import com.adyen.android.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Exposes the data to be used in the task list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class VenueDetailViewModel extends ObservableViewModel {

    private String venueId = null;
    private String name = null;
    private String address = null;
    private String country = null;
    private String city = null;
    private String state = null;
    private String crossStreet = null;

    private boolean loading = false;

    private List<Category> categories;

    private final SnackbarTextMessage mSnackbarText = new SnackbarTextMessage();

    public VenueDetailViewModel(Application context) {
        super(context);
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
        loadDetails();
    }

    private void loadDetails() {
        setLoading(true);
        FoursquareServiceFactory.getVenueDetail(venueId, getApplication().getResources())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            setVenue(response.getResponse().getVenue());
                            setLoading(false);
                        },
                        error -> {
                            showSnackbarMessage("Error: "+ error.getMessage());
                            Log.e(VenueDetailViewModel.class.getSimpleName(), error.getMessage());
                            setLoading(false);
                        }
                );
    }

    private void setVenue(Venue venue) {
        name = venue.getName();
        address = StringUtil.flattenStringList(venue.getLocation().getFormattedAddress());
        address = address != null ? address : venue.getLocation().getAddress();
        country = venue.getLocation().getCountry();
        city = venue.getLocation().getCity();
        state = venue.getLocation().getState();
        crossStreet = venue.getLocation().getCrossStreet();
        categories = venue.getCategories();
        notifyPropertyChanged(BR._all);

    }

    public SnackbarTextMessage getSnackbarTextMessage() {
        return mSnackbarText;
    }

    private void showSnackbarMessage(String message) {
        mSnackbarText.setValue(message);
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getAddress() {
        return address;
    }
    @Bindable
    public String getCountry() {
        return country;
    }

    @Bindable
    public String getCity() {
        return city;
    }

    @Bindable
    public String getState() {
        return state;
    }

    @Bindable
    public String getCrossStreet() {
        return crossStreet;
    }

    @Bindable
    public List<String> getCategories() {
        List<String> cats = new ArrayList<>();
        if (categories != null) {
            for (Category cat : categories) {
                cats.add(cat.getName());
            }
        }
        return cats;
    }

    private void setLoading(boolean value) {
        if (loading != value) {
            loading = value;
            notifyPropertyChanged(BR.loading);
        }
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public static VenueDetailViewModel obtainInstance(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(VenueDetailViewModel.class);
    }
}
