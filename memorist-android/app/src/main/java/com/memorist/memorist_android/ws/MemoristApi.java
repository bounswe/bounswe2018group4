package com.memorist.memorist_android.ws;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.memorist.memorist_android.ApplicationClass;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.helper.JSONHelper;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.model.ApiResultNoData;
import com.memorist.memorist_android.model.ApiResultProfile;
import com.memorist.memorist_android.model.ApiResultUser;
import com.memorist.memorist_android.model.Memory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoristApi {

    // The coreApi instance for the application.
    private static CoreApi coreApi = ApplicationClass.getCoreApi();

    public static void loginUsernamePassword(String username, String password,
                                             Response.Listener<ApiResultUser> listener, Response.ErrorListener errorListener) {
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

    public static void recoverUser(String userCredentials, Response.Listener<ApiResultNoData> recoveryListener,
                                   Response.ErrorListener errorListener) {

        // TODO : Connection to the api will be implemented later on.

    }

    public static void createMemory(final String token, String memoryTitle, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                                    ArrayList<Integer> memoryMultimediaID, ArrayList<String> memoryTags) throws JSONException {
        String url = Constants.API_CREATE_MEMORY;

        JSONObject JRequestObject = new JSONObject();
        JRequestObject.put("title", memoryTitle);
        JRequestObject.put("story", JSONHelper.listToJSONArray(memoryText));
        JRequestObject.put("media", JSONHelper.listToJSONArray2(memoryMultimediaID));
        JRequestObject.put("format", JSONHelper.listToJSONArray(memoryFormat));
        JRequestObject.put("tags", JSONHelper.listToJSONArray(memoryTags));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JRequestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Erroneous Response", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);

                return headers;
            }
        };

        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryImage(final Context context, final File imageFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_PHOTO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, imageFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryVideo(final Context context, final File videoFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_VIDEO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, videoFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryAudio(final Context context, final File audioFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_AUDIO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, audioFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.addToRequestQueue(request);
    }

    public static void getMemoryList(final String token, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_MEMORY;

        JSONArray JRequestArray = new JSONArray();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, JRequestArray, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);

                return headers;
            }
        };

        coreApi.addToRequestQueue(request);
    }

    public static void getProfile(String token, Response.Listener<ApiResultProfile> listener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_PROFILE;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultProfile> request = new GsonRequest<>(Request.Method.GET, url,
                ApiResultProfile.class, headers, params, listener, errorListener);

        coreApi.addToRequestQueue(request);
    }
}
