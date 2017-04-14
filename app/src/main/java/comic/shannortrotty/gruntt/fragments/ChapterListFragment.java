package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.activities.ReadComicActivity;
import comic.shannortrotty.gruntt.adapters.ChapterListAdapter;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.OnChapterListener;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.presenter.ChapterPresenter;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChapterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterListFragment extends Fragment implements GenericView<Chapter>, OnChapterListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_TITLE = "comic.title.issue";
    private static final String COMIC_LINK = "comic.link.issue";

    private String mLink;
    private String mTitle;
    private ChapterListAdapter mChapterListAdapter;
    private ChapterPresenter genericPresenter;
    private Chapter lastReadComic;
    private AVLoadingIndicatorView loadingIndicatorView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ChapterListFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param comicTitle The Title of the Comic, String
     * @param comicLink The Formatted Link for the Comic, String
     * @return A new instance of fragment ChapterListFragment.
     */
    public static ChapterListFragment newInstance(String comicTitle, String comicLink) {
        ChapterListFragment fragment = new ChapterListFragment();
        Bundle args = new Bundle();
        args.putString(COMIC_LINK, comicLink);
        args.putString(COMIC_TITLE,comicTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(COMIC_TITLE);
            mLink = getArguments().getString(COMIC_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        //Makes the call on create to fetch the list of Issues
        setHasOptionsMenu(true);
        genericPresenter = new ChapterPresenter(getContext(), this, new ComicTvNetworkImplementation());
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_chapter_list_fragment));
        listView = ((ListView) view.findViewById(R.id.listView_fragment_chapter_list));
        loadingIndicatorView = ((AVLoadingIndicatorView) view.findViewById(R.id.loading_icon_fragment_chapter_list));
        mChapterListAdapter = new ChapterListAdapter(getContext(), this);
        listView.setAdapter(mChapterListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestType request = new RequestType(RequestType.Type.NETWORK);
                loadChapters(request);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Perform Request for Comic Chapters
        RequestType request = new RequestType(RequestType.Type.DATABASE);
        loadChapters(request);
    }

    private void loadChapters(RequestType request){
        request.addExtras(Constants.COMIC_LINK, mLink);
        request.addExtras(Constants.COMIC_NAME,mTitle);
        genericPresenter.startRequest(request);
    }

    @Override
    public void onClickedChapter(Chapter chapter, int position) {
        lastReadComic = chapter;
        genericPresenter.saveComicProgress(
                mTitle,
                mChapterListAdapter.getChapters(),
                chapter,
                position
        );
        ReadComicActivity.start(getContext(),chapter.getFormattedURL(),chapter.getChapterNumber());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chapter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.chapter_menu_refresh_chapters:
                RequestType requestType = new RequestType(RequestType.Type.NETWORK);
                loadChapters(requestType);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //***************** Methods the Activity Call to perform actions on Fragment
    public void resumeReading(){
        onClickedChapter(lastReadComic, mChapterListAdapter.getIndex(lastReadComic));
    }

    //********************* OnGenericView implementation **************
    @Override
    public void setItems(List<Chapter> items) {
        mChapterListAdapter.addChapters(items);
        genericPresenter.saveToDatabase(mTitle, items, lastReadComic);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setErrorMessage(APIError errorMessage) {

    }

    @Override
    public void hideLoading() {
        loadingIndicatorView.hide();
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItem(Chapter item) {
        //Last Chapter Read
        lastReadComic = item;
    }

    @Override
    public void showLoading() {
        listView.setVisibility(View.INVISIBLE);
        loadingIndicatorView.show();
    }

}
