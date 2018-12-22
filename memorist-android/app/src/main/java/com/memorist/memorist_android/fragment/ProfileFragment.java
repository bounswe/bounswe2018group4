package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.model.ApiResultFollower;
import com.memorist.memorist_android.model.ApiResultFollowing;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.tv_profileUsername) TextView tvProfileUsername;
    @BindView(R.id.tv_profileNameSurname) TextView tvProfileNameSurname;
    @BindView(R.id.tv_profileCity) TextView tvProfileLocation;
    @BindView(R.id.tv_profileGender) TextView tvProfileGender;
    @BindView(R.id.tv_profilePostCount) TextView tvProfilePostCount;
    @BindView(R.id.iv_profileProfilePicture) ImageView ivProfilePicture;
    @BindView(R.id.lv_profileMemoryList) ListView lvProfileMemoryList;
    @BindView(R.id.tv_profileFollowersCount) TextView tvProfileFollowerCount;
    @BindView(R.id.tv_profileFollowingsCount) TextView tvProfileFollowingsCount;

    private OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Memory> memories;

    // The adapter to fit the data onto list.
    private MemoryAdapter adapter;

    // Followerings data
    private ArrayList<ApiResultFollower> followerList;
    private ArrayList<ApiResultFollowing> followingList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchMemoryFragment.
     */
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memories = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        mListener.getUserMemoryList();
        mListener.getUserProfile();
        mListener.getFollowers();
        mListener.getFollowings();

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

    public void updateProfileInfo(User user) {
        String userName = "@" + user.getUsername();
        String nameSurname = user.getFirst_name() + " " + user.getLast_name();
        String userGender = user.getGender();
        String userLocation = user.getLocation();
        String userAvatar = user.getPhoto();

        tvProfileUsername.setText(userName);
        tvProfileNameSurname.setText(nameSurname);

        if(userGender != null) {
            tvProfileGender.setVisibility(View.VISIBLE);

            if(userGender.equals("1")) {
                tvProfileGender.setText(String.valueOf("Male"));
            } else if(userGender.equals("2")) {
                tvProfileGender.setText(String.valueOf("Female"));
            } else {
                tvProfileGender.setText(String.valueOf("Other"));
            }
        }

        if(userLocation != null) {
            tvProfileLocation.setVisibility(View.VISIBLE);
            tvProfileLocation.setText(userLocation);
        }

        if(userAvatar != null) {
            String avatarURL = Constants.API_BASE_URL + "/multimedia/" + userAvatar;
            Picasso.get().load(avatarURL).into(ivProfilePicture);
        } else {
            ivProfilePicture.setImageDrawable(getResources().getDrawable(R.drawable.avatar_icon));
        }
    }

    public void updateMemories(ArrayList<Memory> memoryList) {
        memories.clear();
        memories.addAll(memoryList);

        if(adapter == null) {
            adapter = new MemoryAdapter(memories, getContext());
            lvProfileMemoryList.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
        tvProfilePostCount.setText(String.valueOf(memoryList.size()));
    }

    public void updateFollowers(ArrayList<ApiResultFollower> listFollowers) {
        tvProfileFollowerCount.setText(String.valueOf(listFollowers.size()));
        followerList = listFollowers;
    }

    public void updateFollowings(ArrayList<ApiResultFollowing> listFollowings) {
        tvProfileFollowingsCount.setText(String.valueOf(listFollowings.size()));
        followingList = listFollowings;
    }

    @OnClick(R.id.btn_profileFollowers)
    public void profileFollowersClicked() {
        mListener.getFollowerList(followerList);
    }

    @OnClick(R.id.btn_profileFollowings)
    public void profileFollowingsClicked() {
        mListener.getFollowingList(followingList);
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
        void getUserProfile();
        void getUserMemoryList();
        void getFollowers();
        void getFollowings();
        void getFollowerList(ArrayList<ApiResultFollower> list);
        void getFollowingList(ArrayList<ApiResultFollowing> list);
    }
}
