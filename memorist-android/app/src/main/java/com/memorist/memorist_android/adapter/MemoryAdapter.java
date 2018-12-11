package com.memorist.memorist_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Multimedia;
import com.memorist.memorist_android.model.Tag;
import com.memorist.memorist_android.model.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryAdapter extends ArrayAdapter<Memory> {

    // The container list of all existing meeting data objects.
    private ArrayList<Memory> dataSet;

    // The context that the adapter will work on.
    private Context context;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.iv_feedProfilePicture) ImageView ivFeedProfilePicture;
        @BindView(R.id.tv_feedUsername) TextView tvFeedUsername;
        @BindView(R.id.tv_feedPostedTime) TextView tvFeedPostedTime;
        @BindView(R.id.tv_feedMentionedTime) TextView tvFeedMentionedTime;
        @BindView(R.id.tv_feedLocation) TextView tvFeedLocation;
        @BindView(R.id.tv_feedTitle) TextView tvFeedTitle;
        @BindView(R.id.tv_feedStory) TextView tvFeedStory;
        @BindView(R.id.tv_feedTags) TextView tvFeedTags;
        @BindView(R.id.btn_feedLike) ImageButton btnLike;
        @BindView(R.id.tv_likeCount) TextView tvLikeCount;
        @BindView(R.id.btn_feedComment) ImageButton btnComment;
        @BindView(R.id.tv_commentCount) TextView tvCommentCount;
        @BindView(R.id.btn_feedAnnotate) ImageButton btnAnnotate;
        @BindView(R.id.tv_annotationCount) TextView tvAnnotationCount;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MemoryAdapter(ArrayList<Memory> dataSet, Context context) {
        super(context, R.layout.adapter_memory_row, dataSet);

        this.dataSet = dataSet;
        this.context = context;
    }

    /**
     * Constructs the view and sets it content. Returns the result which will be displayed.
     * It's a get method and it will be called by the occupant listView object.
     *
     * @param position: The placement of the specific row within the list.
     * @param convertView: The view object to display
     * @param parent: The parent object which holds views jointly inside.
     * @return The view object to display as filled with content.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // The memory in the placement of the specific row.
        final Memory memory = getItem(position);

        /*
         * ViewHolder is used to avoid instantiating a view for every item in your adapter,
         * when a view scrolls off-screen, it can be reused, or recycled.
         */
        ViewHolder viewHolder;

        /*
         * If convertView view object has never been constructed, then inflate the layout,
         * create the viewHolder and set convertView's tag.
         * Else get convertView's tag.
         */
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_memory_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Sets the content of the view.
        setViewContent(position, convertView, viewHolder, memory);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     * @param memory: The meeting object which holds the content.
     */
    private void setViewContent(int position, @Nullable View convertView, final ViewHolder viewHolder, final Memory memory) {
        if(memory != null) {
            String username = "@" + memory.getOwner();
            String postedTime = "Posted on " + memory.getPosting_time();
            String mentionedTime = "Mentions about " + "???";
            String location = "Place is " + "???";
            String title = memory.getTitle();

            StringBuilder storyBuilder = new StringBuilder();
            for(Text text: memory.getTexts()) {
                storyBuilder.append(text.getText());
            }

            StringBuilder tagBuilder = new StringBuilder();
            for(Tag tag: memory.getTags()) {
                tagBuilder.append("#").append(tag.getTag().substring(tag.getTag().lastIndexOf(' ') + 1)).append(" ");
            }

            viewHolder.tvFeedUsername.setText(username);
            viewHolder.tvFeedPostedTime.setText(postedTime);
            viewHolder.tvFeedMentionedTime.setText(mentionedTime);
            viewHolder.tvFeedLocation.setText(location);
            viewHolder.tvFeedTitle.setText(title);
            viewHolder.tvFeedStory.setText(storyBuilder.toString());
            viewHolder.tvFeedTags.setText(tagBuilder.toString());

            if(memory.getNumlikes() != 0) {
                viewHolder.btnLike.setBackgroundColor(context.getResources().getColor(R.color.likeMemory));
            }

            viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(memory.getNumlikes() == 0) {
                        viewHolder.tvLikeCount.setText("1");
                        viewHolder.btnLike.setImageDrawable(context.getResources().getDrawable(R.drawable.yeslike_icon));
                    } else {
                        viewHolder.tvLikeCount.setText("0");
                        viewHolder.btnLike.setImageDrawable(context.getResources().getDrawable(R.drawable.nolike_icon));
                    }
                }
            });


            Multimedia[] multimedia = memory.getMultimedia();
            for(Multimedia mm: multimedia) {
                String mediaURL = Constants.API_BASE_URL + mm.getMultimedia().getMedia();
                int mediaType = mm.getMedia_type();

                if(mediaType == 1) {
                    ViewGroup layout = (ViewGroup) convertView.findViewById(R.id.layoutMultimediaContent);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                    params.setMargins(0, 0, 10, 0);

                    ImageView addImage = new ImageView(getContext());
                    addImage.setLayoutParams(params);
                    layout.addView(addImage);

                    Picasso.get()
                            .load(mediaURL)
                            .into(addImage);
                }
            }

            /*
            ArrayList<Uri> memoryImage = memory.getMemoryImage();
            ArrayList<Uri> memoryVideo = memory.getMemoryVideo();
            ArrayList<Uri> memoryAudio = memory.getMemoryAudio();

            for(Uri selectedImage: memoryImage) {
                ViewGroup layout = (ViewGroup) convertView.findViewById(R.id.layoutMultimediaContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, 10, 0);

                ImageView addImage = new ImageView(getContext());
                addImage.setLayoutParams(params);
                addImage.setImageURI(selectedImage);
                layout.addView(addImage);
            }

            for(Uri selectedVideo: memoryVideo) {
                ViewGroup layout = (ViewGroup) convertView.findViewById(R.id.layoutMultimediaContent);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(256, 256);
                params.setMargins(0, 0, 10, 0);

                VideoView addVideo = new VideoView(getContext());
                addVideo.setLayoutParams(params);
                addVideo.setVideoURI(selectedVideo);
                layout.addView(addVideo);
            }
            */
        }
    }
}