package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.utils.OnComicListener;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class PopularComicRecyclerViewAdapter extends RecyclerView.Adapter<PopularComicRecyclerViewAdapter.ViewHolder> {

    private final List<PopularComic> mPopularComics;
    private final OnComicListener mListener;
    private static final String TAG = "MyComicRecyclerViewAdap";
    private final Context mContext;

    public PopularComicRecyclerViewAdapter(Context context, List<PopularComic> items, OnComicListener listener) {
        mPopularComics = items;
        mListener = listener;
        mContext = context;
    }

    public PopularComicRecyclerViewAdapter(Context context, OnComicListener listener){
        mPopularComics = new ArrayList<>();
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

    public void addItems(List<PopularComic> popularComics){
//        mPopularComics.clear();
        int currentSize = mPopularComics.size();
        mPopularComics.addAll(popularComics);
        notifyDataSetChanged();
        notifyItemRangeInserted(currentSize, mPopularComics.size() - 1);
    }

    public void addItem(PopularComic popularComic){
        mPopularComics.add(popularComic);
        notifyItemChanged(mPopularComics.size() - 1);
    }

    public void clearItems(){
        mPopularComics.clear();
        notifyDataSetChanged();
    }
    /**
     * Method where data should be set, replace contents
     * @param holder viewHolder
     * @param position location in the data set
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PopularComic mPopularComic = mPopularComics.get(position);
        holder.getComicTitle().setText(mPopularComic.getTitle());
        holder.getComicIssueCount().setText(String.valueOf("Issue Count: " + mPopularComic.getIssueCount()));
        Picasso.with(mContext)
                .load(mPopularComic.getThumbnailUrl())
                .placeholder(R.drawable.load_icon_8)
                .error(R.drawable.placeholder)
                .fit()
                .into(holder.getComicImg());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListComicSelection(mPopularComic, PopularComicFragment.TAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPopularComics.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mComicTitle;
        private final TextView mComicIssueCount;
        private final ImageView mComicImg;
        private final View mView;

        ViewHolder(View view) {
            super(view);
            mComicTitle = (TextView) view.findViewById(R.id.textView_popular_frag_comic_title);
            mComicIssueCount = (TextView) view.findViewById(R.id.textView_popular_frag_comic_issue_count);
            mComicImg = (ImageView) view.findViewById(R.id.ImageView_popular_frag_comic_img);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mComicTitle.getText().toString() + "'";
        }

        public View getView(){return mView;}

        private TextView getComicTitle() {
            return mComicTitle;
        }

        private TextView getComicIssueCount() {
            return mComicIssueCount;
        }

        private ImageView getComicImg() {
            return mComicImg;
        }
    }
}
