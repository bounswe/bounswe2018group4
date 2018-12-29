package com.memorist.memorist_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.memorist.memorist_android.R;
import com.memorist.memorist_android.helper.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    @BindView(R.id.iv_editProfilePicture) ImageView ivProfilePicture;
    @BindView(R.id.et_editProfileFirstName) EditText etEditProfileFirstName;
    @BindView(R.id.et_editProfileLastName) EditText etEditProfileLastName;
    @BindView(R.id.et_editProfileGender) EditText etEditProfileGender;
    @BindView(R.id.et_editProfileLocation) EditText etEditProfileLocation;

    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String GENDER = "gender";
    private static final String LOCATION = "location";
    private static final String PHOTO = "photo";

    private int GALLERY_REQUEST = 1;

    private String mUsername;
    private String mFirstName;
    private String mLastName;
    private String mGender;
    private String mLocation;
    private String mPhoto;
    private Uri imageChange;

    private OnFragmentInteractionListener mListener;

    public EditProfileFragment() {
        // Empty constructor..
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String username, String first_name, String last_name, String gender, String location, String photo) {
        EditProfileFragment fragment = new EditProfileFragment();

        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(FIRST_NAME, first_name);
        args.putString(LAST_NAME, last_name);
        args.putString(GENDER, gender);
        args.putString(LOCATION, location);
        args.putString(PHOTO, photo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mUsername = getArguments().getString(USERNAME);
            mFirstName = getArguments().getString(FIRST_NAME);
            mLastName = getArguments().getString(LAST_NAME);
            mGender = getArguments().getString(GENDER);
            mLocation = getArguments().getString(LOCATION);
            mPhoto = getArguments().getString(PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        String avatarURL = Constants.API_BASE_URL + "/multimedia/" + mPhoto;

        etEditProfileFirstName.setText(mFirstName);
        etEditProfileLastName.setText(mLastName);
        etEditProfileLocation.setText(mLocation);
        Picasso.get().load(avatarURL).into(ivProfilePicture);

        if(mGender.equals("1")) {
            etEditProfileGender.setText("Male");
        } else if(mGender.equals("2")) {
            etEditProfileGender.setText("Female");
        } else {
            etEditProfileGender.setText("Other");
        }

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                imageChange = data.getData();
                ivProfilePicture.setImageURI(imageChange);
            }
        }
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

    @OnClick(R.id.btn_editProfileInformation)
    public void profileInformationUpdate() {
        String newFirst = etEditProfileFirstName.getText().toString();
        String newLast = etEditProfileLastName.getText().toString();
        String newGender = etEditProfileGender.getText().toString();
        String newLocation = etEditProfileLocation.getText().toString();
        int genderType = 0;

        if(newGender.equalsIgnoreCase("Male")) {
            genderType = 1;
        } else if(newGender.equalsIgnoreCase("Female")) {
            genderType = 2;
        } else {
            genderType = 3;
        }

        mListener.updateProfileInformation(newFirst, newLast, genderType, newLocation);
        if(imageChange != null) {
            mListener.profilePhotoUpdate(imageChange);
        }
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
        void updateProfileInformation(String first, String last, int gender, String location);
        void profilePhotoUpdate(Uri photo);
    }
}
