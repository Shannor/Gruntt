package comic.shannortrotty.gruntt.services;

import android.provider.BaseColumns;
import android.util.StringBuilderPrinter;

/**
 * Created by shannortrotty on 3/11/17.
 * Forms the contract for the comic favorites Database Table
 */

public final class ComicDatabaseContract {

    //So that no one can create this class
    private ComicDatabaseContract(){}

    public static class ComicInfoEntry implements BaseColumns{

        //Table Names
        public static final String TABLE_NAME_FAVORITE = "favorite_comics";
        public static final String TABLE_NAME_CHAPTERS = "comic_chapters";

        //Comic Details Columns
        public static final String COLUMN_NAME_TITLE = "comic_title";
        public static final String COLUMN_NAME_STATUS = "comic_status";
        public static final String COLUMN_NAME_AUTHOR = "comic_author";
        public static final String COLUMN_NAME_GENRE = "comic_genre";
        public static final String COLUMN_NAME_RELEASE_DATE = "comic_release_date";
        public static final String COLUMN_NAME_ALT_TITLE = "comic_alt_name";
        public static final String COLUMN_NAME_DESCRIPTION = "comic_description";
        public static final String COLUMN_NAME_COMIC_IMAGE = "comic_image";
        public static final String COLUMN_NAME_IS_FAVORITE =  "comic_is_favorite";


        //Comic Chapter List Columns
        public static final String COLUMN_NAME_LAST_READ_COMIC = "last_comic_read";
        public static final String COLUMN_NAME_CHAPTER_LIST = "comic_chapter_list";

    }


}
