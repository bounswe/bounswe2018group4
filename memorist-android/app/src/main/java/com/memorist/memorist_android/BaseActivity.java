package com.memorist.memorist_android;

import android.support.v7.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

abstract class BaseActivity extends AppCompatActivity {

    KProgressHUD hud;

    protected void showIndicator() {
        hud = new KProgressHUD(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show();
    }

    protected void hideIndicator() {
        hud.dismiss();
    }
}
