package comic.shannortrotty.gruntt.services;

import android.util.Log;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.ComicDetails;
import comic.shannortrotty.gruntt.classes.Pages;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.SearchComic;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.model.RetrofitGrunttBackendService;
import comic.shannortrotty.gruntt.utils.ErrorUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shannortrotty on 2/28/17.
 * Implementation of NetworkModel to perform Request for readcomic.tv
 */

public class GrunttRESTfulImpl implements NetworkModel {

    private static final String TAG = "ComicTvNetworkImplement";
    /**
     *
     * @param pageNumber
     * @param listener
     */
    @Override
    public void getPopularComics(String pageNumber, String source, final OnResponseListListener<PopularComic> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit.
                create(RetrofitGrunttBackendService.class);

        Call<List<PopularComic>> call = retrofitGrunttBackendService.getPopularComics(pageNumber,source);
        listener.setRequestListCall(call);
        call.enqueue(new Callback<List<PopularComic>>() {
            @Override
            public void onResponse(Call<List<PopularComic>> call, Response<List<PopularComic>> response) {
                if(response.isSuccessful()) {
                    listener.onListSuccess(response.body());
                }else{
                    Log.d(TAG, "onResponse: " + response.toString());
                    APIError error = ErrorUtils.parseError(response);
                    listener.onListFailure(error);
                }
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
    /**
     *
     * @param listener
     */
    @Override
    public void getAllComics(String source, final OnResponseListListener<Comic> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit
                .create(RetrofitGrunttBackendService.class);

        Call<List<Comic>> call = retrofitGrunttBackendService.getAllComics(source);
        listener.setRequestListCall(call);
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if(response.isSuccessful()){
                    listener.onListSuccess(response.body());
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    listener.onListFailure(error);
                }
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {
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
    public void getChapters(String comicLink, String source, final OnResponseListListener<Chapter> listener, final OnResponseItemListener<Chapter> itemListener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit
                .create(RetrofitGrunttBackendService.class);

        Call<List<Chapter>> call = retrofitGrunttBackendService.getComicChapters(comicLink, source);
        listener.setRequestListCall(call);
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
               if(response.isSuccessful()) {
                    itemListener.onItemSuccess(response.body().get(0));
                    listener.onListSuccess(response.body());
                }else{
                   APIError error = ErrorUtils.parseError(response);
                   listener.onListFailure(error);
               }

            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else {
                    //TODO: Need error catching of some type
                    listener.onAPIFailure(t);
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
    public void getComicDescription(String comicLink , String source, final OnResponseItemListener<ComicDetails> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit
                .create(RetrofitGrunttBackendService.class);

        Call<ComicDetails> call = retrofitGrunttBackendService.getComicDescription(comicLink, source);
        listener.setRequestCall(call);
        call.enqueue(new Callback<ComicDetails>() {
            @Override
            public void onResponse(Call<ComicDetails> call, Response<ComicDetails> response) {
                if(response.isSuccessful()) {
                    listener.onItemSuccess(response.body());
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    listener.onItemFailure(error);
                }
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

    /**
     *
     * @param comicLink
     * @param chapterNum
     * @param listener
     */
    @Override
    public void getChapterPages(String comicLink, String chapterNum, String source, final OnResponseItemListener<Pages> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit.
                create(RetrofitGrunttBackendService.class);

        Call<Pages> call = retrofitGrunttBackendService.getPages(comicLink, chapterNum, source);
        listener.setRequestCall(call);
        call.enqueue(new Callback<Pages>() {
            @Override
            public void onResponse(Call<Pages> call, Response<Pages> response) {
                if(response.isSuccessful()) {
                    listener.onItemSuccess(response.body());
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    listener.onItemFailure(error);
                }
            }

            @Override
            public void onFailure(Call<Pages> call, Throwable t) {
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
     * @param keyword
     * @param include
     * @param exclude
     * @param status
     * @param listener
     */
    @Override
    public void searchComics(String keyword, String include, String exclude, String status, String pageNumber,final OnResponseListListener<SearchComic> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit
                .create(RetrofitGrunttBackendService.class);
        Call<List<SearchComic>> call = retrofitGrunttBackendService.searchComics(keyword,include,exclude,status,pageNumber);
        listener.setRequestListCall(call);

        call.enqueue(new Callback<List<SearchComic>>() {
            @Override
            public void onResponse(Call<List<SearchComic>> call, Response<List<SearchComic>> response) {
                if(response.isSuccessful()) {
                    listener.onListSuccess(response.body());
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    listener.onListFailure(error);
                }
            }

            @Override
            public void onFailure(Call<List<SearchComic>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "Error on Retrofit call for Search Comics.", t);
                    listener.onAPIFailure(t);
                }
            }
        });
    }


    /**
     *
     * @param listener
     */
    @Override
    public void getSearchCategories(final OnResponseListListener<String> listener) {
        RetrofitGrunttBackendService retrofitGrunttBackendService = RetrofitGrunttBackendService.retrofit
                .create(RetrofitGrunttBackendService.class);
        Call<List<String>> call = retrofitGrunttBackendService.getSearchCategories();
        listener.setRequestListCall(call);

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    listener.onListSuccess(response.body());
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    listener.onListFailure(error);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if(call.isCanceled()){
                    Log.e(TAG, "User canceled the Call. ", t);
                }else{
                    Log.e(TAG, "Error on Retrofit call for Search Categories.", t);
                    listener.onAPIFailure(t);
                }
            }
        });
    }
}
