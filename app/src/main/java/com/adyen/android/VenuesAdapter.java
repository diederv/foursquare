/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.adyen.android;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.adyen.android.foursquare.Venue;
import com.adyen.android.databinding.VenueItemBinding;
import com.adyen.android.viewmodel.VenuesViewModel;

import java.util.List;


public class VenuesAdapter extends BaseAdapter {

    private final VenuesViewModel mTasksViewModel;

    private List<Venue> mVenues;

    public VenuesAdapter(List<Venue> venues,
                         VenuesViewModel tasksViewModel) {
        mTasksViewModel = tasksViewModel;
        setList(venues);
    }

    public void replaceData(List<Venue> venues) {
        setList(venues);
    }

    @Override
    public int getCount() {
        return mVenues != null ? mVenues.size() : 0;
    }

    @Override
    public Venue getItem(int position) {
        return mVenues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        VenueItemBinding binding;
        if (view == null) {
            // Inflate
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            // Create the binding
            binding = VenueItemBinding.inflate(inflater, viewGroup, false);
        } else {
            // Recycling view
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setVenue(mVenues.get(position));

        binding.setEven(position % 2 == 0);
        binding.setListener(new VenueUserActionsListener() {
            @Override
            public void onVenueSelected(Venue venue) {
                mTasksViewModel.selectVenue(venue);
            }
        });

        binding.executePendingBindings();
        return binding.getRoot();
    }


    private void setList(List<Venue> venues) {
        mVenues = venues;
        notifyDataSetChanged();
    }
}
