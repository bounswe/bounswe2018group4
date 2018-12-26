package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.CommentAdapter;
import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.model.Comments;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedCommentFragment extends Fragment {

    @BindView(R.id.lv_commentList) ListView lvCommentList;
    @BindView(R.id.et_commentText) EditText etComment;

    private static final String COMMENTS = "comments";
    private static final String ID = "id";

    private OnFragmentInteractionListener mListener;
    private ArrayList<Comments> comments;
    private CommentAdapter adapter;
    private int memoryID;

    public FeedCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedMemoryFragment.
     */
    public static FeedCommentFragment newInstance(Comments[] comments, int memoryID) {
        FeedCommentFragment fragment = new FeedCommentFragment();

        Bundle args = new Bundle();
        args.putSerializable(COMMENTS, comments);
        args.putInt(ID, memoryID);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comments = new ArrayList<>();

        if (getArguments() != null) {
            Comments[] commentsArr = (Comments[]) getArguments().getSerializable(COMMENTS);
            memoryID = getArguments().getInt(ID);
            comments.addAll(Arrays.asList(commentsArr));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_feed_comment, container, false);
        ButterKnife.bind(this, view);

        adapter = new CommentAdapter(comments, getContext());
        lvCommentList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    @OnClick(R.id.btn_createComment)
    public void addComment(View v) {
        if(!etComment.getText().toString().equals("")) {
            mListener.addComment(etComment.getText().toString(), memoryID);
        }
    }

    public void updateComments(ArrayList<Comments> commentsArrayList) {
        comments.clear();
        comments.addAll(commentsArrayList);

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
        void addComment(String comment, int memoryID);
    }
}

