package com.codepath.rishi.instagramviewer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
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
		TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
		TextView tvTimePassed = (TextView) convertView.findViewById(R.id.tvTimePassed);
		
		TextView tvCommentsTag = (TextView) convertView.findViewById(R.id.tvCommentsTag);
		TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
		
		CircularImageView imgProfilePic = (CircularImageView) convertView.findViewById(R.id.imgProfilePic);
		//imgProfilePic.setBorderColor(getResources().getColor(R.color.Black));
		
		imgProfilePic.setBorderColor(Color.parseColor("#FAAC58"));
		imgProfilePic.setBorderWidth(8);
		// Populate the subviews (textfield, imageview)
		//tvUserName.setText(photo.username + " -- ");
		//tvUserName.setTextSize(15);

		//tvLikes.setText(photo.likesCount + " likes");
		
		//tvCaption.setText(photo.caption);
		
		
		tvLikes.setText(Html.fromHtml("<font color=\"#8B0000\"><b> &hearts;</b></font><font color=\"#206199\"><b> " + photo.likesCount
                + " likes </b></font>"));
		
		if(!photo.caption.isEmpty()) {
			tvCaption.setText(Html.fromHtml("<font face=\"Comic Sans MS\" color=\"#206199\"><b>" + photo.username
                + " " + "</b></font><font color=\"#000000\">" + photo.caption + "</font>"));
		}
		else {
			tvCaption.setText(Html.fromHtml("<font face=\"Comic Sans MS\" color=\"#206199\"><b>" + photo.username + "</b></font>"));
		}
		tvUser.setText(Html.fromHtml("<font face=\"Comic Sans MS\" color=\"#206199\"><b>" + photo.username + "</b></font>"));
		

        Calendar timePassed = Calendar.getInstance();
        timePassed.setTimeInMillis(Long.parseLong(photo.time));
        Date submittedTime = timePassed.getTime();
        long diff_Min = (new Date(System.currentTimeMillis()).getTime() / 1000) - submittedTime.getTime();

        long diff_Hr = TimeUnit.SECONDS.toHours(diff_Min);

        tvTimePassed.setText(diff_Hr+"h");
		
		
        tvCommentsTag.setText(photo.commentsTag);
        tvComments.setText(Html.fromHtml("<font face=\"Comic Sans MS\" color=\"#206199\"><b>" + photo.comment1 + " </b></font>" 
        								  + photo.comment1_text + "<br>"
        								  + "<font face=\"Comic Sans MS\" color=\"#206199\"><b>" + photo.comment2 + " </b></font>"
        								  + photo.comment2_text 
        								  + "<br>"));
		
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		
		// Reset the image from the recycled view
		imgPhoto.setImageResource(0);
		
		// Ask for the photo to be added to the imageview based on the photo imgurl
		// Send a network request to the url, download the image bytes, resizing & convert into bitmap, insert bitmap into the imageView
		// Using the picasso library
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		Picasso.with(getContext()).load(photo.profilePicUrl).into(imgProfilePic);

		// Return the view for the data item
		return convertView;
		
	}

	// getView method (int position)
	// Default, takes the model (InstagramPhoto) toString()

}
