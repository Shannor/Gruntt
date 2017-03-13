package comic.shannortrotty.gruntt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.fragments.AllComicsFragment;
import comic.shannortrotty.gruntt.fragments.FavoriteComicsFragment;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.OnComicListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FavoriteComicsFragment.OnListFragmentInteractionListener,
        OnComicListener{

    public static final String PICKED_COMIC_LINK ="Comic.picked.link";
    public static final String PICKED_COMIC_TITLE = "Comic.picked.title";
    public static final String PICKED_COMIC_ORIGIN_LOCATION ="Comic.picked.origin.location";
    private static final String TAG = "MainActivity";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set Default Fragment
        //TODO:Should be Dynamic
        mFragment = PopularComicFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, mFragment).commit();
        //Code for Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Need to look up what this does
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Code to handle onclick action in Navigation Drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //TODO: Should be Dynamic
        navigationView.setCheckedItem(R.id.nav_popular_comics);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_all_comics) {
            mFragment = AllComicsFragment.newInstance();
        } else if (id == R.id.nav_stared_comics) {
            mFragment = FavoriteComicsFragment.newInstance(2);
        } else if (id == R.id.nav_popular_comics) {
            mFragment = PopularComicFragment.newInstance();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if( mFragment != null && mFragment != getSupportFragmentManager().findFragmentById(R.id.frame_container)){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.frame_container, mFragment).commit();
        }else{
//            Return an error
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Highlight the selected item, update the title, and close the drawer    // Highlight the selected item, update the title, and close the drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ComicDetails item) {

    }

    @Override
    public void onListComicSelection(PopularComic popularComic, String originOfClick) {
        Intent intent = new Intent(this,InfoAndChapterActivity.class);
        intent.putExtra(PICKED_COMIC_LINK, popularComic.getURLFormattedLink());
        intent.putExtra(PICKED_COMIC_ORIGIN_LOCATION, originOfClick);
        intent.putExtra(PICKED_COMIC_TITLE, popularComic.getTitle());
        startActivity(intent);
    }

}
