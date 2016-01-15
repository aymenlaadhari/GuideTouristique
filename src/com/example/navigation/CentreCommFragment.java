package com.example.navigation;

import java.util.ArrayList;
import java.util.List;

import model.CentreCommercial;
import util.BackTaskCallBack;
import ContentProviderCentreCom.BackTaskCentreCom;
import ContentProviderCentreCom.CcommercialDataSource;
import adapter.CentreComListAdapter;
import android.content.Context;
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

public class CentreCommFragment extends Fragment implements
		OnItemClickListener, BackTaskCallBack {

	private CcommercialDataSource dataSource;
	private ListView listView;
	private List<CentreCommercial> centreCommercials = new ArrayList<CentreCommercial>();
	private CentreComListAdapter adapter;

	public CentreCommFragment() {

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
		View viewCC = inflater.inflate(R.layout.centrecomm, container, false);
		adapter = new CentreComListAdapter(getActivity(), centreCommercials);

		// Getting a reference to listview of main.xml layout file
		listView = (ListView) viewCC.findViewById(R.id.listview);

		// Setting the adapter to the listView
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		centreCommercials.addAll(recupCentre());
		adapter.notifyDataSetChanged();
		// load data
		if (isNetworkAvailable()) {
			BackTaskCentreCom backtask = new BackTaskCentreCom(getActivity());
			backtask.setCallback(this);
			backtask.execute();
		} else {
			Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
					.show();
		}

		return viewCC;

	}

	public List<CentreCommercial> recupCentre() {
		dataSource = new CcommercialDataSource(getActivity());
		dataSource.open();

		List<CentreCommercial> values = dataSource.getAllCentreCommercials();
		dataSource.close();

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

		case R.id.help:

			return true;
		case R.id.quiter:

			return true;
		}
		return false;
	}

	@Override
	public void onDoneLoadingData() {
		centreCommercials.clear();
		centreCommercials.addAll(recupCentre());
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onFailedLoadingData() {
		Log.d("TAG", "onFailedLoadingData");

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("TAG", "onItemClick");

		// Intent in = new Intent(getActivity(), ActivityHotel.class);//
		// Creating
		// an Intent
		// Object to
		// invoke
		// DisplayActivity
		// CentreCommercial centre = centreCommercials.get(position);
		// in.putExtra("HotelInfo", centre);
		// startActivityForResult(in, 0);

	}

}
