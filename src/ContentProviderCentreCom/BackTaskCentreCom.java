package ContentProviderCentreCom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.BackTaskCallBack;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import com.example.navigation.R;

public class BackTaskCentreCom extends AsyncTask<Void, Integer, Boolean> {

	Context context;
	static JSONObject jObj = null;
	private static final String TAG_Centres = "centreCommercials";
	private static final String TAG_ID = "idcc";
	private static final String TAG_TELEPHONE = "telephone";
	private static final String TAG_NOM = "nom";
	private static final String TAG_Image = "imageMobile";
	private static final String TAG_Description = "description";
	private static final String TAG_LONGITUDE = "longitude";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_TYPE = "type";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_VILLE = "ville";
	private static final String TAG_PAYS = "pays";
	private static final String TAG_SITWEB = "siteweb";
	
	private Context mContext;
	private CcommercialDataSource datasource;
	private ProgressDialog pDialog;
	private BackTaskCallBack callback;

	public BackTaskCentreCom(Context context) {
		this.context = context;
	}

	public void setCallback(BackTaskCallBack callback) {
		this.callback = callback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setTitle("Veuiller patienter");
		pDialog.setMessage("Chargement ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		pDialog.dismiss();

		if (callback != null) { // send callback
			if (result)
				callback.onDoneLoadingData();
			else
				callback.onFailedLoadingData();
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		String textCentre = readCentresFeed();
		JSONObject jsonCentre = parseCentre(textCentre);
		Boolean result = recCentre(jsonCentre);
		if (result == true) {
			SharedPreferences pref = context
					.getSharedPreferences("lastSync", 0); // 0 - for private
															// mode
			Editor editor = pref.edit();
			Date currentDate = new Date();
			editor.putLong("lastSync", currentDate.getTime());
			editor.commit();
		}
		return result;
	}

	private Boolean recCentre(JSONObject jsonCentre) {
		datasource = new CcommercialDataSource(context);
		datasource.open();

		try {
			JSONArray centres = jsonCentre.getJSONArray(TAG_Centres);

			for (int i = 0; i < centres.length(); i++) {

				JSONObject c = centres.getJSONObject(i);

				Long id = c.getLong(TAG_ID);
				String telephone = c.getString(TAG_TELEPHONE);
				String image = c.getString(TAG_Image);
				String nom = c.getString(TAG_NOM);
				String description = c.getString(TAG_Description);
				String longitude = c.getString(TAG_LONGITUDE);
				String latitude = c.getString(TAG_LATITUDE);
				String type = c.getString(TAG_TYPE);
				String ville = c.getString(TAG_VILLE);
				String pays = c.getString(TAG_PAYS);
				String siteweb = c.getString(TAG_SITWEB);
				String email = c.getString(TAG_EMAIL);

				datasource.createCentre(id, telephone, image, nom, description,
						longitude, latitude, type, ville, pays, siteweb, email);
			}
			return true;

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			datasource.close();
		}

	}

	// Methode de recuperation de Json
	public String readCentresFeed() {
		StringBuilder builder = new StringBuilder();
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://"+mContext.getString(R.string.ipAdress)+":8084/ServiceWeb/CentreCommercial/GetCentreCommercials");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
			} else {
				// Log.e(CentreCommercialeActivity.class.toString(),
				// "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	// Methode pour parser json
	public JSONObject parseCentre(String centre) {
		try {
			jObj = new JSONObject(centre);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jObj;
	}
}
