package com.adyen.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.adyen.android.R;
import com.adyen.android.databinding.VenueDetailsBinding;
import com.adyen.android.viewmodel.VenueDetailViewModel;


public class VenueDetailActivity extends AppCompatActivity {

    public final static String EXTRA_VENUE_ID = "EXTRA_VENUE_ID";

    private VenueDetailsBinding binding;
    private VenueDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = VenueDetailViewModel.obtainInstance(this);
        String venueId = getIntent().getStringExtra(EXTRA_VENUE_ID);
        mViewModel.setVenueId(venueId);
        setupSnackbar();
        binding = DataBindingUtil.setContentView(this, R.layout.venue_details);
        binding.setViewmodel(mViewModel);
    }

    private void setupSnackbar() {
        mViewModel.getSnackbarTextMessage().observe(this, new SnackbarTextMessage.SnackbarTextObserver() {
            @Override
            public void onNewMessage(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();            }
        });
    }
}
