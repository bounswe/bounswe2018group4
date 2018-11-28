package com.memorist.memorist_android;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.ws.MemoristApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MemoryActivity extends AppCompatActivity
    implements CreateMemoryFragment.OnFragmentInteractionListener,
    FeedMemoryFragment.OnFragmentInteractionListener,
    SearchMemoryFragment.OnFragmentInteractionListener,
    RecommendationsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    private final String TAG = "MemoryActivity";
    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_SEARCH_MEMORY_FRAGMENT = "fragment_search_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";
    private final String TAG_RECOMMENDATIONS_FRAGMENT = "fragment_recommendations";
    private final String TAG_USER_PROFILE_FRAGMENT = "fragment_user_profile";

    private ArrayList<Integer> memoryMultimediaID;
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ButterKnife.bind(this);
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
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
    public void memoryShared(String memoryTitle, String mentionedTime, String location, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                             ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        memoryMultimediaID = new ArrayList<>();

        for(Uri uri: memoryImage) {
            String filePath = getPathFromURI(this, uri);

            if(filePath != null) {
                File imageFile = new File(getPathFromURI(this, uri));
                Bitmap imageBitmap = null;

                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch(IOException e) {
                    e.printStackTrace();
                }

                MemoristApi.createMemoryImage(getApplicationContext(), imageFile, imageBitmap, mediaUploadListener, mediaUploadErrorListener);
            }
        }

        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
    }

    @Override
    public void memoryCanceled() {
        onBackPressed();
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
    public void tabAddClicked(View view) {
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

    public String getPathFromURI(Context context, Uri contentUri) {
        String[] dataPath = { MediaStore.Images.Media.DATA };
        try (Cursor mediaCursor = context.getContentResolver().query(contentUri, dataPath, null, null, null)) {
            if (mediaCursor != null) {
                int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                mediaCursor.moveToFirst();

                return mediaCursor.getString(column_index);
            }
        }

        return null;
    }

    private Response.Listener<ApiResultMediaUpload> mediaUploadListener = new Response.Listener<ApiResultMediaUpload>() {
        @Override
        public void onResponse(ApiResultMediaUpload response) {
            memoryMultimediaID.add(response.getId());
        }
    };

    private Response.ErrorListener mediaUploadErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), Integer.toString(error.networkResponse.statusCode), Toast.LENGTH_LONG).show();
        }
    };
}
