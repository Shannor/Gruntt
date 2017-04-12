package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.EndlessRecyclerViewScrollListener;
import comic.shannortrotty.gruntt.adapters.PopularComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.OnComicListener;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.PopularComicsPresenter;
import comic.shannortrotty.gruntt.presenter.ComicPresenter;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PopularComicFragment extends Fragment implements GenericView<PopularComic> {

    private OnComicListener mListener;
    public static final String TAG = "PopularComicFragment";
    private PopularComicRecyclerViewAdapter popularComicRecyclerViewAdapter;
    private RecyclerView mComicRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ComicPresenter genericPresenter;
    private AVLoadingIndicatorView loadingIndicatorView;
    private EndlessRecyclerViewScrollListener scrollListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PopularComicFragment() {
    }

    @SuppressWarnings("unused")
    public static PopularComicFragment newInstance() {
        return new PopularComicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_comic, container, false);
        genericPresenter = new PopularComicsPresenter(getContext(),this, new ComicTvNetworkImplementation());
        mLayoutManager = new LinearLayoutManager(getContext());
        mComicRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_fragment_popular_comics);
        loadingIndicatorView = ((AVLoadingIndicatorView) view.findViewById(R.id.loading_icon_fragment_popular_comics));
        mComicRecyclerView.setLayoutManager(mLayoutManager);
        popularComicRecyclerViewAdapter = new PopularComicRecyclerViewAdapter(getContext().getApplicationContext() ,mListener);
        mComicRecyclerView.setAdapter(popularComicRecyclerViewAdapter);
        scrollListener =  new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadRequest(page);
            }
        };

        mComicRecyclerView.addOnScrollListener(scrollListener);
        loadRequest(1);

        return view;
    }

    /**
     *
     * @param pageCount
     */
    private void loadRequest(int pageCount){
        //Only make this networking call on Create and show load when making the call.
        RequestType type = new RequestType(RequestType.Type.LOAD);
        type.addExtras(Constants.PAGE_NUMBER,String.valueOf(pageCount));
        genericPresenter.startRequest(type);
    }

    private void clearItems(){
        popularComicRecyclerViewAdapter.clearItems();
        scrollListener.resetState();
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
        clearItems();
        genericPresenter.cancelRequest();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        genericPresenter.onDestroy();
    }

    //********************OnGenericView Listener***************************
    @Override
    public void setItems(List<PopularComic> items) {
        popularComicRecyclerViewAdapter.addItems(items);
    }

    @Override
    public void setItem(PopularComic item) {
        //Not In Use
    }

    @Override
    public void setErrorMessage() {

    }

    @Override
    public void hideLoading() {
        loadingIndicatorView.hide();
    }

    @Override
    public void showLoading() {
        loadingIndicatorView.show();
    }


}
