package ContentProviderHotel;

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

public class BackTaskHotel extends AsyncTask<Void, Integer, Boolean> {

	Context context;
	static JSONObject jObj = null;
	private static final String TAG_Hotels = "hotels";
	private static final String TAG_ID = "idhotel";
	private static final String TAG_NAME = "nomh";
	private static final String TAG_siteweb = "sitewebh";
	private static final String TAG_Longitude = "longitude";
	private static final String TAG_Latitude = "latitude";
	private static final String TAG_Description = "description";
	private static final String TAG_etoile = "nbreetoile";
	private static final String TAG_telephone = "telephoneh";
	private static final String TAG_email = "emailh";
	private static final String TAG_ville = "ville";
	private static final String TAG_pays = "pays";
	private static final String TAG_image = "imageMobile";
	
	
	
	private HotelDataSource datasource;
	private ProgressDialog pDialog;
	private BackTaskCallBack callback;
	

	public BackTaskHotel(Context context) {
		this.context = context;
	}

	public void setCallback(BackTaskCallBack callback) {
		this.callback = callback;
	}

	@Override
	protected void onPreExecute() {
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
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled(Boolean result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		String textMonument = readRestaurantsFeed();
		JSONObject jsonHotel = parseHotel(textMonument);

		Boolean result = recHotel(jsonHotel);
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

	private boolean recHotel(JSONObject jsonHotel) {
		datasource = new HotelDataSource(context);
		datasource.open();

		try {
			JSONArray hotels = jsonHotel.getJSONArray(TAG_Hotels);

			for (int i = 0; i < hotels.length(); i++) {

				JSONObject c = hotels.getJSONObject(i);

				Long id = c.getLong(TAG_ID);
				String nom = c.getString(TAG_NAME);
				String longitude = c.getString(TAG_Longitude);
				String latitude = c.getString(TAG_Latitude);
				String description = c.getString(TAG_Description);
				String telephone = c.getString(TAG_telephone);
				String email = c.getString(TAG_email);
				String siteweb = c.getString(TAG_siteweb);
				String image = c.getString(TAG_image);
				String etoiles = c.getString(TAG_etoile);
				String ville = c.getString(TAG_ville);
				String pays = c.getString(TAG_pays);

				datasource.createRestaurant(id, nom, longitude, latitude,
						description, email, etoiles, siteweb, image, telephone,ville,pays);
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

	// Methode de recuperation de Json object
	public String readRestaurantsFeed() {
		StringBuilder builder = new StringBuilder();

		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpGet httpGet = new HttpGet(
				"http://"+context.getString(R.string.ipAdress)+":8080/ServiceWeb/HotelService/GetHotels");
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
				// Log.e(MainActivity.class.toString(),
				// "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	/**
	 * Methode pour parser json
	 * 
	 * @param restaurant
	 * @return
	 */
	public JSONObject parseHotel(String hotel) {
		try {
			jObj = new JSONObject(hotel);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jObj;
	}

	/**
	 * BackTaskHotel callback
	 * 
	 * @author hp
	 */
	/*public interface BackTaskHotelCallback {
		public void onDoneLoadingData();

		public void onFailedLoadingData();
	}*/
}
