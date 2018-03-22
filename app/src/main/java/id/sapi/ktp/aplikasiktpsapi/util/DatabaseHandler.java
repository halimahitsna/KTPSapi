package id.sapi.ktp.aplikasiktpsapi.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ASUS on 3/22/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tabelUser";

    // Contacts table name
    private static final String TABLE_USER = "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_USER = "username";
    private static final String KEY_NAMA = "nama";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_USER + "VARCHAR," + KEY_USER + " VARCHAR,"
                + KEY_NAMA + " VARCHAR," + " )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    /*public void addUser(SimpanJawaban jawaban) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_SOAL, jawaban.getId_soal());
        values.put(KEY_ID_JAWABAN, jawaban.getId_jawaban());
        values.put(KEY_KUNCI, jawaban.getKunci());

        // Inserting Row
        db.insert(TABLE_JAWABAN, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public SimpanJawaban getJawaban(int id_soal) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_JAWABAN, new String[]{KEY_ID,
                        KEY_ID_SOAL, KEY_ID_JAWABAN, KEY_KUNCI}, KEY_ID_SOAL + "=?",
                new String[]{String.valueOf(id_soal)}, null, null, null, null);
        SimpanJawaban jawaban = new SimpanJawaban(0, 0, 0, 0);
        if (cursor != null && cursor.moveToFirst()) {

            jawaban = new SimpanJawaban(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
        }
        // return contact
        return jawaban;
    }

    // Updating single contact
    public int updateJawaban(SimpanJawaban jawaban) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_SOAL, jawaban.getId_soal());
        values.put(KEY_ID_JAWABAN, jawaban.getId_jawaban());
        values.put(KEY_KUNCI, jawaban.getKunci());

        // updating row
        return db.update(TABLE_JAWABAN, values, KEY_ID_SOAL + " = ?",
                new String[]{String.valueOf(jawaban.getId_soal())});
    }

    //Drop Table
    public void emptyTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_TABLE = "DELETE FROM " + TABLE_JAWABAN;

        try {
            db.execSQL(DROP_TABLE);
        } catch (SQLException e) {
            Log.e("SQLITE: ", e.toString());
        }

    }*/
}