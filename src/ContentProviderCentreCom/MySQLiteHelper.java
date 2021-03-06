package ContentProviderCentreCom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_CentreComercial = "centreCommercial";
	public static final String COLUMN_ID = "idcc";
	public static final String COLUMN_NOM = "nom";
	public static final String COLUMN_longitude = "longitude";
	public static final String COLUMN_latitude = "latitude";
	public static final String COLUMN_description = "description";
	public static final String COLUMN_categorie = "type";
	public static final String COLUMN_siteWeb = "siteweb";
	public static final String COLUMN_telephone = "telephone";
	public static final String COLUMN_image = "image";
	public static final String COLUMN_email = "email";
	public static final String COLUMN_ville = "ville";
	public static final String COLUMN_pays = "pays";
	private static final String DATABASE_NAME = "centrecommercial.db";
	private static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CentreComercial + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NOM + " text , "
			+ COLUMN_longitude + " text , " + COLUMN_latitude + " text , "
			+ COLUMN_description + " text , " + COLUMN_email + " text , "
			+ COLUMN_ville + " text , " + COLUMN_pays + " text , "
			+ COLUMN_telephone + " text,"+COLUMN_siteWeb + " text," + COLUMN_image + " text);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CentreComercial);
		onCreate(db);
	}
}