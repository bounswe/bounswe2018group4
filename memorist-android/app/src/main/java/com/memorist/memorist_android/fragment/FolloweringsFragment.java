package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.memorist.memorist_android.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FolloweringsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FolloweringsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FolloweringsFragment extends Fragment {

    @BindView(R.id.tv_followering_follow) TextView tvFollowing;
    @BindView(R.id.tv_followering_followed) TextView tvFollower;
    @BindView(R.id.lv_followerings) ListView lvFollowerings;

    private static final String FOLLOWER = "follower";
    private static final String FOLLOWING = "following";

    private ArrayList<Integer> followers;
    private ArrayList<Integer> followings;

    private ArrayAdapter<Integer> adapter;

    private OnFragmentInteractionListener mListener;

    public FolloweringsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Followerings.
     */
    public static FolloweringsFragment newInstance(ArrayList<Integer> listFollower, ArrayList<Integer> listFollowing) {
        FolloweringsFragment fragment = new FolloweringsFragment();

        Bundle args = new Bundle();
        args.putIntegerArrayList(FOLLOWER, listFollower);
        args.putIntegerArrayList(FOLLOWING, listFollowing);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            followers = getArguments().getIntegerArrayList(FOLLOWER);
            followings = getArguments().getIntegerArrayList(FOLLOWING);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /// Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_followerings, container, false);
        ButterKnife.bind(this, view);

        setContent();
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

    public void setContent() {
        if(followers != null) {
            adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, followers);
            tvFollower.setVisibility(View.VISIBLE);
            tvFollowing.setVisibility(View.GONE);
        } else {
            adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, followings);
            tvFollower.setVisibility(View.GONE);
            tvFollowing.setVisibility(View.VISIBLE);
        }

        lvFollowerings.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    }
}
