package com.memorist.memorist_android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.memorist.memorist_android.fragment.LoginFragment;
import com.memorist.memorist_android.fragment.RecoverPasswordFragment;
import com.memorist.memorist_android.fragment.RegisterFragment;
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.model.ApiResultNoData;
import com.memorist.memorist_android.model.ApiResultUser;
import com.memorist.memorist_android.ws.MemoristApi;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterLoginActivity extends BaseActivity
    implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener,
        RecoverPasswordFragment.OnFragmentInteractionListener {

    private static final String TAG = "RegisterLoginActivity";
    private static final String TAG_LOGIN_FRAGMENT = "fragment_login";
    private static final String TAG_REGISTER_FRAGMENT = "fragment_register";
    private static final String TAG_RECOVER_PASSWORD = "fragment_recover_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        LoginFragment fragment = LoginFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.registerLoginFragmentContent,
                fragment, TAG_LOGIN_FRAGMENT).commit();

        askPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void askPermissions() {
        ActivityCompat.requestPermissions(RegisterLoginActivity.this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private Response.Listener<ApiResultUser> loginListener = new Response.Listener<ApiResultUser>() {
        @Override
        public void onResponse(ApiResultUser response) {
            String userWelcome = "Welcome, " + response.getUser().getFirst_name() + " " + response.getUser().getLast_name();
            String userToken = response.getToken();

            SharedPrefHelper.setUserToken(getApplicationContext(), userToken);
            Toast.makeText(getApplicationContext(), userWelcome, Toast.LENGTH_LONG).show();

            startActivity(new Intent(RegisterLoginActivity.this, MemoryActivity.class));
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            finish();
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String wrongCredentials = "Email or password is incorrect!";
            String serverIsDown = "We had a short maintenance break, please try again later.";

            if(error.networkResponse.statusCode == 400) {
                Toast.makeText(getApplicationContext(), wrongCredentials, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
            }
        }
    };

    private Response.Listener<ApiResultUser> registerListener = new Response.Listener<ApiResultUser>() {
        @Override
        public void onResponse(ApiResultUser response) {
            String userRegister = "Hooraaay! Lucky to have you aboard :)";
            Toast.makeText(getApplicationContext(), userRegister, Toast.LENGTH_LONG).show();

            onBackPressed();
        }
    };

    private Response.ErrorListener registerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<ApiResultNoData> recoveryListener = new Response.Listener<ApiResultNoData>() {
        @Override
        public void onResponse(ApiResultNoData response) {
            String recoveryInfo = "We have sent you an email for recovery details.";
            Toast.makeText(getApplicationContext(), recoveryInfo, Toast.LENGTH_LONG).show();

            onBackPressed();
        }
    };

    private Response.ErrorListener recoveryErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
        }
    };
}
