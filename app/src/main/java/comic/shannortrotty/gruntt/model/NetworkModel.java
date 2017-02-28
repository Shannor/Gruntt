package comic.shannortrotty.gruntt.model;

import java.util.List;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {
    interface OnFinishedListener<T>{
        void onListFinished(List<T> items);
    }
    interface OnItemFinishedListener<T>{
        void onItemFinished(T item);
    }
    void getPopularComics(OnFinishedListener listener);
    void getAllComics(OnFinishedListener listener);
    void getChapters(OnFinishedListener listener);
    void getComicDescription(OnItemFinishedListener listener);
    void getComicPages(OnFinishedListener listener);
}
