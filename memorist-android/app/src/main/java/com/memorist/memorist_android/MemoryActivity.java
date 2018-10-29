package com.memorist.memorist_android;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.model.ApiResultCreatePost;
import com.memorist.memorist_android.ws.MemoristApi;

import java.util.ArrayList;

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
    public void memoryShared(String title, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                             ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio,
                             ArrayList<String> memoryTags) {
        MemoristApi.createNewMemory(title, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags, createPostListener, createPostErrorListener);
    }

    private Response.Listener<ApiResultCreatePost> createPostListener = new Response.Listener<ApiResultCreatePost>() {
        @Override
        public void onResponse(ApiResultCreatePost response) {
            Toast.makeText(getApplicationContext(), "Create post is successful", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    };

    private Response.ErrorListener createPostErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Create post is NOT successful", Toast.LENGTH_LONG).show();
            Log.v("Error", error.toString());
        }
    };
}
