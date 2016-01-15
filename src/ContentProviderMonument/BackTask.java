package ContentProviderMonument;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import ContentProviderMonument.MonumentDataSource;

public class BackTask extends AsyncTask<Void, Integer, Void>{

	 Context context;
	 static JSONObject jObj = null;
	 private static final String TAG_Monuments = "monuments";
	    private static final String TAG_ID = "idMonument";
	    private static final String TAG_NAME = "nomM";
	    private static final String TAG_Longitude = "longitude";
	    private static final String TAG_Latitude = "latitude";
	    private static final String TAG_Description = "description";
	    private static final String TAG_Type = "type";
	    private static final String TAG_Image = "image";
	    private static final String TAG_Date_Construction = "dateconstruction";
	    private MonumentDataSource datasource;
	    private ProgressDialog pDialog;
	 
	 
	 public BackTask(Context context) {
	         this.context = context;
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
		protected void onPostExecute(Void result) {
			 pDialog.dismiss();
		}


	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled(Void result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... params) {
		 String textMonument = readMonumentsFeed();
	        JSONObject jsonMonument = parseMonument(textMonument);
	        Boolean result  = recMonument(jsonMonument);
	        if(result == true) {
	            SharedPreferences pref = context.getSharedPreferences("lastSync", 0); // 0 - for private mode
	            Editor editor = pref.edit();
	            Date currentDate=new Date();
	            editor.putLong("lastSync", currentDate.getTime());
	            editor.commit();
	        }
	        return null;
	}
	
	private Boolean recMonument(JSONObject jsonMonument) {
        datasource = new MonumentDataSource(context);
        datasource.open();
         
        try {
            JSONArray contacts = jsonMonument.getJSONArray(TAG_Monuments);
              
            for(int i = 0; i < contacts.length(); i++){
             
                JSONObject c = contacts.getJSONObject(i);
 
                Long id = c.getLong(TAG_ID);
                String nom = c.getString(TAG_NAME);
                String longitude = c.getString(TAG_Longitude);
                String latitude = c.getString(TAG_Latitude);
                String description = c.getString(TAG_Description);
                String type = c.getString(TAG_Type);
                String image = c.getString(TAG_Image);
                String dateConstr = c.getString(TAG_Date_Construction);
                
               datasource.createMonument(id,nom, longitude, latitude, description, type,image, dateConstr);
            }
            datasource.close();
            return true;
             
        } catch (JSONException e) {
            e.printStackTrace();
            datasource.close();
            return false;
        }
    }

	//Methode de recuperation de Json
	public String readMonumentsFeed() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://192.168.1.100:8080/ServiceWeb/MonumentService/GetMonuments");
        try {
          HttpResponse response = client.execute(httpGet);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line + "\n");
            }
          } else {
            //Log.e(MainActivity.class.toString(), "Failed to download file");
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return builder.toString();
      }
   
	//Methode pour parser json
	public JSONObject parseMonument(String monument){
        try {
               jObj = new JSONObject(monument);            
           } catch (JSONException e) {
               Log.e("JSON Parser", "Error parsing data " + e.toString());
           }
       return jObj;
}
}
