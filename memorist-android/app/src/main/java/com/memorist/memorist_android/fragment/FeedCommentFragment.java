package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.CommentAdapter;
import com.memorist.memorist_android.model.Comment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedCommentFragment extends Fragment {

    @BindView(R.id.lv_commentList)
    ListView lvCommentList;

    private FeedCommentFragment.OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Comment> comments;

    // The adapter to fit the data onto list.
    private CommentAdapter adapter;

    public FeedCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedMemoryFragment.
     */
    public static FeedCommentFragment newInstance() {
        return new FeedCommentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_feed_comment, container, false);
        ButterKnife.bind(this, view);

        comments = new ArrayList<>();
        mListener.getUserCommentList();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FeedCommentFragment.OnFragmentInteractionListener) {
            mListener = (FeedCommentFragment.OnFragmentInteractionListener) context;
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

    public void updateMemories(ArrayList<Comment> commentList) {
        comments.clear();
        comments.addAll(commentList);

        if(adapter == null) {
            adapter = new CommentAdapter(comments, getContext());
            lvCommentList.setAdapter(adapter);
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
        void getUserCommentList();
    }
}

