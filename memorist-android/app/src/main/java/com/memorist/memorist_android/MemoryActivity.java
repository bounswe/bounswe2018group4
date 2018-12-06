package com.memorist.memorist_android;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.memorist.memorist_android.fragment.CreateMemoryFragment;
import com.memorist.memorist_android.fragment.FeedMemoryFragment;
import com.memorist.memorist_android.fragment.ProfileFragment;
import com.memorist.memorist_android.fragment.RecommendationsFragment;
import com.memorist.memorist_android.fragment.SearchMemoryFragment;
import com.memorist.memorist_android.helper.SharedPrefHelper;
import com.memorist.memorist_android.helper.UriPathHelper;
import com.memorist.memorist_android.model.ApiResultMediaUpload;
import com.memorist.memorist_android.model.ApiResultProfile;
import com.memorist.memorist_android.model.Media;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Multimedia;
import com.memorist.memorist_android.model.Tag;
import com.memorist.memorist_android.ws.MemoristApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    public void retrieveProfile() {
        MemoristApi.getProfile(SharedPrefHelper.getUserToken(getApplicationContext()), getProfileListener, getProfileErrorListener);
    }

    @Override
    public void getMemoriesOfUser() {
        // MemoristApi.getMemoryList(SharedPrefHelper.getUserToken(getApplicationContext()), getMemoryListListener2, getUserMemoryListErrorListener);
    }

    @Override
    public void getMemoriesOfUser2() {
        // MemoristApi.getMemoryList(SharedPrefHelper.getUserToken(getApplicationContext()), getMemoryListListener3, getUserMemoryListErrorListener);
    }

    @Override
    public void memoryShared(String memoryTitle, String mentionedTime, String location, ArrayList<String> memoryFormat, ArrayList<String> memoryText,
                             ArrayList<Uri> memoryImage, ArrayList<Uri> memoryVideo, ArrayList<Uri> memoryAudio, ArrayList<String> memoryTags) {
        this.memoryMultimediaID = new ArrayList<>();
        this.memoryTitle = memoryTitle;
        this.memoryFormat = memoryFormat;
        this.memoryText = memoryText;
        this.memoryTags = memoryTags;
        this.multimediaCounter = 0;

        for(Uri uri: memoryImage) {
            String filePath = UriPathHelper.getPathFromURI_Image(this, uri);

            if(filePath != null) {
                File imageFile = new File(filePath);
                MemoristApi.createMemoryImage(getApplicationContext(), imageFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                multimediaCounter++;
            }
        }

        for(Uri uri: memoryVideo) {
            String filePath = UriPathHelper.getPathFromURI_Video(this, uri);

            if(filePath != null) {
                File videoFile = new File(filePath);
                MemoristApi.createMemoryVideo(getApplicationContext(), videoFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                multimediaCounter++;
            }
        }

        for(Uri uri: memoryAudio) {
            String filePath = UriPathHelper.getPathFromURI_Audio(this, uri);

            if(filePath != null) {
                File audioFile = new File(filePath);
                MemoristApi.createMemoryAudio(getApplicationContext(), audioFile, filePath, mediaUploadListener, mediaUploadErrorListener);
                multimediaCounter++;
            }
        }

        if(memoryMultimediaID.size() == 0) {
            try {
                MemoristApi.createMemory(SharedPrefHelper.getUserToken(getApplicationContext()), memoryTitle, memoryFormat, memoryText, memoryMultimediaID, memoryTags);
            } catch (JSONException e) {
                e.printStackTrace();
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
                if(getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT) == null)
                    fragment = FeedMemoryFragment.newInstance();
                else {
                    fragment = FeedMemoryFragment.newInstance();

                    /*

                    FeedMemoryFragment fragment2 = (FeedMemoryFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT);
                    fragment2.getMemories().add(new Memory(100, "berkeesmer123", "12.04.2018", "Love in Kilyos", new String[]{"It was a firs sight love. We met in a gathering " +
                            "after the preparation exam and we played guitar together. She was beautiful. Kilyos is a great place for love. You have to visit there!"}, null, null, 4));
                    fragment2.updateMemories(fragment2.getMemories());
                    fragment = fragment2;
                    */
                }
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

            if(memoryMultimediaID.size() == multimediaCounter) {
                try {
                    MemoristApi.createMemory(SharedPrefHelper.getUserToken(getApplicationContext()), memoryTitle, memoryFormat, memoryText, memoryMultimediaID, memoryTags);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Response.ErrorListener mediaUploadErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), Integer.toString(error.networkResponse.statusCode), Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<JSONArray> getMemoryListListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<Memory> memoryList = new ArrayList<>();
            Log.v("aaaaaa", response.toString());

            for(int i=0; i<response.length(); i++) {
                try {
                    JSONObject memoryObject = (JSONObject) response.get(i);
                    int id = memoryObject.getInt("id");
                    String owner = memoryObject.getString("owner");
                    String posting_time = memoryObject.getString("posting_time");
                    String title = memoryObject.getString("title");
                    int numlikes = memoryObject.getInt("numlikes");

                    JSONArray textsObject = (JSONArray) memoryObject.getJSONArray("texts");
                    String[] texts = new String[textsObject.length()];

                    for(int j=0; j<textsObject.length(); j++) {
                        JSONObject t = (JSONObject) textsObject.get(j);
                        texts[j] = (String) t.getString("text");
                    }

                    JSONArray multimediaObject = (JSONArray) memoryObject.getJSONArray("multimedia");
                    Multimedia[] multimedia = new Multimedia[multimediaObject.length()];

                    for(int j=0; j<multimediaObject.length(); j++) {
                        JSONObject arrObject = (JSONObject)multimediaObject.get(j);

                        int idMedia = arrObject.getInt("id");
                        int media_type = arrObject.getInt("media_type");
                        int order = arrObject.getInt("order");
                        int memory = arrObject.getInt("memory");

                        JSONObject mediaObject = (JSONObject)arrObject.get("multimedia");
                        String media = mediaObject.getString("media");
                        Media m = new Media(media);

                        multimedia[j] = new Multimedia(idMedia, media_type, order, memory, m);
                    }

                    JSONArray tagsObject = (JSONArray) memoryObject.getJSONArray("tags");
                    Tag[] tags = new Tag[tagsObject.length()];

                    for(int j=0; j<tagsObject.length(); j++) {
                        JSONObject obj = (JSONObject) tagsObject.get(j);

                        int idTag = obj.getInt("id");
                        String tag = obj.getString("tag");
                        int memory = obj.getInt("memory");

                        tags[j] = new Tag(idTag, tag, memory);
                    }

                    //Memory mem = new Memory(id, owner, posting_time, title, texts, multimedia, tags, numlikes);
                    //memoryList.add(mem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            FeedMemoryFragment fragment = (FeedMemoryFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT);

            if(fragment != null) {
                fragment.updateMemories(memoryList);
            }

        }
    };

    private Response.Listener<JSONArray> getMemoryListListener2 = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<Memory> memoryList = new ArrayList<>();
            Log.v("aaaaaa", response.toString());

            for(int i=0; i<response.length(); i++) {
                try {
                    JSONObject memoryObject = (JSONObject) response.get(i);
                    int id = memoryObject.getInt("id");
                    String owner = memoryObject.getString("owner");
                    String posting_time = memoryObject.getString("posting_time");
                    String title = memoryObject.getString("title");
                    int numlikes = memoryObject.getInt("numlikes");

                    JSONArray textsObject = (JSONArray) memoryObject.getJSONArray("texts");
                    String[] texts = new String[textsObject.length()];

                    for(int j=0; j<textsObject.length(); j++) {
                        JSONObject t = (JSONObject) textsObject.get(j);
                        texts[j] = (String) t.getString("text");
                    }

                    JSONArray multimediaObject = (JSONArray) memoryObject.getJSONArray("multimedia");
                    Multimedia[] multimedia = new Multimedia[multimediaObject.length()];

                    for(int j=0; j<multimediaObject.length(); j++) {
                        JSONObject arrObject = (JSONObject)multimediaObject.get(j);

                        int idMedia = arrObject.getInt("id");
                        int media_type = arrObject.getInt("media_type");
                        int order = arrObject.getInt("order");
                        int memory = arrObject.getInt("memory");

                        JSONObject mediaObject = (JSONObject)arrObject.get("multimedia");
                        String media = mediaObject.getString("media");
                        Media m = new Media(media);

                        multimedia[j] = new Multimedia(idMedia, media_type, order, memory, m);
                    }

                    JSONArray tagsObject = (JSONArray) memoryObject.getJSONArray("tags");
                    Tag[] tags = new Tag[tagsObject.length()];

                    for(int j=0; j<tagsObject.length(); j++) {
                        JSONObject obj = (JSONObject) tagsObject.get(j);

                        int idTag = obj.getInt("id");
                        String tag = obj.getString("tag");
                        int memory = obj.getInt("memory");

                        tags[j] = new Tag(idTag, tag, memory);
                    }

                    //Memory mem = new Memory(id, owner, posting_time, title, texts, multimedia, tags, numlikes);
                    //memoryList.add(mem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);

            if(fragment != null) {
                fragment.updateList(memoryList);
            }

        }
    };

    private Response.Listener<JSONArray> getMemoryListListener3 = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<Memory> memoryList = new ArrayList<>();
            Log.v("aaaaaa", response.toString());

            for(int i=0; i<response.length(); i++) {
                try {
                    JSONObject memoryObject = (JSONObject) response.get(i);
                    int id = memoryObject.getInt("id");
                    String owner = memoryObject.getString("owner");
                    String posting_time = memoryObject.getString("posting_time");
                    String title = memoryObject.getString("title");
                    int numlikes = memoryObject.getInt("numlikes");

                    JSONArray textsObject = (JSONArray) memoryObject.getJSONArray("texts");
                    String[] texts = new String[textsObject.length()];

                    for(int j=0; j<textsObject.length(); j++) {
                        JSONObject t = (JSONObject) textsObject.get(j);
                        texts[j] = (String) t.getString("text");
                    }

                    JSONArray multimediaObject = (JSONArray) memoryObject.getJSONArray("multimedia");
                    Multimedia[] multimedia = new Multimedia[multimediaObject.length()];

                    for(int j=0; j<multimediaObject.length(); j++) {
                        JSONObject arrObject = (JSONObject)multimediaObject.get(j);

                        int idMedia = arrObject.getInt("id");
                        int media_type = arrObject.getInt("media_type");
                        int order = arrObject.getInt("order");
                        int memory = arrObject.getInt("memory");

                        JSONObject mediaObject = (JSONObject)arrObject.get("multimedia");
                        String media = mediaObject.getString("media");
                        Media m = new Media(media);

                        multimedia[j] = new Multimedia(idMedia, media_type, order, memory, m);
                    }

                    JSONArray tagsObject = (JSONArray) memoryObject.getJSONArray("tags");
                    Tag[] tags = new Tag[tagsObject.length()];

                    for(int j=0; j<tagsObject.length(); j++) {
                        JSONObject obj = (JSONObject) tagsObject.get(j);

                        int idTag = obj.getInt("id");
                        String tag = obj.getString("tag");
                        int memory = obj.getInt("memory");

                        tags[j] = new Tag(idTag, tag, memory);
                    }

                    //Memory mem = new Memory(id, owner, posting_time, title, texts, multimedia, tags, numlikes);
                    //memoryList.add(mem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RecommendationsFragment fragment = (RecommendationsFragment) getSupportFragmentManager().findFragmentByTag(TAG_RECOMMENDATIONS_FRAGMENT);

            if(fragment != null) {
                fragment.updateList(memoryList);
            }

        }
    };

    private Response.Listener<ArrayList<Memory>> getUserMemoryListListener = new Response.Listener<ArrayList<Memory>>() {
        @Override
        public void onResponse(ArrayList<Memory> response) {
            FeedMemoryFragment fragment = (FeedMemoryFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_MEMORY_FRAGMENT);
            if(fragment != null) {
                fragment.updateMemories(response);
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

    private Response.Listener<ApiResultProfile> getProfileListener = new Response.Listener<ApiResultProfile>() {
        @Override
        public void onResponse(ApiResultProfile response) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_PROFILE_FRAGMENT);
            if(fragment != null) {
                fragment.updateInfo(response.getUsername(), response.getFirst_name(), response.getLast_name(), response.getEmail());
            }
        }
    };

    private Response.ErrorListener getProfileErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
}
