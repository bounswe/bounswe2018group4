package com.memorist.memorist_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.memorist.memorist_android.fragment.LoginFragment;
import com.memorist.memorist_android.fragment.RecoverPasswordFragment;
import com.memorist.memorist_android.fragment.RegisterFragment;
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.model.ApiResultNoData;
import com.memorist.memorist_android.model.ApiResultUser;
import com.memorist.memorist_android.ws.MemoristApi;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterLoginActivity extends BaseActivity
    implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener,
        RecoverPasswordFragment.OnFragmentInteractionListener {

    private static final String TAG = "RegisterLoginActivity";
    private static final String TAG_LOGIN_FRAGMENT = "fragment_login";
    private static final String TAG_REGISTER_FRAGMENT = "fragment_register";
    private static final String TAG_RECOVER_PASSWORD = "fragment_recover_password";

    // The database manager used in application.
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        // Initializing the tools.
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        // Whether the user has already credentials.
        if(SharedPrefHelper.getUserToken(getApplicationContext()) != null) {
            startActivity(new Intent(RegisterLoginActivity.this, MemoryActivity.class));
            finish();
        } else {
            LoginFragment fragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.registerLoginFragmentContent,
                    fragment, TAG_LOGIN_FRAGMENT).commit();
        }
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

    @Override
    public void processRegister(String username, String password, String email, String firstName, String lastName) {
        MemoristApi.registerNewUser(username, password, email, firstName, lastName, registerListener, registerErrorListener);
    }

    @Override
    public void processRecovery(String userCredentials) {
        MemoristApi.recoverUser(userCredentials, recoveryListener, recoveryErrorListener);
    }

    @Override
    public void proceedToRegister() {
        RegisterFragment fragment = RegisterFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                .enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.registerLoginFragmentContent, fragment, TAG_REGISTER_FRAGMENT);
        fragmentTransaction.addToBackStack(TAG_REGISTER_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void proceedToRecover() {
        RecoverPasswordFragment fragment = RecoverPasswordFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim
                .enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.registerLoginFragmentContent, fragment, TAG_RECOVER_PASSWORD);
        fragmentTransaction.addToBackStack(TAG_RECOVER_PASSWORD);
        fragmentTransaction.commit();
    }

    private Response.Listener<ApiResultUser> loginListener = new Response.Listener<ApiResultUser>() {
        @Override
        public void onResponse(ApiResultUser response) {
            Toast.makeText(getApplicationContext(), "Login is successful", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterLoginActivity.this, MemoryActivity.class));
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            finish();

            String token = response.getToken();
            SharedPrefHelper.setUserToken(getApplicationContext(), token);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Login is NOT successful", Toast.LENGTH_LONG).show();
            Log.v("Error", error.toString());

            byte[] msg = error.networkResponse.data;
            String smsg = "";

            for(byte m: msg) {
                char c = (char)m;
                smsg += c;
            }

            Log.v("msg", smsg);
        }
    };

    private Response.Listener<ApiResultUser> registerListener = new Response.Listener<ApiResultUser>() {
        @Override
        public void onResponse(ApiResultUser response) {
            Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    };

    private Response.ErrorListener registerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Register is NOT successful", Toast.LENGTH_LONG).show();
            Log.v("Error", error.toString());
        }
    };

    private Response.Listener<ApiResultNoData> recoveryListener = new Response.Listener<ApiResultNoData>() {
        @Override
        public void onResponse(ApiResultNoData response) {
            Toast.makeText(getApplicationContext(), "Recovery is successful", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    };

    private Response.ErrorListener recoveryErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Recovery is NOT successful", Toast.LENGTH_LONG).show();
            Log.v("Error", error.toString());
        }
    };
}
