package org.segfault.foodme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDetailsFragment extends Fragment{
	
	ContentResolverMenuItem foodDetails;
	ContentResolverReview foodReview;
	RatingBar ratingBar;
	RatingBar customerRating;
	TextView review;
	TextView ratingNumber;
	TextView foodDescription;
	TextView leaveARating;
	TextView price;
	ImageView foodImage;
	Button submitButton;
	Button addButton;
	short quantity = 0;
	
	int lastMenuItemIndex = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.food_details, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        foodDetails = ContentResolverMenuItem.getInstance(this.getActivity());
        foodReview = ContentResolverReview.getInstance(this.getActivity());
    	foodDescription = (TextView) getActivity().findViewById(R.id.food_description);
		foodImage = (ImageView)getActivity().findViewById(R.id.food_image);
		ratingNumber = (TextView) getActivity().findViewById(R.id.review_number);
    	ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
    	customerRating = (RatingBar) getActivity().findViewById(R.id.customerRating);
    	leaveARating = (TextView) getActivity().findViewById(R.id.leave_review);
    	submitButton = (Button) getActivity().findViewById(R.id.submit_button);
    	addButton = (Button) getActivity().findViewById(R.id.add_button);
    	price = (TextView) getActivity().findViewById(R.id.price);
    	
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
						Toast ratingConfirm = Toast.makeText(getActivity().getApplicationContext(),"Thank you for rating this item",Toast.LENGTH_SHORT);
						Review customerReview =  new Review(0, foodDetails.getMenuItemId(lastMenuItemIndex), "", (short)customerRating.getRating(), 
								"", "");
						new SendReview().execute(customerReview);
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
				submitDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
				submitDialog.show();
				
			}
    	});
    	
    	
    	addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
			       getActivity().getWindow().getDecorView().setSystemUiVisibility(
				    		View.SYSTEM_UI_FLAG_FULLSCREEN
			                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
				dialogBuilder.setTitle("Quantity");
				dialogBuilder.setSingleChoiceItems( R.array.quantity_number, -1, new DialogInterface.OnClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int which) {
		                    switch(which)
		                    {
		                        case 0:
		                        	quantity = 1;
		                            break;
		                        case 1:
		                        	quantity = 2;
		                            break;
		                        case 2:
		                        	quantity = 3;           
		                            break;
		                        case 3:
		                        	quantity = 4;           
		                            break;
		                        case 4:
		                        	quantity = 5;           
		                            break;
		                        default:
		                        	quantity = 0;
		                        	break;
		                    }
		               }
		           });
				
				dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(quantity != 0)
						{
							AlertDialog.Builder test1 = new AlertDialog.Builder(getActivity());
						       getActivity().getWindow().getDecorView().setSystemUiVisibility(
							    		View.SYSTEM_UI_FLAG_FULLSCREEN
						                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
						       test1.setTitle("Would you like to add a note?");
						       EditText input = new EditText(getActivity());
						       input.setHint("Add a note here");
						       test1.setView(input);
						       input.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
						       test1.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if(quantity != 0)
										{
					            	   	   String confirmString = (quantity) + " " + foodDetails.getName(lastMenuItemIndex) + "(s) added to cart";
					                       Toast addConfirm = Toast.makeText(getActivity().getApplicationContext(),confirmString,Toast.LENGTH_LONG);
					                       addConfirm.show();
					                       TicketItem test = new TicketItem(SplashScreenActivity.ticket.ticketId, foodDetails.getMenuItemId(lastMenuItemIndex), 
					                    		   lastMenuItemIndex, quantity,"", (short) 0, foodDetails.getName(lastMenuItemIndex), foodDetails.getPrice(lastMenuItemIndex));
					                       SplashScreenActivity.orders.add(test);
					                       quantity = 0;
										}
									}
								});
						       test1.setNegativeButton("No", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if(quantity != 0)
										{
					            	   	   String confirmString = (quantity) + " " + foodDetails.getName(lastMenuItemIndex) + "(s) added to cart";
					                       Toast addConfirm = Toast.makeText(getActivity().getApplicationContext(),confirmString,Toast.LENGTH_LONG);
					                       addConfirm.show();
					                       TicketItem test = new TicketItem(SplashScreenActivity.ticket.ticketId, foodDetails.getMenuItemId(lastMenuItemIndex), 
					                    		   lastMenuItemIndex, quantity,"", (short) 0, foodDetails.getName(lastMenuItemIndex), foodDetails.getPrice(lastMenuItemIndex));
					                       SplashScreenActivity.orders.add(test);
					                       quantity = 0;
										}
									}
								});
						       AlertDialog submitDialog = test1.create();
								submitDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
								submitDialog.show();
								submitDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
						}
						else
						{
							Toast notAdded = Toast.makeText(getActivity().getApplicationContext(),"Please select a quantity",Toast.LENGTH_SHORT);
							notAdded.show();
							quantity = 0;
						}
					}
				});
				dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
	                       Toast notAdded = Toast.makeText(getActivity().getApplicationContext(),"Item NOT added to cart",Toast.LENGTH_SHORT);
	                       notAdded.show();
	                       quantity = 0;
					}
				});
				AlertDialog submitDialog = dialogBuilder.create();
				submitDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
				submitDialog.show();
				
			}
    	});
    	ratingBar.setFocusable(false);
    	
        updateFoodDetails(lastMenuItemIndex);
    }
    public void updateFoodDetails(int menuItemIndex)
    {
    	foodImage.setImageResource(R.drawable.dessert);
		if(menuItemIndex == -1)
		{
			foodDescription.setVisibility(View.GONE);
    		foodImage.setVisibility(View.GONE);			
    		customerRating.setVisibility(View.GONE);
    		leaveARating.setVisibility(View.GONE);
    		submitButton.setVisibility(View.GONE);
    		addButton.setVisibility(View.GONE);
    		ratingNumber.setVisibility(View.GONE);
    		ratingBar.setVisibility(View.GONE);
    		price.setVisibility(View.GONE);
		}
		else
		{
			foodDescription.setVisibility(View.VISIBLE);
    		foodImage.setVisibility(View.VISIBLE);
    		customerRating.setVisibility(View.VISIBLE);
    		leaveARating.setVisibility(View.VISIBLE);
    		submitButton.setVisibility(View.VISIBLE);
    		addButton.setVisibility(View.VISIBLE);
    		customerRating.setRating(0);
    		ratingNumber.setVisibility(View.VISIBLE);
    		ratingBar.setVisibility(View.VISIBLE);
    		price.setVisibility(View.VISIBLE);
			foodDescription.setText(foodDetails.getDescription(menuItemIndex));
			int menuItemId = foodDetails.getMenuItemId(menuItemIndex);
			float rating = (float)foodReview.getAvgRating(menuItemId);
			if(rating != -1.0 )
			{
				ratingBar.setRating(rating);
	    		ratingNumber.setText("\nRating Number : " + rating);
			}
			else
			{
				ratingBar.setRating(0);
				ratingNumber.setText("\nNo rating available");
			}
			price.setText("Price: $" +  foodDetails.getPrice(menuItemIndex));
		}
		lastMenuItemIndex = menuItemIndex;
    }
}
