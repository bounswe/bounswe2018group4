package com.memorist.memorist_android;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.memorist.memorist_android.fragment.LoginFragment;
import com.memorist.memorist_android.model.ApiResultUser;
import com.memorist.memorist_android.ws.MemoristApi;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterLoginActivity extends BaseActivity
    implements LoginFragment.OnFragmentInteractionListener {

    private static final String TAG = "RegisterLoginActivity";
    private static final String TAG_LOGIN_FRAGMENT = "fragment_login";

    // The database manager used in application.
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        // Initializing the tools.
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.registerLoginFragmentContent,
                fragment, TAG_LOGIN_FRAGMENT).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * Font set up for the activity.
     * @param newBase: The context which the fonts will be set on.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void processLogin(String username, String password) {
        MemoristApi.loginUsernamePassword(username, password, loginListener, errorListener);
    }

    private Response.Listener<ApiResultUser> loginListener = new Response.Listener<ApiResultUser>() {
        @Override
        public void onResponse(ApiResultUser response) {
            Toast.makeText(getApplicationContext(), "Login is successful", Toast.LENGTH_LONG).show();
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Login is NOT successful", Toast.LENGTH_LONG).show();
            Log.v("Error", error.toString());
        }
    };
}
