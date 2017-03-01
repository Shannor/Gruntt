package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicSpecifics;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {
    interface OnFinishedComicListener{
        void onListFinished(List<Comic> items);
    }
    interface OnItemFinishedListener{
        void onItemFinished(ComicSpecifics item);
    }

    interface OnFinishedChapterListener{
        void onListFinished(List<Chapter> items);
    }

    //Will be edited
    interface OnProcess{
        void currentProcess();
    }
    void getPopularComics(String pageNumber, OnFinishedComicListener listener);
    void getAllComics(OnFinishedComicListener listener);
    void getChapters(String comicLink, OnFinishedChapterListener listener);
    void getComicDescription(String comicLink, OnItemFinishedListener listener);
    void getComicPages(String comicLink, String chapterNumber,OnFinishedComicListener listener);
}
