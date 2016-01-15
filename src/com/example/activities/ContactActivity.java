package com.example.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.navigation.R;

public class ContactActivity extends Fragment {
	private String telephone, email, siteWeb, nom;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View viewContact = inflater.inflate(R.layout.activity_contact,
				container, false);
		TextView telephoneT = (TextView) viewContact
				.findViewById(R.id.contactTel);
		TextView emailT = (TextView) viewContact
				.findViewById(R.id.contactEmail);
		TextView sitewebT = (TextView) viewContact
				.findViewById(R.id.contactWeb);

		telephoneT.setText(telephone);
		emailT.setText(email);
		sitewebT.setText(siteWeb);
		ImageButton buttonTel = (ImageButton) viewContact
				.findViewById(R.id.imageButtonTel);
		ImageButton buttonMail = (ImageButton) viewContact
				.findViewById(R.id.ImageButtonMail);
		ImageButton buttonShare = (ImageButton) viewContact
				.findViewById(R.id.ImageButtonShare);
		ImageButton buttonWeb = (ImageButton) viewContact
				.findViewById(R.id.ImageButtonWeb);
		WebView webView = (WebView) viewContact.findViewById(R.id.webView1);

		buttonTel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + telephone));
				startActivity(callIntent);

			}
		});

		buttonMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/html");
				intent.putExtra(Intent.EXTRA_EMAIL,
						"emailaddress@emailaddress.com");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
				intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

				startActivity(Intent.createChooser(intent, "Send Email"));

			}
		});

		buttonWeb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// webView.loadUrl(siteWeb);
			}
		});

		buttonShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				builder.setTitle("Commentaire");
				builder.setView(inflater.inflate(R.layout.dialogshare, null))
						.setPositiveButton("Envoyer",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								});
				builder.create();
				builder.show();
			}
		});

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
			String telephoneH, String site, String nom) {

		ContactActivity f = new ContactActivity();

		f.telephone = telephoneH;
		f.email = emailH;
		f.siteWeb = site;
		f.nom = nom;

		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
