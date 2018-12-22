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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.helper.UriPathHelper;
import com.memorist.memorist_android.model.ApiResultFollower;
import com.memorist.memorist_android.model.ApiResultFollowing;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;
import com.memorist.memorist_android.ws.MemoristApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MemoryActivity extends BaseActivity
    implements CreateMemoryFragment.OnFragmentInteractionListener,
    FeedMemoryFragment.OnFragmentInteractionListener,
    SearchMemoryFragment.OnFragmentInteractionListener,
    RecommendationsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_SEARCH_MEMORY_FRAGMENT = "fragment_search_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";
    private final String TAG_RECOMMENDATIONS_FRAGMENT = "fragment_recommendations";
    private final String TAG_USER_PROFILE_FRAGMENT = "fragment_user_profile";

    private ArrayList<Integer> memoryMultimediaID;
    private ArrayList<String> memoryFormat;
    private ArrayList<String> memoryText;
    private ArrayList<String> memoryTags;
    private String memoryTitle;
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
    public void memoryShared(String memoryTitle, String mentionedTime, String location, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                             ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        this.memoryMultimediaID = new ArrayList<>();
        this.memoryTitle = memoryTitle;
        this.memoryFormat = memoryFormat;
        this.memoryText = memoryText;
        this.memoryTags = memoryTags;
        this.multimediaCounter = memoryImage.size() + memoryVideo.size() + memoryAudio.size();

        showIndicator();

        if(this.multimediaCounter == 0) {
            try {
                MemoristApi.createMemory(SharedPrefHelper.getUserToken(getApplicationContext()), memoryTitle, memoryFormat,
                        memoryText, memoryMultimediaID, memoryTags, createMemoryListener, createMemoryErrorListener);
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
                            memoryText, memoryMultimediaID, memoryTags, createMemoryListener, createMemoryErrorListener);
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
            if(fragment != null) {
                fragment.updateFollowers(response);
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
}
