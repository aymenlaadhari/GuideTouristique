package ContentProviderMonument;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_MONUMENT = "monument";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM = "nomM"; 
    public static final String COLUMN_longitude = "longitude";
    public static final String COLUMN_latitude = "latitude";
    public static final String COLUMN_description = "description";
    public static final String COLUMN_type = "type";
    public static final String COLUMN_image = "image";
    public static final String COLUMN_dateconstruction = "dateconstruction";
    public static final String COLUMN_ville = "ville";
    public static final String COLUMN_pays = "pays";
    private static final String DATABASE_NAME = "monument.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
        + TABLE_MONUMENT + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_NOM
        + " text , " + COLUMN_longitude
        + " text , " + COLUMN_latitude
        + " text , " + COLUMN_description
        + " text , " + COLUMN_type
        + " text , " + COLUMN_image
        + " text , " + COLUMN_dateconstruction
        + " text , " + COLUMN_ville
        + " text , " + COLUMN_pays
        + " text);";

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
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONUMENT);
      onCreate(db);
    }
}