package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.SearchComic;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface NetworkModel {

    interface OnResponseListListener<T>{
        void onListSuccess(List<T> items);
        void setRequestListCall(Call<List<T>> call);
        void onListFailed(Throwable throwable);
    }
    interface OnResponseItemListener<T>{
        void onItemSuccess(T item);
        void setRequestCall(Call<T> call);
    }

    void getPopularComics(String pageNumber, OnResponseListListener<PopularComic> listener);
    void getAllComics(OnResponseListListener<BareComicsCategory> listener);
    void getChapters(String comicLink, OnResponseListListener<Chapter> listener, OnResponseItemListener<Chapter> chapterListener);
    void getComicDescription(String comicLink, OnResponseItemListener<ComicDetails> listener);
    void getChapterPages(String comicLink, String chapterNumber, OnResponseListListener<String> listener);
    void searchComics(String keyword, String include, String exclude,
                      String status,String pageNumber, OnResponseListListener<SearchComic> listener);
    void getSearchCategories(OnResponseListListener<String> listener);
}
