package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.model.ApiResultProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    private String FIRST_NAME;
    private String LAST_NAME;
    private String GENDER;
    private String LOCATION;

    @BindView(R.id.et_editProfileFirstName) EditText etEditProfileFirstName;
    @BindView(R.id.et_editProfileLastName) EditText etEditProfileLastName;
    @BindView(R.id.et_editProfileGender) EditText etEditProfileGender;
    @BindView(R.id.et_editProfileLocation) EditText etEditProfileLocation;

    private OnFragmentInteractionListener mListener;

    public EditProfileFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        mListener.getUserProfileForEdit();
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

    public void updateProfileInfo(ApiResultProfile resultProfile) {
        etEditProfileFirstName.setText(resultProfile.getFirst_name());
        etEditProfileLastName.setText(resultProfile.getLast_name());
        //etEditProfileGender.setText(GENDER);
        //etEditProfileLocation.setText(LOCATION);
    }

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
        void getUserProfileForEdit();
    }
}
