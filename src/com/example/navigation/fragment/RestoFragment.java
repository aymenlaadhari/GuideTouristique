package com.example.navigation.fragment;

import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import util.BackTaskCallBack;
import ContentProviderRestaurant.BackTaskResto;
import ContentProviderRestaurant.RestaurantDataSource;
import adapter.RestaurantListAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activities.pager.ActivityRestaurantPager;
import com.example.navigation.R;

public class RestoFragment extends Fragment implements OnItemClickListener,
		BackTaskCallBack {
	private RestaurantDataSource dataSource;
	private ListView listview;
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private RestaurantListAdapter adapter;

	public RestoFragment() {

	}

	private boolean isNetworkAvailable() {

		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Creating view correspoding to the fragment
		View viewResto = inflater.inflate(R.layout.resto, container, false);
		adapter = new RestaurantListAdapter(getActivity(), restaurants);
		// Getting a reference to listview of main.xml layout file
		listview = (ListView) viewResto.findViewById(R.id.listviewResto);
		// Setting the adapter to the listView
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		restaurants = recupRestaurant();
		adapter.notifyDataSetChanged();
		if (isNetworkAvailable()) {
			BackTaskResto backtask = new BackTaskResto(getActivity());
			backtask.setCallback(this);
			backtask.execute();
		} else {
			Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
					.show();
		}
		return viewResto;
	}

	public List<Restaurant> recupRestaurant() {
		dataSource = new RestaurantDataSource(getActivity());
		dataSource.open();

		List<Restaurant> values = dataSource.getAllRestaurants();
		dataSource.close();

		return values;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// On regarde quel item a été cliqué grâce à son id et on déclenche une
		// action
		switch (item.getItemId()) {

		case R.id.position:

			return true;
		case R.id.propos:
			// Pour fermer l'application il suffit de faire finish()

			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent in = new Intent(getActivity(), ActivityRestaurantPager.class);// Creating
		// an
		// Intent
		// Object
		// to
		// invoke
		// DisplayActivity
		Restaurant restaurant = restaurants.get(position);
		in.putExtra("RestaurantInfo", restaurant);
		startActivityForResult(in, 0);

	}

	@Override
	public void onDoneLoadingData() {
		restaurants.clear();
		restaurants.addAll(recupRestaurant());
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onFailedLoadingData() {
		Log.d("TAG", "onFailedLoadingData");

	}

}
