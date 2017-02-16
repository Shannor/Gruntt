package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendComicsEvent;
import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.MyComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.models.ComicEventBus;
import comic.shannortrotty.gruntt.models.OnComicListener;
import comic.shannortrotty.gruntt.services.ComicTvHttpService;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PopularComicFragment extends Fragment{

    private OnComicListener mListener;
    public static final String TAG = "PopularComicFragment";
    private MyComicRecyclerViewAdapter adapter;
    private ServiceMediator serviceMediator = ServiceMediator.getInstance();
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
        //TODO: Remove this set. Will be done by USER
        serviceMediator.setServiceTag(ComicTvHttpService.TAG);
        //TODO:Page count will be incremented by USER as well
        serviceMediator.getPopularList(getContext().getApplicationContext(), (String.valueOf(pageCount)));

//        TODO: Probably add more to the view.
        // Only works if entire screen is Recycler View, If not must change this block
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MyComicRecyclerViewAdapter(getContext().getApplicationContext() ,mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Subscribe
    public void onComicEvent(SendComicsEvent event){
        adapter.addItems(event.getComics());
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
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
