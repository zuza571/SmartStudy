package android.smartstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    // tabela 1
    private final static String DATABASE_NAME = "SmartStudyDataBase.db";

    private final static String TABLE_NAME = "Users";
    private final static String TABLE_COLUMN_ID = "Id";
    private final static String TABLE_COLUMN_NAME = "Name";
    private final static String TABLE_COLUMN_SURNAME = "Surname";
    private final static String TABLE_COLUMN_UNIVERISTY = "Study";
    private final static String TABLE_COLUMN_LOGIN = "Login";
    private final static String TABLE_COLUMN_PASSWORD = "Password";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
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

        // stworzy nową tabelę
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_NAME, user.getName());
        cv.put(TABLE_COLUMN_SURNAME, user.getSurname());
        cv.put(TABLE_COLUMN_UNIVERISTY, user.getUniversity());
        cv.put(TABLE_COLUMN_LOGIN, user.getLogin());
        cv.put(TABLE_COLUMN_PASSWORD, user.getPassword());

        long rezultat = db.insert(TABLE_NAME, null, cv);
        if(rezultat == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Użytkownika!", Toast.LENGTH_SHORT).show();
        }
    }
}