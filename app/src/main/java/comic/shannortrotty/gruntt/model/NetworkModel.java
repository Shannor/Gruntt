package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.PopularComic;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {

    interface OnResponseListListener<T>{
        void onListFinished(List<T> items);
        void onCanceledRequest(Call<List<T>> call);
    }
    interface OnResponseItemListener<T>{
        void onItemFinished(T item);
        void onCanceledRequest(Call<T> call);
    }

    void getPopularComics(String pageNumber, OnResponseListListener<PopularComic> listener);
    void getAllComics(OnResponseListListener<BareComicsCategory> listener);
    void getChapters(String comicLink, OnResponseListListener<Chapter> listener);
    void getComicDescription(String comicLink, OnResponseItemListener<ComicDetails> listener);
    void getComicPages(String comicLink, String chapterNumber, OnResponseListListener<String> listener);
}
