package comic.shannortrotty.gruntt.presenter;

import android.content.ContentValues;
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
import comic.shannortrotty.gruntt.services.DatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/16/17.
 */

public class ChapterPresenter implements  GenericPresenter, NetworkModel.OnResponseListListener<Chapter>{
    private GenericView<Chapter> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private boolean finished;
    private Call call;
    private static final String TAG = "ChapterPresenter";

    public ChapterPresenter(Context mContext, GenericView<Chapter> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = mContext;
        this.canceled = false;
        this.finished = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        if (requestType.getType() == RequestType.Type.CHAPTERS) {
            List<Chapter> chapters = checkDatabaseForChapterList(requestType.getExtras().get(Constants.COMIC_NAME));
            if (chapters.isEmpty()) {
                networkModel.getChapters(
                        requestType.getExtras().get(Constants.COMIC_LINK),
                        this);
            } else {
                //Remove last read chapter from the front
                Chapter lastRead = chapters.get(0);
                chapters.remove(0);
                genericView.setItem(lastRead);
                onListFinished(chapters);
            }
        }else {
//            Throw Error
        }
    }


    @Override
    public void onDestroy(){
        genericView = null;
        networkModel = null;
    }

    @Override
    public void onCanceledRequest(Call<List<Chapter>> call) {
        this.call = call;
    }

    @Override
    public void cancelRequest() {
        canceled = true;
    }

    @Override
    public void onListFinished(List<Chapter> items) {
        if(!canceled) {
            genericView.setItems(items);
            genericView.hideLoading();
        }
    }


    public void saveComicProgress(String comicName, List<Chapter>chapterList, Chapter last_read_chapter, int chapterIndex){
        SQLiteDatabase db = new DatabaseHelper(mContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        last_read_chapter.setHaveRead(true);
        chapterList.get(chapterIndex).setHaveRead(true);
        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
                DatabaseHelper.getJSONChapterString(last_read_chapter));
        contentValues.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
                DatabaseHelper.getJSONChapterList(chapterList));

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
    private @NonNull
    List<Chapter> checkDatabaseForChapterList(String comicName){
        DatabaseHelper mDatabase = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDatabase.getReadableDatabase();
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

        // Filter results WHERE "title" = 'Title of Comic correctly'
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
        //Only one item in the database
        if ( cursor.getCount() > 0) {
            //inside of Favorites
            if(cursor.moveToFirst()){
                chapters = DatabaseHelper.getChapterList(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST)));
                lastReadChapter = DatabaseHelper.getChapter(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER)));
                //Add lastChapter to the front
                chapters.add(0, lastReadChapter);
                db.close();
                return chapters;
            }
        }
        cursor.close();
        db.close();
        return chapters;
    }
}


