package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.AllComicsResponse;
import comic.shannortrotty.gruntt.classes.BareComics;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shannortrotty on 2/28/17.
 * Interface of RetroFit ComicTV Service
 */

public interface RetrofitComicTVService {

    @GET("popular-comics/{pageNumber}")
    Call<List<PopularComic>> listPopularComics(@Path("pageNumber") String pageNumber);

    @GET("list-issues/{comicName}")
    Call<List<Chapter>> listComicChapters(@Path("comicName") String comicName);

    @GET("comic-list-AZ/")
    Call<List<AllComicsResponse>> listAllComics();

    @GET("read-comic/{comicName}/{chapterNumber}")
    Call<List<String>> listPages(@Path("comicName") String comicName, @Path("chapterNumber") String chapterNumber);

    @GET("{comicName}/description")
    Call<ComicDetails> getComicDescription(@Path("comicName") String comicName);


     Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://gruntt-156003.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
