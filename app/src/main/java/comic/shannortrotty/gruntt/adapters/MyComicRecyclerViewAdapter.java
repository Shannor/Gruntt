package comic.shannortrotty.gruntt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.models.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyComicRecyclerViewAdapter extends RecyclerView.Adapter<MyComicRecyclerViewAdapter.ViewHolder> {

    private final List<Comic> mComics;
    private final PopularComicFragment.OnListPopularComicListener mListener;

    public MyComicRecyclerViewAdapter(List<Comic> items, PopularComicFragment.OnListPopularComicListener listener) {
        mComics = items;
        mListener = listener;
    }

    public MyComicRecyclerViewAdapter(PopularComicFragment.OnListPopularComicListener listener){
        mComics = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_popular_view, parent, false);
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
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mComics.get(position);
        holder.mComicTitle.setText(mComics.get(position).getTitle());
        holder.mComicLink.setText(mComics.get(position).getLink());

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



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mComicTitle;
        public final TextView mComicLink;
        public Comic mItem;

        public ViewHolder(View view) {
            super(view);
            mComicTitle= (TextView) view.findViewById(R.id.textView_popular_comic_title);
            mComicLink = (TextView) view.findViewById(R.id.textView_popular_comic_link);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mComicTitle.getText().toString() + "'";
        }
    }
}
