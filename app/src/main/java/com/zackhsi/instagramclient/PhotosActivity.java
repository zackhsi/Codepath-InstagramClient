package com.zackhsi.instagramclient;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "ef097a910ab745408953d9c3f32ad87d";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    AsyncHttpClient client;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        client = new AsyncHttpClient();
        photos = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        configureSwipeContainer();
        fetchPopularPhotos();
    }

    private void configureSwipeContainer() {
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                aPhotos.clear();

                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject photoJSON = data.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.createdAt = photoJSON.getInt("created_time");
                        photo.userImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
