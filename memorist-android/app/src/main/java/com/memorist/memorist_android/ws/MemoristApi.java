package com.memorist.memorist_android.ws;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.memorist.memorist_android.ApplicationClass;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.model.ApiResultCreatePost;
import com.memorist.memorist_android.model.ApiResultUser;

import java.util.ArrayList;
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

    public static void registerNewUser(String username, String password, String email, String firstName,
                                       String lastName, Response.Listener<ApiResultUser> registerListener,
                                       Response.ErrorListener registerErrorListener) {
        String url = Constants.API_REGISTER;

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("first_name", firstName);
        params.put("last_name", lastName);

        GsonRequest<ApiResultUser> request = new GsonRequest<>(Request.Method.POST, url,
                ApiResultUser.class, headers, params, registerListener, registerErrorListener);

        coreApi.addToRequestQueue(request);
    }

    public static void createNewMemory(String title, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                                       ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio,
                                       ArrayList<String> memoryTags, Response.Listener<ApiResultCreatePost> createPostListener,
                                       Response.ErrorListener createPostErrorListener) {
        // TODO: This method will be done.

    }
}
