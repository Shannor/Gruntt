package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.AllComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.OnComicListener;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.presenter.BareComicsPresenter;
import comic.shannortrotty.gruntt.presenter.ComicPresenter;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.view.GenericView;


public class AllComicsFragment extends Fragment implements GenericView<BareComicsCategory> {
    public static final String TAG = "AllComicsFragment";

    private OnComicListener mListener;
    private ComicPresenter mPresenter;
    private AVLoadingIndicatorView loadingIndicator;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManger;
    private AllComicRecyclerViewAdapter allComicsAdapter;
    private FastScrollRecyclerView scrollRecyclerView;

    public AllComicsFragment() {
        // Required empty public constructor
    }


    public static AllComicsFragment newInstance() {
        return new AllComicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_comics_fragement, container, false);
//        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView_fragment_all_comics));
        scrollRecyclerView = ((FastScrollRecyclerView) view.findViewById(R.id.recyclerView_fastscroll_all_comics));
        loadingIndicator = (AVLoadingIndicatorView)view.findViewById(R.id.loading_icon_fragment_all_comics);
        layoutManger = new LinearLayoutManager(getContext());
        mPresenter = new BareComicsPresenter(getContext(),this, new ComicTvNetworkImplementation());

        scrollRecyclerView.setLayoutManager(layoutManger);
//        recyclerView.setLayoutManager(layoutManger);

        //TODO:Change Layout from Nested to Scrollbar with Letters
        RequestType request = new RequestType();
        request.setType(RequestType.Type.LOAD);
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
//        recyclerView.setVisibility(View.VISIBLE);
        scrollRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        loadingIndicator.show();
//        recyclerView.setVisibility(View.INVISIBLE);
        scrollRecyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setItem(BareComicsCategory item) {
        //Wont get Called for this fragment
    }

    @Override
    public void setErrorMessage(APIError errorMessage) {

    }

    @Override
    public void setItems(List<BareComicsCategory> items) {
        allComicsAdapter = new AllComicRecyclerViewAdapter(getContext(),items, mListener);
//        recyclerView.setAdapter(allComicsAdapter);
        scrollRecyclerView.setAdapter(allComicsAdapter);
    }
}
