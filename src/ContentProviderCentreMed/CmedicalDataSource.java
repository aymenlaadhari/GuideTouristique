package ContentProviderCentreMed;

import java.util.ArrayList;
import java.util.List;

import model.CentreMedicale;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CmedicalDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelperCentreMed dbHelper;
	private String[] allColumns = { MySQLiteHelperCentreMed.COLUMN_ID,
			MySQLiteHelperCentreMed.COLUMN_NOM,
			MySQLiteHelperCentreMed.COLUMN_longitude,
			MySQLiteHelperCentreMed.COLUMN_latitude,
			MySQLiteHelperCentreMed.COLUMN_description,
			MySQLiteHelperCentreMed.COLUMN_siteWeb,
			MySQLiteHelperCentreMed.COLUMN_telephone,
			MySQLiteHelperCentreMed.COLUMN_image,
			MySQLiteHelperCentreMed.COLUMN_categorie,
			MySQLiteHelperCentreMed.COLUMN_email,
			MySQLiteHelperCentreMed.COLUMN_ville,
			MySQLiteHelperCentreMed.COLUMN_pays, };

	public CmedicalDataSource(Context context) {
		dbHelper = new MySQLiteHelperCentreMed(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public CentreMedicale createCentre(long id, String telephone, String image,
			String email, String nom, String description, String siteweb,
			String longitude, String latitude, String categorie, String ville,
			String pays) {
		Boolean exist = existCentreWithId(id);

		if (exist == true) {
			CentreMedicale existCentre = getCentreWithId(id);
			CentreMedicale updatedCentre = updateCentre(id, existCentre);
			return updatedCentre;
		} else {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelperCentreMed.COLUMN_NOM, nom);
			values.put(MySQLiteHelperCentreMed.COLUMN_longitude, longitude);
			values.put(MySQLiteHelperCentreMed.COLUMN_latitude, latitude);
			values.put(MySQLiteHelperCentreMed.COLUMN_description, description);
			values.put(MySQLiteHelperCentreMed.COLUMN_categorie, categorie);
			values.put(MySQLiteHelperCentreMed.COLUMN_siteWeb, siteweb);
			values.put(MySQLiteHelperCentreMed.COLUMN_telephone, telephone);
			values.put(MySQLiteHelperCentreMed.COLUMN_image, image);
			values.put(MySQLiteHelperCentreMed.COLUMN_email, email);
			values.put(MySQLiteHelperCentreMed.COLUMN_ville, ville);
			values.put(MySQLiteHelperCentreMed.COLUMN_pays, pays);
			long insertId = database
					.insert(MySQLiteHelperCentreMed.TABLE_CentreComercial,
							null, values);
			Cursor cursor = database.query(
					MySQLiteHelperCentreMed.TABLE_CentreComercial, allColumns,
					MySQLiteHelperCentreMed.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			CentreMedicale newCentre = cursorToCentre(cursor);
			cursor.close();
			return newCentre;
		}
	}

	public CentreMedicale updateCentre(long id, CentreMedicale centre) {
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelperCentreMed.COLUMN_NOM, centre.getNom());
		values.put(MySQLiteHelperCentreMed.COLUMN_longitude,
				centre.getLongitude());
		values.put(MySQLiteHelperCentreMed.COLUMN_latitude,
				centre.getLatitude());
		values.put(MySQLiteHelperCentreMed.COLUMN_description,
				centre.getDescription());
		values.put(MySQLiteHelperCentreMed.COLUMN_categorie,
				centre.getCategorie());
		values.put(MySQLiteHelperCentreMed.COLUMN_siteWeb, centre.getSiteweb());
		values.put(MySQLiteHelperCentreMed.COLUMN_telephone,
				centre.getTelephone());
		values.put(MySQLiteHelperCentreMed.COLUMN_image, centre.getImage());
		values.put(MySQLiteHelperCentreMed.COLUMN_email, centre.getEmail());
		values.put(MySQLiteHelperCentreMed.COLUMN_ville, centre.getVille());
		values.put(MySQLiteHelperCentreMed.COLUMN_pays, centre.getPays());

		database.update(MySQLiteHelperCentreMed.TABLE_CentreComercial, values,
				MySQLiteHelperCentreMed.COLUMN_ID + " = " + centre.getId(),
				null);

		return getCentreWithId(centre.getId());
	}

	public CentreMedicale getCentreWithId(long id) {
		Cursor c = database.query(
				MySQLiteHelperCentreMed.TABLE_CentreComercial, allColumns,
				MySQLiteHelperCentreMed.COLUMN_ID + " = \"" + id + "\"", null,
				null, null, null);
		c.moveToFirst();
		CentreMedicale centre = cursorToCentre(c);
		c.close();
		return centre;
	}

	public Boolean existCentreWithId(long id) {
		Cursor c = database.query(
				MySQLiteHelperCentreMed.TABLE_CentreComercial, allColumns,
				MySQLiteHelperCentreMed.COLUMN_ID + " = \"" + id + "\"", null,
				null, null, null);
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void deleteCentre(CentreMedicale centre) {
		long id = centre.getId();
		System.out.println("Contact deleted with id: " + id);
		database.delete(MySQLiteHelperCentreMed.TABLE_CentreComercial,
				MySQLiteHelperCentreMed.COLUMN_ID + " = " + id, null);
	}

	public List<CentreMedicale> getAllCentreMedicales() {
		List<CentreMedicale> centres = new ArrayList<CentreMedicale>();

		Cursor cursor = database.query(
				MySQLiteHelperCentreMed.TABLE_CentreComercial, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CentreMedicale centre = cursorToCentre(cursor);
			centres.add(centre);
			cursor.moveToNext();
		}

		cursor.close();
		return centres;
	}

	private CentreMedicale cursorToCentre(Cursor cursor) {
		CentreMedicale centre = new CentreMedicale();
		centre.setId(cursor.getLong(0));
		centre.setNom(cursor.getString(1));
		centre.setLongitude(cursor.getString(2));
		centre.setLatitude(cursor.getString(3));
		centre.setDescription(cursor.getString(4));
		centre.setCategorie(cursor.getString(5));
		centre.setSiteweb(cursor.getString(6));
		centre.setTelephone(cursor.getString(7));
		centre.setImage(cursor.getString(8));
		centre.setEmail(cursor.getString(9));
		centre.setVille(cursor.getString(10));
		centre.setPays(cursor.getString(11));
		return centre;
	}

}
