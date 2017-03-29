package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.ReadComicActivity;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.OnChapterListener;
import comic.shannortrotty.gruntt.fragments.ChapterListFragment;

/**
 * Created by shannortrotty on 2/15/17.
 * Simple list adapter for displaying chapters
 */

public class ChapterListAdapter extends ArrayAdapter<Chapter> {
    private List<Chapter> mChapters;
    private Context mContext;
    private OnChapterListener mListener;

    public ChapterListAdapter(Context context, List<Chapter> chapterList, OnChapterListener listener){
        super(context, R.layout.listview_chapter_view, chapterList);
        mContext = context;
        mChapters = chapterList;
        mListener = listener;
    }

    public ChapterListAdapter(Context context, OnChapterListener listener){
        super(context, R.layout.listview_chapter_view);
        mContext = context;
        mChapters = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final Chapter chapter = getItem(position);
        //Check if Reusing block or new
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_chapter_view, parent, false);
        }

        TextView chapterName = ((TextView) convertView.findViewById(R.id.textView_listView_chapter_name));
        TextView chapterReleaseDate = ((TextView) convertView.findViewById(R.id.textView_listView_chapter_release_date));

        chapterName.setText(chapter.getChapterName());
        chapterReleaseDate.setText(chapter.getReleaseDate());
        if(chapter.getHaveRead()){
            //TODO:Change to strings color
            chapterName.setTextColor(Color.GRAY);
            chapterReleaseDate.setTextColor(Color.GRAY);
        }else{
            chapterName.setTextColor(ContextCompat.getColor(mContext, R.color.primary_text));
            chapterReleaseDate.setTextColor(ContextCompat.getColor(mContext, R.color.primary_text));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start activity for reading that chapter
                mListener.onClickedChapter(chapter,position);
            }
        });
        return convertView;
    }

    public int getIndex(Chapter chapter){
        return this.mChapters.indexOf(chapter);
    }

    public void addChapters(List<Chapter> chapters){
        this.mChapters.clear();
        if (Integer.parseInt(chapters.get(0).getChapterNumber()) <= 1){
            Collections.reverse(chapters);
        }
        this.mChapters.addAll(chapters);
        notifyDataSetChanged();
    }

    public List<Chapter> getChapters(){
        return this.mChapters;
    }

    @Nullable
    @Override
    public Chapter getItem(int position) {
        return  mChapters.get(position);
    }

    @Override
    public int getCount() {
        return mChapters.size();
    }
}
