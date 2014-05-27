package org.segfault.foodme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDetailsFragment extends Fragment{

	RatingBar ratingBar;
	RatingBar customerRating;
	TextView review;
	TextView ratingNumber;
	TextView foodDescription;
	TextView leaveARating;
	ImageView foodImage;
	Button submitButton;
	
	int lastPosition = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.food_details, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
    	foodDescription = (TextView) getActivity().findViewById(R.id.food_description);
		foodImage = (ImageView)getActivity().findViewById(R.id.food_image);
		ratingNumber = (TextView) getActivity().findViewById(R.id.review_number);
    	ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
    	customerRating = (RatingBar) getActivity().findViewById(R.id.customerRating);
    	leaveARating = (TextView) getActivity().findViewById(R.id.leave_review);
    	submitButton = (Button) getActivity().findViewById(R.id.submit_button);
    	ratingBar.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    	submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
				
				dialogBuilder.setTitle("Submit Rating");
				dialogBuilder.setMessage("Are you sure you want to give this item this rating?");
				dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast ratingConfirm = Toast.makeText(getActivity().getApplicationContext(),"Thank you for your rating",Toast.LENGTH_SHORT);
						ratingConfirm.show();
						customerRating.setRating(0);
					}
				});
				dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						customerRating.setRating(0);
					}
				});
				AlertDialog submitDialog = dialogBuilder.create();
				submitDialog.show();
				
			}
    	});
    	ratingBar.setFocusable(false);
    	
        updateFoodDetails(lastPosition);
    }
    public void updateFoodDetails(int position)
    {

    	float exa = 3;
    	ratingBar.setRating(exa);
    	ratingNumber.setText("Rating Number : " + exa);
    	foodImage.setImageResource(R.drawable.dessert);
		if(position == -1)
		{
			foodDescription.setVisibility(View.GONE);
    		foodImage.setVisibility(View.GONE);			
    		customerRating.setVisibility(View.GONE);
    		leaveARating.setVisibility(View.GONE);
    		submitButton.setVisibility(View.GONE);
			getActivity().findViewById(R.id.review_number).setVisibility(View.GONE);
			getActivity().findViewById(R.id.ratingBar).setVisibility(View.GONE);
		}
		else
		{
			foodDescription.setVisibility(View.VISIBLE);
    		foodImage.setVisibility(View.VISIBLE);
    		customerRating.setVisibility(View.VISIBLE);
    		leaveARating.setVisibility(View.VISIBLE);
    		submitButton.setVisibility(View.VISIBLE);
    		customerRating.setRating(0);
			getActivity().findViewById(R.id.review_number).setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.ratingBar).setVisibility(View.VISIBLE);
			switch(position)
    		{
	    	case 1:
	    		foodDescription.setText(R.string.dummy1);
	    		break;
	    	case 2:
	    		foodDescription.setText(R.string.dummy2);
	    		break;
	    	case 3:
	    		foodDescription.setText(R.string.dummy3);
	    		break;
	    	}
		}
    	lastPosition = position;
    }
}
