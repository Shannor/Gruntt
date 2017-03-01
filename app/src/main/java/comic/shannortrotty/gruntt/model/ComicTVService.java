package comic.shannortrotty.gruntt.model;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicSpecifics;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shannortrotty on 2/28/17.
 */

public interface ComicTVService {

    @GET("popular-comics/{pageNumber}")
    Call<List<Comic>> listPopularComics(@Path("pageNumber") String pageNumber);

    @GET("list-issues/{comicName}")
    Call<List<Chapter>> listComicChapters(@Path("comicName") String comicName);

    @GET("comic-list-AZ/")
    Call<List<Comic>> listAllComics();

    @GET("read-comic/{comicName}/{chapterNumber}")
    Call<List<String>> listPages(@Path("comicName") String comicName, @Path("chapterNumber") String chapterNumber);

    @GET("{comicName}/description")
    Call<ComicSpecifics> getComicDescription(@Path("comicName") String comicName);


     Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://gruntt-156003.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
