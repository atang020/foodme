package org.segfault.foodme;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodItemFragment extends ListFragment{
	public static final String ARG_SUBCATEGORY_NUMBER = "subcategory_number";
	
	onFoodItemSelectedListener callback;
    public interface onFoodItemSelectedListener {
        public void onFoodItemSelected(int position);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, getResources().getStringArray(R.array.test_fragments)));
        
    }
    
    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.fooddetails_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
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
