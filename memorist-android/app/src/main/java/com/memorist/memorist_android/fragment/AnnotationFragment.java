package com.memorist.memorist_android.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.memorist.memorist_android.R;
import com.memorist.memorist_android.adapter.AnnotationAdapter;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.model.Annotation;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Multimedia;
import com.memorist.memorist_android.model.Tag;
import com.memorist.memorist_android.model.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnotationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnotationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnotationFragment extends Fragment {

    @BindView(R.id.annotation_feedProfilePicture) ImageView ivProfilePicture;
    @BindView(R.id.annotation_feedUsername) TextView tvUsername;
    @BindView(R.id.annotation_feedPostedTime) TextView tvPostedTime;
    @BindView(R.id.annotation_feedMentionedTime) TextView tvMentionedTime;
    @BindView(R.id.annotation_feedLocation) TextView tvLocation;
    @BindView(R.id.annotation_feedTitle) TextView tvTitle;
    @BindView(R.id.annotation_feedStory) TextView tvStory;
    @BindView(R.id.annotation_feedTags) TextView tvTags;
    @BindView(R.id.annotation_memoryMultimedia1) ImageView memoryMultimedia1;
    @BindView(R.id.annotation_memoryMultimedia2) ImageView memoryMultimedia2;
    @BindView(R.id.annotation_memoryMultimedia3) ImageView memoryMultimedia3;
    @BindView(R.id.annotation_memoryMultimedia4) ImageView memoryMultimedia4;
    @BindView(R.id.annotation_memoryMultimedia5) ImageView memoryMultimedia5;
    @BindView(R.id.annotation_memoryMultimedia6) ImageView memoryMultimedia6;
    @BindView(R.id.annotation_memoryMultimedia7) ImageView memoryMultimedia7;
    @BindView(R.id.annotation_memoryMultimedia8) ImageView memoryMultimedia8;
    @BindView(R.id.annotation_memoryMultimedia9) ImageView memoryMultimedia9;
    @BindView(R.id.annotation_memoryMultimedia10) ImageView memoryMultimedia10;
    @BindView(R.id.annotation_commentList) ListView lvAnnotation;
    @BindView(R.id.material_design_floating_action_menu_item1) FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    // The data set for memory objects.
    private ArrayList<Annotation> annotations;

    // The adapter to fit the data onto list.
    private AnnotationAdapter adapter;

    private static final String MEMORY = "memory";
    private Memory theMemory;

    public AnnotationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedMemoryFragment.
     */
    public static AnnotationFragment newInstance(Memory memory) {
        AnnotationFragment fragment = new AnnotationFragment();
        Bundle args = new Bundle();
        args.putSerializable(MEMORY, memory);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        annotations = new ArrayList<>();
        if(getArguments() != null) {
            theMemory = (Memory) getArguments().getSerializable(MEMORY);
        } else {
            theMemory = null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout and bind view components.
        View view = inflater.inflate(R.layout.fragment_annotation, container, false);
        ButterKnife.bind(this, view);

        setContent();
        mListener.getAnnotationList(theMemory.getId());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addAnnotation(theMemory);
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

    public void setContent() {
        if(theMemory != null) {
            String avatarURL = Constants.API_BASE_URL + "/multimedia/" + theMemory.getOwner().getPhoto();
            String username = "@" + theMemory.getOwner().getUsername();
            String postedTime = "Posted on " + theMemory.getPosting_time();
            String mentionedTime = "Mentions about ";
            String location = "Place is ";
            String title = theMemory.getTitle();

            if(theMemory.getMentioned_time().length != 0) {
                mentionedTime += theMemory.getMentioned_time()[0].getDate_string1();
                if(theMemory.getMentioned_time()[0].getDate_string2() != null && !theMemory.getMentioned_time()[0].getDate_string2().equals("")) {
                    mentionedTime += " - " + theMemory.getMentioned_time()[0].getDate_string2();
                }
            }

            if(theMemory.getLocation().length != 0) {
                location += theMemory.getLocation()[0].getLocation()[0].getLocation_name();
                if(theMemory.getLocation()[0].getLocation().length > 1) {
                    location += " to " + theMemory.getLocation()[0].getLocation()[1].getLocation_name();
                }
            }

            StringBuilder storyBuilder = new StringBuilder();
            for(Text text: theMemory.getTexts()) {
                storyBuilder.append(text.getText());
            }

            StringBuilder tagBuilder = new StringBuilder();
            for(Tag tag: theMemory.getTags()) {
                tagBuilder.append("#").append(tag.getTag().substring(tag.getTag().lastIndexOf(' ') + 1)).append(" ");
            }

            Picasso.get().load(avatarURL).into(ivProfilePicture);
            tvUsername.setText(username);
            tvPostedTime.setText(postedTime);
            tvMentionedTime.setText(mentionedTime);
            tvLocation.setText(location);
            tvTitle.setText(title);
            tvStory.setText(storyBuilder.toString());
            tvTags.setText(tagBuilder.toString());

            int mediaCounter = 1;
            for(Multimedia mm: theMemory.getMultimedia()) {
                String mediaURL = Constants.API_BASE_URL + mm.getMultimedia().getMedia();
                int mediaType = mm.getMedia_type();

                if(mediaType == 1) {
                    switch (mediaCounter) {
                        case 1:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia1);
                            memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia2);
                            memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia3);
                            memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia4);
                            memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia5);
                            memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia6);
                            memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia7);
                            memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia8);
                            memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia9);
                            memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            Picasso.get().load(mediaURL).fit().into(memoryMultimedia10);
                            memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if (mediaType == 2) {
                    switch (mediaCounter) {
                        case 1:
                            memoryMultimedia1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            memoryMultimedia2.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            memoryMultimedia3.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            memoryMultimedia4.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            memoryMultimedia5.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            memoryMultimedia6.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            memoryMultimedia7.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            memoryMultimedia8.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            memoryMultimedia9.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            memoryMultimedia10.setImageDrawable(getContext().getResources().getDrawable(R.drawable.video_play_icon));
                            memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if(mediaType == 3) {
                    switch (mediaCounter) {
                        case 1:
                            memoryMultimedia1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            memoryMultimedia2.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            memoryMultimedia3.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            memoryMultimedia4.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            memoryMultimedia5.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            memoryMultimedia6.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            memoryMultimedia7.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            memoryMultimedia8.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            memoryMultimedia9.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            memoryMultimedia10.setImageDrawable(getContext().getResources().getDrawable(R.drawable.audio_icon));
                            memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                mediaCounter++;
            }

            memoryMultimedia1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 1);
                }
            });

            memoryMultimedia2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 2);
                }
            });

            memoryMultimedia3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 3);
                }
            });

            memoryMultimedia4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 4);
                }
            });

            memoryMultimedia5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 5);
                }
            });

            memoryMultimedia6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 6);
                }
            });

            memoryMultimedia7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 7);
                }
            });

            memoryMultimedia8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 8);
                }
            });

            memoryMultimedia9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 9);
                }
            });

            memoryMultimedia10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(theMemory, 10);
                }
            });
        }
    }

    public void updateAnnotations(ArrayList<Annotation> annotationList) {
        annotations.clear();
        annotations.addAll(annotationList);

        if(adapter == null) {
            adapter = new AnnotationAdapter(annotations, theMemory, getContext());
            lvAnnotation.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();

        StringBuilder storyBuilder = new StringBuilder();
        for(Text text: theMemory.getTexts()) {
            storyBuilder.append(text.getText());
        }

        for(Annotation annotation: annotationList) {
            Spannable WordtoSpan = new SpannableString(storyBuilder.toString());
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), Integer.valueOf(annotation.getTarget()[0].getSelector()[0].getStart()),
                    Integer.valueOf(annotation.getTarget()[0].getSelector()[0].getEnd()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvStory.setText(WordtoSpan);
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
        void getAnnotationList(int id);
        void memoryMultimediaClick(Memory memory, int id);
        void addAnnotation(Memory memory);
    }
}
