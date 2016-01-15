package com.example.navigation.fragment;

import java.util.ArrayList;
import java.util.List;

import model.Monument;
import util.BackTaskCallBack;
import ContentProviderMonument.BackTaskMonument;
import ContentProviderMonument.MonumentDataSource;
import adapter.MonumentListAdapter;
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

import com.example.activities.pager.ActivityMonumentPager;
import com.example.navigation.R;

public class MonumentFragment extends Fragment implements OnItemClickListener,
		BackTaskCallBack {

	private MonumentDataSource datasource;
	private ListView listView;
	private List<Monument> monuments = new ArrayList<Monument>();
	private MonumentListAdapter adapter;

	public MonumentFragment() {

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
		View viewMonument = inflater.inflate(R.layout.monument, container,
				false);
		adapter = new MonumentListAdapter(getActivity(), monuments);
		// Getting a reference to listview of main.xml layout file
		listView = (ListView) viewMonument.findViewById(R.id.listviewMonument);
		// Setting the adapter to the listView
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		monuments.addAll(recupMonument());
		adapter.notifyDataSetChanged();

		// load data
		if (isNetworkAvailable()) {
			BackTaskMonument backtask = new BackTaskMonument(getActivity());
			backtask.setCallback(this);
			backtask.execute();
		} else {
			Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
					.show();
		}

		return viewMonument;
	}

	public List<Monument> recupMonument() {

		datasource = new MonumentDataSource(getActivity());
		datasource.open();

		List<Monument> values = datasource.getAllMonuments();
		datasource.close();

		return values;

	}

	// ////////Menu
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("TAG", "onItemClick");
		Intent in = new Intent(getActivity(), ActivityMonumentPager.class);// Creating
																			// an
																			// Intent
																			// Object
																			// to
																			// invoke
																			// DisplayActivity
		Monument monument = monuments.get(position);
		in.putExtra("MonumentInfo", monument);
		startActivityForResult(in, 0);

	}

	@Override
	public void onDoneLoadingData() {
		monuments.clear();
		monuments.addAll(recupMonument());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onFailedLoadingData() {

		Log.d("TAG", "onFailedLoadingData");
	}

}
