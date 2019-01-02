package com.memorist.memorist_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.model.ApiResultFollowing;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends ArrayAdapter<ApiResultFollowing> {

    // The container list of all existing meeting data objects.
    private ArrayList<ApiResultFollowing> dataSet;
    private ArrayList<ApiResultFollowing> myFollowings;

    // The context that the adapter will work on.
    private Context context;

    // Listener to inform memory activity.
    private UserOnClickListener mListener;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.user_username) TextView tvUsername;
        @BindView(R.id.user_follow) Button btnFollow;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public UserAdapter(ArrayList<ApiResultFollowing> dataSet, ArrayList<ApiResultFollowing> myFollowings, Context context) {
        super(context, R.layout.adapter_user_row, dataSet);

        this.dataSet = dataSet;
        this.myFollowings = myFollowings;
        this.context = context;
        this.mListener = (UserOnClickListener) getContext();

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
            convertView = inflater.inflate(R.layout.adapter_user_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        boolean follow = false;
        for(ApiResultFollowing apiResultFollowing: myFollowings) {
            if(apiResultFollowing.getUsername().equals(dataSet.get(position).getUsername())) {
                follow = true;
            }
        }

        // Sets the content of the view.
        setViewContent(viewHolder, dataSet.get(position), follow);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     */
    private void setViewContent(final ViewHolder viewHolder, final ApiResultFollowing following, final boolean follow) {
        if(following != null) {
            String username = "@" + following.getUsername();
            viewHolder.tvUsername.setText(username);

            if(follow) {
                viewHolder.btnFollow.setText("Unfollow");
            } else {
                viewHolder.btnFollow.setText("Follow");
            }

            viewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(follow) {
                        mListener.unfollowUser(following.getId());
                        viewHolder.btnFollow.setText("Follow");
                    } else {
                        mListener.followUser(following.getId());
                        viewHolder.btnFollow.setText("Unfollow");
                    }
                }
            });
        }
    }

    public interface UserOnClickListener {
        void followUser(int id);
        void unfollowUser(int id);
    }
}