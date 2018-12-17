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
import com.memorist.memorist_android.model.Comments;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends ArrayAdapter<Comments> {

    // The container list of all existing meeting data objects.
    private ArrayList<Comments> dataSet;

    // The context that the adapter will work on.
    private Context context;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.iv_commentProfilePicture)
        ImageView ivCommentProfilePicture;

        @BindView(R.id.tv_commentUsername)
        TextView tvCommentUsername;

        @BindView(R.id.tv_commentPostedTime)
        TextView tvCommentPostedTime;

        @BindView(R.id.tv_commentText)
        TextView tvCommentText;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public CommentAdapter(ArrayList<Comments> dataSet, Context context) {
        super(context, R.layout.adapter_comment_row, dataSet);

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
        // The commment in the placement of the specific row.
        final Comments comment = getItem(position);

        /*
         * ViewHolder is used to avoid instantiating a view for every item in your adapter,
         * when a view scrolls off-screen, it can be reused, or recycled.
         */
        CommentAdapter.ViewHolder viewHolder;

        /*
         * If convertView view object has never been constructed, then inflate the layout,
         * create the viewHolder and set convertView's tag.
         * Else get convertView's tag.
         */
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_comment_row, parent, false);
            viewHolder = new CommentAdapter.ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommentAdapter.ViewHolder) convertView.getTag();
        }

        // Sets the content of the view.
        setViewContent(position, convertView, viewHolder, comment);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     * @param comment: The meeting object which holds the content.
     */
    private void setViewContent(int position, @Nullable View convertView, final CommentAdapter.ViewHolder viewHolder, final Comments comment) {
        if(comment != null) {
            String username = "@" + comment.getOwner().getUsername();
            String postedTime = "Posted on " + comment.getCommentTime();
            String text= comment.getComment();

            viewHolder.tvCommentUsername.setText(username);
            viewHolder.tvCommentPostedTime.setText(postedTime);
            viewHolder.tvCommentText.setText(text);
        }
    }
}