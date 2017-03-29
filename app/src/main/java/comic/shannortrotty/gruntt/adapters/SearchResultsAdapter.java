package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.SearchResultsActivity;
import comic.shannortrotty.gruntt.classes.OnComicListener;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.SearchComic;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.services.VolleyWrapper;

/**
 * Created by shannortrotty on 3/23/17.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final List<SearchComic> mSearchResults;
    private final OnComicListener mListener;
    private final Context mContext;

    public SearchResultsAdapter(Context mContext, List<SearchComic> mSearchResults, OnComicListener mListener) {
        this.mSearchResults = mSearchResults;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    public SearchResultsAdapter(Context mContext, OnComicListener mListener) {
        this.mListener = mListener;
        this.mContext = mContext;
        this.mSearchResults = new ArrayList<>();
    }

    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_search_results, parent, false);
        //Where margins, size, padding can be set for the view
        return new SearchResultsAdapter.ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    @Override
    public void onBindViewHolder(final SearchResultsAdapter.ViewHolder holder, int position) {
        final SearchComic mSearchComic = mSearchResults.get(position);
        holder.getTitle().setText(mSearchComic.getTitle());
        holder.getGenre().setText(mSearchComic.getGenres());
        Picasso.with(mContext)
                .load(mSearchComic.getThumbnailImg())
                .fit()
                .into(holder.getComicImage());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListComicSelection(mSearchComic, SearchResultsActivity.TAG);
            }
        });
    }

    public void addItems(List<SearchComic> searchComics){
        int currentSize = mSearchResults.size();
        mSearchResults.addAll(searchComics);
        notifyDataSetChanged();
        notifyItemRangeInserted(currentSize, mSearchResults.size() - 1);
    }


    public void clearItems(){
        mSearchResults.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mComicImage;
        private final TextView mTitle;
        private final TextView mGenre;

        ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.mComicImage = ((ImageView) view.findViewById(R.id.imageView_search_results_img));
            this.mTitle = ((TextView) view.findViewById(R.id.textView_search_results_title));
            this.mGenre = ((TextView) view.findViewById(R.id.textView_search_results_genre));
        }

        public ImageView getComicImage() {
            return mComicImage;
        }

        public View getView() {
            return mView;
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getGenre() {
            return mGenre;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
