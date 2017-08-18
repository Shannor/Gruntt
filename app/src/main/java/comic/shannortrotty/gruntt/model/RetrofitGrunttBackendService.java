package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Comic;
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

public interface RetrofitGrunttBackendService {

    @GET("popular-comics/{pageNumber}")
    Call<List<PopularComic>> getPopularComics(@Path("pageNumber") String pageNumber , @Query("source") String source);

    @GET("chapter-list/{comicName}")
    Call<List<Chapter>> getComicChapters(@Path("comicName") String comicName, @Query("source") String source);

    @GET("all-comics/")
    Call<List<Comic>> getAllComics(@Query("source") String source);

    @GET("chapter-pages/{comicName}/{chapterNumber}")
    Call<Pages> getPages(@Path("comicName") String comicName, @Path("chapterNumber") String chapterNumber, @Query("source") String source);

    @GET("description/{comicName}")
    Call<ComicDetails> getComicDescription(@Path("comicName") String comicName, @Query("source") String source);

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
