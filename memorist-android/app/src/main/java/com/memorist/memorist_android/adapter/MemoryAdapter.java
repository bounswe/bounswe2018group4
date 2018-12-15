package com.memorist.memorist_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
        @BindView(R.id.layoutMultimediaContent) LinearLayout multimediaContent;

        @BindView(R.id.iv_memoryMultimedia1) ImageView memoryMultimedia1;
        @BindView(R.id.iv_memoryMultimedia2) ImageView memoryMultimedia2;
        @BindView(R.id.iv_memoryMultimedia3) ImageView memoryMultimedia3;
        @BindView(R.id.iv_memoryMultimedia4) ImageView memoryMultimedia4;
        @BindView(R.id.iv_memoryMultimedia5) ImageView memoryMultimedia5;
        @BindView(R.id.iv_memoryMultimedia6) ImageView memoryMultimedia6;
        @BindView(R.id.iv_memoryMultimedia7) ImageView memoryMultimedia7;
        @BindView(R.id.iv_memoryMultimedia8) ImageView memoryMultimedia8;
        @BindView(R.id.iv_memoryMultimedia9) ImageView memoryMultimedia9;
        @BindView(R.id.iv_memoryMultimedia10) ImageView memoryMultimedia10;

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
        setViewContent(viewHolder, getItem(position));
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     */
    private void setViewContent(ViewHolder viewHolder, Memory memory) {
        if(memory != null) {
            String username = "@" + memory.getOwner().getUsername();
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

            Multimedia[] multimedia = memory.getMultimedia();
            int mediaCounter = 1;

            for(Multimedia mm: multimedia) {
                String mediaURL = Constants.API_BASE_URL + mm.getMultimedia().getMedia();
                int mediaType = mm.getMedia_type();

                if(mediaType == 1) {
                    switch (mediaCounter) {
                        case 1:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia1);
                            viewHolder.memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia2);
                            viewHolder.memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia3);
                            viewHolder.memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia4);
                            viewHolder.memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia5);
                            viewHolder.memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia6);
                            viewHolder.memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            Picasso.get().load(mediaURL).into(viewHolder.memoryMultimedia7);
                            viewHolder.memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                    }

                    mediaCounter++;
                }
            }
        }
    }
}