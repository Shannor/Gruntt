package comic.shannortrotty.gruntt.model;

import android.util.Log;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicSpecifics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shannortrotty on 2/28/17.
 * Implementation of NetworkModel to perform Request for readcomic.tv
 */

public class ComicTvNetworkImplementation implements NetworkModel {

    private static final String TAG = "ComicTvNetworkImplement";


    @Override
    public void getAllComics(OnFinishedListListener listener) {

    }

    /**
     *
     * @param comicLink
     * @param listener
     */
    @Override
    public void getChapters(String comicLink, final OnFinishedListListener listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit
                .create(RetrofitComicTVService.class);

        Call<List<Chapter>> call = retrofitComicTVService.listComicChapters(comicLink);
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Log.e(TAG, "Error on Retrofit call for Chapters", t);
            }
        });
    }

    /**
     *
     * @param comicLink
     * @param listener
     */
    @Override
    public void getComicDescription(String comicLink , final OnFinishedItemListener listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit
                .create(RetrofitComicTVService.class);

        Call<ComicSpecifics> call = retrofitComicTVService.getComicDescription(comicLink);
        call.enqueue(new Callback<ComicSpecifics>() {
            @Override
            public void onResponse(Call<ComicSpecifics> call, Response<ComicSpecifics> response) {
                listener.onItemFinished(response.body());
            }

            @Override
            public void onFailure(Call<ComicSpecifics> call, Throwable t) {

            }
        });
    }

    @Override
    public void getComicPages(String comicLink, String chapterNum, OnFinishedListListener listener) {

    }

    /**
     *
     * @param pageNumber
     * @param listener
     */
    @Override
    public void getPopularComics(String pageNumber, final OnFinishedListListener listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit.
                create(RetrofitComicTVService.class);

        Call<List<Comic>> call = retrofitComicTVService.listPopularComics(pageNumber);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });

    }
}
