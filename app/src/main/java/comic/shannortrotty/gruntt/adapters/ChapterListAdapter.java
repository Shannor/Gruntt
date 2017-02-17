package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.ReadComicActivity;
import comic.shannortrotty.gruntt.models.Chapter;

/**
 * Created by shannortrotty on 2/15/17.
 * Simple list adapter for displaying chapters
 */

public class ChapterListAdapter extends ArrayAdapter<Chapter> {
    private List<Chapter> mChapters;
    private Context mContext;

    public ChapterListAdapter(Context context, List<Chapter> chapterList){
        super(context, R.layout.listview_chapter_view, chapterList);
        mContext = context;
        mChapters = chapterList;
    }

    public ChapterListAdapter(Context context){
        super(context, R.layout.listview_chapter_view);
        mContext = context;
        mChapters = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Chapter chapter = getItem(position);
        //Check if Reusing block or new
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_chapter_view, parent, false);
        }

        TextView chapterName = ((TextView) convertView.findViewById(R.id.textView_listView_chapter_name));
        TextView chapterReleaseDate = ((TextView) convertView.findViewById(R.id.textView_listView_chapter_release_date));

        chapterName.setText(chapter.getChapterName());
        chapterReleaseDate.setText(chapter.getReleaseDate());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Start activity for reading that chapter
                Toast.makeText(mContext, "Would start reading Activity", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public void addChapters(List<Chapter> chapters){
        this.mChapters.clear();
        Collections.reverse(chapters);
        this.mChapters.addAll(chapters);
        notifyDataSetChanged();
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
