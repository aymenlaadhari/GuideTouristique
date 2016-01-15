package com.example.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import util.HttpConnection;
import util.PathJSONParser;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapFrag extends Fragment {
	static final LatLng TutorialsPoint = new LatLng(21, 57);
	private GoogleMap googleMap;
	PolylineOptions polylineOptions;
	private SupportMapFragment fragment;
	private LatLng EPI = new LatLng(35.82982, 10.589868);
	private double latitude;
	private double longitude;
	private LatLng To;
	// private AppLocationService appLocationService;
	ArrayList<String> runningactivities = new ArrayList<String>();

	public LatLng getTo() {
		return To;
	}

	public void setTo(LatLng to) {
		To = to;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewMap = inflater.inflate(R.layout.mapfrag, container, false);
		ActivityManager activityManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		// appLocationService = new AppLocationService(getActivity());
		List<RunningTaskInfo> services = activityManager
				.getRunningTasks(Integer.MAX_VALUE);
		for (int i1 = 0; i1 < services.size(); i1++) {
			runningactivities.add(0, services.get(i1).topActivity.toString());
		}

		try {
			if (googleMap == null) {
				googleMap = ((SupportMapFragment) getFragmentManager()
						.findFragmentById(R.id.mapFrag)).getMap();
				googleMap.setMyLocationEnabled(true);

				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				googleMap.setTrafficEnabled(true);
				
				if (googleMap != null) {
					googleMap = ((SupportMapFragment) getFragmentManager()
							.findFragmentById(R.id.mapFrag)).getMap();

					String url = getMapsApiDirectionsUrl();
					ReadTask downloadTask = new ReadTask();
					downloadTask.execute(url);
					addMarkers();
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EPI,
							8));
					// }
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return viewMap;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Location gpsLocation = appLocationService
		// .getLocation(LocationManager.GPS_PROVIDER);
		// if (gpsLocation != null) {
		// latitudeMyloc = gpsLocation.getLatitude();
		// longitudeMyLoc = gpsLocation.getLongitude();
		// EPI = new LatLng(21, 57);
		// }
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapFrag);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.mapFrag, fragment).commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (googleMap == null) {

			googleMap = fragment.getMap();
			String url = getMapsApiDirectionsUrl();
			ReadTask downloadTask = new ReadTask();
			downloadTask.execute(url);
			addMarkers();
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EPI, 8));
			// googleMap.addMarker(new MarkerOptions().position(EPI));
			// }
		}
	}

	private String getMapsApiDirectionsUrl() {
		String waypoints = "waypoints=optimize:true|" + EPI.latitude + ","
				+ EPI.longitude + "|" + "|" + To.latitude + "," + To.longitude;

		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	// Add markers
	private void addMarkers() {
		if (googleMap != null) {
			googleMap.addMarker(new MarkerOptions().position(EPI).title("EPI"));
			googleMap.addMarker(new MarkerOptions().position(To));

		}
	}

	// Add markers from liste

	// for (int i = 0; i < yourArrayList.size(); i++) {
	//
	// double lati=Double.parseDouble(pins.get(i).latitude);
	// double longLat=Double.parseDouble(pins.get(i).longitude);
	// MAP.addMarker(new MarkerOptions().position(new
	// LatLng(lati,longLat)).title(pins.get(i).pinname).snippet(pins.get(i).address));
	// }

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(2);
				polyLineOptions
						.color(Color.BLUE/* Color.parseColor("#2bb4f9") */);
				polyLineOptions.geodesic(true);
			}

			googleMap.addPolyline(polyLineOptions);
		}
	}

	public static MapFrag newInstance(String text, double latitude,
			double longitude) {
		MapFrag f = new MapFrag();
		f.latitude = latitude;
		f.longitude = longitude;
		f.To = new LatLng(f.latitude, f.longitude);
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);

		return f;
	}

	public static MapFrag newInstance1(String text) {
		MapFrag f = new MapFrag();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);

		return f;
	}

}
