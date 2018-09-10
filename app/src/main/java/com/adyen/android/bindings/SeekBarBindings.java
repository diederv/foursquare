package com.adyen.android.bindings;

import android.databinding.BindingAdapter;
import android.widget.SeekBar;

import com.adyen.android.SetRadiusListener;

public class SeekBarBindings {

    @BindingAdapter(value = {"listener", "setRadius"}, requireAll = true)
    public static void register(SeekBar view, SetRadiusListener listener, int progress) {

        view.setProgress(progress);

        view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int progress = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) { }

            public void onStopTrackingTouch(SeekBar seekBar) {
                listener.setRadius(progress);
            }
        });
    }
}
