package comic.shannortrotty.gruntt.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.services.DatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ComicDetailPresenter implements ComicDetialPresenter, NetworkModel.OnResponseItemListener<ComicDetails> {

    private GenericView<ComicDetails> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private Call<ComicDetails> call;
    private boolean canceled;
    private boolean finished;
    private static final String TAG = "ComicDetailPresenter";

    public ComicDetailPresenter(Context context, GenericView<ComicDetails> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = context;
        this.canceled = false;
        this.finished = false;
    }

    @Override
    public void onDestroy() {
        genericView = null;
    }

    @Override
    public void onItemSuccess(ComicDetails item) {
        finished = true;
        if(!canceled) {
            genericView.setItem(item);
            genericView.hideLoading();
        }
    }

    @Override
    public void cancelRequest() {
        if(!finished) {
            canceled = true;
            call.cancel();
        }

    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        finished = false;
        if(requestType.getType() == RequestType.Type.LOAD){
            //Perform Database call, if found in database load it.
            ComicDetails comicDetails = checkDatabase(requestType.getExtras().get(Constants.COMIC_NAME));
            if(comicDetails == null){
                networkModel.getComicDescription(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    this);
            }else{
                onItemSuccess(comicDetails);
            }

        }else{
//          Throw some error here
        }
    }

    @Override
    public void setRequestCall(Call<ComicDetails> call) {
        this.call = call;
    }


    public void addToFavorites(ComicDetails comicDetails) {
        //Get data base reference
        SQLiteDatabase db = new DatabaseHelper(mContext).getWritableDatabase();
        byte[] comicImageByteArray = DatabaseHelper.getBytes(comicDetails.getLocalBitmap());


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

//        values = new ContentValues();
//        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE, comicDetails.getTitle());
//        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
//                DatabaseHelper.getJSONChapterList(chapterList));
//        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
//                DatabaseHelper.getJSONChapterString(lastReadChapter));
//
//        db.insert(DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS, null, values);
        db.close();
    }

    public void removeFromFavorites(ComicDetails comicDetails) {
        SQLiteDatabase db = new DatabaseHelper(mContext).getWritableDatabase();
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


    /**
     *
     * @param comicName Name of Desired Comic
     * @return comicDetails Information about the comic
     */
    private ComicDetails checkDatabase(String comicName){
        DatabaseHelper mDatabase = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDatabase.getReadableDatabase();

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

        // Filter results WHERE "title" = 'Title of Comic correctly'
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

            comicDetails.setLocalBitmap(DatabaseHelper.getImage(
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
