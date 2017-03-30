package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Pages;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.SearchComic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shannortrotty on 2/28/17.
 * Interface of RetroFit ComicTV Service
 */

public interface RetrofitComicTVService {

    @GET("popular-comics/{pageNumber}")
    Call<List<PopularComic>> listPopularComics(@Path("pageNumber") String pageNumber);

    @GET("list-chapters/{comicName}")
    Call<List<Chapter>> listComicChapters(@Path("comicName") String comicName);

    @GET("comic-list-AZ/")
    Call<List<BareComicsCategory>> listAllComics();

    @GET("read-comic/{comicName}/{chapterNumber}")
    Call<Pages> listPages(@Path("comicName") String comicName, @Path("chapterNumber") String chapterNumber);

    @GET("{comicName}/description")
    Call<ComicDetails> getComicDescription(@Path("comicName") String comicName);

    @GET("advanced-search")
    Call<List<SearchComic>> searchComics(@Query("key") String keyword,
                                         @Query("include") String include,
                                         @Query("exclude") String exclude,
                                         @Query("status") String Status,
                                          @Query("page")String pageNumber);

    @GET("search-categories")
    Call<List<String>> getSearchCategories();

    //TODO: Maybe add timeout's
     Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://gruntt-156003.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}
