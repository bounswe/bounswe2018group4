package com.memorist.memorist_android;

import android.support.v7.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

abstract class BaseActivity extends AppCompatActivity {

    KProgressHUD hud;

    protected void showIndicator() {
        hud = new KProgressHUD(this);
        hud.setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel("Please Wait")
                .setCancellable(false)
                .setMaxProgress(100)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    protected void setIndicatorDetail(String indicatorDetail) {
        if(hud != null) {
            hud.setDetailsLabel(indicatorDetail);
        }
    }

    protected void setProgressDetail(int progress) {
        if(hud != null) {
            hud.setProgress(progress);
        }
    }

    protected void hideIndicator() {
        hud.dismiss();
        hud = null;
    }
}
