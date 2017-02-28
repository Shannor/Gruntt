package comic.shannortrotty.gruntt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendChapterPagesEvent;
import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import comic.shannortrotty.gruntt.services.VolleyWrapper;

public class ReadComicActivity extends AppCompatActivity {

    public static final String COMIC_LINK = "comic.link.read";
    public static final String COMIC_CHAPTER_NUMBER = "comic.chapter.number.read";
    private ViewPager mViewPager;
    private ServiceMediator serviceMediator = ServiceMediator.getInstance();
    private ImagesPagerAdapter mImagesPagesAdapter;
    private String mComicLink;
    private String mComicChapterNumber;
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

        serviceMediator.getComicChapterPages(getApplicationContext(), mComicLink, mComicChapterNumber);
        mViewPager = ((ViewPager) findViewById(R.id.viewPager_activity_read_comic));
        mImagesPagesAdapter = new ImagesPagerAdapter(this);
        mViewPager.setAdapter(mImagesPagesAdapter);

    }

    public static void start(Context context, String comicLink, String comicChapterNumber) {
        Intent starter = new Intent(context, ReadComicActivity.class);
        starter.putExtra(COMIC_CHAPTER_NUMBER, comicChapterNumber);
        starter.putExtra(COMIC_LINK, comicLink);
        context.startActivity(starter);
    }

    @Subscribe
    public void onChapterPagesLinks(SendChapterPagesEvent chapterPagesEvent){
        mImagesPagesAdapter.addList(chapterPagesEvent.getChapterPagesLinks());
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }



    public class  ImagesPagerAdapter extends PagerAdapter{
        private List<String> chapterPageLinks;
        private Context context;
        private LayoutInflater layoutInflater;

        public ImagesPagerAdapter(Context context){
            this.context = context;
            layoutInflater = ((LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE));
            this.chapterPageLinks = new ArrayList<>();
        }
        public ImagesPagerAdapter(Context context,List<String> chapterPageLinks){
            this.context = context;
            this.chapterPageLinks = chapterPageLinks;
        }

        public void addList(List<String> chapterPagesLinks){
            this.chapterPageLinks.clear();
            this.chapterPageLinks.addAll(chapterPagesLinks);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return chapterPageLinks.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = this.layoutInflater.inflate(R.layout.chapter_pages_layout,container, false);
            NetworkImageView networkImageView = ((NetworkImageView) view.findViewById(R.id.networkImgView_read_comic_chapter_pages));
            networkImageView.setImageUrl(this.chapterPageLinks.get(position), VolleyWrapper.getInstance(getApplicationContext()).getImageLoader());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }
}
