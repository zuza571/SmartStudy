package android.smartstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import android.widget.Toast;

import java.time.LocalDate;
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

    // tabela 2 - Note
    private final static String TABLE_NAME_NOTE = "Notes";
    private final static String TABLE_COLUMN_ID_NOTE = "Id";
    private final static String TABLE_COLUMN_TEXT_NOTE = "Text";
    private final static String TABLE_COLUMN_DATE_NOTE = "LocalDate";
    private final static String TABLE_COLUMN_USER_LOGIN_NOTE = "UserLogin";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUser = "CREATE TABLE " + TABLE_NAME +
                        " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TABLE_COLUMN_NAME + " TEXT, " +
                        TABLE_COLUMN_SURNAME + " TEXT, " +
                        TABLE_COLUMN_UNIVERISTY + " TEXT, " +
                        TABLE_COLUMN_LOGIN + " TEXT, " +
                        TABLE_COLUMN_PASSWORD + " TEXT);";
        db.execSQL(queryUser);

        String queryNote = "CREATE TABLE " + TABLE_NAME_NOTE +
                            " (" + TABLE_COLUMN_ID_NOTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            TABLE_COLUMN_TEXT_NOTE + " TEXT, " +
                            TABLE_COLUMN_DATE_NOTE + " TEXT, " +
                            TABLE_COLUMN_USER_LOGIN_NOTE + " TEXT, " +
                            " FOREIGN KEY " + "(" + TABLE_COLUMN_USER_LOGIN_NOTE + ")" + " REFERENCES " + TABLE_NAME + "(" + TABLE_COLUMN_LOGIN + ")" + ");";
        db.execSQL(queryNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // stworzy nową tabelę USERS
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // stworzy nową tabelę NOTES
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTE);
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

    void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String date = CalendarOperations.dateFormatter(note.getDate());

        cv.put(TABLE_COLUMN_TEXT_NOTE, note.getName());
        cv.put(TABLE_COLUMN_DATE_NOTE, date);
        cv.put(TABLE_COLUMN_USER_LOGIN_NOTE, note.getNoteOwner().getLogin());

        long result = db.insert(TABLE_NAME_NOTE, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Notatkę!", Toast.LENGTH_SHORT).show();
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
        while(!cursorLogin.isAfterLast()) {
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

        List<String> currentUserData = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            currentUserData.add(cursorUser.getString(i));
        }
        return currentUserData;
    }

    List<Note> getAllNotes(User currentUser) {
        String login = currentUser.getLogin();
        String query = "SELECT * FROM " + TABLE_NAME_NOTE + " WHERE " + TABLE_COLUMN_USER_LOGIN_NOTE + " = " + "login";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorNote = db.rawQuery(query, null);
        cursorNote.moveToFirst();

        List<Note> notes = new ArrayList<>();

        while (!cursorNote.isAfterLast()) {
            String noteText = cursorNote.getString(1);
            LocalDate noteDate = LocalDate.parse(cursorNote.getString(2));
            Note note = new Note(noteText, noteDate, currentUser);
            notes.add(note);
            cursorNote.moveToNext();
        }
        return notes;
    }
}