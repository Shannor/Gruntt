package comic.shannortrotty.gruntt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.fragments.FavoriteComicsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFavoriteComicsRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteComicsRecyclerViewAdapter.ViewHolder> {

    private final List<ComicDetails> mValues;
    private final OnListFragmentInteractionListener mListener;


    public MyFavoriteComicsRecyclerViewAdapter(List<ComicDetails> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_comics, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ComicDetails comicDetail = mValues.get(position);
        holder.getTitle().setText(comicDetail.getTitle());
        holder.getAuthor().setText(comicDetail.getAuthor());
        holder.getStatus().setText(comicDetail.getStatus());
        holder.getComicImage().setImageBitmap(comicDetail.getLocalBitmap());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.onListFragmentInteraction(comicDetail);
            }
        });
//        holder.mItem = mValues.get(position);
////        holder.mIdView.setText(mValues.get(position).id);
////        holder.mContentView.setText(mValues.get(position).content);
//
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
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mComicImage;
        private final TextView mTitle;
        private final TextView mStatus;
        private final TextView mAuthor;

        ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.mTitle = (TextView) view.findViewById(R.id.textView_favorite_comic_title);
            this.mStatus = (TextView) view.findViewById(R.id.textView_favorite_comic_status);
            this.mAuthor = ((TextView) view.findViewById(R.id.textView_favorite_comic_author));
            this.mComicImage = ((ImageView) view.findViewById(R.id.imageView_favorite_comic_image));

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

        public TextView getStatus() {
            return mStatus;
        }

        public TextView getAuthor() {
            return mAuthor;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
