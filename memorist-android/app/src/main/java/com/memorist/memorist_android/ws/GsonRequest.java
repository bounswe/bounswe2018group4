package com.memorist.memorist_android.ws;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Gson is an open source Java library to serialize and deserialize Java objects to (and from) JSON.
 * This class materializes the GsonRequest functions and their execution.
 *
 * @param <T>: Expected model type for result
 */
public class GsonRequest<T> extends Request<T> {

    public static final String TAG = "GsonRequest";

    // A new instance of Gson
    private final Gson gson = new Gson();

    // The class type which the result will be mapped to.
    private final Class<T> clazz;

    // Header contents are mapped.
    private final Map<String, String> headers;

    // Parameter contents are mapped.
    private final Map<String, String> params;

    // When the respond is arrived, this listener will carry the response.
    private final Response.Listener<T> listener;

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Map<String, String> params, Response.Listener<T> listener, Response
                               .ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }

    /**
     * Gets the used header contents.
     *
     * @return Map<String, String>: Header content
     * @throws AuthFailureError: Could give error when authenticating the request
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    /**
     * Gets the used parameter contents.
     *
     * @return Map<String, String>: Parameter content
     * @throws AuthFailureError: Could give error when authenticating the request
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    /**
     * Obtains the network response, parses it into a json string and maps the content into
     * given object type/class.
     *
     * @param response: The response object came from the server.
     * @return Response object
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T data = null;

            if(clazz != null) {
                data = gson.fromJson(json, clazz);
            }

            return Response.success(data, null);
        }catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    public void deliverError(VolleyError error) {
        getErrorListener().onErrorResponse(error);
    }

    /**
     * Uploads the response onto listener.
     * @param response: Responded object
     */
    @Override
    protected void deliverResponse(T response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }
}