package org.segfault.foodme;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrderActivity extends Activity implements ActionBar.TabListener,
		OnItemClickListener {

	ListView list;
	TextView subtotal;
	TextView total;
	Button checkout;
	private String subtotalVal;
	private String[] subcategoryNames;
	private DrawerLayout subcategoryLayout;
	private ListView subcategoryList;
	private AlertDialog.Builder dialogBuilder;
	private ViewPager mViewPager;
	ArrayList<TicketItem> item = SplashScreenActivity.orders;
	ArrayList<TicketItem> sentItems = new ArrayList<TicketItem>();
	ArrayAdapter<String> adapter;
	private AlertDialog.Builder dialogBuild;
	private AlertDialog.Builder dialogBuild1;
	Button button;
	Button restButton;
	short trueCheck = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the view from activity_main.xml
		setContentView(R.layout.activity_my_order);
		View decorView = getWindow().getDecorView();

		int mUIFlag = View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		decorView.setSystemUiVisibility(mUIFlag);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Display tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("Home")
				.setTabListener(this), false);
		actionBar.addTab(
				actionBar.newTab().setText("Drinks").setTabListener(this),
				false);
		actionBar.addTab(actionBar.newTab().setText("Appetizers")
				.setTabListener(this), false);
		actionBar.addTab(
				actionBar.newTab().setText("Entrees").setTabListener(this),
				false);
		actionBar.addTab(
				actionBar.newTab().setText("Desserts").setTabListener(this),
				false);
		actionBar.addTab(
				actionBar.newTab().setText("My Order").setTabListener(this),
				true);
		actionBar.addTab(actionBar.newTab().setText("Call Waiter")
				.setTabListener(this), false);

		mViewPager = (ViewPager) findViewById(R.id.pager);

		// Set the adapter for the list view
		button = (Button) findViewById(R.id.button1);
		list = (ListView) findViewById(R.id.listView1);
		subtotal = (TextView) findViewById(R.id.textView6);
		total = (TextView) findViewById(R.id.textView1);
		restButton = (Button) findViewById(R.id.button2);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice);
		subtotalVal = subTotal();
		// Display items that were added to order
		for (int i = 0; i < item.size(); i++) {
			adapter.add(item.get(i).toString());
		}

		list.setAdapter(adapter);
		subtotal.setText(subtotalVal);
		total.setText(totalString(SplashScreenActivity.total));
		// "Send To Kitchen"
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				confirmation();

				// TODO Sync with database again here
			}
		});

		// "Final Checkout"
		restButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final_checkout();
			}
		});

		// Array that determines if an item is checked or not
		// checkArray = new int[item.size()];

		// Methods for items in the list
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemSelected(AdapterView parentView, View childView,
					int position, long id) {
				// TODO
			}

			public void onNothingSelected(AdapterView parentView) {
				// Do nothing
			}

			// Clicking on an item prompts an edit option to display
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				editOrder(position);
				adapter.notifyDataSetChanged();
			}
		});

		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			// Clicking on an item prompts an edit option to display
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				toggleStatus(position);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
	}

	private void toggleStatus(final int position) {
		dialogBuilder = new AlertDialog.Builder(this);

		dialogBuilder.setTitle("Change Send Status");
		dialogBuilder.setMessage("Would you like to change status of item?");
		dialogBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String s1 = item.get(position).getCheckStatus();
						short tempStatus = 2;

						if (s1 == "Ready to send!")
							tempStatus = 1;

						else if (s1 == "Not ready to send.")
							tempStatus = 0;

						TicketItem temp = new TicketItem(item.get(position)
								.getOrderId(), item.get(position)
								.getMenuItemId(), item.get(position)
								.getMenuItemIndex(), item.get(position)
								.getQuantity(), item.get(position).getNotes(),
								item.get(position).getKitchenStatus(), item
										.get(position).getMenuItemName(), item
										.get(position).getPrice());

						if (tempStatus == 1) {
							temp.setCheckStatusNotReady();
						}

						else if (tempStatus == 0) {
							temp.setCheckStatusReady();
						}

						adapter.remove(item.get(position).toString());
						item.remove(position);
						item.add(temp);
						adapter.add(item.get((item.size() - 1)).toString());

						Toast toggleConfirm = Toast.makeText(
								getApplicationContext(),
								"Item status has been changed.",
								Toast.LENGTH_SHORT);
						toggleConfirm.show();
					}
				});

		dialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast waiterConfirm = Toast.makeText(
								getApplicationContext(),
								"Item status has NOT been changed..",
								Toast.LENGTH_SHORT);
						new CallWaiter().execute();
						waiterConfirm.show();
					}
				});

		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		dialog.show();
	}

	// "Final Checkout"
	private void final_checkout() {
		dialogBuild1 = new AlertDialog.Builder(this);
		dialogBuild1.setTitle("Check Please");
		dialogBuild1.setMessage("Are you sure you want your check?"
				+ totalString(SplashScreenActivity.total));
		dialogBuild1.setNegativeButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						for (int i = 0; i < item.size(); i++) {
							short a = 1;
							item.get(i).setKitchenStatus(a);
						}

						for (int i = 0; i < item.size(); i++) {
							new SendTicketItems().execute(item.get(i));
						}

						Toast makeText = Toast
								.makeText(
										getApplicationContext(),
										"Successfully checked out. A waiter will be with you shortly.",
										Toast.LENGTH_SHORT);
						makeText.show();
						item = null;
						adapter = null;

						// Switch to Splash Screen
						Intent homeIntent = new Intent(MyOrderActivity.this,
								SplashScreenActivity.class);
						homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

						startActivity(homeIntent);
					}
				});

		dialogBuild1.setPositiveButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast makeText = Toast.makeText(
								getApplicationContext(),
								"Check will not be sent, yet.",
								Toast.LENGTH_SHORT);
						makeText.show();
					}
				});

		AlertDialog dialogConfirm = dialogBuild1.create();
		dialogConfirm.show();
	}

	// "Send To Kitchen"
	private void confirmation() {
		dialogBuild1 = new AlertDialog.Builder(this);
		dialogBuild1.setTitle("Send To Kitchen");
		dialogBuild1.setMessage("Are you sure?");

		dialogBuild1.setNegativeButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String readyString = "Ready to send!";
						String inString;
						short showMessage = 0;

						// Set kitchen status for items that are ready to send
						for (int i = 0; i < item.size(); i++) {

							inString = item.get(i).getCheckStatus();

							if (readyString.compareTo(inString) == 0) {
								short a = 1;
								item.get(i).setKitchenStatus(a);
							}
						}

						// Executed send for items that are ready to send
						for (int i = 0; i < item.size(); i++) {
							inString = item.get(i).getCheckStatus();

							if (readyString.compareTo(inString) == 0) {
								new SendTicketItems().execute(item.get(i));
							}
						}

						int itemSize = item.size();

						// Variable keeps track of orders that have been sent
						int incr = 0;

						// Clear sent items from ListView
						for (int i = 0; i < itemSize; i++) {
							inString = item.get(incr).getCheckStatus();

							if (readyString.compareTo(inString) == 0) {
								adapter.remove(item.get(incr).toString());
								totalValue(item.get(incr).getQuantity(), item
										.get(incr).getPrice());
								adapter.add(item.get(incr).toString2());
								item.remove(incr);
								subtotal.setText(subTotal());
								showMessage = 1;
							}

							else
								incr++;
						}
						total.setText(totalString(SplashScreenActivity.total));
						if (showMessage == 1) {
							Toast makeText = Toast.makeText(
									getApplicationContext(),
									"Order has been sent to the kitchen.",
									Toast.LENGTH_SHORT);
							makeText.show();
						}
					}
				});

		dialogBuild1.setPositiveButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast makeText = Toast.makeText(
								getApplicationContext(),
								"Order has NOT been sent to kitchen.",
								Toast.LENGTH_SHORT);
						makeText.show();
					}
				});

		AlertDialog dialogConfirm = dialogBuild1.create();
		dialogConfirm.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	// This is prompted when user clicks on an item in the ListView
	private void editOrder(final int position) {
		dialogBuild = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		input.setHint("Notes");
		final EditText quantity = new EditText(this);
		quantity.setHint("Quantity");
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(input);
		layout.addView(quantity);
		dialogBuild.setTitle("Edit Item");
		dialogBuild.setView(layout);
		dialogBuild.setNegativeButton("Done",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						short quantityNum = item.get(position).getQuantity();
						String notesVal = item.get(position).getNotes();
						String value = input.getText().toString();
						String quantityVal = quantity.getText().toString();
						String blankStr = "";
						short eligibleUpdate = 1;
						short quantityIsEdited = 1;
						short noteIsEdited = 0;

						// Change notes if user inputed something
						if (value.compareTo(blankStr) != 0) {
							notesVal = value;
							noteIsEdited = 1;
						}

						// Ensure quantity is positive
						if (quantityVal.startsWith("-")) {
							Toast negInput = Toast.makeText(
									getApplicationContext(),
									"Please enter a positive integer.",
									Toast.LENGTH_SHORT);
							negInput.show();
							quantityIsEdited = 0;
							eligibleUpdate = 0;
						}

						if (quantityVal.length() <= 0)
							quantityIsEdited = 0;

						// Check for valid quantity
						if ((quantityVal.length() > 0) && (eligibleUpdate == 1)) {
							System.out.println("REACH HERE");
							try {
								quantityNum = Short.parseShort(quantityVal);
							}

							catch (NumberFormatException e) {
								Toast makeText1 = Toast.makeText(
										getApplicationContext(),
										"Please enter a valid integer.",
										Toast.LENGTH_SHORT);
								makeText1.show();
								quantityNum = item.get(position).getQuantity();
								quantityIsEdited = 0;
							}
						}

						TicketItem temp = new TicketItem(item.get(position)
								.getOrderId(), item.get(position)
								.getMenuItemId(), item.get(position)
								.getMenuItemIndex(), quantityNum, notesVal,
								item.get(position).getKitchenStatus(), item
										.get(position).getMenuItemName(), item
										.get(position).getPrice());
						adapter.remove(item.get(position).toString());
						item.remove(position);
						SplashScreenActivity.orders.remove(position);
						item.add(temp);
						SplashScreenActivity.orders.add(temp);
						adapter.add(item.get((item.size() - 1)).toString());
						subtotalVal = subTotal();
						subtotal.setText(subtotalVal);

						if ((quantityIsEdited == 1) || (noteIsEdited == 1)) {
							Toast makeText = Toast
									.makeText(getApplicationContext(),
											"Item has been edited.",
											Toast.LENGTH_SHORT);
							makeText.show();
						}

						else {
							Toast makeText = Toast.makeText(
									getApplicationContext(),
									"Item has NOT been edited.",
									Toast.LENGTH_SHORT);
							makeText.show();
						}
					}
				});
		dialogBuild.setNeutralButton("Delete Item",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						adapter.remove(item.get(position).toString());
						item.remove(position);
						Toast makeText = Toast.makeText(
								getApplicationContext(),
								"Item has been deleted.", Toast.LENGTH_SHORT);
						makeText.show();
						subtotalVal = subTotal();
						subtotal.setText(subtotalVal);
					}
				});

		dialogBuild.setPositiveButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast makeText = Toast.makeText(
								getApplicationContext(),
								"Canceled editing order.", Toast.LENGTH_SHORT);
						makeText.show();
					}
				});

		AlertDialog dialogEditName = dialogBuild.create();
		dialogEditName.show();
	}

	public String subTotal() {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal bg1, bg2, bg3;
		for (int i = 0; i < item.size(); i++) {
			bg1 = new BigDecimal(item.get(i).getPrice());
			bg2 = new BigDecimal(item.get(i).getQuantity());
			bg3 = bg2.multiply(bg1);
			sum = sum.add(bg3);
		}
		sum = sum.setScale(2);
		return "Subtotal: " + sum;
	}

	public String totalString(BigDecimal total) {
		return " | Total: " + total;
	}

	public void totalValue(short q, double p) {
		BigDecimal sum = new BigDecimal(0);
		BigDecimal bg1, bg2, bg3;
		bg1 = new BigDecimal(p);
		bg2 = new BigDecimal(q);
		bg3 = bg2.multiply(bg1);
		sum = sum.add(bg3);
		sum = sum.setScale(2);
		SplashScreenActivity.total = SplashScreenActivity.total.add(sum);
	}

	/*
	 * public void onItemClick1(AdapterView<?> parent, View view, int position,
	 * long id) { // TODO Auto-generated method stub
	 * 
	 * }
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		String tabChosen = tab.getText().toString();
		switch (tabChosen) {
		case "Home":
			Intent homeIntent = new Intent(MyOrderActivity.this,
					MainMenuActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(homeIntent);
			break;

		case "Drinks":
			Intent drinkIntent = new Intent(MyOrderActivity.this,
					DrinkActivity.class);
			drinkIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(drinkIntent);
			break;

		case "Appetizers":
			Intent appetizerIntent = new Intent(MyOrderActivity.this,
					AppetizerActivity.class);
			appetizerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(appetizerIntent);
			break;

		case "Entrees":
			Intent entreeIntent = new Intent(MyOrderActivity.this,
					EntreeActivity.class);
			entreeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(entreeIntent);
			break;

		case "Desserts":
			Intent dessertIntent = new Intent(MyOrderActivity.this,
					DessertActivity.class);
			dessertIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(dessertIntent);
			break;

		case "Call Waiter":
			callWaiterPress();
			break;

		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		if (tab.getText().toString() == "Call Waiter")
			callWaiterPress();
	}

	private void callWaiterPress() {
		dialogBuilder = new AlertDialog.Builder(this);

		dialogBuilder.setTitle("Contact Waiter");
		dialogBuilder.setMessage("Would you like to contact a waiter?");
		dialogBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast waiterConfirm = Toast.makeText(
								getApplicationContext(),
								"A waiter has been contacted.",
								Toast.LENGTH_SHORT);
						waiterConfirm.show();
					}
				});
		dialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast waiterConfirm = Toast.makeText(
								getApplicationContext(),
								"A waiter has NOT been contacted.",
								Toast.LENGTH_SHORT);
						new CallWaiter().execute();
						waiterConfirm.show();
					}
				});
		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		dialog.show();
	}
}