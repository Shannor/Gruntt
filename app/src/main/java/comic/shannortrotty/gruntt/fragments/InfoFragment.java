package comic.shannortrotty.gruntt.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicSpecifics;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.OnComicListener;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ItemPresenter;
import comic.shannortrotty.gruntt.presenter.GenericNetworkPresenter;
import comic.shannortrotty.gruntt.services.VolleyWrapper;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements GenericView<ComicSpecifics> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_LINK = "comic.link.info";
    private static final String COMIC_TITLE = "comic.title.info";
    private static final String TAG = "InfoFragment";

    private String mLink;
    private String mTitle;

    private NetworkImageView networkLargeComicImg;
    private TextView comicTitleView;
    private TextView comicAltTitleView;
    private TextView comicReleaseDateView;
    private TextView comicStatusView;
    private TextView comicAuthorView;
    private TextView comicGenreView;
    private TextView comicDescriptionView;
    private GenericNetworkPresenter genericNetworkPresenter;
    private AVLoadingIndicatorView loadingIndicatorView;
    private  Button addToFavoritesBtn;
    private Button resumeReading;
    private OnInfoFragmentListener mListener;


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
        //TODO:Replace with Factory call
        //TODO: Add Decription Title
        //TODO: Find a better way to display genre
        genericNetworkPresenter = new ItemPresenter<>(this, new ComicTvNetworkImplementation());

        networkLargeComicImg = ((NetworkImageView) view.findViewById(R.id.networkImgView_info_fragment_large_img));
        networkLargeComicImg.setDefaultImageResId(R.drawable.ic_menu_camera);
        loadingIndicatorView = ((AVLoadingIndicatorView) view.findViewById(R.id.loading_icon_fragment_info));
        comicTitleView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_name));
        comicAltTitleView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_alt_name));
        comicAuthorView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_author));
        comicGenreView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_genres));
        comicReleaseDateView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_release_date));
        comicDescriptionView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_description));
        comicStatusView = ((TextView) view.findViewById(R.id.textView_info_fragment_comic_status));
        addToFavoritesBtn = ((Button) view.findViewById(R.id.btn_info_fragment_comic_add_to_favorites));
        resumeReading = ((Button) view.findViewById(R.id.btn_info_fragment_comic_resume));

        RequestType requestType = new RequestType(RequestType.Type.COMICSDESCRIPTION);
        requestType.addExtras(Constants.COMIC_LINK,mLink);
        genericNetworkPresenter.startRequest(requestType);

        addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change text to say Favorited once saved
                ComicSpecifics comicSpecifics = getComicSpecifics();
                mListener.saveDescription(comicSpecifics);
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

    public ComicSpecifics getComicSpecifics(){
        ComicSpecifics comicSpecifics = new ComicSpecifics();
        comicSpecifics.setAuthor(comicAuthorView.getText().toString());
        comicSpecifics.setDescription(comicDescriptionView.getText().toString());
        comicSpecifics.setAltName(comicAltTitleView.getText().toString());
        comicSpecifics.setGenre(comicGenreView.getText().toString());
        comicSpecifics.setName(comicTitleView.getText().toString());
        comicSpecifics.setReleaseDate(comicReleaseDateView.getText().toString());
        comicSpecifics.setStatus(comicStatusView.getText().toString());
        return  comicSpecifics;
    }

    public void setVisibility(int visibility){
        networkLargeComicImg.setVisibility(visibility);
        comicAltTitleView.setVisibility(visibility);
        comicAuthorView.setVisibility(visibility);
        comicDescriptionView.setVisibility(visibility);
        comicGenreView.setVisibility(visibility);
        comicReleaseDateView.setVisibility(visibility);
        comicTitleView.setVisibility(visibility);
        comicStatusView.setVisibility(visibility);
        resumeReading.setVisibility(visibility);
        addToFavoritesBtn.setVisibility(visibility);
    }

    @Override
    public void hideLoading() {
        loadingIndicatorView.hide();
        setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        setVisibility(View.INVISIBLE);
        loadingIndicatorView.show();
    }

    @Override
    public void setItems(List<ComicSpecifics> items) {
        //Do nothing here, don't get back a list
    }

    @Override
    public void updateProgress(int progress) {
//        Not Using Currently
    }

    @Override
    public void setItem(ComicSpecifics comicSpecifics) {
        comicTitleView.setText(comicSpecifics.getFormattedName());
        comicAltTitleView.setText(comicSpecifics.getFormattedAltName());
        comicAuthorView.setText(comicSpecifics.getFormattedAuthor());
        comicGenreView.setText(comicSpecifics.getFormattedGenre());
        comicReleaseDateView.setText(comicSpecifics.getFormattedReleaseDate());
        comicDescriptionView.setText(comicSpecifics.getFormattedDescription());
        comicStatusView.setText(comicSpecifics.getFormattedStatus());
        //TODO:Change to Picasso, also able to get Bitmap
        ImageLoader imageLoader = VolleyWrapper.getInstance(getContext().getApplicationContext()).getImageLoader();
        networkLargeComicImg.setImageUrl(comicSpecifics.getLargeImgURL(), imageLoader);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        genericNetworkPresenter.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInfoFragmentListener) {
            mListener = (OnInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnInfoFragmentListener {
        //TODO:add bitmap
        void saveDescription(ComicSpecifics comicSpecifics);
    }

}
