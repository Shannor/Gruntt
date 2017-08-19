package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.ComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.OnComicListener;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.presenter.AllComicsPresenter;
import comic.shannortrotty.gruntt.presenter.ComicPresenter;
import comic.shannortrotty.gruntt.services.GrunttRESTfulImpl;
import comic.shannortrotty.gruntt.view.GenericView;


public class ComicsFragment extends Fragment implements GenericView<Comic> {
    public static final String TAG = "ComicsFragment";

    private OnComicListener mListener;
    private ComicPresenter mPresenter;
    private AVLoadingIndicatorView loadingIndicator;
    private ComicRecyclerViewAdapter comicAdapter;
    private FastScrollRecyclerView scrollRecyclerView;

    public ComicsFragment() {
        // Required empty public constructor
    }


    public static ComicsFragment newInstance() {
        return new ComicsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.menu_comics, menu);
    }

    @Override
    public void onPrepareOptionsMenu(final Menu menu) {
        final MenuItem mSearchMenuItem = menu.findItem(R.id.comic_action_search);
        final MenuItem mClearIcon = menu.findItem(R.id.comic_clear_search);

        mClearIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                comicAdapter.restoreAll();
                return false;
            }
        });

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                comicAdapter.filter(query);
                mSearchMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comic_fragement, container, false);
        scrollRecyclerView = ((FastScrollRecyclerView) view.findViewById(R.id.recyclerView_fastscroll_comics));
        loadingIndicator = (AVLoadingIndicatorView)view.findViewById(R.id.loading_icon_fragment_comics);
        mPresenter = new AllComicsPresenter(getContext(),this, new GrunttRESTfulImpl());

        scrollRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        RequestType request = new RequestType();
        request.setType(RequestType.Type.LOAD);
        request.addExtras(Constants.SOURCE_TAG, Constants.COMIC_EXTRA_TAG);
        mPresenter.startRequest(request);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComicListener) {
            mListener = (OnComicListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnComicListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.cancelRequest();
        mPresenter.onDestroy();
    }


    //Methods for Generic View
    @Override
    public void hideLoading() {
        loadingIndicator.hide();
        scrollRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        loadingIndicator.show();
        scrollRecyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setItem(Comic item) {
        //Wont get Called for this fragment
    }

    @Override
    public void setErrorMessage(APIError errorMessage) {

    }

    @Override
    public void setItems(List<Comic> items) {
        comicAdapter = new ComicRecyclerViewAdapter(getContext(),items, mListener);
        scrollRecyclerView.setAdapter(comicAdapter);
    }
}
