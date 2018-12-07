package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.model.Memory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.tv_profileNameSurname) TextView tvProfileNameSurname;
    @BindView(R.id.tv_profileUsername) TextView tvProfileUsername;
    @BindView(R.id.tv_profileBio) TextView tvProfileEmail;
    @BindView(R.id.lv_profileMemoryList) ListView lvProfileMemoryList;
    @BindView(R.id.tv_profilePostCount) TextView tvProfilePostCount;

    private OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Memory> memories;

    // The adapter to fit the data onto list.
    private MemoryAdapter adapter;

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

        memories = new ArrayList<Memory>();
        mListener.getUserMemoryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        mListener.getUserProfile();
        adapter = new MemoryAdapter(memories, getContext());
        lvProfileMemoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    public void updateProfileInfo(String username, String firstname, String lastname, String email) {
        String finaluser = "@" + username;
        tvProfileUsername.setText(finaluser);
        String name = firstname + " " + lastname;
        tvProfileNameSurname.setText(name);
        tvProfileEmail.setText(email);
    }

    public void updateMemories(ArrayList<Memory> memoryList) {
        memories.clear();
        memories.addAll(memoryList);

        if(adapter == null) {
            adapter = new MemoryAdapter(memories, getContext());
            lvProfileMemoryList.setAdapter(adapter);
        }

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
        void getUserProfile();
        void getUserMemoryList();
    }
}
