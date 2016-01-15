package com.example.navigation;


import java.util.ArrayList;
import java.util.List;

import model.CentreMedicale;
import util.BackTaskCallBack;
import ContentProviderCentreMed.BackTaskCentreMed;
import ContentProviderCentreMed.CmedicalDataSource;
import adapter.CentreMedListAdaptrer;
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

public class CentreMedicalFragment extends Fragment implements OnItemClickListener,
BackTaskCallBack
{

	private CmedicalDataSource datasource;
	private ListView listView;
	private List<CentreMedicale> medicales = new ArrayList<CentreMedicale>();
	private CentreMedListAdaptrer adapter;
	public  CentreMedicalFragment() 
    {
		
		
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
       View viewCM= inflater.inflate(R.layout.centremedical, container, false);
       adapter = new CentreMedListAdaptrer(getActivity(), medicales);
       // Getting a reference to listview of main.xml layout file
      listView = ( ListView ) viewCM.findViewById(R.id.listview);

       // Setting the adapter to the listView
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(this);
   
       medicales.addAll(recupCentre());
	   adapter.notifyDataSetChanged();
		// load data
				if (isNetworkAvailable()) {
					BackTaskCentreMed backtask = new BackTaskCentreMed(getActivity());
					backtask.setCallback(this);
					backtask.execute();
				} else {
					Toast.makeText(getActivity(), "Offline lunch", Toast.LENGTH_LONG)
							.show();
				}
        return viewCM;
   	
    }
   

public List<CentreMedicale> recupCentre() {
	
	datasource = new CmedicalDataSource(getActivity());
	datasource.open();
	List<CentreMedicale> values = datasource.getAllCentreMedicales();
	datasource.close();
	return values;
	}

//////////Menu
 public void onCreateOptionsMenu(Menu menu ,  MenuInflater inflater) {
 
        
        inflater.inflate(R.menu.main, menu);
    	 super.onCreateOptionsMenu(menu,inflater);
     }
 
      public boolean onOptionsItemSelected(MenuItem item) {
         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
         switch (item.getItemId()) {
           
           case R.id.help:
                
               return true;
           case R.id.quiter:
               
               return true;
         }
         return false;}


	@Override
	public void onDoneLoadingData() {
		Log.d("TAG", "onDoneLoadingData");

		medicales.clear();
		medicales.addAll(recupCentre());
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

		Intent in = new Intent(getActivity(), ActivityHotel.class);// Creating
																	// an Intent
																	// Object to
	      															// invoke
																	// DisplayActivity
		CentreMedicale centreMedicale = medicales.get(position);
		in.putExtra("HotelInfo", centreMedicale);
		startActivityForResult(in, 0);
		
	}
    
    
    
    
    
   
}


