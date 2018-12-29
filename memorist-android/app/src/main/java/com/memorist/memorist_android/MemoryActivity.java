package com.memorist.memorist_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import com.memorist.memorist_android.adapter.MemoryAdapter;
import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.EditProfileFragment;
import com.memorist.memorist_android.fragment.FeedCommentFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.FolloweringsFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.helper.Constants;
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.helper.UriPathHelper;
import com.memorist.memorist_android.model.ApiResultFollower;
import com.memorist.memorist_android.model.ApiResultFollowing;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.model.ApiResultProfile;
import com.memorist.memorist_android.model.Comments;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Multimedia;
import com.memorist.memorist_android.model.User;
import com.memorist.memorist_android.ws.MemoristApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MemoryActivity extends BaseActivity
    implements CreateMemoryFragment.OnFragmentInteractionListener,
    FeedMemoryFragment.OnFragmentInteractionListener,
    SearchMemoryFragment.OnFragmentInteractionListener,
    RecommendationsFragment.OnFragmentInteractionListener,
    FolloweringsFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener,
        MemoryAdapter.MemoryOnClickListener,
        FeedCommentFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_SEARCH_MEMORY_FRAGMENT = "fragment_search_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";
    private final String TAG_RECOMMENDATIONS_FRAGMENT = "fragment_recommendations";
    private final String TAG_USER_PROFILE_FRAGMENT = "fragment_user_profile";
    private final String TAG_FOLLOWERINGS_FRAGMENT = "fragment_followerings";
    private final String TAG_EDIT_PROFILE = "fragment_edit_profile";
    private final String TAG_FEED_COMMENT_FRAGMENT = "fragment_feed_comment";

    private ArrayList<Integer> memoryMultimediaID;
    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<String> memoryTags;
    private String memoryTitle;
    private String mentionedTime;
    private String mentionedTime2;
    private String selectedDateFormat;
    private int selectedDateType;
    private int multimediaCounter;

    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ButterKnife.bind(this);
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Font set up for the activity.
     * @param newBase: The context which the fonts will be set on.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void getUserMemoryList() {
        MemoristApi.getUserMemoryList(SharedPrefHelper.getUserToken(getApplicationContext()), getUserMemoryListListener, getUserMemoryListErrorListener);
    }

    @Override
    public void getMemoryList() {
        MemoristApi.getMemoryList(SharedPrefHelper.getUserToken(getApplicationContext()), getUserMemoryListListener, getUserMemoryListErrorListener);
    }

    @Override
    public void getSearchResults() {
        MemoristApi.getMemoryList(SharedPrefHelper.getUserToken(getApplicationContext()), getUserMemoryListListener, getUserMemoryListErrorListener);
    }

    @Override
    public void getUserProfile() {
        MemoristApi.getProfile(SharedPrefHelper.getUserToken(getApplicationContext()), SharedPrefHelper.getUserId(getApplicationContext()),
                getUserProfileListener, getUserProfileErrorListener);
    }

    @Override
    public void getFollowers() {
        MemoristApi.getFollowers(SharedPrefHelper.getUserToken(getApplicationContext()), getFollowersListener, getFollowersFollowingsErrorListener);
    }

    @Override
    public void getFollowings() {
        MemoristApi.getFollowings(SharedPrefHelper.getUserToken(getApplicationContext()), getFollowingsListener, getFollowersFollowingsErrorListener);
    }

    @Override
    public void getFollowerList(ArrayList<ApiResultFollower> list) {
        ArrayList<String> listFollower = new ArrayList<>();

        for(ApiResultFollower follower: list) {
            listFollower.add("@" + follower.getUsername());
        }

        FolloweringsFragment fragment = FolloweringsFragment.newInstance(listFollower, null);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.memoryFragmentContent, fragment, TAG_FOLLOWERINGS_FRAGMENT)
                .addToBackStack(TAG_FOLLOWERINGS_FRAGMENT)
                .commit();

        currentTab = 6;
    }

    @Override
    public void getFollowingList(ArrayList<ApiResultFollowing> list) {
        ArrayList<String> listFollowing = new ArrayList<>();

        for(ApiResultFollowing following: list) {
            listFollowing.add("@" + following.getUsername());
        }

        FolloweringsFragment fragment = FolloweringsFragment.newInstance(null, listFollowing);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.memoryFragmentContent, fragment, TAG_FOLLOWERINGS_FRAGMENT)
                .addToBackStack(TAG_FOLLOWERINGS_FRAGMENT)
                .commit();

        currentTab = 6;
    }

    @Override
    public void memoryShared(String memoryTitle, String mentionedTime, String mentionedTime2, String location, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, int selectedDateType, String selectedDateFormat, ArrayList<Uri> memoryImage,
                             ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        this.memoryMultimediaID = new ArrayList<>();
        this.memoryTitle = memoryTitle;
        this.memoryFormat = memoryFormat;
        this.selectedDateType = selectedDateType;
        this.selectedDateFormat = selectedDateFormat;
        this.mentionedTime = mentionedTime;
        this.mentionedTime2 = mentionedTime2;
        this.memoryText = memoryText;
        this.memoryTags = memoryTags;
        this.multimediaCounter = memoryImage.size() + memoryVideo.size() + memoryAudio.size();

        showIndicator();

        if(this.multimediaCounter == 0) {
            try {
                MemoristApi.createMemory(SharedPrefHelper.getUserToken(getApplicationContext()), memoryTitle, memoryFormat,
                        memoryText, selectedDateType, selectedDateFormat, mentionedTime, mentionedTime2,
                        memoryMultimediaID, memoryTags, createMemoryListener, createMemoryErrorListener);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            for(Uri uri: memoryImage) {
                String filePath = UriPathHelper.getPathFromURI_Image(this, uri);

                if(filePath != null) {
                    File imageFile = new File(filePath);
                    MemoristApi.createMemoryImage(getApplicationContext(), imageFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                }
            }

            for(Uri uri: memoryVideo) {
                String filePath = UriPathHelper.getPathFromURI_Video(this, uri);

                if(filePath != null) {
                    File videoFile = new File(filePath);
                    MemoristApi.createMemoryVideo(getApplicationContext(), videoFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                }
            }

            for(Uri uri: memoryAudio) {
                String filePath = UriPathHelper.getPathFromURI_Audio(this, uri);
                Log.v("audio", filePath);
                if(filePath != null) {
                    File audioFile = new File(filePath);
                    MemoristApi.createMemoryAudio(getApplicationContext(), audioFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                }
            }
        }
    }

    @Override
    public void memoryCanceled() {
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
    }

    @Override
    public void displayImageDialog(Uri selectedImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        ImageView imageContent = v.findViewById(R.id.dialog_imageContent);
        imageContent.setImageURI(selectedImage);
        imageContent.setVisibility(View.VISIBLE);

        builder.show();
    }

    @Override
    public void displayVideoDialog(Uri selectedVideo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Video");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        VideoView videoContent = v.findViewById(R.id.dialog_videoContent);
        videoContent.setVideoURI(selectedVideo);
        videoContent.setVisibility(View.VISIBLE);

        builder.show();
        videoContent.start();
    }

    @Override
    public void displayAudioDialog(Uri selectedAudio) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, selectedAudio);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Audio");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.stop();
            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        ImageView audioContent = v.findViewById(R.id.dialog_imageContent);
        audioContent.setImageDrawable(getResources().getDrawable(R.drawable.audio_icon));
        audioContent.setVisibility(View.VISIBLE);

        builder.show();
        mediaPlayer.start();
    }

    @Override
    public void proceedProfileEdit(String username, String first_name, String last_name, String gender, String location, String photo) {
        EditProfileFragment fragment = EditProfileFragment.newInstance(username, first_name, last_name, gender, location, photo);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                        .enter_from_left, R.anim.exit_to_right)
                .replace(R.id.memoryFragmentContent, fragment, TAG_EDIT_PROFILE)
                .addToBackStack(TAG_EDIT_PROFILE)
                .commit();

        currentTab = 6;
    }

    @Override
    public void memoryCommentsClicked(Memory memory) {
        FeedCommentFragment fragment = FeedCommentFragment.newInstance(memory.getComments(), memory.getId());
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.memoryFragmentContent, fragment, TAG_FEED_COMMENT_FRAGMENT)
                .addToBackStack(TAG_FEED_COMMENT_FRAGMENT)
                .commit();

        currentTab = 6;
    }

    @Override
    public void addComment(String comment, int memoryID) {
        MemoristApi.sendComment(SharedPrefHelper.getUserToken(getApplicationContext()), comment, memoryID, sendCommentListener, sendCommentErrorListener);
    }

    @OnClick(R.id.btn_memoristHome)
    public void tabHomeClicked(View view) {
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
    }

    @OnClick(R.id.btn_memoristSearch)
    public void tabSearchClicked(View view) {
        tabSwitcher(TAG_SEARCH_MEMORY_FRAGMENT, 2);
    }

    @OnClick(R.id.btn_memoristAdd)
    public void tabCreateMemoryClicked(View view) {
        tabSwitcher(TAG_CREATE_MEMORY_FRAGMENT, 3);
    }

    @OnClick(R.id.btn_memoristRecommendation)
    public void tabRecommendationsClicked(View view) {
        tabSwitcher(TAG_RECOMMENDATIONS_FRAGMENT, 4);
    }

    @OnClick(R.id.btn_memoristProfile)
    public void tabProfileClicked(View view) {
        tabSwitcher(TAG_USER_PROFILE_FRAGMENT, 5);
    }

    public void tabSwitcher(String targetFragment, int targetTab) {
        if(currentTab != targetTab) {
            Fragment fragment;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if(targetTab == 1) {
                fragment = FeedMemoryFragment.newInstance();
            } else if(targetTab == 2) {
                fragment = SearchMemoryFragment.newInstance();
            } else if(targetTab == 3) {
                fragment = CreateMemoryFragment.newInstance();
            } else if(targetTab == 4) {
                fragment = RecommendationsFragment.newInstance();
            } else {
                fragment = ProfileFragment.newInstance();
            }

            if(targetTab < currentTab) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim
                        .enter_from_right, R.anim.exit_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                        .enter_from_left, R.anim.exit_to_right);
            }

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, targetFragment);
            fragmentTransaction.addToBackStack(targetFragment);
            fragmentTransaction.commit();

            currentTab = targetTab;
        }
    }

    private Response.Listener<ApiResultMediaUpload> mediaUploadListener = new Response.Listener<ApiResultMediaUpload>() {
        @Override
        public void onResponse(ApiResultMediaUpload response) {
            memoryMultimediaID.add(response.getId());

            float progressAmount = (float)memoryMultimediaID.size() / multimediaCounter * 100f;
            setIndicatorDetail(memoryMultimediaID.size() + "/" + multimediaCounter + " media uploaded");
            setProgressDetail((int)progressAmount);

            if(memoryMultimediaID.size() == multimediaCounter) {
                try {
                    MemoristApi.createMemory(SharedPrefHelper.getUserToken(getApplicationContext()), memoryTitle, memoryFormat,
                            memoryText, selectedDateType, selectedDateFormat, mentionedTime, mentionedTime2,
                            memoryMultimediaID, memoryTags, createMemoryListener, createMemoryErrorListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Response.ErrorListener mediaUploadErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();

            String body = "as";
            try {
                body = new String(error.networkResponse.data,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.v("lof", body);
        }
    };

    private Response.Listener<ArrayList<Memory>> getUserMemoryListListener = new Response.Listener<ArrayList<Memory>>() {
        @Override
        public void onResponse(ArrayList<Memory> response) {
            if(currentTab == 1) {
                FeedMemoryFragment fragment = (FeedMemoryFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT);
                if(fragment != null) {
                    fragment.updateMemories(response);
                }
            } else if(currentTab == 2) {
                SearchMemoryFragment fragment = (SearchMemoryFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH_MEMORY_FRAGMENT);
                if(fragment != null) {
                    fragment.updateMemories(response);
                }
            } else if(currentTab == 4) {
                RecommendationsFragment fragment = (RecommendationsFragment) getSupportFragmentManager().findFragmentByTag(TAG_RECOMMENDATIONS_FRAGMENT);
                if(fragment != null) {
                    fragment.updateMemories(response);
                }
            } else if(currentTab == 5) {
                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);
                if(fragment != null) {
                    fragment.updateMemories(response);
                }
            }
        }
    };

    private Response.ErrorListener getUserMemoryListErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<JSONObject> createMemoryListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            String successMessage = "Your story is now alive.. Hooraaay!";
            Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();

            setIndicatorDetail(memoryMultimediaID.size() + "/" + multimediaCounter + " media uploaded");
            setProgressDetail(100);
            hideIndicator();

            tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
        }
    };

    private Response.ErrorListener createMemoryErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<User> getUserProfileListener = new Response.Listener<User>() {
        @Override
        public void onResponse(User response) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);
            if(fragment != null) {
                fragment.updateProfileInfo(response);
            }
        }
    };

    private Response.ErrorListener getUserProfileErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String serverIsDown = "We had a short maintenance break, please try again later.";
            Toast.makeText(getApplicationContext(), serverIsDown, Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<ArrayList<ApiResultFollower>> getFollowersListener = new Response.Listener<ArrayList<ApiResultFollower>>() {
        @Override
        public void onResponse(ArrayList<ApiResultFollower> response) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);
            if (fragment != null) {
                fragment.updateFollowers(response);
            }
        }
    };

    private Response.Listener<ArrayList<Comments>> sendCommentListener = new Response.Listener<ArrayList<Comments>>() {
        @Override
        public void onResponse(ArrayList<Comments> response) {
            FeedCommentFragment fragment = (FeedCommentFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_COMMENT_FRAGMENT);
            if(fragment != null) {
                fragment.updateComments(response);
            }
        }
    };

    private Response.Listener<ArrayList<ApiResultFollowing>> getFollowingsListener = new Response.Listener<ArrayList<ApiResultFollowing>>() {
        @Override
        public void onResponse(ArrayList<ApiResultFollowing> response) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);
            if(fragment != null) {
                fragment.updateFollowings(response);
            }
        }
    };

    private Response.ErrorListener getFollowersFollowingsErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    private Response.ErrorListener sendCommentErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    @Override
    public void memoryMultimediaClick(Memory memory, int position) {
        Multimedia multimedia = memory.getMultimedia()[position - 1];
        String mediaURL = Constants.API_BASE_URL + multimedia.getMultimedia().getMedia();

        if(multimedia.getMedia_type() == 1) {
            playImage(mediaURL);
        } else if(multimedia.getMedia_type() == 2) {
            playVideo(mediaURL);
        } else {
            playAudio(mediaURL);
        }
    }

    public void playImage(String mediaURL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        ImageView imageContent = v.findViewById(R.id.dialog_imageContent);
        imageContent.setVisibility(View.VISIBLE);
        Picasso.get().load(mediaURL).into(imageContent);

        builder.show();
    }

    public void playVideo(String mediaURL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Video");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        VideoView videoContent = v.findViewById(R.id.dialog_videoContent);
        videoContent.setVideoURI(Uri.parse(mediaURL));
        videoContent.setVisibility(View.VISIBLE);

        builder.show();
        videoContent.start();
    }

    public void playAudio(String mediaURL) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.parse(mediaURL));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Audio");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.stop();
            }
        });

        View v = getLayoutInflater().inflate(R.layout.dialog_media, null);
        builder.setView(v);

        ImageView audioContent = v.findViewById(R.id.dialog_imageContent);
        audioContent.setImageDrawable(getResources().getDrawable(R.drawable.audio_icon));
        audioContent.setVisibility(View.VISIBLE);

        builder.show();
        mediaPlayer.start();
    }

    @Override
    public void updateProfileInformation(String first, String last, int gender, String location) {
        MemoristApi.updateProfileInfo(SharedPrefHelper.getUserToken(getApplicationContext()), first, last, gender, location, updateSuccessListener, getUserProfileErrorListener);
    }

    private Response.Listener<ApiResultProfile> updateSuccessListener = new Response.Listener<ApiResultProfile>() {
        @Override
        public void onResponse(ApiResultProfile response) {
            tabSwitcher(TAG_USER_PROFILE_FRAGMENT, 5);
        }
    };

    @Override
    public void profilePhotoUpdate(Uri photo) {
        String filePath = UriPathHelper.getPathFromURI_Image(this, photo);

        if(filePath != null) {
            File imageFile = new File(filePath);
            MemoristApi.photoUpdate(getApplicationContext(), SharedPrefHelper.getUserToken(getApplicationContext()), imageFile, filePath, null, mediaUploadErrorListener);
        }
    }
}
