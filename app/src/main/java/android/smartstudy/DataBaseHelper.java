package android.smartstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    // tabela 1 - User
    private final static String DATABASE_NAME = "SmartStudyDataBase.db";

    private final static String TABLE_NAME_USER = "Users";
    private final static String TABLE_COLUMN_ID_USER = "Id";
    private final static String TABLE_COLUMN_NAME_USER = "Name";
    private final static String TABLE_COLUMN_SURNAME_USER = "Surname";
    private final static String TABLE_COLUMN_UNIVERISTY_USER = "Study";
    private final static String TABLE_COLUMN_LOGIN_USER = "Login";
    private final static String TABLE_COLUMN_PASSWORD_USER = "Password";

    // tabela 2 - Note
    private final static String TABLE_NAME_NOTE = "Notes";
    private final static String TABLE_COLUMN_ID_NOTE = "Id";
    private final static String TABLE_COLUMN_TEXT_NOTE = "Text";
    private final static String TABLE_COLUMN_DATE_NOTE = "LocalDate";
    private final static String TABLE_COLUMN_USER_LOGIN_NOTE = "UserLogin";

    // tabela 3 - Lesson
    private final static String TABLE_NAME_LESSON = "Lessons";
    private final static String TABLE_COLUMN_ID_LESSON = "Id";
    private final static String TABLE_COLUMN_START_LESSON = "StartTime";
    private final static String TABLE_COLUMN_DAY_LESSON = "Day";
    private final static String TABLE_COLUMN_ROOM_LESSON = "Room";
    private final static String TABLE_COLUMN_TEXT_LESSON = "Text";
    private final static String TABLE_COLUMN_DURATION_LESSON = "Duration";
    private final static String TABLE_COLUMN_USER_LOGIN_LESSON = "UserLogin";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUser = "CREATE TABLE " + TABLE_NAME_USER +
                            " (" + TABLE_COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            TABLE_COLUMN_NAME_USER + " TEXT, " +
                            TABLE_COLUMN_SURNAME_USER + " TEXT, " +
                            TABLE_COLUMN_UNIVERISTY_USER + " TEXT, " +
                            TABLE_COLUMN_LOGIN_USER + " TEXT, " +
                            TABLE_COLUMN_PASSWORD_USER + " TEXT);";
        db.execSQL(queryUser);

        String queryNote = "CREATE TABLE " + TABLE_NAME_NOTE +
                            " (" + TABLE_COLUMN_ID_NOTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            TABLE_COLUMN_TEXT_NOTE + " TEXT, " +
                            TABLE_COLUMN_DATE_NOTE + " TEXT, " +
                            TABLE_COLUMN_USER_LOGIN_NOTE + " TEXT, " +
                            " FOREIGN KEY " + "(" + TABLE_COLUMN_USER_LOGIN_NOTE + ")" + " REFERENCES " + TABLE_NAME_USER + "(" + TABLE_COLUMN_LOGIN_USER + ")" + ");";
        db.execSQL(queryNote);

        String queryLesson = "CREATE TABLE " + TABLE_NAME_LESSON +
                              " (" + TABLE_COLUMN_ID_LESSON + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              TABLE_COLUMN_START_LESSON + " TEXT, " +
                              TABLE_COLUMN_DAY_LESSON + " TEXT, " +
                              TABLE_COLUMN_ROOM_LESSON + " TEXT, " +
                              TABLE_COLUMN_TEXT_LESSON + " TEXT, " +
                              TABLE_COLUMN_DURATION_LESSON + " INTEGER, " +
                              TABLE_COLUMN_USER_LOGIN_LESSON + " TEXT, " +
                              " FOREIGN KEY " + "(" + TABLE_COLUMN_USER_LOGIN_LESSON + ")" + " REFERENCES " + TABLE_NAME_USER + "(" + TABLE_COLUMN_LOGIN_USER + ")" + ");";
        db.execSQL(queryLesson);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // stworzy nową tabelę USERS
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        // stworzy nową tabelę NOTES
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTE);
        // stworzy nową tabelę LESSON
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LESSON);
        // tworzenie nowej tabeli
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_NAME_USER, user.getName());
        cv.put(TABLE_COLUMN_SURNAME_USER, user.getSurname());
        cv.put(TABLE_COLUMN_UNIVERISTY_USER, user.getUniversity());
        cv.put(TABLE_COLUMN_LOGIN_USER, user.getLogin());
        cv.put(TABLE_COLUMN_PASSWORD_USER, user.getPassword());

        long result = db.insert(TABLE_NAME_USER, null, cv);
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

        cv.put(TABLE_COLUMN_TEXT_NOTE, note.getText());
        cv.put(TABLE_COLUMN_DATE_NOTE, date);
        cv.put(TABLE_COLUMN_USER_LOGIN_NOTE, note.getNoteOwner().getLogin());

        long result = db.insert(TABLE_NAME_NOTE, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Notatkę!", Toast.LENGTH_SHORT).show();
        }
    }

    void addLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String startTime = lesson.getStartTime().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");
        String dayOfWeek = lesson.getDayOfWeek().format(formatter);

        cv.put(TABLE_COLUMN_START_LESSON, startTime);
        cv.put(TABLE_COLUMN_DAY_LESSON, dayOfWeek);
        cv.put(TABLE_COLUMN_ROOM_LESSON, lesson.getRoom());
        cv.put(TABLE_COLUMN_TEXT_LESSON, lesson.getText());
        cv.put(TABLE_COLUMN_DURATION_LESSON, lesson.getDuration());
        cv.put(TABLE_COLUMN_USER_LOGIN_LESSON, lesson.getLessonOwner().getLogin());

        long result = db.insert(TABLE_NAME_LESSON, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Bład wprowadzenia danych do bazy danych.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano Lekcję!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean login_user(String login, String password) {
        String queryLogin = "SELECT " + TABLE_COLUMN_LOGIN_USER +  " FROM " + TABLE_NAME_USER;
        String queryPassword = "SELECT " + TABLE_COLUMN_PASSWORD_USER + " FROM " + TABLE_NAME_USER;
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
        String query = "SELECT * FROM " + TABLE_NAME_USER + " WHERE " + TABLE_COLUMN_LOGIN_USER + " = " + "\"" + login + "\"";
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
        String userLogin = currentUser.getLogin();
        String queryNote = "SELECT * FROM " + TABLE_NAME_NOTE + " WHERE " + TABLE_COLUMN_USER_LOGIN_NOTE + " = " + "\"" + userLogin + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorNote = db.rawQuery(queryNote, null);
        cursorNote.moveToFirst();

        List<Note> notes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        while (!cursorNote.isAfterLast()) {
            String noteText = cursorNote.getString(1);
            LocalDate noteDate = LocalDate.parse(cursorNote.getString(2), formatter);
            Note note = new Note(noteText, noteDate, currentUser);
            notes.add(note);
            cursorNote.moveToNext();
        }
        return notes;
    }

    List<Lesson> getAllLesson(User currentUser) {
        String userLogin = currentUser.getLogin();
        String queryLesson = "SELECT * FROM " + TABLE_NAME_LESSON + " WHERE " + TABLE_COLUMN_USER_LOGIN_LESSON + " = " + "\"" + userLogin + "\"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorLesson = db.rawQuery(queryLesson, null);
        cursorLesson.moveToFirst();

        List<Lesson> lessons = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");
        while (!cursorLesson.isAfterLast()) {
            LocalTime startLesson = LocalTime.parse(cursorLesson.getString(1));
            LocalDate dayOfWeek = LocalDate.parse(cursorLesson.getString(2), formatter);
            String room = cursorLesson.getString(3);
            String text = cursorLesson.getString(4);
            int duration = Integer.parseInt(cursorLesson.getString(5));

            Lesson lesson = new Lesson(startLesson, dayOfWeek, room, text, duration, currentUser);
            lessons.add(lesson);
            cursorLesson.moveToNext();
        }

        return lessons;
    }

    List<Note> deleteNote(String text, List<Note> notes, User currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_NOTE, "Text=?", new String[]{text});
        if(result == -1){
            Toast.makeText(context, "Nie udało się usunąć notatki.", Toast.LENGTH_SHORT).show();
            return notes;
        }else{
            Toast.makeText(context, "Pomyślnie usunięto notatkę.", Toast.LENGTH_SHORT).show();

            notes.removeAll(notes);

            notes.addAll(getAllNotes(currentUser));
            return notes;
        }
    }
}