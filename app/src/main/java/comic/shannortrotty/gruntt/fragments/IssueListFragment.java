package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.models.Comic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IssueListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_TITLE = "comic.title.issue";
    private static final String COMIC_LINK = "comic.link.issue";

    private String mLink;
    private String mTitle;


    public IssueListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param comicName Parameter 1.
     * @param comicTitle Parameter 2.
     * @return A new instance of fragment IssueListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssueListFragment newInstance(String comicTitle, String comicLink) {
        IssueListFragment fragment = new IssueListFragment();
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
        View view = inflater.inflate(R.layout.fragment_issue_list, container, false);

        return view;
    }

}
