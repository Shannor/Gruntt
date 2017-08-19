package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.fragments.PopularComicFragment;
import comic.shannortrotty.gruntt.utils.OnComicListener;


/**
 * Created by shannortrotty on 3/15/17.
 */

public class ComicRecyclerViewAdapter
        extends RecyclerView.Adapter<ComicRecyclerViewAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter{

    private OnComicListener mListener;
    private List<Comic> mComics;
    private List<Comic> originalComics;
    private final Context mContext;
    private final static String TAG = "AllComicRecyclerView";

    public ComicRecyclerViewAdapter(Context context, List<Comic> items, OnComicListener listener) {
        mComics = items;
        originalComics = new ArrayList<>(items);
        mListener = listener;
        mContext = context;
    }

    @Override
    public ComicRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_comic, parent, false);
        //Where margins, size, padding can be set for the view
        return new ComicRecyclerViewAdapter.ViewHolder(view);
    }

    public void filter(String text){
        mComics.clear();
        if(text.isEmpty()){
            mComics.addAll(originalComics);
        }else{
            text = text.toLowerCase(Locale.getDefault());
            for( Comic comic : originalComics){
                String title = comic.getTitle().toLowerCase(Locale.getDefault());
                if(title.contains(text)){
                    mComics.add(comic);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void addItems(List<Comic> comics){
        mComics.clear();
        mComics.addAll(comics);
        notifyDataSetChanged();
    }

    public void clearItems(){
        mComics.clear();
        notifyDataSetChanged();
    }

    public void restoreAll(){
        mComics.clear();
        mComics.addAll(originalComics);
        notifyDataSetChanged();
    }
    /**
     * Method where data should be set, replace contents
     * @param holder viewHolder
     * @param position location in the data set
     */
    @Override
    public void onBindViewHolder(final ComicRecyclerViewAdapter.ViewHolder holder, int position) {
        final Comic comic = mComics.get(position);
        holder.getComicTitle().setText(comic.getTitle());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListComicSelection(comic, PopularComicFragment.TAG);
            }
        });
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return  mComics.get(position).getTitle().substring(0,1);
    }

    @Override
    public int getItemCount() {
        return mComics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mComicTitle;
        private final View mView;

        ViewHolder(View view) {
            super(view);
            mComicTitle = (TextView) view.findViewById(R.id.recyclerView_comic_name);
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
    }
}
