package comic.shannortrotty.gruntt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.ComicInterface;
import comic.shannortrotty.gruntt.fragments.AdvancedSearchFragment;
import comic.shannortrotty.gruntt.fragments.ComicsFragment;
import comic.shannortrotty.gruntt.fragments.FavoriteComicsFragment;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.utils.OnComicListener;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnComicListener{

    public static final String PICKED_COMIC_LINK ="ComicInterface.picked.link";
    public static final String PICKED_COMIC_TITLE = "ComicInterface.picked.title";
    public static final String PICKED_COMIC_ORIGIN_LOCATION ="ComicInterface.picked.origin.location";
    private static final String TAG = "MainActivity";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottom_navigation);

        setSupportActionBar(toolbar);

        //Set Default Fragment
        //TODO:Should be Dynamic
        if(mFragment == null){
            mFragment = FavoriteComicsFragment.newInstance(2);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all_comics:
                        replaceFragment(ComicsFragment.newInstance());
                        break;
                    case R.id.nav_my_comics:
                        replaceFragment(FavoriteComicsFragment.newInstance(2));
                        break;
                    case R.id.nav_popular_comics:
                        replaceFragment(PopularComicFragment.newInstance());
                        break;
                    case R.id.nav_search_comics:
                        replaceFragment( AdvancedSearchFragment.newInstance());
                        break;
                }
                return true;
            }
        });

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

    private void replaceFragment(Fragment mFragment){
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame_container, mFragment).commit();
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
        switch (item.getItemId()){
            case R.id.drawer_nav_share:
                break;

            case R.id.drawer_nav_send:
                break;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // Highlight the selected item, update the title, and close the drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListComicSelection(ComicInterface comicInterface, String originOfClick) {
        Intent intent = new Intent(this,InfoAndChapterActivity.class);
        intent.putExtra(PICKED_COMIC_LINK, comicInterface.getFormattedURL());
        intent.putExtra(PICKED_COMIC_ORIGIN_LOCATION, originOfClick);
        intent.putExtra(PICKED_COMIC_TITLE, comicInterface.getTitle());
        startActivity(intent);
    }

}
