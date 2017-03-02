package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.ChapterListAdapter;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.GenericNetworkPresenter;
import comic.shannortrotty.gruntt.presenter.ListPresenter;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChapterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterListFragment extends Fragment implements GenericView<Chapter>{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_TITLE = "comic.title.issue";
    private static final String COMIC_LINK = "comic.link.issue";

    private String mLink;
    private String mTitle;
    private ListView listView;
    private ServiceMediator mServiceMediator = ServiceMediator.getInstance();
    private ChapterListAdapter mChapterListAdapter;
    private GenericNetworkPresenter genericNetworkPresenter;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        //Makes the call on create to fetch the list of Issues
//        mServiceMediator.getChapterList(getContext().getApplicationContext(),mLink);
        genericNetworkPresenter = new ListPresenter<>(this, new ComicTvNetworkImplementation());
        listView = ((ListView) view.findViewById(R.id.listView_fragment_chapter_list));
        mChapterListAdapter = new ChapterListAdapter(getContext());
        listView.setAdapter(mChapterListAdapter);
        RequestType requestType = new RequestType(RequestType.Type.CHAPTERS);
        requestType.addExtras(Constants.COMIC_LINK, mLink);
        genericNetworkPresenter.startRequest(requestType);
        return view;
    }

    @Override
    public void setItems(List<Chapter> items) {
        mChapterListAdapter.addChapters(items);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setItem(Chapter item) {
        //Wont Implement
    }

    @Override
    public void showLoading() {

    }

}
