package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.memorist.memorist_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // Binding the view components.
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.tv_loginNotRegistered) TextView tvNotRegistered;
    @BindView(R.id.tv_forgetMyPassword) TextView tvRecoverPassword;

    // The interaction listener is defined.
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        tvNotRegistered.setOnClickListener(notRegisteredClickListener);
        tvRecoverPassword.setOnClickListener(recoverPasswordClickListener);

        // Keyboard send button is listened.
        etPassword.setOnEditorActionListener(IME_SEND_PRESSED);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.btn_login)
    public void btnLoginClicked(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(!username.equals("") && !password.equals("")) {
            mListener.processLogin(username, password);
        }
    }

    private TextView.OnClickListener notRegisteredClickListener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.proceedToRegister();
        }
    };

    private TextView.OnClickListener recoverPasswordClickListener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.proceedToRecover();
        }
    };

    TextView.OnEditorActionListener IME_SEND_PRESSED = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                btnLoginClicked(null);
                return true;
            }
            return false;
        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void processLogin(String username, String password);
        void proceedToRegister();
        void proceedToRecover();
    }
}
