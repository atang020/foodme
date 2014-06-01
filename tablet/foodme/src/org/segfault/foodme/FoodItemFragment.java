package org.segfault.foodme;

import java.util.ArrayList;

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
	ContentResolverMenuItem foodItemNames ;
	String[] names = {};

	
	onFoodItemSelectedListener callback;
    public interface onFoodItemSelectedListener {
        public void onFoodItemSelected(int position);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	foodItemNames = ContentResolverMenuItem.getInstance(this.getActivity());
        if (this.getArguments() != null) {
        	Bundle test = this.getArguments();
        	int subcategoryID = test.getInt(ARG_SUBCATEGORY_NUMBER);
        	names = foodItemNames.getItemsBySubcategory(subcategoryID);
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

        callback = (onFoodItemSelectedListener) activity;
    }
    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
    	//System.out.println(position);
    	int menuItemIndex = foodItemNames.getIndexByName(names[position]);
        callback.onFoodItemSelected(menuItemIndex);
        getListView().setItemChecked(position, true);
    }
}
