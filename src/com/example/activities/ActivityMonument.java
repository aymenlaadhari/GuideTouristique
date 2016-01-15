package com.example.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.navigation.R;

public class ActivityMonument extends Fragment {
	private String nom, description, date, type, image;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewMap = inflater.inflate(R.layout.activity_activity_hotel,
				container, false);
		TextView txtRoll = (TextView) viewMap.findViewById(R.id.textView2);
		TextView txtRoll1 = (TextView) viewMap.findViewById(R.id.textView1);
		txtRoll1.setText(nom + " est un monument " + type
				+ " dont la date de construction est " + type);
		txtRoll1.setTextColor(Color.parseColor("#2bb4f9"));
		txtRoll.setText(description);
		WebView webView = (WebView) viewMap.findViewById(R.id.webView1);
		webView.loadUrl(image);
		return viewMap;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public static ActivityMonument newInstance(String text,String nomM, String dateM,
			String typeM, String imageM, String descriptionM) {

		ActivityMonument f = new ActivityMonument();
		f.description = descriptionM;
		f.type = typeM;
		f.date = dateM;
		f.nom = nomM;
		f.image = imageM;
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
