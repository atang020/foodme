package org.segfault.foodme;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodItemFragment extends ListFragment{
	public static final String ARG_SUBCATEGORY_NUMBER = "subcategory_number";
	ArrayAdapter<String> adapter;
	String[] names = {};
	
	onFoodItemSelectedListener callback;
    public interface onFoodItemSelectedListener {
        public void onFoodItemSelected(int position);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (this.getArguments() != null) {
        	Bundle test = this.getArguments();
        	int i = test.getInt(ARG_SUBCATEGORY_NUMBER);
        	
        	String activityName = this.getActivity().getClass().getSimpleName();
        	switch(activityName)
        	{
        	case "DrinkActivity": 
        		names = getResources().getStringArray(R.array.test_names);
        		break;
        	case "EntreeActivity":
        		names = getResources().getStringArray(R.array.test_fragments);
        		break;
        	case "DessertActivity":
        		names = getResources().getStringArray(R.array.test_fragments);
        		break;
        	case "AppetizerActivity":
        		names = getResources().getStringArray(R.array.test_names);
        		break;
        	}
        }
    	adapter = (new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_activated_1 , names ));
    	setListAdapter(adapter);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

           
    }
    
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        callback = (onFoodItemSelectedListener) activity;
    }
    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
    	//System.out.println(position);
        callback.onFoodItemSelected(position);
        
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
}
