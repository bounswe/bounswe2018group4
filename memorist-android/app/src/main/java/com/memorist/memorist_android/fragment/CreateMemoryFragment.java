package com.memorist.memorist_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.memorist.memorist_android.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateMemoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateMemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMemoryFragment extends Fragment {

    @BindView(R.id.et_memoryTitle) EditText etMemoryTitle;
    @BindView(R.id.et_memoryMentionedTime) EditText etMemoryMentionedTime;
    @BindView(R.id.et_memoryLocation) EditText etMemoryLocation;
    @BindView(R.id.et_memoryStory) EditText etMemoryStory;
    @BindView(R.id.et_memoryTags) EditText etMemoryTags;

    private final int GALLERY_REQUEST = 1;
    private final int VIDEO_REQUEST = 2;
    private OnFragmentInteractionListener mListener;

    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<Uri> memoryImage;
    private ArrayList<Uri> memoryVideo;
    private ArrayList<Uri> memoryAudio;
    private ArrayList<String> memoryTags;

    public CreateMemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateMemoryFragment.
     */
    public static CreateMemoryFragment newInstance() {
        return new CreateMemoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        memoryFormat = new ArrayList<>();
        memoryText = new ArrayList<>();
        memoryImage = new ArrayList<>();
        memoryVideo = new ArrayList<>();
        memoryAudio = new ArrayList<>();
        memoryTags = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_create_memory, container, false);
        ButterKnife.bind(this, view);

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                memoryImage.add(selectedImage);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutImageContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addImage = new ImageView(getContext());
                addImage.setLayoutParams(params);
                addImage.setImageURI(selectedImage);
                layout.addView(addImage);

                TextView tvAddedMultimedia = getView().findViewById(R.id.tv_memoryAddedImages);
                tvAddedMultimedia.setVisibility(View.VISIBLE);
            } else if (requestCode == 2) {
                Uri selectedVideo = data.getData();
                memoryVideo.add(selectedVideo);
                Log.v("uri", selectedVideo.toString());

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutVideoContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(512, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                VideoView addVideo = new VideoView(getContext());
                addVideo.setLayoutParams(params);
                addVideo.setVideoURI(selectedVideo);
                layout.addView(addVideo);

                TextView tvAddedMultimedia = getView().findViewById(R.id.tv_memoryAddedVideos);
                tvAddedMultimedia.setVisibility(View.VISIBLE);
            } else {
                // Nothing for now.
            }
        }
    }

    @OnClick(R.id.btn_cancelMemory)
    public void cancelMemory(View view) {
        mListener.memoryCanceled();
    }

    @OnClick(R.id.btn_postMemory)
    public void shareMemory() {
        memoryFormat.add("T");

        for(Uri memoryImageUri: memoryImage) {
            memoryFormat.add("I");
        }

        for(Uri memoryVideoUri: memoryVideo) {
            memoryFormat.add("V");
        }

        for(Uri memoryAudioUri: memoryAudio) {
            memoryFormat.add("A");
        }

        String title = etMemoryTitle.getText().toString();
        String mentionedTime = etMemoryMentionedTime.getText().toString();
        String location = etMemoryLocation.getText().toString();
        String story = etMemoryStory.getText().toString();
        String tags = etMemoryTags.getText().toString();

        memoryText.add(story);
        String[] tagsArr = tags.split(",");

        for(String tag: tagsArr) {
            memoryTags.add(tag);
        }

        mListener.memoryShared(title, mentionedTime, location, title, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
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
        void memoryCanceled();
        void memoryShared(String title, String mentionedTime, String location, String memoryTitle, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                          ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio,
                          ArrayList<String> memoryTags);
    }
}
