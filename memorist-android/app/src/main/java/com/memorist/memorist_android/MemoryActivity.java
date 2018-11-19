package com.memorist.memorist_android;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;

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

    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_SEARCH_MEMORY_FRAGMENT = "fragment_search_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";
    private final String TAG_RECOMMENDATIONS_FRAGMENT = "fragment_recommendations";
    private final String TAG_USER_PROFILE_FRAGMENT = "fragment_user_profile";

    private String currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ButterKnife.bind(this);
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT);
    }

    public void tabSwitcher(String targetFragment) {
        if(currentTab == null) {
            currentTab = TAG_FEED_MEMORY_FRAGMENT;
            FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.memoryFragmentContent,
                    fragment, TAG_FEED_MEMORY_FRAGMENT).commit();
        } else {
            if(getSupportFragmentManager().findFragmentByTag(targetFragment) != null) {
                if (targetFragment.equals(TAG_FEED_MEMORY_FRAGMENT)) {
                    FeedMemoryFragment fragment = (FeedMemoryFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_FEED_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_FEED_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_SEARCH_MEMORY_FRAGMENT)) {
                    SearchMemoryFragment fragment = (SearchMemoryFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_SEARCH_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_SEARCH_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_CREATE_MEMORY_FRAGMENT)) {
                    CreateMemoryFragment fragment = (CreateMemoryFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_CREATE_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_CREATE_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_RECOMMENDATIONS_FRAGMENT)) {
                    RecommendationsFragment fragment = (RecommendationsFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_RECOMMENDATIONS_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_RECOMMENDATIONS_FRAGMENT);
                    fragmentTransaction.commit();
                } else {
                    ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(targetFragment);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_USER_PROFILE_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_USER_PROFILE_FRAGMENT);
                    fragmentTransaction.commit();
                }
            } else {
                if (targetFragment.equals(TAG_FEED_MEMORY_FRAGMENT)) {
                    FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_FEED_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_FEED_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_SEARCH_MEMORY_FRAGMENT)) {
                    SearchMemoryFragment fragment = SearchMemoryFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_SEARCH_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_SEARCH_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_CREATE_MEMORY_FRAGMENT)) {
                    CreateMemoryFragment fragment = CreateMemoryFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_CREATE_MEMORY_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_CREATE_MEMORY_FRAGMENT);
                    fragmentTransaction.commit();
                } else if (targetFragment.equals(TAG_RECOMMENDATIONS_FRAGMENT)) {
                    RecommendationsFragment fragment = RecommendationsFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_RECOMMENDATIONS_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_RECOMMENDATIONS_FRAGMENT);
                    fragmentTransaction.commit();
                } else {
                    ProfileFragment fragment = ProfileFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                            .enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_USER_PROFILE_FRAGMENT);
                    fragmentTransaction.addToBackStack(TAG_USER_PROFILE_FRAGMENT);
                    fragmentTransaction.commit();
                }
            }
        }
    }

    @OnClick(R.id.btn_memoristHome)
    public void tabHomeClicked(View view) {
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT);
    }

    @OnClick(R.id.btn_memoristSearch)
    public void tabSearchClicked(View view) {
        tabSwitcher(TAG_SEARCH_MEMORY_FRAGMENT);
    }

    @OnClick(R.id.btn_memoristAdd)
    public void tabAddClicked(View view) {
        tabSwitcher(TAG_CREATE_MEMORY_FRAGMENT);
    }

    @OnClick(R.id.btn_memoristRecommendation)
    public void tabRecommendationsClicked(View view) {
        tabSwitcher(TAG_RECOMMENDATIONS_FRAGMENT);
    }

    @OnClick(R.id.btn_memoristProfile)
    public void tabProfileClicked(View view) {
        tabSwitcher(TAG_USER_PROFILE_FRAGMENT);
    }

    @Override
    public void memoryShared(String title, String mentionedTime, String location, String memoryTitle, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo,
                             ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        tabSwitcher(TAG_FEED_MEMORY_FRAGMENT);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, HH:mm");
        String postedTime = sdf.format(new Date());

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
}
