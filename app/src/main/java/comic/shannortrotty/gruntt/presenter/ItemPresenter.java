package comic.shannortrotty.gruntt.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.services.ComicDatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ItemPresenter<T> implements GenericNetworkPresenter, NetworkModel.OnResponseItemListener<T> {

    private GenericView<T> genericView;
    private NetworkModel networkModel;
    private final Class<T> tClass;
    private Context mContext;
    private static final String TAG = "ItemPresenter";

    public ItemPresenter(Context context,GenericView<T> genericView, NetworkModel networkModel, Class<T> castClass) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.tClass = castClass;
        this.mContext = context;
    }

    @Override
    public void onDestroy() {
        genericView = null;
    }

    @Override
    public void onItemFinished(T item) {
        genericView.setItem(tClass.cast(item));
        genericView.hideLoading();
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        if(requestType.getType() == RequestType.Type.COMICSDESCRIPTION){
            //Perform Database call, if found in database load it.
            ComicDetails comicDetails = checkDatabase(requestType.getExtras().get(Constants.COMIC_NAME));
            if(comicDetails == null){
                networkModel.getComicDescription(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    this);
            }else{
                try{
                    onItemFinished(tClass.cast(comicDetails));
                }catch (ClassCastException e){
                    Log.e(TAG, "startRequest: Database retrieval call ",e);
                    throw e;
                }
            }

        }else{
//          Throw some error here
        }
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
                ComicDatabaseContract.ComicFavoriteEntry._ID,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_ALT_TITLE,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_AUTHOR,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_DESCRIPTION,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_GENRE,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_STATUS,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_RELEASE_DATE,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_COMIC_IMAGE,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_IS_FAVORITE
        };

        // Filter results WHERE "title" = 'Title of Comic correctly'
        String selection = ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { comicName };

        Cursor cursor = db.query(
                ComicDatabaseContract.ComicFavoriteEntry.TABLE_NAME,                     // The table to query
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
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_AUTHOR)));
            comicDetails.setDescription(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_DESCRIPTION)));

            comicDetails.setStatus(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_STATUS)));

            comicDetails.setLocalBitmap(DatabaseHelper.getImage(
                    cursor.getBlob(
                            cursor.getColumnIndexOrThrow(
                                    ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_COMIC_IMAGE))));
            comicDetails.setAltTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_ALT_TITLE)));

            comicDetails.setFavoriteFromDatabase(cursor.getInt(
                    cursor.getColumnIndexOrThrow(ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_IS_FAVORITE)));

            comicDetails.setGenre(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_GENRE)));

            comicDetails.setReleaseDate(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_RELEASE_DATE)));
            comicDetails.setTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE)));
            cursor.close();
            return comicDetails;
        }
        cursor.close();
        return null;
    }
}
