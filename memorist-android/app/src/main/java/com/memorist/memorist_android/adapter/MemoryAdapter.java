package com.memorist.memorist_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.model.Memory;

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
        @BindView(R.id.tv_feedStory) TextView tvFeedStory;

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
        // The meeting in the placement of the specific row.
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
        setViewContent(viewHolder, memory);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     * @param memory: The meeting object which holds the content.
     */
    private void setViewContent(ViewHolder viewHolder, Memory memory) {
        if(memory != null) {

            String username = "@" + memory.getMemoryOwner().getUsername();
            String postedTime = "Posted on " + memory.getPostedTime();
            String mentionedTime = "Mentioned about " + memory.getMentionedTime();
            String location = "Place is " + memory.getLocation();
            String story = memory.getStory();

            viewHolder.tvFeedUsername.setText(username);
            viewHolder.tvFeedPostedTime.setText(postedTime);
            viewHolder.tvFeedMentionedTime.setText(mentionedTime);
            viewHolder.tvFeedLocation.setText(location);
            viewHolder.tvFeedStory.setText(story);
        }
    }
}