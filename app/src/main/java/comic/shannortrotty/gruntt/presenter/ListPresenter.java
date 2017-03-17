package comic.shannortrotty.gruntt.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.services.ComicDatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;
import comic.shannortrotty.gruntt.view.GenericView;
/**
 * Created by shannortrotty on 2/28/17.
 * Handles General List Types.
 */

public class ListPresenter<T> implements GenericNetworkPresenter, NetworkModel.OnResponseListListener<T> {
    private GenericView<T> genericView;
    private NetworkModel networkModel;
    private Context mContext;

    public ListPresenter(Context context,GenericView<T> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = context;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        if(requestType.getType() == RequestType.Type.POPULARCOMICS){
            networkModel.getPopularComics(
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this);
        }else if (requestType.getType() == RequestType.Type.CHAPTERS){
            List<Chapter> chapters = checkDatabaseForChapterList(requestType.getExtras().get(Constants.COMIC_NAME));
            if( chapters.isEmpty()){
                networkModel.getChapters(
                        requestType.getExtras().get(Constants.COMIC_LINK),
                        this);
            }else{
//                onListFinished(chapters);
            }

        }else if (requestType.getType() == RequestType.Type.PAGES){
            networkModel.getComicPages(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    requestType.getExtras().get(Constants.CHAPTER_NUMBER),
                    this
            );
        }else if(requestType.getType() == RequestType.Type.ALLCOMICS){
            networkModel.getAllComics(this);
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void onListFinished(List<T> items) {
        genericView.setItems(items);
        genericView.hideLoading();
    }


    private @NonNull List<Chapter> checkDatabaseForChapterList(String comicName){
        DatabaseHelper mDatabase = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDatabase.getReadableDatabase();
        List<Chapter> chapters = new ArrayList<>();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ComicDatabaseContract.ComicInfoEntry._ID,
                ComicDatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_COMIC,
                ComicDatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST
        };

        // Filter results WHERE "title" = 'Title of Comic correctly'
        String selection = ComicDatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { comicName };

        Cursor cursor = db.query(
                ComicDatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if ( cursor.getCount() > 0) {
            //inside of Favorites
        }
        cursor.close();
        return chapters;
    }
}
