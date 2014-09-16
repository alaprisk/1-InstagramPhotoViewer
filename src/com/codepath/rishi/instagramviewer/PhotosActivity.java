package com.codepath.rishi.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotosActivity extends Activity {
	
	public static final String CLIENT_ID = "f92c8c29937744a98f432f237e712d82";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        
        fetchPopularPhotos();
        
        
    }


    private void fetchPopularPhotos() {
    	
    	photos = new ArrayList<InstagramPhoto>();
    	
    	//Create adapter bind it to the data in arraylist   	
    	aPhotos = new InstagramPhotosAdapter(this, photos);
    	
    	//Populate data into listview. 	
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	
    	//Setting adapter to listview.
    	lvPhotos.setAdapter(aPhotos);
    	
    	
    	//https://api.instagram.com/v1/media/popular?client_id=f92c8c29937744a98f432f237e712d82
    	// { "data" => [x] => "images" => "standard_resolution" => "url" }
    	
    	// Setup popular url endpoint
    	
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	
    	// Create the network client
    	
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	// Trigger the network request
    	
    	client.get(popularUrl, new JsonHttpResponseHandler(){
    		// define success and failure callbacks
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,
    				JSONObject response) {
    			// fire once we get a response.
    			//response is == popular photos json
    			
    			// username, caption, image_url, height, likes_count

    	    	// { "data" => [x] => "images" => "standard_resolution" => "url" }
    	    	// { "data" => [x] => "images" => "standard_resolution" => "height" }
    	    	// { "data" => [x] => "user" => "username" }
    	    	// { "data" => [x] => "caption" => "text" }
		
    			//Log.i("INFO",response.toString());
    		
    			JSONArray photosJSON = null;
    			
    			try {
    				
    				photos.clear();
					photosJSON = response.getJSONArray("data");
    				
					
					Log.i("INFO"," " + photosJSON.length());
					
					for(int i = 0; i < photosJSON.length(); i++){
						Log.i("INFO"," " + i);

						JSONObject photoJSON = photosJSON.getJSONObject(i);
						
						InstagramPhoto photo = new InstagramPhoto();
						photo.username = photoJSON.getJSONObject("user").getString("username");
						
						if(!photoJSON.isNull("caption")) {
							photo.caption = photoJSON.getJSONObject("caption").getString("text");
						}
						
						photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
						
						
						photos.add(photo);
					
						
					}
    				aPhotos.notifyDataSetChanged();
    				
				} catch (JSONException e) {

					e.printStackTrace();
				}
    			
    			
    			
    		}
    		 @Override
    		public void onFailure(int statusCode, Header[] headers,
    				Throwable throwable, JSONObject errorResponse) {
					Log.i("INFO","Failure seen");
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    		}
    		
    		
    		
    	});
    	
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
