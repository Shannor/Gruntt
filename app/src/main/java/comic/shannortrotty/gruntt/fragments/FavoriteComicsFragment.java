package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.GridSpacingItemDecoration;
import comic.shannortrotty.gruntt.adapters.MyFavoriteComicsRecyclerViewAdapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.services.ComicDatabaseContract;
import comic.shannortrotty.gruntt.services.DatabaseHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FavoriteComicsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private DatabaseHelper mDatabase;
    private static final String TAG = "FavoriteComicsFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteComicsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FavoriteComicsFragment newInstance(int columnCount) {
        FavoriteComicsFragment fragment = new FavoriteComicsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_comics_list, container, false);

        mDatabase = new DatabaseHelper(getContext());
        List<ComicDetails> comicDetails = getFavoriteComics();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFavoriteComicsRecyclerViewAdapter(comicDetails, mListener));
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.favorite_comics_margin);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,spacingInPixels,true, 0));
        }
        return view;
    }


    //TODO: Change to use the same interface as the one for Popular Fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public List<ComicDetails> getFavoriteComics(){

        SQLiteDatabase db = mDatabase.getReadableDatabase();
        List<ComicDetails> comicDetailsList = new ArrayList<>();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        //TODO: May use this just for specific Columns
//        String[] projection = {
//                ComicDatabaseContract.ComicFavoriteEntry._ID,
//                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE,
//        };

        // Filter results WHERE "title" = 'Title of Comic correctly'
//        String selection = ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = {  };
        Cursor cursor = db.rawQuery("SELECT * FROM " + ComicDatabaseContract.ComicFavoriteEntry.TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do {
                ComicDetails comicDetails = new ComicDetails();
                comicDetails.setTitle(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_TITLE)));
                comicDetails.setStatus(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_STATUS)));
                comicDetails.setAuthor(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_AUTHOR)));
                comicDetails.setLocalBitmap(
                        DatabaseHelper.getImage(
                                cursor.getBlob(
                                        cursor.getColumnIndexOrThrow(
                                                ComicDatabaseContract.ComicFavoriteEntry.COLUMN_NAME_COMIC_IMAGE))));
                // comicDetails.setLargeImgURL();
                comicDetailsList.add(comicDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return comicDetailsList;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ComicDetails item);
    }
}
