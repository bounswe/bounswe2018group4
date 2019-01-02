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
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Multimedia;
import com.memorist.memorist_android.model.Tag;
import com.memorist.memorist_android.model.Text;
import com.memorist.memorist_android.ws.MemoristApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryAdapter extends ArrayAdapter<Memory> {

    // The container list of all existing meeting data objects.
    private ArrayList<Memory> dataSet;

    // The context that the adapter will work on.
    private Context context;

    // Listener to inform memory activity.
    private MemoryOnClickListener mListener;

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
        this.mListener = (MemoryOnClickListener) getContext();
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
            viewHolder.memoryMultimedia1.setVisibility(View.GONE);
            viewHolder.memoryMultimedia1.setImageBitmap(null);
            viewHolder.memoryMultimedia2.setVisibility(View.GONE);
            viewHolder.memoryMultimedia2.setImageBitmap(null);
            viewHolder.memoryMultimedia3.setVisibility(View.GONE);
            viewHolder.memoryMultimedia3.setImageBitmap(null);
            viewHolder.memoryMultimedia4.setVisibility(View.GONE);
            viewHolder.memoryMultimedia4.setImageBitmap(null);
            viewHolder.memoryMultimedia5.setVisibility(View.GONE);
            viewHolder.memoryMultimedia5.setImageBitmap(null);
            viewHolder.memoryMultimedia6.setVisibility(View.GONE);
            viewHolder.memoryMultimedia6.setImageBitmap(null);
            viewHolder.memoryMultimedia7.setVisibility(View.GONE);
            viewHolder.memoryMultimedia7.setImageBitmap(null);
            viewHolder.memoryMultimedia8.setVisibility(View.GONE);
            viewHolder.memoryMultimedia8.setImageBitmap(null);
            viewHolder.memoryMultimedia9.setVisibility(View.GONE);
            viewHolder.memoryMultimedia9.setImageBitmap(null);
            viewHolder.memoryMultimedia10.setVisibility(View.GONE);
            viewHolder.memoryMultimedia10.setImageBitmap(null);
            viewHolder.btnLike.setImageDrawable(getContext().getResources().getDrawable(R.drawable.nolike_icon));
            viewHolder.ivFeedProfilePicture.setImageBitmap(null);
        }

        // Sets the content of the view.
        setViewContent(viewHolder, dataSet.get(position));
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     */
    private void setViewContent(final ViewHolder viewHolder, final Memory memory) {
        if(memory != null) {
            String avatarURL = Constants.API_BASE_URL + "/multimedia/" + memory.getOwner().getPhoto();
            String username = "@" + memory.getOwner().getUsername();
            String postedTime = "Posted on " + memory.getPosting_time();
            String mentionedTime = "Mentions about ";
            String location = "Place is ";
            String title = memory.getTitle();

            if(memory.getMentioned_time().length != 0) {
                mentionedTime += memory.getMentioned_time()[0].getDate_string1();
                if(memory.getMentioned_time()[0].getDate_string2() != null && !memory.getMentioned_time()[0].getDate_string2().equals("")) {
                    mentionedTime += " - " + memory.getMentioned_time()[0].getDate_string2();
                }
            } else {
                mentionedTime += "anytime";
            }

            if(memory.getLocation().length != 0) {
                location += memory.getLocation()[0].getLocation()[0].getLocation_name();
                Log.v("1", "1");
                if(memory.getLocation()[0].getLocation().length > 1) {
                    location += " to " + memory.getLocation()[0].getLocation()[1].getLocation_name();
                    Log.v("2", "2");
                }
            } else {
                location += "anywhere";
            }

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
            if(memory.getOwner().getPhoto() != null && !memory.getOwner().getPhoto().equals("")) {
                Picasso
                        .get()
                        .load(avatarURL)
                        .fit()
                        .into(viewHolder.ivFeedProfilePicture);
            } else {
                viewHolder.ivFeedProfilePicture.setImageDrawable(getContext().getResources().getDrawable(R.drawable.avatar_icon));
            }

            boolean isLiked = false;
            int myUserID = SharedPrefHelper.getUserId(getContext());
            for(int userID : memory.getLiked_users()) {
                if(myUserID == userID) {
                    isLiked = true;
                    break;
                }
            }

            if(isLiked) {
                viewHolder.btnLike.setImageDrawable(getContext().getResources().getDrawable(R.drawable.yeslike_icon));
            }

            final boolean myLike = isLiked;

            viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!myLike) {
                        viewHolder.btnLike.setImageDrawable(getContext().getResources().getDrawable(R.drawable.yeslike_icon));
                        viewHolder.tvLikeCount.setText(String.valueOf(memory.getNumlikes() + 1));
                        MemoristApi.postLike(SharedPrefHelper.getUserToken(getContext()), memory.getId());
                    } else {
                        viewHolder.btnLike.setImageDrawable(getContext().getResources().getDrawable(R.drawable.nolike_icon));
                        viewHolder.tvLikeCount.setText(String.valueOf(memory.getNumlikes() - 1));
                        MemoristApi.postDislike(SharedPrefHelper.getUserToken(getContext()), memory.getId());
                    }
                }
            });

            viewHolder.tvLikeCount.setText(String.valueOf(memory.getNumlikes()));

            Multimedia[] multimedia = memory.getMultimedia();
            int mediaCounter = 1;

            viewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryCommentsClicked(memory);
                }
            });

            viewHolder.btnAnnotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryAnnotationsClicked(memory);
                }
            });

            viewHolder.tvCommentCount.setText(String.valueOf(memory.getNumcomments()));

            for(Multimedia mm: multimedia) {
                String mediaURL = Constants.API_BASE_URL + mm.getMultimedia().getMedia();
                int mediaType = mm.getMedia_type();

                if(mediaType == 1) {
                    switch (mediaCounter) {
                        case 1:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia1);
                            viewHolder.memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia2);
                            viewHolder.memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia3);
                            viewHolder.memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia4);
                            viewHolder.memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia5);
                            viewHolder.memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia6);
                            viewHolder.memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia7);
                            viewHolder.memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia8);
                            viewHolder.memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia9);
                            viewHolder.memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            Picasso.get().load(mediaURL).fit().into(viewHolder.memoryMultimedia10);
                            viewHolder.memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if (mediaType == 2) {
                    switch (mediaCounter) {
                        case 1:
                            viewHolder.memoryMultimedia1.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            viewHolder.memoryMultimedia2.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            viewHolder.memoryMultimedia3.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            viewHolder.memoryMultimedia4.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            viewHolder.memoryMultimedia5.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            viewHolder.memoryMultimedia6.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            viewHolder.memoryMultimedia7.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            viewHolder.memoryMultimedia8.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            viewHolder.memoryMultimedia9.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            viewHolder.memoryMultimedia10.setImageDrawable(context.getResources().getDrawable(R.drawable.video_play_icon));
                            viewHolder.memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if(mediaType == 3) {
                    switch (mediaCounter) {
                        case 1:
                            viewHolder.memoryMultimedia1.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            viewHolder.memoryMultimedia2.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            viewHolder.memoryMultimedia3.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            viewHolder.memoryMultimedia4.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia4.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            viewHolder.memoryMultimedia5.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia5.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            viewHolder.memoryMultimedia6.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia6.setVisibility(View.VISIBLE);
                            break;
                        case 7:
                            viewHolder.memoryMultimedia7.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia7.setVisibility(View.VISIBLE);
                            break;
                        case 8:
                            viewHolder.memoryMultimedia8.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia8.setVisibility(View.VISIBLE);
                            break;
                        case 9:
                            viewHolder.memoryMultimedia9.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia9.setVisibility(View.VISIBLE);
                            break;
                        case 10:
                            viewHolder.memoryMultimedia10.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_icon));
                            viewHolder.memoryMultimedia10.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                mediaCounter++;
            }

            viewHolder.memoryMultimedia1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 1);
                }
            });

            viewHolder.memoryMultimedia2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 2);
                }
            });

            viewHolder.memoryMultimedia3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 3);
                }
            });

            viewHolder.memoryMultimedia4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 4);
                }
            });

            viewHolder.memoryMultimedia5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 5);
                }
            });

            viewHolder.memoryMultimedia6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 6);
                }
            });

            viewHolder.memoryMultimedia7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 7);
                }
            });

            viewHolder.memoryMultimedia8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 8);
                }
            });

            viewHolder.memoryMultimedia9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 9);
                }
            });

            viewHolder.memoryMultimedia10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.memoryMultimediaClick(memory, 10);
                }
            });
        }
    }

    public interface MemoryOnClickListener {
        void memoryMultimediaClick(Memory memory, int position);
        void memoryCommentsClicked(Memory memory);
        void memoryAnnotationsClicked(Memory memory);
    }
}