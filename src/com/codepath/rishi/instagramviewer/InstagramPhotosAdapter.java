package com.codepath.rishi.instagramviewer;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take the data source at position
		// Get the data item
		InstagramPhoto photo = getItem(position);
		
		// Check if we are using a recycled view
		if (convertView == null){
			// false - donot attach it now.
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
			
		}
		
		// Lookup the subview within the templates.
		//TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		
		// Populate the subviews (textfield, imageview)
		//tvUserName.setText(photo.username + " -- ");
		//tvUserName.setTextSize(15);

		//tvLikes.setText(photo.likesCount + " likes");
		
		//tvCaption.setText(photo.caption);
		
		
		tvLikes.setText(Html.fromHtml("<font color=\"#8B0000\"><b> &hearts;</b></font><font color=\"#206199\"><b> " + photo.likesCount
                + " likes </b></font>"));
		
		tvCaption.setText(Html.fromHtml("<font color=\"#206199\"><b>" + photo.username
                + "" + "</b></font>" + " -- " + "<font color=\"#000000\">" + photo.caption + "</font>"));
		
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		
		// Reset the image from the recycled view
		imgPhoto.setImageResource(0);
		
		// Ask for the photo to be added to the imageview based on the photo imgurl
		// Send a network request to the url, download the image bytes, resizing & convert into bitmap, insert bitmap into the imageView
		// Using the picasso library
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		// Return the view for the data item
		return convertView;
		
	}

	// getView method (int position)
	// Default, takes the model (InstagramPhoto) toString()

}
