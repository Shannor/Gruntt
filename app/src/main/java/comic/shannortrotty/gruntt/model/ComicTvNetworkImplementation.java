package comic.shannortrotty.gruntt.model;

import android.os.AsyncTask;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ComicTvNetworkImplementation implements NetworkModel {
    private static final String BASE_REQUEST_URL = "https://gruntt-156003.appspot.com/";

    @Override
    public void getAllComics(OnFinishedListener listener) {

    }

    @Override
    public void getChapters(OnFinishedListener listener) {

    }

    @Override
    public void getComicDescription(OnItemFinishedListener listener) {

    }

    @Override
    public void getComicPages(OnFinishedListener listener) {

    }

    @Override
    public void getPopularComics(String pageNumber,OnFinishedListener listener) {
        final String requestURL = BASE_REQUEST_URL + "popular-comics/" + pageNumber;

    }
}
