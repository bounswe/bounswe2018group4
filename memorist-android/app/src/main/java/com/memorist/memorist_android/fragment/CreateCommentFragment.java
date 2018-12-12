package com.memorist.memorist_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.memorist.memorist_android.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateCommentFragment extends Fragment {

    @BindView(R.id.et_commentTitle)
    EditText etCommentTitle;
    @BindView(R.id.et_commentStory) EditText etCommentStory;
    @BindView(R.id.et_commentMentionedTime) EditText etCommentMentionedTime;
    @BindView(R.id.et_commentLocation) EditText etCommentLocation;
    @BindView(R.id.et_commentTags) EditText etCommentTags;

    private final int GALLERY_REQUEST = 1;
    private final int VIDEO_REQUEST = 2;
    private final int AUDIO_REQUEST = 3;

    private ArrayList<String> commentFormat;
    private ArrayList<String> commentText;
    private ArrayList<Uri> commentImage;
    private ArrayList<Uri> commentVideo;
    private ArrayList<Uri> commentAudio;
    private ArrayList<String> commentTags;

    private CreateCommentFragment.OnFragmentInteractionListener mListener;

    public CreateCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateCommentFragment.
     */
    public static CreateCommentFragment newInstance() {
        return new CreateCommentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commentFormat = new ArrayList<>();
        commentText = new ArrayList<>();
        commentImage = new ArrayList<>();
        commentVideo = new ArrayList<>();
        commentAudio = new ArrayList<>();
        commentTags = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_create_comment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CreateCommentFragment.OnFragmentInteractionListener) {
            mListener = (CreateCommentFragment.OnFragmentInteractionListener) context;
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

    @OnClick(R.id.btn_addImage)
    public void addImageClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @OnClick(R.id.btn_addVideo)
    public void addVideoClicked() {
        Intent videoPickerIntent = new Intent(Intent.ACTION_PICK);
        videoPickerIntent.setType("video/*");
        startActivityForResult(videoPickerIntent, VIDEO_REQUEST);
    }

    @OnClick(R.id.btn_addAudio)
    public void addAudioClicked() {
        Intent audioPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        audioPickerIntent.setType("audio/*");
        startActivityForResult(audioPickerIntent, AUDIO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                commentImage.add(selectedImage);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutImageContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addImage = new ImageView(getContext());
                addImage.setLayoutParams(params);
                addImage.setImageURI(selectedImage);
                layout.addView(addImage);

                MaterialCardView mcAddedImage = getView().findViewById(R.id.mc_createCommentImage);
                mcAddedImage.setVisibility(View.VISIBLE);
            } else if (requestCode == 2) {
                Uri selectedVideo = data.getData();
                commentVideo.add(selectedVideo);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutVideoContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                VideoView addVideo = new VideoView(getContext());
                addVideo.setLayoutParams(params);
                addVideo.setVideoURI(selectedVideo);
                layout.addView(addVideo);

                MaterialCardView mcAddedVideo = getView().findViewById(R.id.mc_createCommentVideo);
                mcAddedVideo.setVisibility(View.VISIBLE);
            } else if (requestCode == 3) {
                Uri selectedAudio = data.getData();
                commentAudio.add(selectedAudio);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutAudioContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addAudio = new ImageView(getContext());
                addAudio.setLayoutParams(params);
                addAudio.setImageDrawable(getResources().getDrawable(R.drawable.audio_icon));
                layout.addView(addAudio);

                MaterialCardView mcAddedAudio = getView().findViewById(R.id.mc_createCommentAudio);
                mcAddedAudio.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.btn_cancelComment)
    public void cancelComment(View view) {
        mListener.commentCanceled();
    }

    @OnClick(R.id.btn_postComment)
    public void shareComment() {
        commentFormat.add("T");

        for(Uri commentImageUri: commentImage) {
            commentFormat.add("I");
        }

        for(Uri commentVideoUri: commentVideo) {
            commentFormat.add("V");
        }

        for(Uri commentAudioUri: commentAudio) {
            commentFormat.add("A");
        }

        String commentTitle = etCommentTitle.getText().toString();
        String mentionedTime = etCommentMentionedTime.getText().toString();
        String location = etCommentLocation.getText().toString();
        String story = etCommentStory.getText().toString();
        String tags = etCommentTags.getText().toString();

        commentText.add(story);
        commentTags.addAll(Arrays.asList(tags.split(",")));
        mListener.commentShared(commentTitle, mentionedTime, location, commentFormat, commentText, commentImage,
                commentVideo, commentAudio, commentTags);
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
        void commentShared(String commentTitle, String mentionedTime, String location,
                           ArrayList<String> commentFormat, ArrayList<String> commentText,
                          ArrayList<Uri> commentImage, ArrayList<Uri> commentVideo, ArrayList<Uri> commentAudio,
                          ArrayList<String> commentTags);
        void commentCanceled();
    }
}
