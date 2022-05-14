package android.smartstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    // tabela 1 - User
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
        // tworzenie nowej tabeli
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

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Użytkownika!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean login_user(String login, String password) {
        String queryLogin = "SELECT " + TABLE_COLUMN_LOGIN +  " FROM " + TABLE_NAME;
        String queryPassword = "SELECT " + TABLE_COLUMN_PASSWORD + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorLogin = db.rawQuery(queryLogin, null);
        Cursor cursorPassword = db.rawQuery(queryPassword, null);

        cursorLogin.moveToFirst();
        cursorPassword.moveToFirst();
        while(cursorLogin.isAfterLast() == false) {
            if (cursorLogin.getString(0).equals(login) && cursorPassword.getString(0).equals(password)) {
                return true;
            }
            cursorLogin.moveToNext();
            cursorPassword.moveToNext();
        }

        return false;
    }

    List<String> current_user_data(String login) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_LOGIN + " = " + "login";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorUser = db.rawQuery(query, null);
        cursorUser.moveToFirst();

        List<String> currentUserData = new ArrayList<String>();

        for(int i = 0; i < 6; i++) {
            currentUserData.add(cursorUser.getString(i));
        }
        return currentUserData;
    }
}