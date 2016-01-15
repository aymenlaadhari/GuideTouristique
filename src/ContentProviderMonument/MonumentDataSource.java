package ContentProviderMonument;

import java.util.ArrayList;
import java.util.List;

import model.Monument;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MonumentDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_NOM, MySQLiteHelper.COLUMN_longitude,
			MySQLiteHelper.COLUMN_latitude, MySQLiteHelper.COLUMN_description,
			MySQLiteHelper.COLUMN_type, MySQLiteHelper.COLUMN_image,
			MySQLiteHelper.COLUMN_dateconstruction,
			MySQLiteHelper.COLUMN_ville, MySQLiteHelper.COLUMN_pays, };

	public MonumentDataSource(Context context)
	{
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close() 
	{
		dbHelper.close();
	}

	public Monument createMonument(Long idMonument, String nomM,
			String longitude, String latitude, String description, String type,
			String image, String dateconstruction, String ville, String pays) {
		Boolean exist = existMonumentWithId(idMonument);

		if (exist == true) {
			Monument existMonument = getMonumentWithId(idMonument);
			Monument updatedMonument = updateContact(idMonument, existMonument);
			return updatedMonument;
		} else {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.COLUMN_NOM, nomM);
			values.put(MySQLiteHelper.COLUMN_longitude, longitude);
			values.put(MySQLiteHelper.COLUMN_latitude, latitude);
			values.put(MySQLiteHelper.COLUMN_description, description);
			values.put(MySQLiteHelper.COLUMN_type, type);
			values.put(MySQLiteHelper.COLUMN_image, image);
			values.put(MySQLiteHelper.COLUMN_dateconstruction, dateconstruction);
			values.put(MySQLiteHelper.COLUMN_ville, ville);
			values.put(MySQLiteHelper.COLUMN_pays, pays);
			long insertId = database.insert(MySQLiteHelper.TABLE_MONUMENT,
					null, values);
			Cursor cursor = database.query(MySQLiteHelper.TABLE_MONUMENT,
					allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId,
					null, null, null, null);
			cursor.moveToFirst();
			Monument newMunument = cursorToMonument(cursor);
			cursor.close();
			return newMunument;
		}
	}

	public Monument updateContact(long idMonument, Monument monument) {
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_NOM, monument.getNomM());
		values.put(MySQLiteHelper.COLUMN_longitude, monument.getLongitude());
		values.put(MySQLiteHelper.COLUMN_latitude, monument.getLatitude());
		values.put(MySQLiteHelper.COLUMN_description, monument.getDescription());
		values.put(MySQLiteHelper.COLUMN_type, monument.getType());
		values.put(MySQLiteHelper.COLUMN_image, monument.getImage());
		values.put(MySQLiteHelper.COLUMN_dateconstruction,
				monument.getDateconstruction());
		values.put(MySQLiteHelper.COLUMN_ville, monument.getVille());
		values.put(MySQLiteHelper.COLUMN_pays, monument.getPays());

		database.update(MySQLiteHelper.TABLE_MONUMENT, values,
				MySQLiteHelper.COLUMN_ID + " = " + monument.getIdMonument(),
				null);

		return getMonumentWithId(monument.getIdMonument());
	}

	public Monument getMonumentWithId(long idMonument) {
		Cursor c = database.query(MySQLiteHelper.TABLE_MONUMENT, allColumns,
				MySQLiteHelper.COLUMN_ID + " = \"" + idMonument + "\"", null,
				null, null, null);
		c.moveToFirst();
		Monument monument = cursorToMonument(c);
		c.close();
		return monument;
	}

	public Boolean existMonumentWithId(long idMonument) {
		Cursor c = database.query(MySQLiteHelper.TABLE_MONUMENT, allColumns,
				MySQLiteHelper.COLUMN_ID + " = \"" + idMonument + "\"", null,
				null, null, null);
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void deleteContact(Monument monument) {
		long id = monument.getIdMonument();
		System.out.println("Contact deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_MONUMENT, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Monument> getAllMonuments() {
		List<Monument> monuments = new ArrayList<Monument>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_MONUMENT,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Monument monument = cursorToMonument(cursor);
			monuments.add(monument);
			cursor.moveToNext();
		}

		cursor.close();
		return monuments;
	}

	private Monument cursorToMonument(Cursor cursor) {
		Monument monument = new Monument();
		monument.setIdMonument(cursor.getLong(0));
		monument.setNomM(cursor.getString(1));
		monument.setLongitude(cursor.getString(2));
		monument.setLatitude(cursor.getString(3));
		monument.setDescription(cursor.getString(4));
		monument.setType(cursor.getString(5));
		monument.setImage(cursor.getString(6));
		monument.setDateconstruction(cursor.getString(7));
		monument.setVille(cursor.getString(8));
		monument.setPays(cursor.getString(9));
		return monument;
	}

	public Monument getmonumentWithName(String nom) {
		// Récupère dans un Cursor les valeur correspondant à un monument
		// contenu dans la BDD (ici on sélectionne le monument grâce à son nom)
		Cursor c = database.rawQuery(
				"SELECT * FROM '" + MySQLiteHelper.TABLE_MONUMENT
						+ "' WHERE nomM = '" + nom.trim() + "'", null);
		c.moveToFirst();
		Monument monument = cursorToMonument(c);
		c.close();
		return monument;
	}

}
