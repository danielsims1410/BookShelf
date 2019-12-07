package com.example.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "author_books_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE_AUTHORS = "authors";
    private static final String KEY_ID_AUTHOR = "_id";
    private static final String KEY_NAME_COLUMN_AUTHOR = "NAME_COLUMN";
    private static final String KEY_GENRE_COLUMN_AUTHOR = "GENRE_COLUMN";
    private static final String KEY_WORKS_COLUMN_AUTHOR = "WORKS_COLUMN";
    private static final String KEY_AGE_COLUMN_AUTHOR = "AGE_COLUMN";
    private static final String KEY_IMAGE_COLUMN_AUTHOR = "IMAGE_COLUMN";
    private static final String CREATE_TABLE_AUTHORS = "CREATE TABLE "
            + DATABASE_TABLE_AUTHORS + "(" + KEY_ID_AUTHOR + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME_COLUMN_AUTHOR + " TEXT,"
            + KEY_GENRE_COLUMN_AUTHOR + " TEXT,"
            + KEY_WORKS_COLUMN_AUTHOR + " TEXT,"
            + KEY_AGE_COLUMN_AUTHOR + " INTEGER, "
            + KEY_IMAGE_COLUMN_AUTHOR + " BLOB);";

    private static final String DATABASE_TABLE_BOOKS = "books";
    private static final String KEY_ID_BOOK = "_id";
    private static final String KEY_TITLE_BOOK = "TITLE_COLUMN";
    private static final String KEY_AUTHOR_ID_BOOK = "AUTHOR_ID_COLUMN";
    private static final String KEY_DATE_READ_BOOK = "DATE_COLUMN";
    private static final String KEY_REVIEW_SCORE_BOOK = "REVIEW_COLUMN";
    private static final String KEY_PAGE_COUNT_BOOK = "PAGE_COUNT_COLUMN";
    private static final String CREATE_TABLE_BOOKS = "CREATE TABLE "
            + DATABASE_TABLE_BOOKS + "(" + KEY_ID_BOOK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE_BOOK + " TEXT,"
            + KEY_AUTHOR_ID_BOOK + " INTEGER,"
            + KEY_DATE_READ_BOOK + " TEXT,"
            + KEY_REVIEW_SCORE_BOOK + " INTEGER,"
            + KEY_PAGE_COUNT_BOOK + " INTEGER,"
            + "FOREIGN KEY(" + KEY_AUTHOR_ID_BOOK + ") REFERENCES " + DATABASE_TABLE_AUTHORS + "(" + KEY_ID_AUTHOR + ")" + "ON DELETE CASCADE);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AUTHORS);
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE_AUTHORS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE_BOOKS + "'");
        onCreate(db);
    }

    public void addAuthor(String name, String genre, String notableWorks, int age, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues newAuthor = new ContentValues();
        newAuthor.put(KEY_NAME_COLUMN_AUTHOR, name);
        newAuthor.put(KEY_GENRE_COLUMN_AUTHOR, genre);
        newAuthor.put(KEY_WORKS_COLUMN_AUTHOR, notableWorks);
        newAuthor.put(KEY_AGE_COLUMN_AUTHOR, age);
        newAuthor.put(KEY_IMAGE_COLUMN_AUTHOR, image);
        database.insert(DATABASE_TABLE_AUTHORS, null, newAuthor);
    }

    public void addBook(String title, int authorid, String dateread, int review, int pagecount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues newBook = new ContentValues();
        newBook.put(KEY_TITLE_BOOK, title);
        newBook.put(KEY_AUTHOR_ID_BOOK, authorid);
        newBook.put(KEY_DATE_READ_BOOK, dateread);
        newBook.put(KEY_REVIEW_SCORE_BOOK, review);
        newBook.put(KEY_PAGE_COUNT_BOOK, pagecount);
        database.insert(DATABASE_TABLE_BOOKS, null, newBook);
    }

    public ArrayList<Author> getAuthors() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Author> authorList = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE_AUTHORS;

        Cursor cursor = database.rawQuery(query, null);
        while(cursor.moveToNext()) {
            Author author = new Author();
            author.setID
                    (cursor.getInt(cursor.getColumnIndex(KEY_ID_AUTHOR)));
            author.setAuthorName
                    (cursor.getString(cursor.getColumnIndex(KEY_NAME_COLUMN_AUTHOR)));
            author.setAuthorGenre
                    (cursor.getString(cursor.getColumnIndex(KEY_GENRE_COLUMN_AUTHOR)));
            author.setAuthorNotableWorks
                    (cursor.getString(cursor.getColumnIndex(KEY_WORKS_COLUMN_AUTHOR)));
            author.setAuthorAge
                    (cursor.getString(cursor.getColumnIndex(KEY_AGE_COLUMN_AUTHOR)));
            author.setImage
                    (cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE_COLUMN_AUTHOR)));
            authorList.add(author);
        }
        return authorList;
    }

    public ArrayList<Book> getBooks(int authorID) {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE_BOOKS + " WHERE " + KEY_AUTHOR_ID_BOOK + " = " + authorID;

        Cursor cursor = database.rawQuery(query, null);
        while(cursor.moveToNext()) {
            Book book = new Book();
            book.setBookID
                    (cursor.getInt(cursor.getColumnIndex(KEY_ID_BOOK)));
            book.setBookTitle
                    (cursor.getString(cursor.getColumnIndex(KEY_TITLE_BOOK)));
            book.setDateRead
                    (cursor.getString(cursor.getColumnIndex(KEY_DATE_READ_BOOK)));
            book.setPageCount
                    (cursor.getInt(cursor.getColumnIndex(KEY_PAGE_COUNT_BOOK)));
            book.setReviewScore
                    (cursor.getInt(cursor.getColumnIndex(KEY_REVIEW_SCORE_BOOK)));
            book.setAuthorID
                    (cursor.getInt(cursor.getColumnIndex(KEY_AUTHOR_ID_BOOK)));
            bookList.add(book);
        }
        return bookList;
    }

    public void updateAuthor(int id, String name, String genre, String notableWorks, int age, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        String where = KEY_ID_AUTHOR + " = ?";
        ContentValues updatedAuthor = new ContentValues();
        updatedAuthor.put(KEY_NAME_COLUMN_AUTHOR, name);
        updatedAuthor.put(KEY_GENRE_COLUMN_AUTHOR, genre);
        updatedAuthor.put(KEY_WORKS_COLUMN_AUTHOR, notableWorks);
        updatedAuthor.put(KEY_AGE_COLUMN_AUTHOR, age);
        updatedAuthor.put(KEY_IMAGE_COLUMN_AUTHOR, image);

        String[] whereArgs = new String[] { String.valueOf(id) };
        database.update(DATABASE_TABLE_AUTHORS, updatedAuthor, where, whereArgs);
    }

    public void UpdateBook(int id, String title, int authorid, String dateread, int review, int pagecount) {
        SQLiteDatabase database = this.getWritableDatabase();
        String where = KEY_ID_BOOK + " = ?";
        ContentValues updatedBook = new ContentValues();
        updatedBook.put(KEY_TITLE_BOOK, title);
        updatedBook.put(KEY_AUTHOR_ID_BOOK, authorid);
        updatedBook.put(KEY_DATE_READ_BOOK, dateread);
        updatedBook.put(KEY_REVIEW_SCORE_BOOK, review);
        updatedBook.put(KEY_PAGE_COUNT_BOOK, pagecount);

        String[] whereArgs = new String[] { String.valueOf(id) };
        database.update(DATABASE_TABLE_BOOKS, updatedBook, where, whereArgs);
    }

    public void deleteEntry(int id, String table) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(id)};
        String selectedTable = "", where = "";
        switch(table) {
            case("author"):
                selectedTable = DATABASE_TABLE_AUTHORS;
                where = KEY_ID_AUTHOR + " = ? ";
                break;
            case("book"):
                selectedTable = DATABASE_TABLE_BOOKS;
                where = KEY_ID_BOOK + " = ? ";
                break;
        }
        database.delete(selectedTable, where, whereArgs);
    }
}
