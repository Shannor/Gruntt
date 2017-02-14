package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.models.OnComicListener;
import comic.shannortrotty.gruntt.services.VolleyWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyComicRecyclerViewAdapter extends RecyclerView.Adapter<MyComicRecyclerViewAdapter.ViewHolder> {

    private final List<Comic> mComics;
    private final OnComicListener mListener;
    private static final String TAG = "MyComicRecyclerViewAdap";
    private final Context mContext;

    public MyComicRecyclerViewAdapter(Context context, List<Comic> items, OnComicListener listener) {
        mComics = items;
        mListener = listener;
        mContext = context;
    }

    public MyComicRecyclerViewAdapter(Context context,  OnComicListener listener){
        mComics = new ArrayList<>();
        mListener = listener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_popular_view, parent, false);
        //Where margins, size, padding can be set for the view
        return new ViewHolder(view);
    }

    public void addItems(List<Comic> comics){
        mComics.addAll(comics);
        notifyDataSetChanged();
        //Suppose to be memory efficient
//        notifyItemChanged(comics.size() -1);
    }

    public void addItem(Comic comic){
        mComics.add(comic);
        notifyItemChanged(mComics.size() - 1);
    }

    /**
     * Method where data should be set, replace contents
     * @param holder viewHolder
     * @param position location in the data set
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Comic mComic = mComics.get(position);
        holder.getComicTitle().setText(mComic.getTitle());
        holder.getComicGenre().setText(mComic.getFormatedGenres());
        ImageLoader imageLoader =  VolleyWrapper.getInstance(mContext).getImageLoader();
        holder.getComicImg().setImageUrl(mComic.getThumbnailUrl(), imageLoader);
        //TODO: Setup NetworkImageView
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mComics.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mComicTitle;
        private final TextView mComicGenre;
        private final NetworkImageView mComicImg;

        ViewHolder(View view) {
            super(view);
            mComicTitle= (TextView) view.findViewById(R.id.textView_popular_frag_comic_title);
            mComicGenre = (TextView) view.findViewById(R.id.textView_popular_frag_comic_genre);
            mComicImg = (NetworkImageView) view.findViewById(R.id.networkImgView_popular_frag_comic_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mComicTitle.getText().toString() + "'";
        }

        public TextView getComicTitle() {
            return mComicTitle;
        }

        public TextView getComicGenre() {
            return mComicGenre;
        }

        public NetworkImageView getComicImg() {
            return mComicImg;
        }
    }
}
