package comic.shannortrotty.gruntt.model;

import java.util.List;

import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {

    interface OnResponseListListener<T>{
        void onListFinished(List<T> items);
        void onCanceledRequest(Call<T> call);
    }
    interface OnResponseItemListener<T>{
        void onItemFinished(T item);
        void onCanceledRequest(Call<T> call);
    }

    void getPopularComics(String pageNumber, OnResponseListListener listener);
    void getAllComics(OnResponseListListener listener);
    void getChapters(String comicLink, OnResponseListListener listener);
    void getComicDescription(String comicLink, OnResponseItemListener listener);
    void getComicPages(String comicLink, String chapterNumber, OnResponseListListener listener);
}
