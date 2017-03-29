package comic.shannortrotty.gruntt.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;

/**
 * Created by shannortrotty on 3/11/17.
 * Sqlite Database implementation
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "gruntt.db";
    //String to create Comic Favorite Table and Columns
    private static final String SQL_CREATE_COMIC_FAVORITE_TABLE =
            "CREATE TABLE " + DatabaseContract.ComicInfoEntry.TABLE_NAME_FAVORITE + " (" +
                    DatabaseContract.ComicInfoEntry._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_ALT_TITLE + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_AUTHOR + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_GENRE + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_STATUS + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_RELEASE_DATE + " TEXT, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_IMAGE + " BLOB, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_IS_FAVORITE + " INTEGER, " +
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_LINK + " TEXT " +
                    ")";

    private static final String SQL_CREATE_COMIC_LIST_TABLE = "CREATE TABLE " +
            DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS + " (" +
            DatabaseContract.ComicInfoEntry._ID + " INTEGER PRIMARY KEY, " +
            DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " TEXT, " +
            DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST + " TEXT, " +
            DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER + " TEXT " + ")";


    private static final String SQL_DELETE_COMIC_FAVORITE_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.ComicInfoEntry.TABLE_NAME_FAVORITE;

    private static final String SQL_DELETE_COMIC_LIST_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS;

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //Needed for older versions of Android
        options.inScaled = false;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }

    public static String getJSONChapterList(List<Chapter> chapterList){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(chapterList);
    }

    public static List<Chapter> getChapterList(String jsonString){
        Gson gson = new GsonBuilder().create();
        Type listChapter = new TypeToken<ArrayList<Chapter>>(){}.getType();
        return gson.fromJson(jsonString, listChapter);
    }

    public static String getJSONChapterString(Chapter chapter){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(chapter);
    }

    public static Chapter getChapter(String jsonString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Chapter.class);
    }

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COMIC_FAVORITE_TABLE);
        db.execSQL(SQL_CREATE_COMIC_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_COMIC_FAVORITE_TABLE);
        db.execSQL(SQL_DELETE_COMIC_LIST_TABLE);
        onCreate(db);
    }
}
