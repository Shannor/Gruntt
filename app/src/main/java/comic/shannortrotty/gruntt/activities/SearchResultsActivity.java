package comic.shannortrotty.gruntt.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.EndlessRecyclerViewScrollListener;
import comic.shannortrotty.gruntt.adapters.SearchResultsAdapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.OnComicListener;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.classes.SearchComic;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ComicPresenter;
import comic.shannortrotty.gruntt.presenter.SearchComicPresenter;
import comic.shannortrotty.gruntt.view.GenericView;


public class SearchResultsActivity extends AppCompatActivity implements GenericView<SearchComic>, OnComicListener {

    private RecyclerView recyclerView;
    private ComicPresenter presenter;
    private SearchResultsAdapter adapter;
    private AVLoadingIndicatorView loadingIndicatorView;
    public static final String TAG = "SearchResultsActivity";
    private String keyword;
    private String include;
    private String exclude;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = ((RecyclerView) findViewById(R.id.recyclerView_search_results_activity));
        loadingIndicatorView = ((AVLoadingIndicatorView) findViewById(R.id.loading_icon_search_results_activity));

        if(getIntent() != null){
            keyword = getIntent().getStringExtra(Constants.SEARCH_COMIC_KEYWORD);
            include = getIntent().getStringExtra(Constants.SEARCH_COMIC_INCLUDE);
            exclude = getIntent().getStringExtra(Constants.SEARCH_COMIC_EXCLUDE);
            status = getIntent().getStringExtra(Constants.SEARCH_COMIC_STATUS);
            if(status.equals("All")){
                status = null;
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SearchResultsAdapter(this,this);
        recyclerView.setAdapter(adapter);
        presenter = new SearchComicPresenter(this, new ComicTvNetworkImplementation());
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadRequest(page);
            }
        };

        loadRequest(1);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void loadRequest(int page){
        RequestType requestType = new RequestType(RequestType.Type.LOAD);
        requestType.addExtras(Constants.SEARCH_COMIC_KEYWORD, keyword);
        requestType.addExtras(Constants.SEARCH_COMIC_INCLUDE, include);
        requestType.addExtras(Constants.SEARCH_COMIC_EXCLUDE, exclude);
        requestType.addExtras(Constants.SEARCH_COMIC_STATUS, status);
        requestType.addExtras(Constants.PAGE_NUMBER, String.valueOf(page));
        presenter.startRequest(requestType);
    }

    public static void startActivity(Context context, String keyword, String include,
                                     String exclude, String status) {

        Intent starter = new Intent(context, SearchResultsActivity.class);
        starter.putExtra(Constants.SEARCH_COMIC_KEYWORD, keyword);
        starter.putExtra(Constants.SEARCH_COMIC_INCLUDE, include);
        starter.putExtra(Constants.SEARCH_COMIC_EXCLUDE, exclude);
        starter.putExtra(Constants.SEARCH_COMIC_STATUS, status);
        context.startActivity(starter);
    }

    //*************   OnComicListener Implementation  **********************
    @Override
    public void onListComicSelection(Comic comic, String originOfClick) {
        Intent intent = new Intent(this,InfoAndChapterActivity.class);
        intent.putExtra(MainActivity.PICKED_COMIC_LINK, comic.getFormattedURL());
        intent.putExtra(MainActivity.PICKED_COMIC_ORIGIN_LOCATION, originOfClick);
        intent.putExtra(MainActivity.PICKED_COMIC_TITLE, comic.getTitle());
        startActivity(intent);
    }

    //*****************   Generic View Implementation  ***********************
    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        loadingIndicatorView.show();
    }

    @Override
    public void hideLoading() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingIndicatorView.hide();
    }

    @Override
    public void setErrorMessage() {

    }

    @Override
    public void setItems(List<SearchComic> items) {
        adapter.addItems(items);
    }

    @Override
    public void setItem(SearchComic item) {
        //Wont use
    }
}
