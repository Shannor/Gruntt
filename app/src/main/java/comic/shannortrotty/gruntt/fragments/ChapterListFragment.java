package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.reactivestreams.Subscription;

import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendChaptersEvent;
import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.ChapterListAdapter;
import comic.shannortrotty.gruntt.models.Chapter;
import comic.shannortrotty.gruntt.models.ComicEventBus;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChapterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_TITLE = "comic.title.issue";
    private static final String COMIC_LINK = "comic.link.issue";

    private String mLink;
    private String mTitle;
    private ListView listView;
    private ServiceMediator mServiceMediator = ServiceMediator.getInstance();
    private ChapterListAdapter mChapterListAdapter;
    public ChapterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param comicTitle Parameter 1.
     * @param comicLink Parameter 2.
     * @return A new instance of fragment ChapterListFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        //Makes the call on create to fetch the list of Issues
        mServiceMediator.getChapterList(getContext().getApplicationContext(),mLink);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        listView = ((ListView) view.findViewById(R.id.listView_fragment_chapter_list));
        mChapterListAdapter = new ChapterListAdapter(getContext());
        listView.setAdapter(mChapterListAdapter);

        return view;
    }
    @Subscribe
    public void onChapterEvent(SendChaptersEvent event){
        mChapterListAdapter.addChapters(event.getChapters());
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}
