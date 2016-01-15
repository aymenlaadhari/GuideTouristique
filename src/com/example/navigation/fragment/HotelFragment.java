package com.example.navigation.fragment;

import java.util.ArrayList;
import java.util.List;

import model.Hotel;
import util.BackTaskCallBack;
import ContentProviderHotel.BackTaskHotel;
import ContentProviderHotel.HotelDataSource;
import adapter.HotellListAdapter;
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

import com.example.activities.ActivityHotel;
import com.example.activities.pager.ActivityHotelPager;
import com.example.navigation.R;

public class HotelFragment extends Fragment implements OnItemClickListener,
		BackTaskCallBack {

	private HotelDataSource datasource;
	private ListView listView;
	public List<Hotel> hotels = new ArrayList<Hotel>();
	private HotellListAdapter adapter;

	public HotelFragment() {

	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
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

		View viewHotel = inflater.inflate(R.layout.hotel, container, false);

		adapter = new HotellListAdapter(getActivity(), hotels);

		// Getting a reference to listview of main.xml layout file
		listView = (ListView) viewHotel.findViewById(R.id.listviewHotel);

		// Setting the adapter to the listView
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		hotels.addAll(recupHotel());
		adapter.notifyDataSetChanged();

		// load data
		if (isNetworkAvailable()) {
			BackTaskHotel backtask = new BackTaskHotel(getActivity());
			backtask.setCallback(this);
			backtask.execute();
		} else {
			Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
					.show();
		}

		return viewHotel;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// On regarde quel item a �t� cliqu� gr�ce � son id et on d�clenche une
		// action
		switch (item.getItemId()) {

		case R.id.position:

			return true;
		case R.id.propos:

			Intent in = new Intent(getActivity(), ActivityHotel.class);

			startActivity(in);

			return true;
		}
		return false;
	}

	/**
	 * Get data from db
	 * 
	 * @return
	 */
	public List<Hotel> recupHotel() {

		datasource = new HotelDataSource(getActivity());
		datasource.open();

		List<Hotel> values = datasource.getAllHotels();
		datasource.close();

		return values;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("TAG", "onItemClick");

		Intent in = new Intent(getActivity(), ActivityHotelPager.class);// Creating
		// an Intent
		// Object to
		// invoke
		// DisplayActivity
		Hotel hotel = hotels.get(position);
		in.putExtra("HotelInfo", hotel);
		startActivityForResult(in, 0);

	}

	@Override
	public void onDoneLoadingData() {
		Log.d("TAG", "onDoneLoadingData");

		hotels.clear();
		hotels.addAll(recupHotel());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onFailedLoadingData() {
		Log.d("TAG", "onFailedLoadingData");
	}

}
