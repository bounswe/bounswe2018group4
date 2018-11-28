package com.memorist.memorist_android;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;
import com.memorist.memorist_android.ws.MemoristApi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ButterKnife.bind(this);
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
    }

    public void tabSwitcher(String targetFragment, int targetTab) {
        Fragment fragment;

        if(getSupportFragmentManager().findFragmentByTag(targetFragment) != null) {
            fragment = getSupportFragmentManager().findFragmentByTag(targetFragment);
        } else if(targetTab == 1) {
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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(targetTab < currentTab) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim
                    .enter_from_right, R.anim.exit_to_left);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                    .enter_from_left, R.anim.exit_to_right);
        }

        if(fragment != null) {
            currentTab = targetTab;
            fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, targetFragment);
            fragmentTransaction.addToBackStack(targetFragment);
            fragmentTransaction.commit();
        }
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

    @Override
    public void memoryShared(String title, String mentionedTime, String location, String memoryTitle, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo,
                             ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, HH:mm");
        String postedTime = sdf.format(new Date());

        String filePath = getPathFromURI(this, memoryImage.get(0));
        File F = new File(filePath);

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), memoryImage.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MemoristApi.createMemorySendMedia(F, bitmap, getApplicationContext());

        Memory memory = new Memory(4, new User(3, "@BerkeTheTechNerd", "Berke", "Esmer", "berkee.eesmer@gmail.com", true),
                postedTime, mentionedTime, location, memoryTitle, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void memoryCanceled() {
        onBackPressed();
    }

    /**
     * Font set up for the activity.
     * @param newBase: The context which the fonts will be set on.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public String getPathFromURI(Context context, Uri contentUri) {
        Cursor mediaCursor = null;
        try {
            String[] dataPath = { MediaStore.Images.Media.DATA };
            mediaCursor = context.getContentResolver().query(contentUri,  dataPath, null, null, null);
            int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            mediaCursor.moveToFirst();
            return mediaCursor.getString(column_index);
        } finally {
            if (mediaCursor != null) {
                mediaCursor.close();
            }
        }
    }
}
