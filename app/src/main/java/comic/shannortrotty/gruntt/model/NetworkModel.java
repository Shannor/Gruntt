package comic.shannortrotty.gruntt.model;

import java.util.List;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {

    interface OnFinishedListListener<T>{
        void onListFinished(List<T> items);
    }
    interface OnFinishedItemListener<T>{
        void onItemFinished(T item);
    }

    //Will be edited
    interface OnProcess{
        void currentProcess();
    }
    void getPopularComics(String pageNumber, OnFinishedListListener listener);
    void getAllComics(OnFinishedListListener listener);
    void getChapters(String comicLink, OnFinishedListListener listener);
    void getComicDescription(String comicLink, OnFinishedItemListener listener);
    void getComicPages(String comicLink, String chapterNumber, OnFinishedListListener listener);
}
