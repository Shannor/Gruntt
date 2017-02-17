package comic.shannortrotty.gruntt.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import comic.shannortrotty.gruntt.EventBusClasses.SendComicDescriptionEvent;
import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.models.ComicSpecifics;
import comic.shannortrotty.gruntt.services.ServiceMediator;
import comic.shannortrotty.gruntt.services.VolleyWrapper;

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

    private NetworkImageView mLargeComicImg;
    private TextView comicTitleView;
    private TextView comicAltTitleView;
    private TextView comicReleaseDateView;
    private TextView comicStatusView;
    private TextView comicAuthorView;
    private TextView comicGenreView;
    private TextView comicDescriptionView;

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
        View view = inflater.inflate(R.layout.fragment_info_fragment, container, false);
        ServiceMediator.getInstance().getComicDescription(getContext(), mLink);

        mLargeComicImg = ((NetworkImageView) view.findViewById(R.id.networkImgView_info_fragment_large_img));
        mLargeComicImg.setDefaultImageResId(R.drawable.ic_menu_camera);

        comicTitleView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_name));
        comicAltTitleView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_alt_name));
        comicAuthorView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_author));
        comicGenreView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_genres));
        comicReleaseDateView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_release_date));
        comicDescriptionView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_description));
        comicStatusView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_status));
        Button addToFavoritesBtn = ((Button) view.findViewById(R.id.btn_info_fragment_comic_add_to_favorites));
        Button resumeReading = ((Button) view.findViewById(R.id.btn_info_fragment_comic_resume));

        addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Would be saved.", Toast.LENGTH_SHORT).show();
            }
        });

        resumeReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Continue Reading.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Subscribe
    public void onChapterDescription(SendComicDescriptionEvent sendComicDescriptionEvent){
        ComicSpecifics comicSpecifics = sendComicDescriptionEvent.getComicSpecifics();
        comicTitleView.setText(comicSpecifics.getFormattedName());
        comicAltTitleView.setText(comicSpecifics.getFormattedAltName());
        comicAuthorView.setText(comicSpecifics.getFormattedAuthor());
        comicGenreView.setText(comicSpecifics.getFormattedGenre());
        comicReleaseDateView.setText(comicSpecifics.getFormattedReleaseDate());
        comicDescriptionView.setText(comicSpecifics.getFormattedDescription());
        comicStatusView.setText(comicSpecifics.getFormattedStatus());
        ImageLoader imageLoader = VolleyWrapper.getInstance(getContext().getApplicationContext()).getImageLoader();
        mLargeComicImg.setImageUrl(comicSpecifics.getLargeImgURL(), imageLoader);
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
