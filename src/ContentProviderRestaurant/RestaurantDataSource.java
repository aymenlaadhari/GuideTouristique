package ContentProviderRestaurant;

import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RestaurantDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelperResto dbHelper;
	private String[] allColumns = { MySQLiteHelperResto.COLUMN_ID,
			MySQLiteHelperResto.COLUMN_NOM,
			MySQLiteHelperResto.COLUMN_longitude,
			MySQLiteHelperResto.COLUMN_latitude,
			MySQLiteHelperResto.COLUMN_description,
			MySQLiteHelperResto.COLUMN_email,
			MySQLiteHelperResto.COLUMNhorairehouverture,
			MySQLiteHelperResto.COLUMN_horairefermeture,
			MySQLiteHelperResto.COLUMN_siteweb,
			MySQLiteHelperResto.COLUMN_image,
			MySQLiteHelperResto.COLUMN_telephone, 
			MySQLiteHelperResto.COLUMN_ville,
			MySQLiteHelperResto.COLUMN_pays,};

	public RestaurantDataSource(Context context) {
		dbHelper = new MySQLiteHelperResto(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Restaurant createRestaurant(long idresto, String nom,
			String longitude, String latitude, String description,
			String email, String horairehouverture, String horairefermeture,
			String siteweb, String image, String telephone, String ville, String pays) {
		Boolean exist = existRestaurantWithId(idresto);

		if (exist == true) {
			Restaurant existRestaurant = getRestaurantWithId(idresto);
			Restaurant updatedRestaurant = updateRestaurant(idresto,
					existRestaurant);
			return updatedRestaurant;
		} else {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelperResto.COLUMN_NOM, nom);
			values.put(MySQLiteHelperResto.COLUMN_longitude, longitude);
			values.put(MySQLiteHelperResto.COLUMN_latitude, latitude);
			values.put(MySQLiteHelperResto.COLUMN_description, description);
			values.put(MySQLiteHelperResto.COLUMN_email, email);
			values.put(MySQLiteHelperResto.COLUMNhorairehouverture,
					horairehouverture);
			values.put(MySQLiteHelperResto.COLUMN_horairefermeture,
					horairefermeture);
			values.put(MySQLiteHelperResto.COLUMN_siteweb, siteweb);
			values.put(MySQLiteHelperResto.COLUMN_image, image);
			values.put(MySQLiteHelperResto.COLUMN_telephone, telephone);
			values.put(MySQLiteHelperResto.COLUMN_ville, ville);
			values.put(MySQLiteHelperResto.COLUMN_pays, pays);
			long insertId = database.insert(
					MySQLiteHelperResto.TABLE_Restaurant, null, values);
			Cursor cursor = database.query(
					MySQLiteHelperResto.TABLE_Restaurant, allColumns,
					MySQLiteHelperResto.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Restaurant newRestaurant = cursorTorestaurant(cursor);
			cursor.close();
			return newRestaurant;
		}
	}

	public Restaurant updateRestaurant(long idresto, Restaurant restaurant) {
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelperResto.COLUMN_NOM, restaurant.getNom());
		values.put(MySQLiteHelperResto.COLUMN_longitude,
				restaurant.getLongitude());
		values.put(MySQLiteHelperResto.COLUMN_latitude,
				restaurant.getLatitude());
		values.put(MySQLiteHelperResto.COLUMN_description,
				restaurant.getDescription());
		values.put(MySQLiteHelperResto.COLUMN_email, restaurant.getEmail());
		values.put(MySQLiteHelperResto.COLUMNhorairehouverture,
				restaurant.getHorairehouverture());
		values.put(MySQLiteHelperResto.COLUMN_horairefermeture,
				restaurant.getHorairefermeture());
		values.put(MySQLiteHelperResto.COLUMN_siteweb, restaurant.getSiteweb());
		values.put(MySQLiteHelperResto.COLUMN_image, restaurant.getImage());
		values.put(MySQLiteHelperResto.COLUMN_telephone,
				restaurant.getTelephone());
		values.put(MySQLiteHelperResto.COLUMN_ville,
				restaurant.getVille());
		values.put(MySQLiteHelperResto.COLUMN_pays,
				restaurant.getPays());

		database.update(
				MySQLiteHelperResto.TABLE_Restaurant,
				values,
				MySQLiteHelperResto.COLUMN_ID + " = " + restaurant.getIdresto(),
				null);

		return getRestaurantWithId(restaurant.getIdresto());
	}

	public Restaurant getRestaurantWithId(long idresto) {
		Cursor c = database.query(MySQLiteHelperResto.TABLE_Restaurant,
				allColumns, MySQLiteHelperResto.COLUMN_ID + " = \"" + idresto
						+ "\"", null, null, null, null);
		c.moveToFirst();
		Restaurant restaurant = cursorTorestaurant(c);
		c.close();
		return restaurant;
	}

	public Restaurant getRestaurantWithName(String nom) {
		// Récupère dans un Cursor les valeur correspondant à un monument
		// contenu dans la BDD (ici on sélectionne le monument grâce à son nom)
		Cursor c = database.rawQuery("SELECT * FROM '"
				+ MySQLiteHelperResto.TABLE_Restaurant + "' WHERE nom = '"
				+ nom.trim() + "'", null);
		c.moveToFirst();
		Restaurant restaurant = cursorTorestaurant(c);
		c.close();
		return restaurant;
	}

	public Boolean existRestaurantWithId(long idresto) {
		Cursor c = database.query(MySQLiteHelperResto.TABLE_Restaurant,
				allColumns, MySQLiteHelperResto.COLUMN_ID + " = \"" + idresto
						+ "\"", null, null, null, null);
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void deleteRestaurant(Restaurant restaurant) {
		long id = restaurant.getIdresto();
		System.out.println("Restaurant deleted with id: " + id);
		database.delete(MySQLiteHelperResto.TABLE_Restaurant,
				MySQLiteHelperResto.COLUMN_ID + " = " + id, null);
	}

	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();

		Cursor cursor = database.query(MySQLiteHelperResto.TABLE_Restaurant,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Restaurant restaurant = cursorTorestaurant(cursor);
			restaurants.add(restaurant);
			cursor.moveToNext();
		}

		cursor.close();
		return restaurants;
	}

	private Restaurant cursorTorestaurant(Cursor cursor) {
		Restaurant restaurant = new Restaurant();
		restaurant.setIdresto(cursor.getLong(0));
		restaurant.setNom(cursor.getString(1));
		restaurant.setLongitude(cursor.getString(2));
		restaurant.setLatitude(cursor.getString(3));
		restaurant.setDescription(cursor.getString(4));
		restaurant.setEmail(cursor.getString(5));
		restaurant.setHorairehouverture(cursor.getString(6));
		restaurant.setHorairefermeture(cursor.getString(7));
		restaurant.setSiteweb(cursor.getString(8));
		restaurant.setImage(cursor.getString(9));
		restaurant.setTelephone(cursor.getString(10));
		restaurant.setVille(cursor.getString(11));
		restaurant.setPays(cursor.getString(12));
		return restaurant;
	}

}
