package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.model.Memory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecommendationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommendationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationsFragment extends Fragment {

    @BindView(R.id.lv_recommendationsMemoryList) ListView lvRecommendationsMemoryList;

    private OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Memory> memories;

    // The adapter to fit the data onto list.
    private MemoryAdapter adapter;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchMemoryFragment.
     */
    public static RecommendationsFragment newInstance() {
        return new RecommendationsFragment();
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
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        ButterKnife.bind(this, view);

        adapter = new MemoryAdapter(memories, getContext());
        lvRecommendationsMemoryList.setAdapter(adapter);
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

    public void updateMemories(ArrayList<Memory> memoryList) {
        memories.clear();
        memories.addAll(memoryList);

        if(adapter == null) {
            adapter = new MemoryAdapter(memories, getContext());
            lvRecommendationsMemoryList.setAdapter(adapter);
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
        void getUserMemoryList();
    }
}
