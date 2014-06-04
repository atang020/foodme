package org.segfault.foodme;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	WebView foodImage;
	Button submitButton;
	Button addButton;
	Button getReview;
	BigDecimal itemPrice;
	short quantity = 0;
	ProgressDialog dialog;
	BroadcastReceiver syncReceiver;
	
	int lastMenuItemIndex = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.food_details, container, false);
    }
    
    @Override
    public void onStart() {
    	//stuff
        super.onStart();
        foodDetails = ContentResolverMenuItem.getInstance(this.getActivity());
        foodReview = ContentResolverReview.getInstance(this.getActivity());
    	foodDescription = (TextView) getActivity().findViewById(R.id.food_description);
		foodImage = (WebView)getActivity().findViewById(R.id.food_image);
		foodImage.setBackgroundColor(Color.TRANSPARENT);
		ratingNumber = (TextView) getActivity().findViewById(R.id.review_number);
    	ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
    	customerRating = (RatingBar) getActivity().findViewById(R.id.customerRating);
    	leaveARating = (TextView) getActivity().findViewById(R.id.leave_review);
    	submitButton = (Button) getActivity().findViewById(R.id.submit_button);
    	addButton = (Button) getActivity().findViewById(R.id.add_button);
    	price = (TextView) getActivity().findViewById(R.id.price);
    	getReview = (Button) getActivity().findViewById(R.id.get_review);
    	dialog = new ProgressDialog(getActivity());
    	syncReceiver = new SyncReceiver(this);
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
				dialogBuilder.setMessage("Are you sure you want to give this item a rating of " + customerRating.getRating() + "?");
				 
				
				dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(customerRating.getRating() != 0)
						{
						AlertDialog.Builder test1 = new AlertDialog.Builder(getActivity());
					       getActivity().getWindow().getDecorView().setSystemUiVisibility(
						    		View.SYSTEM_UI_FLAG_FULLSCREEN
					                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
					       test1.setTitle("Would you like to add a review with your rating?");
					       final EditText review = new EditText(getActivity());
					       review.setHint("Add review here");
					       review.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
					       
					       final EditText name = new EditText(getActivity());
					       name.setHint("Your name");
					       name.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);

					       LinearLayout linearLayout = new LinearLayout(getActivity());
					        linearLayout.setOrientation(LinearLayout.VERTICAL);
					        linearLayout.addView(name);
					        linearLayout.addView(review);
					       test1.setView(linearLayout);
					       test1.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if(name.getText().toString().length() > 0 && review.getText().toString().length() > 0)
									{
										Toast ratingConfirm = Toast.makeText(getActivity().getApplicationContext(),"Thank you for rating this item",Toast.LENGTH_SHORT);
										ratingConfirm.show();
										Review customerReview =  new Review(0, foodDetails.getMenuItemId(lastMenuItemIndex), name.getText().toString(), 
												(short)customerRating.getRating(), review.getText().toString(), "");
										new SendReview().execute(customerReview);
										customerRating.setRating(0);
					                       doSync();

									}
									else
									{
										Toast empty = Toast.makeText(getActivity().getApplicationContext(),"Name and/or review empty",Toast.LENGTH_SHORT);
										empty.show();
										customerRating.setRating(0);
										
									}

								}
							});
					       test1.setNeutralButton("No", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Toast ratingConfirm = Toast.makeText(getActivity().getApplicationContext(),"Thank you for rating this item",Toast.LENGTH_SHORT);
									ratingConfirm.show();
								
									Review customerReview =  new Review(0, foodDetails.getMenuItemId(lastMenuItemIndex), "", (short)customerRating.getRating(), 
											"", "");
									new SendReview().execute(customerReview);
									customerRating.setRating(0);
				                       doSync();
								}
							});
					       
					       test1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
				                       Toast notAdded = Toast.makeText(getActivity().getApplicationContext(),"Item NOT rated",Toast.LENGTH_SHORT);
				                       notAdded.show();
										customerRating.setRating(0);
								}
							});
					       AlertDialog submitDialog = test1.create();
							submitDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
							submitDialog.show();
							submitDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
						}
						else
						{
							Toast notAdded = Toast.makeText(getActivity().getApplicationContext(),"Please choose a rating",Toast.LENGTH_SHORT);
							notAdded.show();
						}
					}
				});
				
				dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
	                    Toast notAdded = Toast.makeText(getActivity().getApplicationContext(),"Item NOT rated",Toast.LENGTH_SHORT);
	                    notAdded.show();
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
						       final EditText input = new EditText(getActivity());
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
					                    		   lastMenuItemIndex, quantity,input.getText().toString(), (short) 0, foodDetails.getName(lastMenuItemIndex), foodDetails.getPrice(lastMenuItemIndex));
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
										   String noNotes = "(no notes)";
					            	   	   String confirmString = (quantity) + " " + foodDetails.getName(lastMenuItemIndex) + "(s) added to cart";
					                       Toast addConfirm = Toast.makeText(getActivity().getApplicationContext(),confirmString,Toast.LENGTH_LONG);
					                       addConfirm.show();
					                       TicketItem test = new TicketItem(SplashScreenActivity.ticket.ticketId, foodDetails.getMenuItemId(lastMenuItemIndex), 
					                    		   lastMenuItemIndex, quantity,noNotes, (short) 0, foodDetails.getName(lastMenuItemIndex), foodDetails.getPrice(lastMenuItemIndex));
					                       SplashScreenActivity.orders.add(test);
					                       quantity = 0;
										}
									}
								});
						       AlertDialog submitDialog = test1.create();
								submitDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
								submitDialog.show();
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
    	
    	getReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
				
				
				dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
					
				});

				
				ArrayList<Review> reviews = foodReview.getReviewByMenuItemId(foodDetails.getMenuItemId(lastMenuItemIndex));
				
				ArrayList<String>customerRatings = new ArrayList<String>();
				String customerName;
				String customerReview;
				Short customerRating;
				if(reviews.size() > 0)
				{
					for(int i = 0; i < reviews.size(); i++)
					{
						customerName = reviews.get(i).getReviewer();
						customerReview = reviews.get(i).getReviewText();
						customerRating = reviews.get(i).getRating();
						if(customerRating != 0)
						{
							if(customerName.length() > 0 && customerReview.length()>0)
							{
								customerRatings.add("Name: " + customerName + "\nReview: " + customerReview + "\nRating: " + customerRating);
							}
							else
							{
								customerRatings.add("Name: Anonymous \nReview: Not available \nRating: " + customerRating);
							}
						}
					}
				}
				else
				{
					customerRatings.add("No customer reviews available");
				}
				ArrayAdapter<String> hi = new ArrayAdapter<String>(getActivity(),
		                android.R.layout.simple_list_item_1,
		                customerRatings);
				dialogBuilder.setTitle("Customer Ratings");
				dialogBuilder.setAdapter(hi, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
					
				});
				AlertDialog dialog = dialogBuilder.create();
				dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

				dialog.show();
			}
    		
    	});
    	ratingBar.setFocusable(false);
    	
        updateFoodDetails(lastMenuItemIndex);
    }
    public void updateFoodDetails(int menuItemIndex)
    {
    	
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
    		getReview.setVisibility(View.GONE);
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
    		getReview.setVisibility(View.VISIBLE);
    		foodImage.loadUrl(foodDetails.getPicturePath(menuItemIndex));
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
			itemPrice = new BigDecimal(foodDetails.getPrice(menuItemIndex));
			itemPrice = itemPrice.setScale(2, RoundingMode.CEILING);
			price.setText("Price: $" +  itemPrice.toString());
		}
		lastMenuItemIndex = menuItemIndex;
    }
    
    public void doSync()
    {
	    dialog = ProgressDialog.show(getActivity(), "Updating Ratings", "Please wait", true, false, null);
	    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
	    
		int titleId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
		int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
		TextView title = (TextView) dialog.findViewById(titleId);
		title.setTextColor(getResources().getColor(R.color.apptheme_color));
		View dividerBar = dialog.findViewById(dividerId);
		
		dividerBar.setBackgroundColor(getResources().getColor(R.color.apptheme_color));
    	Bundle bundle = new Bundle();
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
	    bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
	    ContentResolver.requestSync (SplashScreenActivity.myAccount, "org.segfault.foodme.tabdbprovider", bundle);        
    }
    

    
	public void onResume() {
	    super.onResume();

	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction(SyncAdapter.START_SYNC);
	    intentFilter.addAction(SyncAdapter.FINISH_SYNC);
	    getActivity().getApplicationContext().registerReceiver(syncReceiver, intentFilter);  
	}
	
	
	private class SyncReceiver extends BroadcastReceiver {

	    private FoodDetailsFragment foodDetails; 

	    public SyncReceiver(FoodDetailsFragment frag) {
	    	foodDetails = frag;
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (intent.getAction().equals(SyncAdapter.FINISH_SYNC)) {
	    		foodDetails.updateThings();
	        }
	    }
	}
	
	public void updateThings()
	{
	    foodReview.update();
	    foodDetails.update();
	    int menuItemId = foodDetails.getMenuItemId(lastMenuItemIndex);
		float rating = (float)foodReview.getAvgRating(menuItemId);
		if(rating != -1.0 )
		{
			System.out.println(rating);
			ratingBar.setRating(rating);
			ratingNumber.setText("\nRating Number : " + rating);
		}
		else
		{
			ratingBar.setRating(0);
			ratingNumber.setText("\nNo rating available");
		}
		dialog.dismiss();
	}
}
