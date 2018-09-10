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
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.adyen.android.foursquare.FoursquareServiceFactory;
import com.adyen.android.foursquare.Venue;
import com.adyen.android.SingleLiveEvent;
import com.adyen.android.SnackbarMessage;
import com.adyen.android.R;
import com.adyen.android.util.SettingsUtil;
import com.google.android.gms.location.places.Place;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Exposes the data to be used in the task list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class VenuesViewModel extends AndroidViewModel {

    private final FoursquareServiceFactory.LatLng defaultLocation;

    private FoursquareServiceFactory.LatLng location = null;

    private Venue selectedVenue = null;

    public final ObservableList<Venue> items = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final SingleLiveEvent<Void> refreshLocationEvent = new SingleLiveEvent<>();
    public final SingleLiveEvent<Void> selectVenueEvent = new SingleLiveEvent<>();

    public SwipeRefreshLayout.OnRefreshListener swipeRefreshListener;

    public final ObservableBoolean empty = new ObservableBoolean(false);

    private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    private final SingleLiveEvent<String> mOpenTaskEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mNewTaskEvent = new SingleLiveEvent<>();

    public VenuesViewModel(Application context) {
        super(context);
        defaultLocation = new FoursquareServiceFactory.LatLng(
            context.getString(R.string.default_location)
        );
        location = defaultLocation;
        swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadVenues();
            }
        };
    }

    public SnackbarMessage getSnackbarMessage() {
        return mSnackbarText;
    }

    SingleLiveEvent<String> getOpenTaskEvent() {
        return mOpenTaskEvent;
    }

    public void selectVenue(Venue venue) {
        selectedVenue = venue;
        selectVenueEvent.call();
    }

    public Venue getSelectedVenue() {
        return selectedVenue;
    }

    private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }

    public void setLocation(Location location) {
        this.location = location != null ?
                new FoursquareServiceFactory.LatLng(location) : defaultLocation;
        loadVenues();
    }

    public void setLocation(Place location) {
        this.location = location != null ?
                new FoursquareServiceFactory.LatLng(location) : defaultLocation;
        loadVenues();
    }

    private void loadVenues() {

        dataLoading.set(true);
        int radius = SettingsUtil.getRadius(getApplication(), SettingsViewModel.RADIUS_DEFAUKT_VALUE);
        FoursquareServiceFactory.getVenuesByLocation(location, radius, getApplication().getResources())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            items.clear();
                            for (Venue venue : response.getResponse().getVenues()) {
                                items.add(venue);
                            }
                            empty.set(items.isEmpty());
                            dataLoading.set(false);
                        },
                        error -> {
                            //mIsDataLoadingError.set(true);
                            Log.e(VenuesViewModel.class.getSimpleName(), error.getMessage());
                        }
                );
    }

    public static VenuesViewModel obtainInstance(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(VenuesViewModel.class);
    }
}
