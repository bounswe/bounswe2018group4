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

import com.bumptech.glide.Glide;
import com.memorist.memorist_android.R;
import com.memorist.memorist_android.helper.UriPathHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
    @BindView(R.id.et_memoryStory) EditText etMemoryStory;
    @BindView(R.id.et_memoryMentionedTime) EditText etMemoryMentionedTime;
    @BindView(R.id.et_memoryLocation) EditText etMemoryLocation;
    @BindView(R.id.et_memoryTags) EditText etMemoryTags;

    private final int GALLERY_REQUEST = 1;
    private final int VIDEO_REQUEST = 2;
    private final int AUDIO_REQUEST = 3;

    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<Uri> memoryImage;
    private ArrayList<Uri> memoryVideo;
    private ArrayList<Uri> memoryAudio;
    private ArrayList<String> memoryTags;

    private OnFragmentInteractionListener mListener;

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

        memoryFormat = new ArrayList<>();
        memoryText = new ArrayList<>();
        memoryImage = new ArrayList<>();
        memoryVideo = new ArrayList<>();
        memoryAudio = new ArrayList<>();
        memoryTags = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                memoryImage.add(selectedImage);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutImageContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addImage = new ImageView(getContext());
                addImage.setLayoutParams(params);
                addImage.setImageURI(selectedImage);
                layout.addView(addImage);

                MaterialCardView mcAddedImage = getView().findViewById(R.id.mc_createMemoryImage);
                mcAddedImage.setVisibility(View.VISIBLE);
            } else if (requestCode == 2) {
                Uri selectedVideo = data.getData();
                memoryVideo.add(selectedVideo);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutVideoContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addVideo = new ImageView(getContext());
                addVideo.setLayoutParams(params);
                layout.addView(addVideo);

                Glide
                        .with(getContext())
                        .asBitmap()
                        .load(Uri.fromFile(new File(UriPathHelper.getPathFromURI_Video(getContext(), selectedVideo))))
                        .into(addVideo);

                MaterialCardView mcAddedVideo = getView().findViewById(R.id.mc_createMemoryVideo);
                mcAddedVideo.setVisibility(View.VISIBLE);
            } else if (requestCode == 3) {
                Uri selectedAudio = data.getData();
                memoryAudio.add(selectedAudio);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutAudioContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addAudio = new ImageView(getContext());
                addAudio.setLayoutParams(params);
                addAudio.setImageDrawable(getResources().getDrawable(R.drawable.audio_icon));
                layout.addView(addAudio);

                MaterialCardView mcAddedAudio = getView().findViewById(R.id.mc_createMemoryAudio);
                mcAddedAudio.setVisibility(View.VISIBLE);
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

        String memoryTitle = etMemoryTitle.getText().toString();
        String mentionedTime = etMemoryMentionedTime.getText().toString();
        String location = etMemoryLocation.getText().toString();
        String story = etMemoryStory.getText().toString();
        String tags = etMemoryTags.getText().toString();

        memoryText.add(story);
        memoryTags.addAll(Arrays.asList(tags.split(",")));
        mListener.memoryShared(memoryTitle, mentionedTime, location, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
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
        void memoryShared(String memoryTitle, String mentionedTime, String location, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                          ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio,
                          ArrayList<String> memoryTags);
        void memoryCanceled();
    }
}
