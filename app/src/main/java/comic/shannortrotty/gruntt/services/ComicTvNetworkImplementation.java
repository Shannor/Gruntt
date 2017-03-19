package comic.shannortrotty.gruntt.services;

import android.util.Log;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.model.RetrofitComicTVService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shannortrotty on 2/28/17.
 * Implementation of NetworkModel to perform Request for readcomic.tv
 */

public class ComicTvNetworkImplementation implements NetworkModel {

    private static final String TAG = "ComicTvNetworkImplement";

    /**
     *
     * @param listener
     */
    @Override
    public void getAllComics(final OnResponseListListener<BareComicsCategory> listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit
                .create(RetrofitComicTVService.class);

        Call<List<BareComicsCategory>> call = retrofitComicTVService.listAllComics();
        listener.setRequestCall(call);
        call.enqueue(new Callback<List<BareComicsCategory>>() {
            @Override
            public void onResponse(Call<List<BareComicsCategory>> call, Response<List<BareComicsCategory>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<BareComicsCategory>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "onFailure: Error on All Comics Call.", t );
                }
            }
        });
    }

    /**
     *
     * @param comicLink
     * @param listener
     */
    @Override
    public void getChapters(String comicLink, final OnResponseListListener<Chapter> listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit
                .create(RetrofitComicTVService.class);

        Call<List<Chapter>> call = retrofitComicTVService.listComicChapters(comicLink);
        listener.setRequestCall(call);
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else {
                    Log.e(TAG, "Error on Retrofit call for Chapters. ", t);
                }
            }
        });
    }

    /**
     *
     * @param comicLink
     * @param listener
     */
    @Override
    public void getComicDescription(String comicLink , final OnResponseItemListener<ComicDetails> listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit
                .create(RetrofitComicTVService.class);

        Call<ComicDetails> call = retrofitComicTVService.getComicDescription(comicLink);
        listener.setRequestCall(call);
        call.enqueue(new Callback<ComicDetails>() {
            @Override
            public void onResponse(Call<ComicDetails> call, Response<ComicDetails> response) {
                listener.onItemFinished(response.body());

            }

            @Override
            public void onFailure(Call<ComicDetails> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "Error on Retrofit call for ComicDetails. ", t);
                }
            }
        });
    }

    @Override
    public void getChapterPages(String comicLink, String chapterNum, final OnResponseListListener<String> listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit.
                create(RetrofitComicTVService.class);

        Call<List<String>> call = retrofitComicTVService.listPages(comicLink, chapterNum);
        listener.setRequestCall(call);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "Error on Retrofit call for Chapter Pages. ", t);

                }
            }
        });
    }

    /**
     *
     * @param pageNumber
     * @param listener
     */
    @Override
    public void getPopularComics(String pageNumber, final OnResponseListListener<PopularComic> listener) {
        RetrofitComicTVService retrofitComicTVService = RetrofitComicTVService.retrofit.
                create(RetrofitComicTVService.class);

        Call<List<PopularComic>> call = retrofitComicTVService.listPopularComics(pageNumber);
        listener.setRequestCall(call);
        call.enqueue(new Callback<List<PopularComic>>() {
            @Override
            public void onResponse(Call<List<PopularComic>> call, Response<List<PopularComic>> response) {
                listener.onListFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<PopularComic>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "Error on Retrofit call for Popular Comics.", t);
                }
            }
        });

    }
}
