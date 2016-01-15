package com.example.navigation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.navigation.fragment.CentreCommFragment;
import com.example.navigation.fragment.CentreMedicalFragment;
import com.example.navigation.fragment.HotelFragment;
import com.example.navigation.fragment.MonumentFragment;
import com.example.navigation.fragment.PharmacieFragment;
import com.example.navigation.fragment.RestoFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends ActionBarActivity implements TabListener {
	private ActionBar actionBar;
	MarkerOptions mark;
	private int a, b, c, d, e, f;
	int mPosition = -1;
	String mTitle = "";
	GoogleMap googleMap;
	final String TAG = "PathGoogleMapActivity";
	boolean initializing = true;
	private PendingIntent pendingIntent;
	int temp;
	private String activeFrag;
	PolylineOptions polylineOptions;
	private static final LatLng EPI = new LatLng(35.8294997, 10.5898126);
	private static final LatLng Movenpick = new LatLng(35.841839, 10.62766);

	Fragment mapFragment;
	Fragment listFragment;
	Fragment cFragment;

	// Array of strings storing country names
	String[] mCountries;

	// Array of integers points to images stored in /res/drawable-ldpi/
	int[] mFlags = new int[] { R.drawable.mnum, R.drawable.bed,
			R.drawable.resto, R.drawable.commerce, R.drawable.phar1,
			R.drawable.medic, R.drawable.commerce };

	// Array of strings to initial counts
	String[] mCount = new String[] { "", "", "", "", "", "" };

	private DrawerLayout mDrawerLayout;
	final CharSequence[] items = { "Tunisie", "France", "Italie" };
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout mDrawer;
	private List<HashMap<String, String>> mList;
	private SimpleAdapter mAdapter;
	final private String COUNTRY = "country";
	final private String FLAG = "flag";
	final private String COUNT = "count";

	public String getActiveFrag() {
		return activeFrag;
	}

	public void setActiveFrag(String activeFrag) {
		this.activeFrag = activeFrag;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.YEAR, 2014);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 8);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		Intent myIntent = new Intent(getApplicationContext(), MyReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
				myIntent, 0);
		//
		// AlarmManager alarmManager = (AlarmManager)
		// getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC, varTime,
		// pendingIntent);
		//

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#2bb4f9")));

		Tab tab1 = actionBar.newTab();
		Tab tab2 = actionBar.newTab();
		tab1.setIcon(R.drawable.ic_action_place);
		tab2.setIcon(R.drawable.ic_action_view_as_list);
		tab1.setTabListener(this);
		tab2.setTabListener(this);
		tab2.setText("List");
		tab1.setText("Map");
		// actionBar.addTab(tab1);
		// actionBar.addTab(tab2);
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (mapFragment == null)
			mapFragment = MapInMain.newInstance("mapMain");

		// Creating a fragment transaction
		FragmentTransaction ft1 = fragmentManager.beginTransaction();

		// Adding a fragment to the fragment transaction
		ft1.replace(R.id.content_frame, mapFragment);

		// Committing the transaction
		ft1.commit();

		// Getting an array of country names
		mCountries = getResources().getStringArray(R.array.countries);

		// Title of the activity
		mTitle = (String) getTitle();

		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = (LinearLayout) findViewById(R.id.drawer);

		// Each row in the list stores country name, count and flag
		mList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 6; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(COUNTRY, mCountries[i]);
			hm.put(COUNT, mCount[i]);
			hm.put(FLAG, Integer.toString(mFlags[i]));
			mList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { FLAG, COUNTRY, COUNT };

		// Ids of views in listview_layout
		int[] to = { R.id.flag, R.id.country, R.id.count };

		// Instantiating an adapter to store each items
		// R.layout.drawer_layout defines the layout of each item
		mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from,
				to);

		// Getting reference to DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Creating a ToggleButton for NavigationDrawer with drawer event
		// listener
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				highlightSelectedPlace();
				supportInvalidateOptionsMenu();

			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("Choisir un Lieu ");
				supportInvalidateOptionsMenu();
				// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			}
		};

		// Setting event listener for the drawer
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// ItemClick event handler for the drawer items
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// Increment hit count of the drawer list item
				// incrementHitCount(position);

				if (position < 7) { // Show fragment for countries : 0 to 6
					showFragment(position);
				} else { // Show message box for countries : 6 to 9
					Toast.makeText(getApplicationContext(),
							mCountries[position], Toast.LENGTH_LONG).show();
				}

				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);
			}

			// compter nombre de hits sur le navigation
			// private void incrementHitCount(int position) {
			//
			// HashMap<String, String> item = mList.get(position);
			// String count = item.get(COUNT);
			// item.remove(COUNT);
			// count = "1";
			// if (position == 0) {
			// temp = c;
			//
			// } else if (position == 1) {
			// temp = b;
			// } else if (position == 2) {
			// temp = a;
			// } else if (position == 3) {
			// temp = e;
			// } else if (position == 4) {
			// temp = d;
			// } else if (position == 5) {
			// temp = f;
			// }
			//
			// count = "" + temp + "";
			// item.put(COUNT, count);
			// mAdapter.notifyDataSetChanged();
			// }
		});

		// Enabling Up navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setDisplayShowHomeEnabled(true);

		// Setting the adapter to the listView
		mDrawerList.setAdapter(mAdapter);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.position:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("Choisir destination").setItems(items,
					new DialogInterface.OnClickListener() {
						private CharSequence[] itemstunisie = { "Sousse",
								"KAIROUAN","ARIANA","BEJA","BEN AROUS","BIZERTE","GABES","GAFSA","JANDOUBA","KASSERINE","KEBELI","KEF","MAHDIA","MANOUBA","MEDENINE"};

						public void onClick(DialogInterface dialog, int item) {

							if (items[item] == "Tunisie") {
								AlertDialog.Builder builder = new AlertDialog.Builder(
										MainActivity.this);
								builder.setTitle("Choisir gouvernorat");
								builder.setItems(itemstunisie,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {

												if (which == 0) {
													FragmentManager fragmentManager = getSupportFragmentManager();
													mapFragment = MapInMain
															.newInstanceoption(
																	"mapMain",
																	"TUNISIE",
																	"Sousse");

													// Creating a fragment
													// transaction
													FragmentTransaction ft1 = fragmentManager
															.beginTransaction();

													// Adding a fragment to the
													// fragment transaction
													ft1.replace(
															R.id.content_frame,
															mapFragment);

													// Committing the
													// transaction
													ft1.commit();
												} else if (which == 1) {
													FragmentManager fragmentManager = getSupportFragmentManager();
													mapFragment = MapInMain
															.newInstanceoption(
																	"mapMain",
																	"TUNISIE",
																	"KAIROUAN");

													// Creating a fragment
													// transaction
													FragmentTransaction ft1 = fragmentManager
															.beginTransaction();

													// Adding a fragment to the
													// fragment transaction
													ft1.replace(
															R.id.content_frame,
															mapFragment);

													// Committing the
													// transaction
													ft1.commit();
												} else if (which == 2){
													FragmentManager fragmentManager = getSupportFragmentManager();
													mapFragment = MapInMain
															.newInstanceoption(
																	"mapMain",
																	"TUNISIE",
																	"ARIANA");

													// Creating a fragment
													// transaction
													FragmentTransaction ft1 = fragmentManager
															.beginTransaction();

													// Adding a fragment to the
													// fragment transaction
													ft1.replace(
															R.id.content_frame,
															mapFragment);
													
												}

											}
										});
								builder.show();
							}

						}
					});
			builder.create();
			builder.show();

			return true;

		case R.id.propos:

			FragmentManager fragmentManager = getSupportFragmentManager();
			mapFragment = MapInMain.newInstance("mapMain");

			// Creating a fragment transaction
			FragmentTransaction ft1 = fragmentManager.beginTransaction();

			// Adding a fragment to the fragment transaction
			ft1.replace(R.id.content_frame, mapFragment);

			// Committing the transaction
			ft1.commit();
			return true;
		default:

			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * Show fragment and call item count from db to show in the right
	 */
	public void showFragment(int position) {

		// Currently selected country
		mTitle = mCountries[position];

		// Creating a fragment object

		switch (position) {
		case 0:
			cFragment = new MonumentFragment();
			activeFrag = "monument";
			// MonumentDataSource monumentDataSource = new MonumentDataSource(
			// getApplicationContext());
			// monumentDataSource.open();
			// c = monumentDataSource.getAllMonuments().size();
			// monumentDataSource.close();
			// /

			break;
		case 1:
			cFragment = new HotelFragment();
			activeFrag = "hotel";
			// HotelDataSource datasource = new HotelDataSource(
			// getApplicationContext());
			// datasource.open();
			// b = datasource.getAllHotels().size();
			// datasource.close();

			break;
		case 2:
			cFragment = new RestoFragment();
			activeFrag = "restaurant";
			// RestaurantDataSource restDatasource = new RestaurantDataSource(
			// getApplicationContext());
			// restDatasource.open();
			// a = restDatasource.getAllRestaurants().size();
			// restDatasource.close();

			break;
		case 3:
			cFragment = new CentreCommFragment();
			activeFrag = "commercial";
			// CcommercialDataSource ccommercialDataSource = new
			// CcommercialDataSource(
			// getApplicationContext());
			// ccommercialDataSource.open();
			// e = ccommercialDataSource.getAllCentreCommercials().size();
			// ccommercialDataSource.close();
			// /

			break;
		case 4:
			cFragment = new PharmacieFragment();
			activeFrag = "pharmacie";
			// PharmacieDataSource pharmacieDataSource = new
			// PharmacieDataSource(
			// getApplicationContext());
			// pharmacieDataSource.open();
			// d = pharmacieDataSource.getAllPharmacies().size();
			// pharmacieDataSource.close();

			break;
		case 5:
			cFragment = new CentreMedicalFragment();
			activeFrag = "medical";
			// CmedicalDataSource cmedicalDataSource = new CmedicalDataSource(
			// getApplicationContext());
			// cmedicalDataSource.open();
			// f = cmedicalDataSource.getAllCentreMedicales().size();
			// cmedicalDataSource.close();

			break;

		default:
			break;
		}

		// Creating a Bundle object
		Bundle data = new Bundle();

		// Setting the index of the currently selected item of mDrawerList
		data.putInt("position", position);

		// Setting the position to the fragment
		cFragment.setArguments(data);

		// Getting reference to the FragmentManager
		FragmentManager fragmentManager = getSupportFragmentManager();

		// Creating a fragment transaction
		FragmentTransaction ft = fragmentManager.beginTransaction();

		// Adding a fragment to the fragment transaction
		ft.replace(R.id.content_frame, cFragment);

		// Committing the transaction
		ft.commit();
	}

	// Highlight the selected place : 0 to 6
	public void highlightSelectedPlace() {
		int selectedItem = mDrawerList.getCheckedItemPosition();

		if (selectedItem > 6)
			mDrawerList.setItemChecked(mPosition, true);
		else
			mPosition = selectedItem;

		if (mPosition != -1)
			getSupportActionBar().setTitle(mCountries[mPosition]);
	}

	/*
	 * Show map on map Tab selected
	 */
	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		int i = tab.getPosition();

		FragmentManager fragmentManager = getSupportFragmentManager();

		if (i == 0)

		{
			if (mapFragment == null)
				mapFragment = MapInMain.newInstance("mapMain");

			// Creating a fragment transaction
			FragmentTransaction ft1 = fragmentManager.beginTransaction();

			// Adding a fragment to the fragment transaction
			ft1.replace(R.id.content_frame, mapFragment);

			// Committing the transaction
			ft1.commit();

		} else {
			if (listFragment == null)
				listFragment = ListPoint.newInstance("listpoint");

			// Creating a fragment transaction
			FragmentTransaction ft1 = fragmentManager.beginTransaction();

			// Adding a fragment to the fragment transaction
			ft1.replace(R.id.content_frame, listFragment);

			// Committing the transaction
			ft1.commit();
		}

	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		int i1 = tab.getPosition();
		if (i1 == 0) {
			Toast.makeText(getBaseContext(), "ok", 1000).show();
		}

	}

}