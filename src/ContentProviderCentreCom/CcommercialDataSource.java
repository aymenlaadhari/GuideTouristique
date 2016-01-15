package ContentProviderCentreCom;

import java.util.ArrayList;
import java.util.List;

import model.CentreCommercial;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CcommercialDataSource{
    
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { 
    		MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NOM,
            MySQLiteHelper.COLUMN_longitude,
            MySQLiteHelper.COLUMN_latitude,
            MySQLiteHelper.COLUMN_description,      
            MySQLiteHelper.COLUMN_telephone,
            MySQLiteHelper.COLUMN_image,
           
           };
 
    public CcommercialDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }
 
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }

    public CentreCommercial createCentre(long id, String telephone,String image,
			String nom, String description,String longitude,
			String latitude, String type,String ville, String pays,String siteweb,String email) {
        Boolean exist = existCentreWithId(id);
 
        if(exist == true){
        	CentreCommercial existCentre = getCentreWithId(id);
        	CentreCommercial updatedCentre = updateContact(id, existCentre);
            return updatedCentre;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM, nom);
            values.put(MySQLiteHelper.COLUMN_longitude, longitude);
            values.put(MySQLiteHelper.COLUMN_latitude, latitude);
            values.put(MySQLiteHelper.COLUMN_description, description);
            values.put(MySQLiteHelper.COLUMN_telephone,telephone);
            values.put(MySQLiteHelper.COLUMN_image,image);
            values.put(MySQLiteHelper.COLUMN_categorie,type);
            values.put(MySQLiteHelper.COLUMN_ville,ville);
            values.put(MySQLiteHelper.COLUMN_pays,pays);
            values.put(MySQLiteHelper.COLUMN_siteWeb,siteweb);
            values.put(MySQLiteHelper.COLUMN_email,email);
            long insertId = database.insert(MySQLiteHelper.TABLE_CentreComercial, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_CentreComercial,
                    allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            CentreCommercial newCentre = cursorToCentre(cursor);
            cursor.close();
            return newCentre;
        }
    }

    public CentreCommercial updateContact(long id, CentreCommercial centre){
        ContentValues values = new ContentValues();
 
        values.put(MySQLiteHelper.COLUMN_NOM, centre.getNom());
        values.put(MySQLiteHelper.COLUMN_longitude, centre.getLongitude());
        values.put(MySQLiteHelper.COLUMN_latitude, centre.getLatitude());
        values.put(MySQLiteHelper.COLUMN_description, centre.getDescription());
        values.put(MySQLiteHelper.COLUMN_telephone, centre.getTelephone());
        values.put(MySQLiteHelper.COLUMN_image, centre.getImage());
        values.put(MySQLiteHelper.COLUMN_categorie,centre.getCategorie());
        values.put(MySQLiteHelper.COLUMN_ville,centre.getVille());
        values.put(MySQLiteHelper.COLUMN_pays,centre.getPays());
        values.put(MySQLiteHelper.COLUMN_siteWeb,centre.getSiteweb());
        values.put(MySQLiteHelper.COLUMN_email,centre.getEmail());
       
 
        database.update(MySQLiteHelper.TABLE_CentreComercial, values, MySQLiteHelper.COLUMN_ID + " = " +centre.getId(), null);
 
        return getCentreWithId(centre.getId());
    }

    public CentreCommercial getCentreWithId(long id){
        Cursor c = database.query(MySQLiteHelper.TABLE_CentreComercial, allColumns, MySQLiteHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        c.moveToFirst();
        CentreCommercial centre = cursorToCentre(c);
        c.close();
        return centre;
    }

    public Boolean existCentreWithId(long id){
        Cursor c = database.query(MySQLiteHelper.TABLE_CentreComercial, allColumns, MySQLiteHelper.COLUMN_ID + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    
    public void deleteContact(CentreCommercial centre) {
        long id = centre.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CentreComercial, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<CentreCommercial> getAllCentreCommercials() {
        List<CentreCommercial> centres = new ArrayList<CentreCommercial>();
 
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CentreComercial,
                allColumns, null, null, null, null, null);
 
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	CentreCommercial centre = cursorToCentre(cursor);
        	centres.add(centre);
            cursor.moveToNext();
        }
 
        cursor.close();
        return centres;
    }

    private CentreCommercial cursorToCentre(Cursor cursor) {
    	CentreCommercial centre = new CentreCommercial();
    	centre.setNom(cursor.getString(1));
    	centre.setLongitude(cursor.getString(2));
    	centre.setLatitude(cursor.getString(3));
    	centre.setDescription(cursor.getString(4));
    	centre.setTelephone(cursor.getString(5));
    	centre.setImage(cursor.getString(6));
    	centre.setCategorie(cursor.getString(6));
    	centre.setVille(cursor.getString(7));
    	centre.setPays(cursor.getString(8));
    	centre.setSiteweb(cursor.getString(9));
    	centre.setEmail(cursor.getString(10));
        return centre;
    }

}
