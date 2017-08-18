package comic.shannortrotty.gruntt.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import comic.shannortrotty.gruntt.classes.ComicDetails;

/**
 * Created by shannortrotty on 3/11/17.
 * Sqlite Database implementation
 */

public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseInteractions {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "gruntt.db";
    //String to create ComicInterface Favorite Table and Columns
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

    public SQLiteHelper(Context context){
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

    @Override
    public void saveComicDetails(ComicDetails comicDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] comicImageByteArray = SQLiteHelper.getBytes(comicDetails.getLocalBitmap());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE, comicDetails.getTitle());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_ALT_TITLE, comicDetails.getAltTitle());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_AUTHOR, comicDetails.getAuthor());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_DESCRIPTION, comicDetails.getDescription());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_GENRE, comicDetails.getGenre());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_STATUS, comicDetails.getStatus());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_RELEASE_DATE, comicDetails.getReleaseDate());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_IMAGE, comicImageByteArray);
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_IS_FAVORITE, comicDetails.getFormattedFavorite());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_LINK, comicDetails.getFormattedURL());

        db.insert(DatabaseContract.ComicInfoEntry.TABLE_NAME_FAVORITE, null, values);
        db.close();
    }

    @Override
    public void deleteComicDetails(ComicDetails comicDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        //Should delete was 'LIKE' before
        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " =?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { comicDetails.getTitle() };
        // Drop this comic from favorites list
        db.delete(DatabaseContract.ComicInfoEntry.TABLE_NAME_FAVORITE, selection, selectionArgs);
        //TODO: Remove this and replace with a timely update.
        db.delete(DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS, selection, selectionArgs);
        db.close();
    }

    @Override
    public void saveComicList(String comicTitle, List<Chapter> chapterList, Chapter lastReadChapter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE, comicTitle);
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
                SQLiteHelper.getJSONChapterList(chapterList));
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
                SQLiteHelper.getJSONChapterString(lastReadChapter));

        db.insert(DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS, null, values);
        db.close();
    }

    @Override
    public void deleteComicList(String comicTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        //Should delete was 'LIKE' before
        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " =?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { comicTitle };
        // Drop this comic from favorites list
        db.delete(DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS, selection, selectionArgs);
        db.close();
    }

    @Override
    public void updateComicProgression(String comicName, List<Chapter> chapterList, Chapter last_read_chapter, int chapterIndex) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        last_read_chapter.setHaveRead(true);
        chapterList.get(chapterIndex).setHaveRead(true);
        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
                SQLiteHelper.getJSONChapterString(last_read_chapter));
        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
                SQLiteHelper.getJSONChapterList(chapterList));

        //Only will save the Comics that are favorites, since fails when not a favorite.
        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectArgs = { comicName };

        db.update(
                DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS,
                contentValues,
                selection,
                selectArgs
        );
        db.close();
    }

    @Override
    public List<Chapter> getSavedComicList(String comicName) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Chapter> chapters = new ArrayList<>();
        Chapter lastReadChapter;
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.ComicInfoEntry._ID,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST
        };

        // Filter results WHERE "title" = 'Title of ComicInterface correctly'
        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { comicName };

        Cursor cursor = db.query(
                DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        //inside of Favorites
        if(cursor.moveToFirst()){
            chapters = SQLiteHelper.getChapterList(cursor.getString(cursor.getColumnIndexOrThrow(
                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST)));
            lastReadChapter = SQLiteHelper.getChapter(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER)));
            //Add lastChapter to the from
            if(lastReadChapter != null){
                chapters.add(0, lastReadChapter);
            }

            cursor.close();
            db.close();
            return chapters;
        }
        cursor.close();
        db.close();
        return chapters;
    }

    @Override
    public ComicDetails getComicDetails(String comicName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.ComicInfoEntry._ID,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_ALT_TITLE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_AUTHOR,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_DESCRIPTION,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_GENRE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_STATUS,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_RELEASE_DATE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_IMAGE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_IS_FAVORITE,
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_LINK
        };

        // Filter results WHERE "title" = 'Title of ComicInterface correctly'
        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { comicName };

        Cursor cursor = db.query(
                DatabaseContract.ComicInfoEntry.TABLE_NAME_FAVORITE,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if ( cursor.getCount() > 0) {
            //inside of Favorites
            ComicDetails comicDetails = new ComicDetails();
            cursor.moveToFirst();
            //Should only be one item
            comicDetails.setAuthor(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_AUTHOR)));
            comicDetails.setDescription(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_DESCRIPTION)));

            comicDetails.setStatus(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_STATUS)));

            comicDetails.setLocalBitmap(SQLiteHelper.getImage(
                    cursor.getBlob(
                            cursor.getColumnIndexOrThrow(
                                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_IMAGE))));
            comicDetails.setAltTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_ALT_TITLE)));

            comicDetails.setFavoriteFromDatabase(cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComicInfoEntry.COLUMN_NAME_IS_FAVORITE)));

            comicDetails.setGenre(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_GENRE)));

            comicDetails.setReleaseDate(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_RELEASE_DATE)));

            comicDetails.setTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE)));

            comicDetails.setLink(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_COMIC_LINK)));
            cursor.close();
            return comicDetails;
        }
        cursor.close();
        return null;
    }
}
