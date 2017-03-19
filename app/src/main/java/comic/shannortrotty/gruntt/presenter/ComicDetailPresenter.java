package comic.shannortrotty.gruntt.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

public class ComicDetailPresenter<T> implements GenericPresenter, NetworkModel.OnResponseItemListener<T> {

    private GenericView<T> genericView;
    private NetworkModel networkModel;
    private final Class<T> tClass;
    private Context mContext;
    private Call<T> call;
    private boolean canceled = false;
    private boolean finished = false;
    private static final String TAG = "ComicDetailPresenter";

    public ComicDetailPresenter(Context context, GenericView<T> genericView, NetworkModel networkModel, Class<T> castClass) {
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
        finished = true;
        if(!canceled) {
            genericView.setItem(tClass.cast(item));
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

    @Override
    public void onCanceledRequest(Call<T> call) {
        this.call = call;
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
                DatabaseContract.ComicInfoEntry.COLUMN_NAME_IS_FAVORITE
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
            cursor.close();
            return comicDetails;
        }
        cursor.close();
        return null;
    }
}
