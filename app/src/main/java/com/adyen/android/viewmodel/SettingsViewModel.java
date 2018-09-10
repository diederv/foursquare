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
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.adyen.android.BR;
import com.adyen.android.SetRadiusListener;
import com.adyen.android.SnackbarTextMessage;
import com.adyen.android.foursquare.FoursquareServiceFactory;
import com.adyen.android.foursquare.detail.Category;
import com.adyen.android.foursquare.detail.Venue;
import com.adyen.android.util.SettingsUtil;
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
public class SettingsViewModel extends ObservableViewModel {

    public final static int RADIUS_DEFAUKT_VALUE = 250;

    private int radius;
    public SetRadiusListener radiusListener;

    public SettingsViewModel(Application context) {
        super(context);
        radius = SettingsUtil.getRadius(context, RADIUS_DEFAUKT_VALUE);
        notifyPropertyChanged(BR.radius);
        radiusListener = new SetRadiusListener() {
            @Override
            public void setRadius(int value) {
                SettingsUtil.setRadius(getApplication(), value);
                radius = value;
                notifyPropertyChanged(BR.radius);
                notifyPropertyChanged(BR.radiusStr);
            }
        };
    }

    @Bindable
    public int getRadius() {
        return radius;
    }

    @Bindable
    public String getRadiusStr() {
        return Integer.toString(radius) + " m";
    }

    public static SettingsViewModel obtainInstance(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(SettingsViewModel.class);
    }
}
