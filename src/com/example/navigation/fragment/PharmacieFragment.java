package com.example.navigation.fragment;

import java.util.ArrayList;
import java.util.List;

import model.Pharmacie;
import util.BackTaskCallBack;
import ContentProviderPharmacie.BackTaskPharmacie;
import ContentProviderPharmacie.PharmacieDataSource;
import adapter.PharmacieListAdapter;
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

import com.example.activities.ActivityPharmacie;
import com.example.activities.pager.ActivityPharmaPager;
import com.example.navigation.R;

public class PharmacieFragment extends Fragment implements OnItemClickListener,
		BackTaskCallBack {
	private PharmacieDataSource dataSource;
	private ListView listView;
	private List<Pharmacie> pharmacies = new ArrayList<Pharmacie>();
	private PharmacieListAdapter adapter;

	public PharmacieFragment() {

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
		View viewResto = inflater.inflate(R.layout.pharmacie, container, false);
		adapter = new PharmacieListAdapter(getActivity(), pharmacies);
		// Getting a reference to listview of main.xml layout file
		listView = (ListView) viewResto.findViewById(R.id.listviewPharmacie);
		// Setting the adapter to the listView
		listView.setAdapter(adapter);
		// Setting the listener to the listview
		listView.setOnItemClickListener(this);

		pharmacies.addAll(recupRestaurant());
		adapter.notifyDataSetChanged();

		if (isNetworkAvailable()) {
			BackTaskPharmacie backtask = new BackTaskPharmacie(getActivity());
			backtask.setCallback(this);
			backtask.execute();
		} else {
			Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
					.show();
		}

		return viewResto;
	}

	public List<Pharmacie> recupRestaurant() {
		dataSource = new PharmacieDataSource(getActivity());
		dataSource.open();

		List<Pharmacie> values = dataSource.getAllPharmacies();
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

		Intent in = new Intent(getActivity(), ActivityPharmaPager.class);// Creating
																		// an
																		// Intent
																		// Object
																		// to
																		// invoke
																		// DisplayActivity
		Pharmacie pharmacie = pharmacies.get(position);
		in.putExtra("PharmacieInfo", pharmacie);
		startActivityForResult(in, 0);

	}

	@Override
	public void onDoneLoadingData() {
		pharmacies.clear();
		pharmacies.addAll(recupRestaurant());
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onFailedLoadingData() {
		Log.d("TAG", "onFailedLoadingData");

	}

}
