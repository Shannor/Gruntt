package comic.shannortrotty.gruntt.model;

import java.util.List;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {

    interface OnResponseListListener<T>{
        void onListFinished(List<T> items);
        void onNextStep(List<T> items);
    }
    interface OnResponseItemListener<T>{
        void onItemFinished(T item);
    }

    //Will be edited
    interface OnProcessListener{
        void callProcess();
        void onFished();
    }
    void getPopularComics(String pageNumber, OnResponseListListener listener);
    void getAllComics(OnResponseListListener listener);
    void getChapters(String comicLink, OnResponseListListener listener);
    void getComicDescription(String comicLink, OnResponseItemListener listener);
    void getComicPages(String comicLink, String chapterNumber, OnResponseListListener listener);
}
