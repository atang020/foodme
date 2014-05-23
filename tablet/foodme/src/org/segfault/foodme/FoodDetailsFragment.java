package org.segfault.foodme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FoodDetailsFragment extends Fragment{

	RatingBar ratingBar;
	TextView review;
	int lastPosition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.food_details, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();

        updateFoodDetails(lastPosition);
    }
    public void updateFoodDetails(int position)
    {
		final TextView tab1 = (TextView) getActivity().findViewById(R.id.food_description);
        final ImageView im1 = (ImageView)getActivity().findViewById(R.id.food_image);
        im1.setImageResource(R.drawable.dessert);
		System.out.println(position);
    	switch(position)
    	{
    	case 1:
    		tab1.setText(R.string.dummy1);
    		break;
    	case 2:
    		tab1.setText(R.string.dummy2);
    		break;
    	case 3:
    		tab1.setText(R.string.dummy3);
    		break;
    	}
    	lastPosition = position;
    }
}
