package com.example.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ContactActivity extends Fragment {
	private String telephone, email,siteWeb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewContact = inflater.inflate(R.layout.activity_contact,
				container, false);
		TextView emailT = (TextView) viewContact
				.findViewById(R.id.textViewEmailH);
		TextView tel = (TextView) viewContact.findViewById(R.id.textViewTelH);
		TextView web = (TextView)viewContact.findViewById(R.id.textViewSite);
		ImageButton imgB = (ImageButton) viewContact
				.findViewById(R.id.imageButton1);
		imgB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + telephone));
				startActivity(callIntent);

			}
		});

		emailT.setText(email);
		tel.setText(telephone);
		web.setText(siteWeb);

		return viewContact;

	}
	
	

	public String getSiteWeb() {
		return siteWeb;
	}



	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}



	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static ContactActivity newInstance(String text, String emailH,
			String telephoneH,String site) {

		ContactActivity f = new ContactActivity();

		f.telephone = telephoneH;
		f.email = emailH;
		f.siteWeb=site;

		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
