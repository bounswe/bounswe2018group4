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
    private boolean dummyOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        FeedMemoryFragment fragment = FeedMemoryFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.memoryFragmentContent,
                fragment, TAG_FEED_MEMORY_FRAGMENT).commit();
    }

    @Override
    public void createDummyData() {
        if(!dummyOK) {
            ArrayList<String> memoryFormat = new ArrayList<>();
            ArrayList<String> memoryText = new ArrayList<>();
            ArrayList<Uri> memoryImage = new ArrayList<>();
            ArrayList<Uri> memoryVideo = new ArrayList<>();
            ArrayList<Uri> memoryAudio = new ArrayList<>();
            ArrayList<String> memoryTags = new ArrayList<>();

            memoryFormat.add("T");
            memoryText.add("I was a lovely day.. We had just got out of preparation exam called PROFICIENCY. It was the first day of summer and we planned a night we can spend on the sands of Kilyos." +
                    " We torched a light and we played guitar, we sang together. I had the chance to speak with her first time in that night. Until the dawn... So, Kilyos has a special meaning for us. " +
                    " If you stay in there for your preparation year as I did, do not forget that what happens in Kilyos stays in Kilyos except the love...");
            memoryTags.add("FirstLove");
            memoryTags.add("MyDearKilyos");

            Memory memory = new Memory(1, new User(1, "cemalaytekin", "Cemal", "Aytekin", "cemalaytekinnn@gmail.com"),
                    "October 20 17:23", "June 1st, 2016", "Kilyos", "Day One", memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);

            FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                    getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

            feedMemoryFragment.getMemories().add(memory);
            feedMemoryFragment.getAdapter().notifyDataSetChanged();

            createDummyData2();
        }
    }

    private void createDummyData2() {
        ArrayList<String> memoryFormat = new ArrayList<>();
        ArrayList<String> memoryText = new ArrayList<>();
        ArrayList<Uri> memoryImage = new ArrayList<>();
        ArrayList<Uri> memoryVideo = new ArrayList<>();
        ArrayList<Uri> memoryAudio = new ArrayList<>();
        ArrayList<String> memoryTags = new ArrayList<>();

        memoryFormat.add("T");
        memoryText.add("While we were studying at Robert College, (It is known as Boğaziçi University right now) there were tunnels between every building of faculties. " +
                "Professors would get angry when we use them but they wouldn't lock them anyway. They were very useful.");
        memoryTags.add("RobertCollege");
        memoryTags.add("SecretTunnels");
        memoryTags.add("Classified");

        Memory memory = new Memory(2, new User(2, "cemiloz", "Cemil", "Öz", "cemiloz@gmail.com"),
                "October 22 11:43", "1950s", "Boğaziçi University", "Secret Tunnels of Robert", memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);

        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();

        createDummyData3();
    }

    public void createDummyData3() {
        ArrayList<String> memoryFormat = new ArrayList<>();
        ArrayList<String> memoryText = new ArrayList<>();
        ArrayList<Uri> memoryImage = new ArrayList<>();
        ArrayList<Uri> memoryVideo = new ArrayList<>();
        ArrayList<Uri> memoryAudio = new ArrayList<>();
        ArrayList<String> memoryTagss = new ArrayList<>();

        memoryFormat.add("T");
        memoryText.add("I was the organiser of our high school graduation party. When the duty was offered to me, I volunteered cheerfully. I looked over many places and " +
                "I decided to book Mihrabat Korusu in Beykoz. There was a very beautiful scene of Bosphorus Bridge and the sea. We ate, we had fun. Everybody was grateful.");
        memoryTagss.add("HighSchoolGraduation");
        memoryTagss.add("Party");

        Memory memory = new Memory(3, new User(3, "mehmetdrd", "Mehmet", "Durdu", "mehmet.durdu15@hotmail.com"),
                "October 25 14:21", "2015", "Mihrabat Korusu", "High School of Graduation", memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTagss);

        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();

        dummyOK = true;
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
    public void memoryShared(String title, String mentionedTime, String location, String memoryTitle, ArrayList<String> memoryFormat,
                             ArrayList<String> memoryText, ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo,
                             ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        onBackPressed();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, HH:mm");
        String postedTime = sdf.format(new Date());

        Memory memory = new Memory(4, new User(3, "berkeesmer", "Berke", "Esmer", "berkee.eesmer@gmail.com"),
                postedTime, mentionedTime, location, memoryTitle, memoryFormat, memoryText, memoryImage, memoryVideo, memoryAudio, memoryTags);
        FeedMemoryFragment feedMemoryFragment = (FeedMemoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.memoryFragmentContent);

        feedMemoryFragment.getMemories().add(memory);
        feedMemoryFragment.getAdapter().notifyDataSetChanged();
    }
}
