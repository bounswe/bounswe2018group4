package com.memorist.memorist_android.ws;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class GsonArrayRequest<T> extends Request<ArrayList<T>> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<ArrayList<T>> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     * @param clazz Relevant class object, for Gson's reflection
     * @param url     URL of the request to make
     * @param headers Map of request headers
     */
    public GsonArrayRequest(String url, Class<T> clazz, Map<String, String> headers,
                            Response.Listener<ArrayList<T>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<ArrayList<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Log.v("response: ", json);

            Type listType = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, clazz);
            ArrayList<T> tList = gson.fromJson(json, listType);
            return Response.success(
                    tList,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ArrayList<T> response) {
        listener.onResponse(response);
    }
}