package comic.shannortrotty.gruntt.services;

import android.provider.BaseColumns;

/**
 * Created by shannortrotty on 3/11/17.
 * Forms the contract for the comic favorites Database Table
 */

public final class ComicDatabaseContract {

    //So that no one can create this class
    private ComicDatabaseContract(){}

    public static class ComicFavoriteEntry implements BaseColumns{

        public static final String TABLE_NAME = "favorite_comics";
        // column names
        public static final String COLUMN_NAME_TITLE = "comic_title";
        public static final String COLUMN_NAME_STATUS = "comic_status";
        public static final String COLUMN_NAME_AUTHOR = "comic_author";
        public static final String COLUMN_NAME_GENRE = "comic_genre";
        public static final String COLUMN_NAME_RELEASE_DATE = "comic_release_date";
        public static final String COLUMN_NAME_ALT_TITLE = "comic_alt_name";
        public static final String COLUMN_NAME_DESCRIPTION = "comic_description";
        //TODO: ADD INFO FOR BITMAP
        public static final String COLUMN_NAME_CHAPTER_LIST = "comic_chapter_list";
    }


}
