package comic.shannortrotty.gruntt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.adapters.MyComicRecyclerViewAdapter;
import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.services.OttoBus;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PopularComicFragment extends Fragment {

    private OnListPopularComicListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PopularComicFragment() {
    }

    @SuppressWarnings("unused")
    public static PopularComicFragment newInstance() {
        return new PopularComicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OttoBus.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        OttoBus.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_comic, container, false);

        // Only works if entire screen is Recycler View, If not must change this block
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //Get list of items here
            ArrayList<Comic> testComic = new ArrayList<>();
            Comic test = new Comic("Shannor","Link", "Url");
            testComic.add(test);
            recyclerView.setAdapter(new MyComicRecyclerViewAdapter(testComic, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListPopularComicListener) {
            mListener = (OnListPopularComicListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListPopularComicListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Subscribe
    public void getComicsList(ArrayList<Comic> comics){
        Toast.makeText(getActivity(), "Got Event", Toast.LENGTH_SHORT).show();
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
    public interface OnListPopularComicListener {
        // TODO: Update argument type and name
        void onListComicSelection(Comic comic);
    }
}
