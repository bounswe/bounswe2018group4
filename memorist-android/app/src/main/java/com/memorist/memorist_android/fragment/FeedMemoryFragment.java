package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateMemoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateMemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedMemoryFragment extends Fragment {

    @BindView(R.id.lv_memoryList) ListView lvMemoryList;

    private OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Memory> memories;

    // The adapter to fit the data onto list.
    private MemoryAdapter adapter;

    public FeedMemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateMemoryFragment.
     */
    public static FeedMemoryFragment newInstance() {
        return new FeedMemoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        memories = new ArrayList<Memory>();
        memories.add(new Memory(1, new User(1, "BerkeTheTechNerd", "Berke", "Esmer", "berkee.eesmer@gmail.com"),
                "October 28th, 2018", "September 2018", "Gaziantep", "In the first week of september, we went to Gaziantep all together with 10 friends. It was a week we ate all the time. It was awesome. I can't tell whether I went there to travel or eat lol."));
        memories.add(new Memory(2, new User(2, "cemalaytekinnn", "Cemal", "Aytekin", "cemalaytekinnn@gmail.com"),
                "October 29th, 2018", "October 29th", "Turkey", "Cumhuriyet bayramınız kutlu olsun. \nNe mutlu TÜRKÜM diyene..."));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_feed_memory, container, false);
        ButterKnife.bind(this, view);

        adapter = new MemoryAdapter(memories, getContext());
        lvMemoryList.setAdapter(adapter);
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
