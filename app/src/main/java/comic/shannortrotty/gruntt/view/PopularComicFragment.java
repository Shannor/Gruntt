package comic.shannortrotty.gruntt.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.MyComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.OnComicListener;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ListPresenter;
import comic.shannortrotty.gruntt.presenter.GenericNetworkPresenter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PopularComicFragment extends Fragment implements GenericView<Comic> {

    private OnComicListener mListener;
    public static final String TAG = "PopularComicFragment";
    private MyComicRecyclerViewAdapter myComicRecyclerViewAdapter;
    private RecyclerView mComicRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GenericNetworkPresenter genericNetworkPresenter;
    private int pageCount = 1;

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
        //TODO:Page count will be incremented by USER as well
        //TODO: Change to allow Factory to provide the Implementation
        genericNetworkPresenter = new ListPresenter<>(this, new ComicTvNetworkImplementation());
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mComicRecyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            mComicRecyclerView.setLayoutManager(mLayoutManager);
            myComicRecyclerViewAdapter = new MyComicRecyclerViewAdapter(getContext().getApplicationContext() ,mListener);
            mComicRecyclerView.setAdapter(myComicRecyclerViewAdapter);
        }
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
    public void setItems(List<Comic> items) {
        myComicRecyclerViewAdapter.addItems(items);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        RequestType type = new RequestType(RequestType.Type.POPULARCOMICS);
        type.addExtras(Constants.PAGE_NUMBER,String.valueOf(pageCount));
        genericNetworkPresenter.startRequest(type);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        genericNetworkPresenter.onDestroy();
    }
}
