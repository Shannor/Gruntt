package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.icu.util.ULocale;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.classes.AllComicsResponse;
import comic.shannortrotty.gruntt.classes.BareComics;
import comic.shannortrotty.gruntt.classes.OnComicListener;
import comic.shannortrotty.gruntt.fragments.AllComicsFragment;

/**
 * Created by shannortrotty on 3/15/17.
 */

public class AllComicRecyclerViewAdapter extends ExpandableRecyclerAdapter<AllComicsResponse, BareComics,
        AllComicRecyclerViewAdapter.CategoryViewHolder, AllComicRecyclerViewAdapter.ComicViewHolder> {

    private LayoutInflater mInflater;
    private OnComicListener mListener;

    public AllComicRecyclerViewAdapter(Context context, @NonNull List<AllComicsResponse> comicsResponseList, OnComicListener listener) {
        super(comicsResponseList);
        mInflater = LayoutInflater.from(context);
        this.mListener = listener;
    }
        // onCreate ...
        @Override
        public CategoryViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
            View recipeView = mInflater.inflate(R.layout.recycler_view_category, parentViewGroup, false);
            return new CategoryViewHolder(recipeView);
        }

        @Override
        public ComicViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
            View ingredientView = mInflater.inflate(R.layout.recycler_view_comic, childViewGroup, false);
            return new ComicViewHolder(ingredientView);
        }

        // onBind ...
        @Override
        public void onBindParentViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int parentPosition, @NonNull AllComicsResponse comicsResponse) {
            categoryViewHolder.bind(comicsResponse);
        }

        @Override
        public void onBindChildViewHolder(@NonNull ComicViewHolder comicViewHolder, int parentPosition, int childPosition, @NonNull BareComics comics) {
            comicViewHolder.bind(comics);
        }

    public class CategoryViewHolder extends ParentViewHolder {

        private TextView mCategoryTextView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryTextView = (TextView) itemView.findViewById(R.id.recyclerView_all_comics_category);
        }

        public void bind(AllComicsResponse response) {
            mCategoryTextView.setText(response.getCategory());
        }
    }

    public class ComicViewHolder extends ChildViewHolder {

        private TextView mComicTextView;
        private BareComics bareComic;

        public ComicViewHolder(View itemView) {
            super(itemView);
            mComicTextView = (TextView) itemView.findViewById(R.id.recyclerView_all_comics_comic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListComicSelection(bareComic, AllComicsFragment.TAG);
                }
            });
        }

        public void bind(BareComics comic) {
            mComicTextView.setText(comic.getTitle());
            bareComic = comic;
        }
    }
}
