package comic.shannortrotty.gruntt.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by shannortrotty on 3/11/17.
 * Sqlite Database implementation
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "comic.db";

    //String to create Comic Favorite Table and Columns
    private static final String SQL_CREATE_COMIC_FAVORITE_TABLE =
            "CREATE TABLE " + ComicDatabaseContract.ComicFavoriteEntry.TABLE_NAME + " (" +
                    ComicDatabaseContract.ComicFavoriteEntry._ID + " INTEGER PRIMARY KEY, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_ALT_TITLE + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_AUTHOR + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_GENRE + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_STATUS + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_RELEASE_DATE + " TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_COMIC_IMAGE + " BLOB, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_LAST_READ_COMIC + "TEXT, " +
                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_CHAPTER_LIST +" TEXT" +
                    ")";

    private static final String SQL_DELETE_COMIC_FAVORITE_TABLE =
            "DROP TABLE IF EXISTS " + ComicDatabaseContract.ComicFavoriteEntry.TABLE_NAME;

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferQualityOverSpeed = true;
        options.inScaled = false;
        return BitmapFactory.decodeByteArray(image, 0, image.length, options);
    }


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COMIC_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_COMIC_FAVORITE_TABLE);
        onCreate(db);
    }
}
