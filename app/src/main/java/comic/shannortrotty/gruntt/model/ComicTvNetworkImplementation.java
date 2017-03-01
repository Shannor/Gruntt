package comic.shannortrotty.gruntt.model;

import android.util.Log;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Comic;
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
    public void getAllComics(OnFinishedComicListener listener) {

    }

    @Override
    public void getChapters(OnFinishedChapterListener listener) {

    }

    @Override
    public void getComicDescription(OnItemFinishedListener listener) {

    }

    @Override
    public void getComicPages(OnFinishedComicListener listener) {

    }

    @Override
    public void getPopularComics(String pageNumber, final OnFinishedComicListener listener) {
        ComicTVService comicTVService = ComicTVService.retrofit.create(ComicTVService.class);
        Call<List<Comic>> call = comicTVService.listPopularComics(pageNumber);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {                Log.i(TAG, "onResponse: "+ response.body().toString());
                Log.i(TAG, "onResponse: "+ response.body());
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });

    }
}
