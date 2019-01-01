package com.memorist.memorist_android.ws;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.JsonObjectRequest;

import com.memorist.memorist_android.ApplicationClass;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.helper.JSONHelper;
import com.memorist.memorist_android.model.ApiResultFollower;
import com.memorist.memorist_android.model.ApiResultFollowing;
import com.memorist.memorist_android.model.ApiResultLike;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.model.ApiResultNoData;
import com.memorist.memorist_android.model.ApiResultProfile;
import com.memorist.memorist_android.model.ApiResultUser;
import com.memorist.memorist_android.model.Comments;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;

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

        coreApi.getRequestQueue().getCache().clear();
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

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void recoverUser(String userCredentials, Response.Listener<ApiResultNoData> recoveryListener,
                                   Response.ErrorListener errorListener) {
        // TODO : Connection to the api will be implemented later on.

    }

    public static void createMemory(final String token, String memoryTitle, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                                    int selectedDateType, String selectedDateFormat, String mentionedTime, String mentionedTime2,
                                    ArrayList<Integer> memoryMultimediaID, ArrayList<String> memoryTags,
                                    Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws JSONException {
        String url = Constants.API_CREATE_MEMORY;

        JSONObject JRequestObject = new JSONObject();
        JRequestObject.put("title", memoryTitle);
        JRequestObject.put("story", JSONHelper.listToJSONArrayForString(memoryText));
        JRequestObject.put("media", JSONHelper.listToJSONArrayForInteger(memoryMultimediaID));
        JRequestObject.put("format", JSONHelper.listToJSONArrayForString(memoryFormat));
        JRequestObject.put("tags", JSONHelper.listToJSONArrayForString(memoryTags));
        JRequestObject.put("date_type", selectedDateType);
        JRequestObject.put("date_format", selectedDateFormat);
        JRequestObject.put("date_string1", mentionedTime);
        JRequestObject.put("date_string2", mentionedTime2);

        Log.d("Sent json: ", JRequestObject.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, JRequestObject, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);

                return headers;
            }
        };

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryImage(final Context context, final File imageFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_PHOTO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, imageFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryVideo(final Context context, final File videoFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_VIDEO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, videoFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void createMemoryAudio(final Context context, final File audioFile, final String filePath,
                                         Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                         Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_CREATE_AUDIO;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<>(Request.Method.POST, url,
                ApiResultMediaUpload.class, audioFile, filePath, mediaUploadListener, mediaUploadErrorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getMemoryList(String token, Response.Listener<ArrayList<Memory>> listListener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_MEMORY;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        GsonArrayRequest<Memory> request = new GsonArrayRequest<>(url, Memory.class, headers, listListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getUserMemoryList(String token, Response.Listener<ArrayList<Memory>> listListener, Response.ErrorListener errorListener) {
        String url = Constants.API_PROFILE_MEMORY_LIST;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        GsonArrayRequest<Memory> request = new GsonArrayRequest<>(url, Memory.class, headers, listListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getProfile(String token, int userID, Response.Listener<User> listener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_PROFILE + userID + "/";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        Map<String, String> params = new HashMap<>();

        GsonRequest<User> request = new GsonRequest<>(Request.Method.GET, url,
                User.class, headers, params, listener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void postLike(String token, int memoryID) {
        String url = Constants.API_POST_LIKE + memoryID + "/";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultLike> request = new GsonRequest<>(Request.Method.GET, url,
                ApiResultLike.class, headers, params, null, null);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void postDislike(String token, int memoryID) {
        String url = Constants.API_POST_DISLIKE + memoryID + "/";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultLike> request = new GsonRequest<>(Request.Method.GET, url,
                ApiResultLike.class, headers, params, null, null);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getFollowers(String token, Response.Listener<ArrayList<ApiResultFollower>> listListener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_FOLLEWERS;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        GsonArrayRequest<ApiResultFollower> request = new GsonArrayRequest<>(url, ApiResultFollower.class, headers, listListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getFollowings(String token, Response.Listener<ArrayList<ApiResultFollowing>> listListener, Response.ErrorListener errorListener) {
        String url = Constants.API_GET_FOLLOWINGS;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        GsonArrayRequest<ApiResultFollowing> request = new GsonArrayRequest<>(url, ApiResultFollowing.class, headers, listListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void updateProfileInfo(String token, String first, String last, int gender, String location, Response.Listener<ApiResultProfile> listener, Response.ErrorListener errorListener) {
        String url = Constants.API_EDIT_PROFILE;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        Map<String, String> params = new HashMap<>();
        params.put("first_name", first);
        params.put("last_name", last);
        params.put("location", location);
        params.put("gender", String.valueOf(gender));

        GsonRequest<ApiResultProfile> request = new GsonRequest<>(Request.Method.POST, url, ApiResultProfile.class, headers, params, listener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void photoUpdate(Context context, final String token, File imageFile, String filePath,
                                   Response.Listener<ApiResultMediaUpload> mediaUploadListener,
                                   Response.ErrorListener mediaUploadErrorListener) {
        String url = Constants.API_PHOTO_UPDATE;

        VolleyMultipartRequest<ApiResultMediaUpload> request = new VolleyMultipartRequest<ApiResultMediaUpload>(Request.Method.POST, url,
                ApiResultMediaUpload.class, imageFile, filePath, mediaUploadListener, mediaUploadErrorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);

                return headers;
            }
        };

        request.setMediaUploadType(1);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void sendComment(String token, final String comment, int memoryID,
                                   Response.Listener<ArrayList<Comments>> listListener, Response.ErrorListener errorListener) {
        String url = Constants.API_CREATE_COMMENT + memoryID + "/";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);

        GsonArrayRequest<Comments> request = new GsonArrayRequest<Comments>(url, Comments.class, headers, listListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("comment", comment);

                return params;
            }

            @Override
            public int getMethod() {
                return Method.POST;
            }
        };

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getRecommendationList(String userToken, Response.Listener<ArrayList<Memory>> getUserRecommendationListener, Response.ErrorListener getUserRecommendationErrorListener) {
        String url = Constants.API_RECOMMENDATIONS;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);

        GsonArrayRequest<Memory> request = new GsonArrayRequest<>(url, Memory.class, headers, getUserRecommendationListener, getUserRecommendationErrorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }
}
