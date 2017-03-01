package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendComicsEvent;
import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.MyComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.OnComicListener;
import comic.shannortrotty.gruntt.model.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ComicPresenter;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import comic.shannortrotty.gruntt.view.BasicView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PopularComicFragment extends Fragment implements BasicView{

    private OnComicListener mListener;
    public static final String TAG = "PopularComicFragment";
    private MyComicRecyclerViewAdapter myComicRecyclerViewAdapter;
    private ServiceMediator serviceMediator = ServiceMediator.getInstance();
    private RecyclerView mComicRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ComicPresenter comicPresenter;
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
//        serviceMediator.getPopularList(getContext().getApplicationContext(), (String.valueOf(pageCount)));
//        TODO: Probably add more to the view.
        // Only works if entire screen is Recycler View, If not must change this block
        comicPresenter = new ComicPresenter(this,new ComicTvNetworkImplementation());
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

    @Subscribe
    public void onComicEvent(SendComicsEvent event){
        myComicRecyclerViewAdapter.addItems(event.getComics());
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
        EventBus.getDefault().register(this);
        comicPresenter.startRequest();

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
