package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.memorist.memorist_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchMemoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchMemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchMemoryFragment extends Fragment {

    @BindView(R.id.et_searchMemory) EditText etSearchMemory;
    @BindView(R.id.tv_searchIsReady) TextView tvSearchIsReady;
    @BindView(R.id.lv_searchMemoryList) ListView lv_searchMemory;
    @BindView(R.id.sp_searchSpinner) Spinner spSearchSpinner;

    private OnFragmentInteractionListener mListener;

    public SearchMemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchMemoryFragment.
     */
    public static SearchMemoryFragment newInstance() {
        return new SearchMemoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_search_memory, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.search_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchSpinner.setAdapter(adapter);

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

    @OnClick(R.id.btn_searchClicked)
    public void searchClicked(View view) {
        if(!etSearchMemory.getText().toString().isEmpty()) {
            String result = "You searched \"" + etSearchMemory.getText().toString() + "\" with ";

            if(spSearchSpinner.getSelectedItemId() == 0) {
                result += "Search by keyword.";
            } else if(spSearchSpinner.getSelectedItemId() == 1) {
                result += "Search by location.";
            } else if(spSearchSpinner.getSelectedItemId() == 2){
                result += "Search by posted time.";
            } else if(spSearchSpinner.getSelectedItemId() == 3){
                result += "Search by mentioned time.";
            } else {
                result += "Search by tag.";
            }

            tvSearchIsReady.setText(result);
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
    }
}
