package comic.shannortrotty.gruntt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.ArrayList;
import java.util.List;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.Pages;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.presenter.PagesPresenter;
import comic.shannortrotty.gruntt.model.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ComicDetialPresenter;
import comic.shannortrotty.gruntt.view.GenericView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ReadComicActivity extends AppCompatActivity implements GenericView<Pages> {

    public static final String COMIC_LINK = "comic.link.read";
    public static final String COMIC_CHAPTER_NUMBER = "comic.chapter.number.read";
    private static final String TAG = "ReadComicActivity";
    private ViewPager mViewPager;
    private ImagesPagerAdapter mImagesPagesAdapter;
    private String mComicLink;
    private String mComicChapterNumber;
    private ComicDetialPresenter genericPresenter;
    private AVLoadingIndicatorView loadingIndictorView;

    public static void start(Context context, String comicLink, String comicChapterNumber) {
        Intent starter = new Intent(context, ReadComicActivity.class);
        starter.putExtra(COMIC_CHAPTER_NUMBER, comicChapterNumber);
        starter.putExtra(COMIC_LINK, comicLink);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent() != null){
            mComicChapterNumber = getIntent().getStringExtra(COMIC_CHAPTER_NUMBER);
            mComicLink = getIntent().getStringExtra(COMIC_LINK);
        }
        genericPresenter = new PagesPresenter(this,this, new ComicTvNetworkImplementation());
        loadingIndictorView = ((AVLoadingIndicatorView) findViewById(R.id.loading_icon_read_comic_activity));
        mViewPager = ((ViewPager) findViewById(R.id.viewPager_activity_read_comic));
        mImagesPagesAdapter = new ImagesPagerAdapter(this);
        mViewPager.setAdapter(mImagesPagesAdapter);

        RequestType requestType = new RequestType(RequestType.Type.LOAD);
        requestType.addExtras(Constants.COMIC_LINK, mComicLink);
        requestType.addExtras(Constants.CHAPTER_NUMBER, mComicChapterNumber);
        genericPresenter.startRequest(requestType);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        }else if(id == R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setItem(Pages item) {
        mViewPager.setOffscreenPageLimit(6);
        mImagesPagesAdapter.addPages(item);
    }

    @Override
    public void setItems(List<Pages> items) {
        //Wont use
    }

    @Override
    public void showLoading() {
        loadingIndictorView.show();
    }

    @Override
    public void hideLoading() {
        loadingIndictorView.hide();
    }

    @Override
    public void setErrorMessage() {

    }


    //*****************Images Pages Adapter only for this Activity
    private class  ImagesPagerAdapter extends PagerAdapter{
        private List<String> chapterPageString;
        private Pages page;
        private Context context;
        private LayoutInflater layoutInflater;

        ImagesPagerAdapter(Context context){
            this.context = context;
            this.layoutInflater = ((LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE));
            this.chapterPageString = new ArrayList<>();
        }
        ImagesPagerAdapter(Context context,Pages chapterPage){
            this.context = context;
            this.page = chapterPage;
            this.chapterPageString = page.getPageUrls();

        }

        void addPages(Pages chapterPages){
            this.chapterPageString.clear();
            this.chapterPageString.addAll(chapterPages.getPageUrls());
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return chapterPageString.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = this.layoutInflater.inflate(R.layout.chapter_pages_layout,container, false);
            final AVLoadingIndicatorView loadingIndicatorView = ((AVLoadingIndicatorView) view.findViewById(R.id.loading_icon_viewpager_read_activity));
            loadingIndicatorView.show();
            String url = chapterPageString.get(position);
            PhotoView photoView = ((PhotoView) view.findViewById(R.id.photoView_read_comic_activity));
            final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
            //TODO:Add error message
            Picasso.with(context)
                    .load(url)
                    .fit()
                    .into(photoView, new Callback() {
                        @Override
                        public void onSuccess() {
                            attacher.update();
                            loadingIndicatorView.hide();
                        }

                        @Override
                        public void onError() {

                        }
                    });

            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }
}
