package comic.shannortrotty.gruntt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.fragments.AllComicsFragment;
import comic.shannortrotty.gruntt.fragments.FavoriteComicsFragment;
import comic.shannortrotty.gruntt.fragments.InfoFragment;
import comic.shannortrotty.gruntt.fragments.ChapterListFragment;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.services.DatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;

public class InfoAndChapterActivity extends AppCompatActivity implements InfoFragment.OnInfoFragmentListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String comicLink;
    private String comicTitle;
    private String startingOrigin;
    private static final String TAG = "InfoAndChapterActivity";
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_and_chapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent() != null) {
            comicLink = getIntent().getStringExtra(MainActivity.PICKED_COMIC_LINK);
            comicTitle = getIntent().getStringExtra(MainActivity.PICKED_COMIC_TITLE);
            startingOrigin = getIntent().getStringExtra(MainActivity.PICKED_COMIC_ORIGIN_LOCATION);
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(comicTitle);
        }
//        TODO: User starting origin to decide which Fragment to show first
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mDatabase = new DatabaseHelper(getBaseContext());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager_activity_info_chapter);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(startingOrigin.equals(FavoriteComicsFragment.TAG)){
            mViewPager.setCurrentItem(1, true);
        }else{
            mViewPager.setCurrentItem(0, true);
        }
    }

    /**
     * Method to create the id to get the fragment from the FragmentPager
     * @param viewId Id of the Fragment
     * @param index Location in the FragmentPager
     * @return String
     */
    private String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_and_issue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void resumeReading() {
        String fragmentTag = makeFragmentName(R.id.viewPager_activity_info_chapter,1);
        ChapterListFragment fragment = (ChapterListFragment) getSupportFragmentManager().findFragmentByTag(fragmentTag);
        fragment.resumeReading();
    }

    /**
     *
     * @param comicDetails
     * @param comicImage
     */
    @Override
    public void addToFavorites(ComicDetails comicDetails, Bitmap comicImage) {
        //Get data base reference
        SQLiteDatabase db = mDatabase.getWritableDatabase();
        byte[] comicImageByteArray = DatabaseHelper.getBytes(comicImage);

        String fragmentTag = makeFragmentName(R.id.viewPager_activity_info_chapter,1);
        ChapterListFragment fragment = (ChapterListFragment) getSupportFragmentManager().findFragmentByTag(fragmentTag);
        List<Chapter> chapterList = fragment.getChapters();
        Chapter lastReadChapter = fragment.getLastReadChapter();

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

        values = new ContentValues();
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_TITLE, comicDetails.getTitle());
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_CHAPTER_LIST,
                DatabaseHelper.getJSONChapterList(chapterList));
        values.put(DatabaseContract.ComicInfoEntry.COLUMN_NAME_LAST_READ_CHAPTER,
                DatabaseHelper.getJSONChapterString(lastReadChapter));

        db.insert(DatabaseContract.ComicInfoEntry.TABLE_NAME_CHAPTERS, null, values);
        db.close();
    }

    @Override
    public void removeFromFavorites(ComicDetails comicDetails) {
        SQLiteDatabase db = mDatabase.getWritableDatabase();
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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return InfoFragment.newInstance(comicTitle, comicLink);
                case 1:
                    return ChapterListFragment.newInstance(comicTitle, comicLink);
            }
            return null;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "Chapters";
            }
            return null;
        }
    }

}
