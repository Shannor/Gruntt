package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.models.Chapter;
import comic.shannortrotty.gruntt.models.ComicEventBus;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
        mServiceMediator.getChapterList(getContext().getApplicationContext(), mTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        listView = ((ListView) view.findViewById(R.id.listView_fragment_issue_list));
        //Listen for response of Issues.
        ComicEventBus mComicBus = ComicEventBus.getInstance();
        mComicBus.getChapterObservable().subscribe(new Observer<List<Chapter>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Chapter> value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return view;
    }

}
