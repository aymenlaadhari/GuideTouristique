package com.example.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListPoint extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewListPoint = inflater.inflate(R.layout.listpoint, container, false);
		return  viewListPoint;
	}
	
	
	public static ListPoint newInstance(String text) {
		ListPoint f = new ListPoint();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);

		return f;
	}
}
