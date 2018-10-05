package com.memorist.memorist_android.ws;

import com.android.volley.Request;
import com.android.volley.Response;
import com.memorist.memorist_android.ApplicationClass;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.model.ApiResultUser;

import java.util.HashMap;
import java.util.Map;

public class MemoristApi {

    // The coreApi instance for the application.
    private static CoreApi coreApi = ApplicationClass.getCoreApi();

    public static void loginUsernamePassword(String username, String password,
                                             Response.Listener listener, Response.ErrorListener errorListener) {
        String url = Constants.API_LOGIN;

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        GsonRequest<ApiResultUser> request = new GsonRequest<>(Request.Method.POST, url,
                ApiResultUser.class, headers, params, listener, errorListener);

        coreApi.addToRequestQueue(request);
    }
}
