package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comic.shannortrotty.gruntt.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_LINK = "comic.link.info";
    private static final String COMIC_TITLE = "comic.title.info";
    private static final String TAG = "InfoFragment";

    private String mLink;
    private String mTitle;


    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param comicTitle Parameter 1.
     * @param comicLink Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String comicTitle, String comicLink) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(COMIC_LINK, comicLink);
        args.putString(COMIC_TITLE, comicTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLink = getArguments().getString(COMIC_LINK);
            mTitle = getArguments().getString(COMIC_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_framgment, container, false);

        return view;
    }

}
