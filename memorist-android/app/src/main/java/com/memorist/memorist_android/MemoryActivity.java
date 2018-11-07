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
    FeedMemoryFragment.OnFragmentInteractionListener {

    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ButterKnife.bind(this);

        FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.memoryFragmentContent,
                fragment, TAG_FEED_MEMORY_FRAGMENT).commit();
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
    public void memoryCanceled() {
        onBackPressed();
    }

    @Override
    public void memoryShared(String title, String mentionedTime, String location, String memoryTitle, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo,
                             ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        onBackPressed();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, HH:mm");
        String postedTime = sdf.format(new Date());

        Memory memory = new Memory(4, new User(3, "@BerkeTheTechNerd", "Berke", "Esmer", "berkee.eesmer@gmail.com", true),
                postedTime, mentionedTime, location, memoryTitle, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.btn_memoristHome)
    public void tabHomeClicked(View view) {
        FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                .enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_FEED_MEMORY_FRAGMENT);
        fragmentTransaction.addToBackStack(TAG_FEED_MEMORY_FRAGMENT);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.btn_memoristSearch)
    public void tabSearchClicked(View view) {
        Toast.makeText(getApplicationContext(), "Search is not functional yet..", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_memoristAdd)
    public void tabAddClicked(View view) {
        CreateMemoryFragment fragment = CreateMemoryFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                .enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_CREATE_MEMORY_FRAGMENT);
        fragmentTransaction.addToBackStack(TAG_CREATE_MEMORY_FRAGMENT);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.btn_memoristRecommendation)
    public void tabRecommendationsClicked(View view) {
        Toast.makeText(getApplicationContext(), "Recommendation is not functional yet..", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_memoristProfile)
    public void tabProfileClicked(View view) {
        Toast.makeText(getApplicationContext(), "Profile is not functional yet..", Toast.LENGTH_LONG).show();
    }
}
