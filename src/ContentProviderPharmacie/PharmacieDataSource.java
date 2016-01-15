package ContentProviderPharmacie;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.cu;

import model.Pharmacie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PharmacieDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelperPharmacie dbHelper;
	private String[] allColumns = { MySQLiteHelperPharmacie.COLUMN_ID,
			MySQLiteHelperPharmacie.COLUMN_NOM,
			MySQLiteHelperPharmacie.COLUMN_longitude,
			MySQLiteHelperPharmacie.COLUMN_latitude,
			MySQLiteHelperPharmacie.COLUMN_description,
			MySQLiteHelperPharmacie.COLUMN_email,
			MySQLiteHelperPharmacie.COLUMNType,
			MySQLiteHelperPharmacie.COLUMN_siteweb,
			MySQLiteHelperPharmacie.COLUMN_image,
			MySQLiteHelperPharmacie.COLUMN_telephone,
			MySQLiteHelperPharmacie.COLUMN_ville,
			MySQLiteHelperPharmacie.COLUMN_pays,};

	public PharmacieDataSource(Context context) {
		dbHelper = new MySQLiteHelperPharmacie(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Pharmacie createPharmacie(long idPharmacie, String nom,
			String longitude, String latitude, String description,
			String email, String type, String siteweb, String image,
			String telephone, String ville, String pays) {
		Boolean exist = existPharmaieWithId(idPharmacie);

		if (exist == true) {
			Pharmacie existpharmacie = getPharmacieWithId(idPharmacie);
			Pharmacie updatedPharmacie = updateHotel(idPharmacie,
					existpharmacie);
			return updatedPharmacie;
		} else {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelperPharmacie.COLUMN_NOM, nom);
			values.put(MySQLiteHelperPharmacie.COLUMN_longitude, longitude);
			values.put(MySQLiteHelperPharmacie.COLUMN_latitude, latitude);
			values.put(MySQLiteHelperPharmacie.COLUMN_description, description);
			values.put(MySQLiteHelperPharmacie.COLUMN_email, email);
			values.put(MySQLiteHelperPharmacie.COLUMNType, type);
			values.put(MySQLiteHelperPharmacie.COLUMN_siteweb, siteweb);
			values.put(MySQLiteHelperPharmacie.COLUMN_image, image);
			values.put(MySQLiteHelperPharmacie.COLUMN_telephone, telephone);
			values.put(MySQLiteHelperPharmacie.COLUMN_ville, ville);
			values.put(MySQLiteHelperPharmacie.COLUMN_pays, pays);
			long insertId = database.insert(
					MySQLiteHelperPharmacie.TABLE_Pharmacie, null, values);
			Cursor cursor = database.query(
					MySQLiteHelperPharmacie.TABLE_Pharmacie, allColumns,
					MySQLiteHelperPharmacie.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Pharmacie newPharmacie = cursorToPharmacie(cursor);
			cursor.close();
			return newPharmacie;
		}
	}

	public Pharmacie updateHotel(long idPharmacie, Pharmacie pharmacie) {
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelperPharmacie.COLUMN_NOM, pharmacie.getNom());
		values.put(MySQLiteHelperPharmacie.COLUMN_longitude,
				pharmacie.getLongitude());
		values.put(MySQLiteHelperPharmacie.COLUMN_latitude,
				pharmacie.getLatitude());
		values.put(MySQLiteHelperPharmacie.COLUMN_description,
				pharmacie.getDescription());
		values.put(MySQLiteHelperPharmacie.COLUMN_email, pharmacie.getEmail());
		values.put(MySQLiteHelperPharmacie.COLUMNType, pharmacie.getType());
		values.put(MySQLiteHelperPharmacie.COLUMN_siteweb,
				pharmacie.getSiteweb());
		values.put(MySQLiteHelperPharmacie.COLUMN_image, pharmacie.getImage());
		values.put(MySQLiteHelperPharmacie.COLUMN_telephone,
				pharmacie.getTelephone());
		values.put(MySQLiteHelperPharmacie.COLUMN_ville, pharmacie.getVille());
		values.put(MySQLiteHelperPharmacie.COLUMN_pays, pharmacie.getPays());

		database.update(MySQLiteHelperPharmacie.TABLE_Pharmacie, values,
				MySQLiteHelperPharmacie.COLUMN_ID + " = " + pharmacie.getId(),
				null);

		return getPharmacieWithId(pharmacie.getId());
	}

	public Pharmacie getPharmacieWithId(long idPharmacie) {
		Cursor c = database.query(MySQLiteHelperPharmacie.TABLE_Pharmacie,
				allColumns, MySQLiteHelperPharmacie.COLUMN_ID + " = \""
						+ idPharmacie + "\"", null, null, null, null);
		c.moveToFirst();
		Pharmacie restaurant = cursorToPharmacie(c);
		c.close();
		return restaurant;
	}

	public Boolean existPharmaieWithId(long idPharmacie) {
		Cursor c = database.query(MySQLiteHelperPharmacie.TABLE_Pharmacie,
				allColumns, MySQLiteHelperPharmacie.COLUMN_ID + " = \""
						+ idPharmacie + "\"", null, null, null, null);
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public Pharmacie getPharmacieWithName(String nom) {
		// Récupère dans un Cursor les valeur correspondant à un monument
		// contenu dans la BDD (ici on sélectionne le monument grâce à son nom)
		Cursor c = database.rawQuery("SELECT * FROM '"
				+ MySQLiteHelperPharmacie.TABLE_Pharmacie + "' WHERE nom = '"
				+ nom.trim() + "'", null);
		c.moveToFirst();
		Pharmacie pharmacie = cursorToPharmacie(c);
		c.close();
		return pharmacie;
	}

	public void deleteHotel(Pharmacie pharmacie) {
		long id = pharmacie.getId();
		System.out.println("Phamacie deleted with id: " + id);
		database.delete(MySQLiteHelperPharmacie.TABLE_Pharmacie,
				MySQLiteHelperPharmacie.COLUMN_ID + " = " + id, null);
	}

	public void deleteAll() {
		database.delete(MySQLiteHelperPharmacie.TABLE_Pharmacie, null, null);
	}

	public List<Pharmacie> getAllPharmacies() {
		List<Pharmacie> pharmacies = new ArrayList<Pharmacie>();

		Cursor cursor = database.query(MySQLiteHelperPharmacie.TABLE_Pharmacie,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Pharmacie pharmacie = cursorToPharmacie(cursor);
			pharmacies.add(pharmacie);
			cursor.moveToNext();
		}

		cursor.close();
		return pharmacies;
	}

	private Pharmacie cursorToPharmacie(Cursor cursor) {
		Pharmacie pharmacie = new Pharmacie();
		pharmacie.setId(cursor.getLong(0));
		pharmacie.setNom(cursor.getString(1));
		pharmacie.setLongitude(cursor.getString(2));
		pharmacie.setLatitude(cursor.getString(3));
		pharmacie.setDescription(cursor.getString(4));
		pharmacie.setEmail(cursor.getString(5));
		pharmacie.setType(cursor.getString(6));
		pharmacie.setSiteweb(cursor.getString(7));
		pharmacie.setImage(cursor.getString(8));
		pharmacie.setTelephone(cursor.getString(9));
		pharmacie.setVille(cursor.getString(10));
		pharmacie.setPays(cursor.getString(11));
		return pharmacie;
	}

}
