package comic.shannortrotty.gruntt.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import comic.shannortrotty.gruntt.R;
import comic.shannortrotty.gruntt.models.Chapter;

/**
 * Created by shannortrotty on 2/15/17.
 */

public class ChapterListAdapter extends ArrayAdapter<Chapter> {
    private List<Chapter> chapters;
    private Context mContext;

    public ChapterListAdapter(Context context, List<Chapter> chapterList){
        super(context, R.layout.listview_chapter_view, chapterList);
        mContext = context;
        chapters = chapterList;
    }
}
