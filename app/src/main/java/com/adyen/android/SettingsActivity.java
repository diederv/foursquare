package com.adyen.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adyen.android.databinding.SettingsBinding;
import com.adyen.android.viewmodel.SettingsViewModel;


public class SettingsActivity extends AppCompatActivity {

    private SettingsBinding binding;
    private SettingsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = SettingsViewModel.obtainInstance(this);
        binding = DataBindingUtil.setContentView(this, R.layout.settings);
        binding.setViewmodel(mViewModel);
    }
}
