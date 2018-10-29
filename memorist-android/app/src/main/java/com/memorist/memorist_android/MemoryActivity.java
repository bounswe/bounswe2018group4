package com.memorist.memorist_android;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemoryActivity extends AppCompatActivity
    implements CreateMemoryFragment.OnFragmentInteractionListener,
    FeedMemoryFragment.OnFragmentInteractionListener {

    private final String TAG_FEED_MEMORY_FRAGMENT = "fragment_feed_memory";
    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.memoryFragmentContent,
                fragment, TAG_FEED_MEMORY_FRAGMENT).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_memory_feed, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_memory:
                CreateMemoryFragment fragment = CreateMemoryFragment.newInstance();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim
                        .enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.memoryFragmentContent, fragment, TAG_CREATE_MEMORY_FRAGMENT);
                fragmentTransaction.addToBackStack(TAG_CREATE_MEMORY_FRAGMENT);
                fragmentTransaction.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void memoryCanceled() {
        onBackPressed();
    }

    @Override
    public void memoryShared(String title, String mentionedTime, String location, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo,
                             ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        onBackPressed();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, HH:mm");
        String postedTime = sdf.format(new Date());

        Memory memory = new Memory(1, new User(1, "berkeesmer", "Berke", "Esmer", "berkee.eesmer@gmail.com"),
                postedTime, mentionedTime, location, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();
    }
}
