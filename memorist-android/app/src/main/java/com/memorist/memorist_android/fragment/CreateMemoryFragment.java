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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
    @BindView(R.id.et_memoryMentionedTime2) EditText etMemoryMentionedTime2;
    @BindView(R.id.et_selectLocation1) EditText etMemoryLocation1;
    @BindView(R.id.et_selectLocation2) EditText etMemoryLocation2;
    @BindView(R.id.et_memoryTags) EditText etMemoryTags;
    @BindView(R.id.sp_timeSpinner) Spinner spTimeSpinner;
    @BindView(R.id.rg_timeFormat) RadioGroup rgTimeFormat;
    @BindView(R.id.rg_locationFormat) RadioGroup rgLocationFormat;

    private final int GALLERY_REQUEST = 1;
    private final int VIDEO_REQUEST = 2;
    private final int AUDIO_REQUEST = 3;

    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<Uri> memoryImage;
    private ArrayList<Uri> memoryVideo;
    private ArrayList<Uri> memoryAudio;
    private ArrayList<String> memoryTags;

    private int selectedDateType = 0;
    private int selectedLocationType = 0;
    private String selectedDateFormat = "d";

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
        final View view = inflater.inflate(R.layout.fragment_create_memory, container, false);
        ButterKnife.bind(this, view);

        if(getContext() != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.time_list, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimeSpinner.setAdapter(adapter);
        }

        rgTimeFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = (RadioButton) view.findViewById(checkedId);
                etMemoryMentionedTime2.setText("");

                if(String.valueOf(selectedButton.getText()).equals("Time Interval")) {
                    selectedDateType = 1;

                    etMemoryMentionedTime.setVisibility(View.VISIBLE);
                    etMemoryMentionedTime2.setVisibility(View.VISIBLE);

                    etMemoryMentionedTime.setHint("From date");
                    etMemoryMentionedTime2.setHint("To date");
                } else {
                    selectedDateType = 0;

                    etMemoryMentionedTime.setVisibility(View.VISIBLE);
                    etMemoryMentionedTime2.setVisibility(View.GONE);

                    etMemoryMentionedTime.setHint("Type the date");
                }

                spTimeSpinner.setVisibility(View.VISIBLE);
            }
        });

        rgLocationFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = (RadioButton) view.findViewById(checkedId);
                etMemoryLocation2.setText("");

                if(String.valueOf(selectedButton.getText()).equals("Path")) {
                    selectedLocationType = 1;

                    etMemoryLocation1.setVisibility(View.VISIBLE);
                    etMemoryLocation2.setVisibility(View.VISIBLE);

                    etMemoryLocation1.setHint("From?");
                    etMemoryLocation2.setHint("To?");
                } else {
                    selectedLocationType = 0;

                    etMemoryLocation1.setVisibility(View.VISIBLE);
                    etMemoryLocation2.setVisibility(View.GONE);

                    etMemoryLocation1.setHint("Type the location");
                }
            }
        });

        spTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    selectedDateFormat = "d";
                } else if(position == 1) {
                    selectedDateFormat = "y";
                } else if(position == 2) {
                    selectedDateFormat = "ym";
                } else if(position == 3) {
                    selectedDateFormat = "ymd";
                } else {
                    selectedDateFormat = "full";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                final Uri selectedImage = data.getData();
                memoryImage.add(selectedImage);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutImageContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addImage = new ImageView(getContext());
                addImage.setLayoutParams(params);
                addImage.setImageURI(selectedImage);
                layout.addView(addImage);
                addImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.displayImageDialog(selectedImage);
                    }
                });

                MaterialCardView mcAddedImage = getView().findViewById(R.id.mc_createMemoryImage);
                mcAddedImage.setVisibility(View.VISIBLE);
            } else if (requestCode == 2) {
                final Uri selectedVideo = data.getData();
                memoryVideo.add(selectedVideo);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutVideoContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addVideo = new ImageView(getContext());
                addVideo.setLayoutParams(params);
                layout.addView(addVideo);
                addVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.displayVideoDialog(selectedVideo);
                    }
                });

                Glide
                        .with(getContext())
                        .asBitmap()
                        .load(Uri.fromFile(new File(UriPathHelper.getPathFromURI_Video(getContext(), selectedVideo))))
                        .into(addVideo);

                MaterialCardView mcAddedVideo = getView().findViewById(R.id.mc_createMemoryVideo);
                mcAddedVideo.setVisibility(View.VISIBLE);
            } else if (requestCode == 3) {
                final Uri selectedAudio = data.getData();
                memoryAudio.add(selectedAudio);

                ViewGroup layout = (ViewGroup) getView().findViewById(R.id.layoutAudioContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, (int)getResources().getDimension(R.dimen.margin_sm), 0);

                ImageView addAudio = new ImageView(getContext());
                addAudio.setLayoutParams(params);
                addAudio.setImageDrawable(getResources().getDrawable(R.drawable.audio_icon));
                layout.addView(addAudio);
                addAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.displayAudioDialog(selectedAudio);
                    }
                });

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
        String mentionedTime2 = etMemoryMentionedTime2.getText().toString();
        String location1 = etMemoryLocation1.getText().toString();
        String location2 = etMemoryLocation2.getText().toString();
        String story = etMemoryStory.getText().toString();
        String tags = etMemoryTags.getText().toString();

        memoryText.add(story);
        memoryTags.addAll(Arrays.asList(tags.split(",")));
        mListener.memoryShared(memoryTitle, mentionedTime, mentionedTime2, location1, location2, memoryFormat, memoryText, selectedDateType, selectedDateFormat, selectedLocationType, memoryImage, memoryVideo, memoryAudio, memoryTags);
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
        void memoryShared(String memoryTitle, String mentionedTime, String mentionedTime2, String location1, String location2, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                         int selectedDateType, String selectedDateFormat, int selectedLocationType, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio,
                          ArrayList<String> memoryTags);
        void memoryCanceled();
        void displayImageDialog(Uri selectedImage);
        void displayVideoDialog(Uri selectedVideo);
        void displayAudioDialog(Uri selectedAudio);
    }
}
