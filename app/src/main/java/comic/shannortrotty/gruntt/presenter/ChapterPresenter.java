package comic.shannortrotty.gruntt.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.databases.SQLiteHelper;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.databases.DatabaseContract;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/16/17.
 */

public class ChapterPresenter implements ComicPresenter, NetworkModel.OnResponseListListener<Chapter>,
        NetworkModel.OnResponseItemListener<Chapter>{

    private GenericView<Chapter> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private static final String TAG = "ChapterPresenter";

    public ChapterPresenter(Context mContext, GenericView<Chapter> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = mContext;
        this.canceled = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        //Check Database for information first
        if (requestType.getType() == RequestType.Type.DATABASE || requestType.getType() == RequestType.Type.EITHER) {
            List<Chapter> chapters = new SQLiteHelper(mContext).getSavedComicList(requestType.getExtras().get(Constants.COMIC_NAME));
//            List<Chapter> chapters = checkDatabaseForChapterList(requestType.getExtras().get(Constants.COMIC_NAME));
            //If not in data base perform Network call
            if (chapters.isEmpty()) {
                networkModel.getChapters(
                        requestType.getExtras(Constants.COMIC_LINK),
                        requestType.getExtras(Constants.SOURCE_TAG),
                        this,this);
            } else {
                //Remove last read chapter from the front
                Chapter lastRead = chapters.get(0);
                chapters.remove(0);
                onItemSuccess(lastRead);
                onListSuccess(chapters);
            }
        //Start with Network call first
        }else if (requestType.getType() == RequestType.Type.NETWORK) {
            networkModel.getChapters(
                    requestType.getExtras(Constants.COMIC_LINK),
                    requestType.getExtras(Constants.SOURCE_TAG),
                    this, this);
        }else{
            //Throw error
        }
    }


    @Override
    public void onDestroy(){
        genericView = null;
        networkModel = null;
        mContext = null;
    }

    @Override
    public void setRequestListCall(Call<List<Chapter>> call) {
        this.call = call;
    }

    @Override
    public void setRequestCall(Call<Chapter> call) {
        //Wont Use
    }

    @Override
    public void cancelRequest() {
        canceled = true;
        call.cancel();
    }

    @Override
    public void onListSuccess(List<Chapter> items) {
        if(!canceled) {
            genericView.setItems(items);
            genericView.hideLoading();
        }
    }

    @Override
    public void onItemSuccess(Chapter item) {
        if(!canceled){
            genericView.setItem(item);
        }
    }

    @Override
    public void onItemFailure(APIError error) {
    }

    @Override
    public void onListFailure(APIError error) {
        genericView.setErrorMessage(error);
        genericView.hideLoading();
    }

    @Override
    public void onAPIFailure(Throwable throwable) {

    }

    public void saveToDatabase(String comicTitle, List<Chapter> chapterList, Chapter lastReadChapter){
        SQLiteHelper db = new SQLiteHelper(mContext);
        List<Chapter> chapters = db.getSavedComicList(comicTitle);
        if(chapters.isEmpty()) {
            db.saveComicList(comicTitle, chapterList,lastReadChapter);
        }
    }

    public void saveComicProgress(String comicName, List<Chapter>chapterList, Chapter last_read_chapter, int chapterIndex){
        new SQLiteHelper(mContext).updateComicProgression(comicName, chapterList, last_read_chapter, chapterIndex);
//        SQLiteDatabase db = new SQLiteHelper(mContext).getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        last_read_chapter.setHaveRead(true);
//        chapterList.get(chapterIndex).setHaveRead(true);
//        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
//                SQLiteHelper.getJSONChapterString(last_read_chapter));
//        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
//                SQLiteHelper.getJSONChapterList(chapterList));
//
//        //Only will save the Comics that are favorites, since fails when not a favorite.
//        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " LIKE ?";
//        String[] selectArgs = { comicName };
//
//        db.update(
//                DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS,
//                contentValues,
//                selection,
//                selectArgs
//        );
//        db.close();

    }

//    @NonNull
//    private List<Chapter> checkDatabaseForChapterList(String comicName){
//        return new SQLiteHelper(mContext).getSavedComicList(comicName);
//    }
//        SQLiteHelper mDatabase = new SQLiteHelper(mContext);
//        SQLiteDatabase db = mDatabase.getReadableDatabase();
//        List<Chapter> chapters = new ArrayList<>();
//        Chapter lastReadChapter;
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                DatabaseContract.ComicInfoEntry._ID,
//                DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE,
//                DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
//                DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST
//        };
//
//        // Filter results WHERE "title" = 'Title of ComicInterface correctly'
//        String selection = DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = { comicName };
//
//        Cursor cursor = db.query(
//                DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS,                     // The table to query
//                projection,                               // The columns to return
//                selection,                                // The columns for the WHERE clause
//                selectionArgs,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                null                                 // The sort order
//        );
//        //inside of Favorites
//        if(cursor.moveToFirst()){
//            chapters = SQLiteHelper.getChapterList(cursor.getString(cursor.getColumnIndexOrThrow(
//                    DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST)));
//            lastReadChapter = SQLiteHelper.getChapter(cursor.getString(
//                    cursor.getColumnIndexOrThrow(
//                            DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER)));
//            //Add lastChapter to the from
//            if(lastReadChapter != null){
//                chapters.add(0, lastReadChapter);
//            }
//
//            cursor.close();
//            db.close();
//            return chapters;
//        }
//        cursor.close();
//        db.close();
//        return chapters;
//    }
}


