package comic.shannortrotty.gruntt.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.presenter.ComicDetailPresenter;
import comic.shannortrotty.gruntt.model.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.GenericPresenter;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements GenericView<ComicDetails> {

    private static final String COMIC_LINK = "comic.link.info";
    private static final String COMIC_TITLE = "comic.title.info";
    private static final String TAG = "InfoFragment";

    private String mLink;
    private String mTitle;

    private ImageView largeComicImg;
    private TextView comicTitleView;
    private TextView comicAltTitleView;
    private TextView comicReleaseDateView;
    private TextView comicStatusView;
    private TextView comicAuthorView;
    private TextView comicGenreView;
    private TextView comicDescriptionView;
    private GenericPresenter genericPresenter;
    private AVLoadingIndicatorView loadingIndicatorView;
    private Button addToFavoritesBtn;
    private Button resumeReading;
    private OnInfoFragmentListener mListener;
    private ComicDetails currentSpecifics;
    private Boolean isFavorite;
    private Bitmap comicImg;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Interface for this Fragment
     */
    public interface OnInfoFragmentListener {
        void addToFavorites(ComicDetails comicDetails, Bitmap comicImage);
        void removeFromFavorites(ComicDetails comicDetails);
        void resumeReading();
    }

    /**
     *
     * @param comicTitle Title of the clicked Comic.
     * @param comicLink Formatted link for the Comic.
     * @return A new instance of fragment InfoFragment.
     */
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
        //TODO: Add Description title
        genericPresenter = new ComicDetailPresenter(getContext(),this, new ComicTvNetworkImplementation());

        largeComicImg = ((ImageView) view.findViewById(R.id.imageView_info_fragment_large_img));
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
        loadComicRequest();

        addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite){
                    //TODO: Can be cleaned up I'm sure
                    //Remove from Database and change text to "add"
                    currentSpecifics.setFavorite(false);
                    mListener.removeFromFavorites(currentSpecifics);
                    isFavorite = !isFavorite;
                    setFavoriteLabel(isFavorite);
                }else{
                    //Add to Database and Change text to "Remove"
                    currentSpecifics.setFavorite(true);
                    mListener.addToFavorites(currentSpecifics, comicImg);
                    isFavorite = !isFavorite;
                    setFavoriteLabel(isFavorite);
                }
            }
        });

        resumeReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.resumeReading();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadComicRequest(){
        RequestType requestType = new RequestType(RequestType.Type.COMICSDESCRIPTION);
        requestType.addExtras(Constants.COMIC_LINK,mLink);
        requestType.addExtras(Constants.COMIC_NAME, mTitle);
        genericPresenter.startRequest(requestType);
    }


    /**
     * True = Is in favorites
     * False = Not in Favorites
     * @param isAFavorite boolean
     */
    public void setFavoriteLabel(Boolean isAFavorite){
        if (isAFavorite){
            addToFavoritesBtn.setText(R.string.favorite_btn_remove_from_favorites);
        }else{
            addToFavoritesBtn.setText(R.string.favorite_btn_add_to_favorites);
        }
    }

    public void setVisibility(int visibility){
        largeComicImg.setVisibility(visibility);
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

    //****************************** Methods for Generic View interface *************************
    @Override
    public void hideLoading() {
        loadingIndicatorView.hide();
        setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorMessage() {

    }

    @Override
    public void showLoading() {
        setVisibility(View.INVISIBLE);
        loadingIndicatorView.show();
    }

    @Override
    public void setItems(List<ComicDetails> items) {
        //Do nothing here, don't get back a list
    }

    @Override
    public void setItem(ComicDetails comicDetails) {
        currentSpecifics = comicDetails;
        comicTitleView.setText(comicDetails.getFormattedName());
        comicAltTitleView.setText(comicDetails.getFormattedAltName());
        comicAuthorView.setText(comicDetails.getFormattedAuthor());
        comicGenreView.setText(comicDetails.getFormattedGenre());
        comicReleaseDateView.setText(comicDetails.getFormattedReleaseDate());
        comicDescriptionView.setText(comicDetails.getFormattedDescription());
        comicStatusView.setText(comicDetails.getFormattedStatus());
        isFavorite = comicDetails.getFavorite();
        setFavoriteLabel(comicDetails.getFavorite());
        //Check if Loaded from Database or Need to grab from url
        //TODO:Set Error Image, and Load Image
        if(comicDetails.getLocalBitmap() != null){
            largeComicImg.setImageBitmap(comicDetails.getLocalBitmap());
        }else{
            Picasso.with(getContext())
                    .load(comicDetails.getLargeImgURL())
                    .into(target);
        }

    }

    /**
     *
     */
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            comicImg = bitmap;
            largeComicImg.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            largeComicImg.setImageDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            largeComicImg.setImageDrawable(placeHolderDrawable);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        genericPresenter.onDestroy();
        Picasso.with(getContext()).cancelRequest(target);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInfoFragmentListener) {
            mListener = (OnInfoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInfoFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        genericPresenter.cancelRequest();
        mListener = null;
    }


}
