package ContentProviderHotel;

import java.util.ArrayList;
import java.util.List;

import model.Hotel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HotelDataSource{
    
    private SQLiteDatabase database;
    private MySQLiteHelperHotel dbHelper;
    private String[] allColumns = { MySQLiteHelperHotel.COLUMN_ID,
            MySQLiteHelperHotel.COLUMN_NOM,
            MySQLiteHelperHotel.COLUMN_longitude,
            MySQLiteHelperHotel.COLUMN_latitude,
            MySQLiteHelperHotel.COLUMN_description,
            MySQLiteHelperHotel.COLUMN_email,
            MySQLiteHelperHotel.COLUMNetoile,
            MySQLiteHelperHotel.COLUMN_siteweb,
            MySQLiteHelperHotel.COLUMN_image,
            MySQLiteHelperHotel.COLUMN_telephone,
            MySQLiteHelperHotel.COLUMN_ville,
            MySQLiteHelperHotel.COLUMN_pays,
           };
 
    public HotelDataSource(Context context) {
        dbHelper = new MySQLiteHelperHotel(context);
    }
 
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }

    public Hotel createRestaurant(long idresto, String nom, String longitude, String latitude,String description,
		String email, String etoiles,String siteweb,String image, String telephone,String ville,String pays) {
        Boolean exist = existRestaurantWithId(idresto);
 
        if(exist == true){
        	Hotel existHotel = getHotelWithId(idresto);
        	Hotel updatedHotel = updateHotel(idresto, existHotel);
            return updatedHotel;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelperHotel.COLUMN_NOM, nom);
            values.put(MySQLiteHelperHotel.COLUMN_longitude, longitude);
            values.put(MySQLiteHelperHotel.COLUMN_latitude, latitude);
            values.put(MySQLiteHelperHotel.COLUMN_description, description);
            values.put(MySQLiteHelperHotel.COLUMN_email, email);
            values.put(MySQLiteHelperHotel.COLUMNetoile, etoiles);
            values.put(MySQLiteHelperHotel.COLUMN_siteweb, siteweb);
            values.put(MySQLiteHelperHotel.COLUMN_image, image);
            values.put(MySQLiteHelperHotel.COLUMN_telephone,telephone);
            values.put(MySQLiteHelperHotel.COLUMN_ville,ville);
            values.put(MySQLiteHelperHotel.COLUMN_pays,pays);
            long insertId = database.insert(MySQLiteHelperHotel.TABLE_Hotel, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelperHotel.TABLE_Hotel,
                    allColumns, MySQLiteHelperHotel.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Hotel newRestaurant = cursorToHotel(cursor);
            cursor.close();
            return newRestaurant;
        }
    }

    public Hotel updateHotel(long idHotel, Hotel hotel){
        ContentValues values = new ContentValues();
 
        values.put(MySQLiteHelperHotel.COLUMN_NOM, hotel.getNomH());
        values.put(MySQLiteHelperHotel.COLUMN_longitude, hotel.getLongitude());
        values.put(MySQLiteHelperHotel.COLUMN_latitude, hotel.getLatitude());
        values.put(MySQLiteHelperHotel.COLUMN_description, hotel.getDescription());
        values.put(MySQLiteHelperHotel.COLUMN_email, hotel.getEmailH());
        values.put(MySQLiteHelperHotel.COLUMNetoile, hotel.getNbreetoile());
        values.put(MySQLiteHelperHotel.COLUMN_siteweb, hotel.getSitewebH());
        values.put(MySQLiteHelperHotel.COLUMN_image, hotel.getImage());
        values.put(MySQLiteHelperHotel.COLUMN_telephone, hotel.getTelephoneH());
        values.put(MySQLiteHelperHotel.COLUMN_ville, hotel.getVille());
        values.put(MySQLiteHelperHotel.COLUMN_pays, hotel.getPays());
       
 
        database.update(MySQLiteHelperHotel.TABLE_Hotel, values, MySQLiteHelperHotel.COLUMN_ID + " = " +hotel.getIdHotel(), null);
 
        return getHotelWithId(hotel.getIdHotel());
    }

    public Hotel getHotelWithId(long idHotel){
        Cursor c = database.query(MySQLiteHelperHotel.TABLE_Hotel, allColumns, MySQLiteHelperHotel.COLUMN_ID + " = \"" + idHotel +"\"", null, null, null, null);
        c.moveToFirst();
        Hotel restaurant = cursorToHotel(c);
        c.close();
        return restaurant;
    }

    public Boolean existRestaurantWithId(long idHotel){
        Cursor c = database.query(MySQLiteHelperHotel.TABLE_Hotel, allColumns, MySQLiteHelperHotel.COLUMN_ID + " = \"" + idHotel +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    
    public void deleteHotel(Hotel hotel) {
        long id = hotel.getIdHotel();
        System.out.println("Restaurant deleted with id: " + id);
        database.delete(MySQLiteHelperHotel.TABLE_Hotel, MySQLiteHelperHotel.COLUMN_ID
                + " = " + id, null);
    }
    
    public void deleteAll()
    {
    	database.delete(MySQLiteHelperHotel.TABLE_Hotel, null, null);
    }

    public Hotel getHotelwithName(String nom){
		//Récupère dans un Cursor les valeur correspondant à un hotel contenu dans la BDD (ici on sélectionne le l'hotel grâce à son nom)
    	Cursor c = database.rawQuery("SELECT * FROM '"+MySQLiteHelperHotel.TABLE_Hotel+"' WHERE nomH = '"+nom.trim()+"'", null);		
    	 c.moveToFirst();
         Hotel hotel = cursorToHotel(c);
         c.close();
         return hotel;
	}
    
    public List<Hotel> getAllHotels() {
        List<Hotel> restaurants = new ArrayList<Hotel>();
 
        Cursor cursor = database.query(MySQLiteHelperHotel.TABLE_Hotel,
                allColumns, null, null, null, null, null);
 
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Hotel hotel = cursorToHotel(cursor);
        	restaurants.add(hotel);
            cursor.moveToNext();
        }
 
        cursor.close();
        return restaurants;
    }

    
    private Hotel cursorToHotel(Cursor cursor) {
    	Hotel hotel = new Hotel();
    	hotel.setIdHotel(cursor.getLong(0));
    	hotel.setNomH(cursor.getString(1));
    	hotel.setLongitude(cursor.getString(2));
    	hotel.setLatitude(cursor.getString(3));	
    	hotel.setDescription(cursor.getString(4));
    	hotel.setEmailH(cursor.getString(5));
    	hotel.setNbreetoile(cursor.getString(6));
    	hotel.setSitewebH(cursor.getString(7));
    	hotel.setImage(cursor.getString(8));
    	hotel.setTelephoneH(cursor.getString(9));
    	hotel.setVille(cursor.getString(10));
    	hotel.setPays(cursor.getString(11));
    	
    	return hotel;
    }

}
