package com.memorist.memorist_android.ws;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * This class contains the api that will handle core network requests and actions.
 * It uses a requestQueue and adds the requests into its requestQueue instance.
 */
public class CoreApi {

    // The class TAG.
    private static final String TAG = "CoreApi";

    // RequestQueue is used to execute incoming requests.
    private RequestQueue mRequestQueue;

    // An instance of CoreApi is held to use when it is needed by a function.
    private static CoreApi sInstance;

    // The context that the Api will be used.
    private Context appContext;

    public CoreApi(Context context) {
        sInstance = this;
        appContext = context;
    }

    /**
     * Gets the instance of CoreApi.
     * @return CoreApi
     */
    public CoreApi get() {
        return sInstance;
    }

    /**
     * Creates a new requestQueue if it does not exist and returns it.
     * Or it returns what already exists.
     *
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(appContext);
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req: The incoming request.
     * @param tag: Where about of the request.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // Set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req: The incoming request.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // Set the default tag if tag is empty
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag: Where about of the request.
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
