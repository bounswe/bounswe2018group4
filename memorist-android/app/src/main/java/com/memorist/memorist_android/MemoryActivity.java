package com.memorist.memorist_android;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.memorist.memorist_android.fragment.CreateMemoryFragment;

public class MemoryActivity extends AppCompatActivity
    implements CreateMemoryFragment.OnFragmentInteractionListener {

    private final String TAG_CREATE_MEMORY_FRAGMENT = "fragment_create_memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
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
}
