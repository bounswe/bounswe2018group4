package com.memorist.memorist_android;

import android.app.Application;
import com.memorist.memorist_android.ws.CoreApi;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * This class contains the features that will be used throughout the entire application.
 */
public class ApplicationClass extends Application {

    // The Api that which will handle network actions and requests.
    private static CoreApi coreApi;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the core Api.
        coreApi = new CoreApi(getApplicationContext());

        // Initialize the default font.
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    /**
     * Gets the core Api that will be used for network actions and requests.
     * Avoids the thread interference and memory consistency errors.
     *
     * @return CoreApi: Synchronized for safety.
     */
    public static synchronized CoreApi getCoreApi(){
        return coreApi.get();
    }
}
