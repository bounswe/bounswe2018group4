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

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    @BindView(R.id.et_registerUsername) EditText etRegisterUsername;
    @BindView(R.id.et_registerPassword) EditText etRegisterPassword;
    @BindView(R.id.et_registerEmail) EditText etRegisterEmail;
    @BindView(R.id.et_registerFirstName) EditText etRegisterFirstName;
    @BindView(R.id.et_registerLastName) EditText etRegisterLastName;

    // The interaction listener is defined.
    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        // Keyboard send button is listened.
        etRegisterLastName.setOnEditorActionListener(IME_SEND_PRESSED);

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

    @OnClick(R.id.btn_register)
    public void btnRegisterClicked(View view) {
        String username = etRegisterUsername.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String email = etRegisterEmail.getText().toString();
        String firstName = etRegisterFirstName.getText().toString();
        String lastName = etRegisterLastName.getText().toString();

        if(!username.equals("") && !password.equals("") && !email.equals("") && !firstName.equals("") && !lastName.equals("")) {
            mListener.processRegister(username, password, email, firstName, lastName);
        }
    }

    TextView.OnEditorActionListener IME_SEND_PRESSED = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                btnRegisterClicked(null);
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
        void processRegister(String username, String password, String email, String firstName, String lastName);
    }
}
