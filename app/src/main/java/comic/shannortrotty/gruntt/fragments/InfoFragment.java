package comic.shannortrotty.gruntt.fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.services.ComicDatabaseContract;
import comic.shannortrotty.gruntt.services.ComicTvNetworkImplementation;
import comic.shannortrotty.gruntt.presenter.ItemPresenter;
import comic.shannortrotty.gruntt.presenter.GenericNetworkPresenter;
import comic.shannortrotty.gruntt.services.DatabaseHelper;
import comic.shannortrotty.gruntt.services.VolleyWrapper;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements GenericView<ComicDetails> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMIC_LINK = "comic.link.info";
    private static final String COMIC_TITLE = "comic.title.info";
    private static final String TAG = "InfoFragment";

    private String mLink;
    private String mTitle;

//    private NetworkImageView networkLargeComicImg;
    private ImageView largeComicImg;
    private TextView comicTitleView;
    private TextView comicAltTitleView;
    private TextView comicReleaseDateView;
    private TextView comicStatusView;
    private TextView comicAuthorView;
    private TextView comicGenreView;
    private TextView comicDescriptionView;
    private GenericNetworkPresenter genericNetworkPresenter;
    private AVLoadingIndicatorView loadingIndicatorView;
    private Button addToFavoritesBtn;
    private Button resumeReading;
    private OnInfoFragmentListener mListener;
    private ComicDetails currentSpecifics;
    private DatabaseHelper mDatabase;
    private Boolean isFavorite;
    private Bitmap comicImg;

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
        mDatabase = new DatabaseHelper(getContext());
        //TODO:Replace with Factory call
        //TODO: Add Decription Title
        //TODO: Find a better way to display genre
        genericNetworkPresenter = new ItemPresenter<>(this, new ComicTvNetworkImplementation());

//        networkLargeComicImg = ((NetworkImageView) view.findViewById(R.id.networkImgView_info_fragment_large_img));
//        networkLargeComicImg.setDefaultImageResId(R.drawable.ic_menu_camera);
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

        checkForFavorite();
        //TODO: Move this code to the favorite check
        RequestType requestType = new RequestType(RequestType.Type.COMICSDESCRIPTION);
        requestType.addExtras(Constants.COMIC_LINK,mLink);
        genericNetworkPresenter.startRequest(requestType);

        addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check database if data is there already to know how to display the button
                if(isFavorite){
                    //Remove from Database and change text to "add"
                    mListener.removeFromFavorites(currentSpecifics);
                    isFavorite = !isFavorite;
                    setFavoriteLabel(isFavorite);
                }else{
                    //Add to Database and Change text to "Remove"
                    mListener.addToFavorites(currentSpecifics, comicImg);
                    isFavorite = !isFavorite;
                    setFavoriteLabel(isFavorite);
                }
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

    /**
     *
     */
    public void checkForFavorite(){
        SQLiteDatabase db = mDatabase.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ComicDatabaseContract.ComicFavoriteEntry._ID,
                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE,
        };

        // Filter results WHERE "title" = 'Title of Comic correctly'
        String selection = ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { mTitle };
        Log.i(TAG, "checkForFavorite: " + mTitle);

        Cursor cursor = db.query(
                ComicDatabaseContract.ComicFavoriteEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if ( cursor.getCount() > 0){
            //Already a favorite
            isFavorite = true;
            setFavoriteLabel(isFavorite);
            //TODO:Also if favorite, Load data from database and not network call
        }else{
            //Not a favorite
            isFavorite = false;
            setFavoriteLabel(isFavorite);
            //TODO: Load from Network call
        }
        cursor.close();
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
//        networkLargeComicImg.setVisibility(visibility);
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
    public void setItems(List<ComicDetails> items) {
        //Do nothing here, don't get back a list
    }

    @Override
    public void updateProgress(int progress) {
//        Not Using Currently
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
        Picasso.with(getContext())
                .load(comicDetails.getLargeImgURL())
                .into(target);
    }

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
        genericNetworkPresenter.onDestroy();
        Picasso.with(getContext()).cancelRequest(target);
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
        void addToFavorites(ComicDetails comicDetails, Bitmap comicImage);
        void removeFromFavorites(ComicDetails comicDetails);
    }

}
