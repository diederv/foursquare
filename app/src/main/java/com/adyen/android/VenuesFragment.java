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

package com.adyen.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adyen.android.foursquare.Venue;
import com.adyen.android.R;
import com.adyen.android.databinding.VenueFragBinding;
import com.adyen.android.viewmodel.VenuesViewModel;

import java.util.ArrayList;

/**
 * Display a grid of {@link Venue}s. User can choose to view all, active or completed tasks.
 */
public class VenuesFragment extends Fragment {

    private VenuesViewModel mVenuesViewModel;

    private VenueFragBinding mVenueFragBinding;

    private VenuesAdapter mVenuesAdapter;

    public VenuesFragment() {
        // Requires empty public constructor
    }

    public static VenuesFragment newInstance() {
        return new VenuesFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mVenueFragBinding = VenueFragBinding.inflate(inflater, container, false);

        mVenuesViewModel = VenuesViewModel.obtainInstance(getActivity());

        mVenueFragBinding.setViewmodel(mVenuesViewModel);

        setHasOptionsMenu(true);

        return mVenueFragBinding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                //TODO: change settings
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupSnackbar();
        setupListAdapter();
    }

    private void setupSnackbar() {
        mVenuesViewModel.getSnackbarMessage().observe(this, new SnackbarMessage.SnackbarObserver() {
            @Override
            public void onNewMessage(@StringRes int snackbarMessageResourceId) {
                Snackbar.make(getView(), getString(snackbarMessageResourceId), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupListAdapter() {
        ListView listView =  mVenueFragBinding.venueList;

        mVenuesAdapter = new VenuesAdapter(
                new ArrayList<Venue>(0),
                mVenuesViewModel
        );
        listView.setAdapter(mVenuesAdapter);
    }
}
