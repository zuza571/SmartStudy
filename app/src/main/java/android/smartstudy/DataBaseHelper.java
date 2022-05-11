package android.smartstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private final static String DATABASE_NAME = "SmartStudyBazaDanych.db";
    private final static int DATABASE_VERSION = 1;

    private final static String TABLE_NAME = "Użytkownicy";
    private final static String TABLE_COLUMN_ID = "id";
    private final static String TABLE_COLUMN_NAME = "Imię";
    private final static String TABLE_COLUMN_SURNAME = "Nazwisko";
    private final static String TABLE_COLUMN_UNIVERISTY = "Studia";
    private final static String TABLE_COLUMN_LOGIN = "Login";
    private final static String TABLE_COLUMN_PASSWORD = "Haslo";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TABLE_COLUMN_NAME + " TEXT, " +
                        TABLE_COLUMN_SURNAME + " TEXT, " +
                        TABLE_COLUMN_UNIVERISTY + " TEXT, " +
                        TABLE_COLUMN_LOGIN + " TEXT, " +
                        TABLE_COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addUser(String imie, String nazwisko, String studia, String login, String haslo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_NAME, imie);
        cv.put(TABLE_COLUMN_SURNAME, nazwisko);
        cv.put(TABLE_COLUMN_UNIVERISTY, studia);
        cv.put(TABLE_COLUMN_LOGIN, login);
        cv.put(TABLE_COLUMN_PASSWORD, haslo);

        long rezultat = db.insert(TABLE_NAME, null, cv);
        if(rezultat == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Użytkownika!", Toast.LENGTH_SHORT).show();
        }
    }
}