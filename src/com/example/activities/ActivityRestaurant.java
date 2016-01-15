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

public class ActivityRestaurant extends Fragment {
	private String nom, fermeture, image, description, ouverture, specialite;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewMap = inflater.inflate(R.layout.activity_activity_hotel,
				container, false);
		TextView txtRoll = (TextView) viewMap.findViewById(R.id.textView2);
		TextView txtRoll1 = (TextView) viewMap.findViewById(R.id.textView1);

		txtRoll1.setText(nom + fermeture + ouverture);
		txtRoll1.setTextColor(Color.parseColor("#2bb4f9"));
		txtRoll.setText(description);
		WebView webView = (WebView) viewMap.findViewById(R.id.webView1);
		webView.loadUrl(image);

		return viewMap;

	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFermeture() {
		return fermeture;
	}

	public void setFermeture(String fermeture) {
		this.fermeture = fermeture;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOuverture() {
		return ouverture;
	}

	public void setOuverture(String ouverture) {
		this.ouverture = ouverture;
	}

	public static ActivityRestaurant newInstance(String text, String nom,
			String ouverture, String fermeture, String image,
			String description, String specialite) {

		ActivityRestaurant f = new ActivityRestaurant();
		f.description = description;
		f.specialite = specialite;
		f.nom = nom;
		f.image = image;
		f.ouverture = ouverture;
		f.fermeture = fermeture;
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
