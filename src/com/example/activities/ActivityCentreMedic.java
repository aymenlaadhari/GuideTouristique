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

public class ActivityCentreMedic extends Fragment {
	private String nom, telephone, image, description, email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewMap = inflater.inflate(R.layout.activity_activity_hotel,
				container, false);
		TextView txtRoll = (TextView) viewMap.findViewById(R.id.textView2);
		TextView txtRoll1 = (TextView) viewMap.findViewById(R.id.textView1);
		txtRoll1.setText(nom);
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static ActivityCentreMedic newInstance(String text, String nom,
			String email, String telephone, String image, String description) {

		ActivityCentreMedic f = new ActivityCentreMedic();
		f.description = description;

		f.telephone = telephone;
		f.nom = nom;
		f.image = image;
		f.email = email;
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);
		return f;
	}

}
