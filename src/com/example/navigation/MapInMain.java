package com.example.navigation;

import java.util.ArrayList;
import java.util.List;

import util.ImageLoader;
import model.CentreCommercial;
import model.CentreMedicale;
import model.Hotel;
import model.Monument;
import model.Pharmacie;
import model.Restaurant;
import ContentProviderCentreCom.CcommercialDataSource;
import ContentProviderCentreMed.CmedicalDataSource;
import ContentProviderHotel.HotelDataSource;
import ContentProviderMonument.MonumentDataSource;
import ContentProviderPharmacie.PharmacieDataSource;
import ContentProviderRestaurant.RestaurantDataSource;
import adapter.ImageWebView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapInMain extends SupportMapFragment {
	private LatLng mPosFija;
	private static final LatLng EPI = new LatLng(35.8294997, 10.5898126);
	private View marker;
	//ImageWebView imgIcon;
	ImageView imgIcon ;
	 int loader = R.drawable.glogo;
	 private String payMap;
	 private String villeMap;

	public MapInMain() {
		super();

	}

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		 
		View v = super.onCreateView(arg0, arg1, arg2);
		 marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
//		 imgIcon = (ImageWebView) marker
//					.findViewById(R.id.imgIcon);
		imgIcon = (ImageView)marker.findViewById(R.id.imgIcon);
		
		initMap();
		return v;
	}

	private void initMap() {
		UiSettings settings = getMap().getUiSettings();
		settings.setAllGesturesEnabled(false);
		settings.setMyLocationButtonEnabled(false);
		addMarkersMonuments();
		addMarkersRestaurants();
		addMarkersMedicals();
		addMarkersPharmacie();
		
		settings.setScrollGesturesEnabled(true);
		settings.setZoomGesturesEnabled(true);

		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 8));
		getMap().addMarker(new MarkerOptions().position(mPosFija));
	}

	// static final LatLng TutorialsPoint = new LatLng(21, 57);
	// private GoogleMap googleMap;
	// PolylineOptions polylineOptions;
	// private SupportMapFragment fragment;
	// private static final LatLng EPI = new LatLng(35.8294997, 10.5898126);
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View viewMap = inflater.inflate(R.layout.activity_map_in_main,
	// container, false);
	//
	// try {
	// if (googleMap == null) {
	// googleMap = ((SupportMapFragment) getFragmentManager()
	// .findFragmentById(R.id.mapInMain)).getMap();
	// googleMap.setMyLocationEnabled(true);
	// // Location location = googleMap.getMyLocation();
	//
	// googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	// if (googleMap != null) {
	//
	// // Cal add line to show lines
	// // addLines();
	// // if (runningactivities
	// //
	// .contains("ComponentInfo{com.app/com.example.activities.ActivityHotelPager}")
	// // == true)
	// // {
	// googleMap = ((SupportMapFragment) getFragmentManager()
	// .findFragmentById(R.id.mapInMain)).getMap();
	// //
	// // String url = getMapsApiDirectionsUrl();
	// // ReadTask downloadTask = new ReadTask();
	// // downloadTask.execute(url);
	// // addMarkers();
	// googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EPI,
	// 13));
	// // }
	// }
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return viewMap;
	//
	// }
	//
	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	// FragmentManager fm = getChildFragmentManager();
	// fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapInMain);
	// if (fragment == null) {
	// fragment = SupportMapFragment.newInstance();
	// fm.beginTransaction().replace(R.id.mapInMain, fragment).commit();
	// }
	// }
	//
	// @Override
	// public void onResume() {
	// super.onResume();
	// if (googleMap == null) {
	// // if (runningactivities
	// //
	// .contains("ComponentInfo{com.app/com.example.activities.ActivityHotelPager}")
	// // == true) {
	//
	// googleMap = fragment.getMap();
	// addMarkers();
	// googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EPI, 13));
	// // googleMap.addMarker(new MarkerOptions().position(EPI));
	// // }
	// }
	// }
	//
	// // Add markers
	// private void addMarkers() {
	// if (googleMap != null) {
	// googleMap.addMarker(new MarkerOptions().position(EPI).title("EPI"));
	// googleMap.addMarker(new MarkerOptions().position(TutorialsPoint));
	//
	// }
	// }
	//
	// Add markers from liste
	public void addMarkersMonuments() {
		MonumentDataSource monumentDataSource = new MonumentDataSource(
				getActivity());
		monumentDataSource.open();
		List<Monument> monuments = monumentDataSource.getAllMonuments();
		int a = monuments.size();
		Log.e("lenghAAA", Integer.toHexString(a));
		monumentDataSource.close();
		if(payMap != null)
		{
			if(villeMap != null)
			{
				 Filter<Monument,String> filter = new Filter<Monument,String>() {
			            public boolean isMatched(Monument object, String text) {
			                return object.getVille().startsWith(String.valueOf(text));
			            }
			        };
			     List <Monument> lisMonumentsVille = new FilterList().filterList(monuments, filter, villeMap);
			     monuments.clear();
			     monuments = lisMonumentsVille;
			
			}else
			{
				Filter<Monument,String> filter = new Filter<Monument,String>() {
		            public boolean isMatched(Monument object, String text) {
		                return object.getVille().startsWith(String.valueOf(text));
		            }
		        };
		     List <Monument> lisMonumentsVille = new FilterList().filterList(monuments, filter, payMap);
		     monuments.clear();
		     monuments = lisMonumentsVille;
			}
			
			
		}

		for (int i = 0; i < monuments.size(); i++) {

			double lati = Double.parseDouble(monuments.get(i).getLatitude());
			double longLat = Double
					.parseDouble(monuments.get(i).getLongitude());
			ImageLoader imgLoader = new ImageLoader(getActivity());
			imgLoader.DisplayImage(monuments.get(i).getImage(), loader, imgIcon);
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.fromBitmap(createDrawableFromView(getActivity(), marker)))
							.position(new LatLng(lati, longLat))
							.title(monuments.get(i).getNomM())
							.snippet(monuments.get(i).getType()));
		}
	}
	
	public void addMarkersRestaurants() {
		RestaurantDataSource restaurantDataSource = new RestaurantDataSource(
				getActivity());
		restaurantDataSource.open();
		List<Restaurant> restaurants = restaurantDataSource.getAllRestaurants();
		restaurantDataSource.close();

		for (int i = 0; i < restaurants.size(); i++) {

			double lati = Double.parseDouble(restaurants.get(i).getLatitude());
			double longLat = Double
					.parseDouble(restaurants.get(i).getLongitude());
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
							.position(new LatLng(lati, longLat))
							.title(restaurants.get(i).getNom())
							.snippet(restaurants.get(i).getSiteweb()));
		}
	}
	
	public void addMarkersMedicals() {
		CmedicalDataSource cmedicalDataSource  = new CmedicalDataSource(
				getActivity());
		cmedicalDataSource.open();
		List<CentreMedicale> centreMedicales = cmedicalDataSource.getAllCentreMedicales();
		cmedicalDataSource.close();

		for (int i = 0; i < centreMedicales.size(); i++) {

			double lati = Double.parseDouble(centreMedicales.get(i).getLatitude());
			double longLat = Double
					.parseDouble(centreMedicales.get(i).getLongitude());
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
							.position(new LatLng(lati, longLat))
							.title(centreMedicales.get(i).getNom())
							.snippet(centreMedicales.get(i).getSiteweb()));
		}
	}

	public void addMarkersCommercials() {
		CcommercialDataSource ccommercialDataSource  = new CcommercialDataSource(
				getActivity());
		ccommercialDataSource.open();
		List<CentreCommercial> centreCommercials = ccommercialDataSource.getAllCentreCommercials();
		ccommercialDataSource.close();

		for (int i = 0; i < centreCommercials.size(); i++) {

			double lati = Double.parseDouble(centreCommercials.get(i).getLatitude());
			double longLat = Double
					.parseDouble(centreCommercials.get(i).getLongitude());
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
							.position(new LatLng(lati, longLat))
							.title(centreCommercials.get(i).getNom())
							.snippet(centreCommercials.get(i).getSiteweb()));
		}
	}
	
	public void addMarkersPharmacie() {
		PharmacieDataSource pharmacieDataSource  = new PharmacieDataSource(
				getActivity());
		pharmacieDataSource.open();
		List<Pharmacie> pharmacies = pharmacieDataSource.getAllPharmacies();
		pharmacieDataSource.close();

		for (int i = 0; i < pharmacies.size(); i++) {

			double lati = Double.parseDouble(pharmacies.get(i).getLatitude());
			double longLat = Double
					.parseDouble(pharmacies.get(i).getLongitude());
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
							.position(new LatLng(lati, longLat))
							.title(pharmacies.get(i).getNom())
							.snippet(pharmacies.get(i).getSiteweb()));
		}
	}

	public void addMarkersHotels() {
		HotelDataSource datasourceHotel = new HotelDataSource(
				getActivity());
		datasourceHotel.open();
		List<Hotel> hotels = datasourceHotel.getAllHotels();
		datasourceHotel.close();

		for (int i = 0; i < hotels.size(); i++) {

			double lati = Double.parseDouble(hotels.get(i).getLatitude());
			double longLat = Double
					.parseDouble(hotels.get(i).getLongitude());
			getMap().addMarker(
					new MarkerOptions()
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
							.position(new LatLng(lati, longLat))
							.title(hotels.get(i).getNomH())
							.snippet(hotels.get(i).getTelephoneH()));
		}
	}
	
	public static Bitmap createDrawableFromView(Context context, View view) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
 
		return bitmap;
	}
	
	
	public static MapInMain newInstance(String text) {
		MapInMain f = new MapInMain();
		f.mPosFija = EPI;
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);

		return f;
	}
	
	public static MapInMain newInstanceoption(String texte,String pay, String ville)
	{
		MapInMain f = new MapInMain();
		f.mPosFija = EPI;
		f.payMap = pay;
		f.villeMap = ville;		
		Bundle b = new Bundle();
		b.putString("msg", texte);
		f.setArguments(b);

		return f;
		
	}

	
	public class FilterList<E> {
	    public  <T> List filterList(List<T> originalList, Filter filter, E text) {
	        List<T> filterList = new ArrayList<T>();
	        for (T object : originalList) {
	            if (filter.isMatched(object, text)) {
	                filterList.add(object);
	            } else {
	                continue;
	            }
	        }
	        return filterList;
	    }
	} 
	
	interface Filter<T,E> {
	    public boolean isMatched(T object, E text);
	}
}
