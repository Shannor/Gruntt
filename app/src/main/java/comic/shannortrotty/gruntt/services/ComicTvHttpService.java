package comic.shannortrotty.gruntt.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.models.Genre;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ComicTvHttpService extends IntentService {

    private static final String ACTION_FETCH_AZ_JSON = "comic.az.list";
    private static final String ACTION_FETCH_POPULAR_JSON = "comic.popular.list";
    private static final String BASE_REQUEST_URL = "https://gruntt-156003.appspot.com";
    private static final String ACTION_SEND_COMIC_LIST_BROADCAST = "comic.list.activity";

    private static final String CHAPTER_NUMBER = "comic.chapter.number";
    private static final String PAGE_NUMBER = "popular.comic.page.number";
    private static final String COMIC_NAME = "comic.name";
    private static final String BROADCAST_COMIC_LIST_TAG = "comic.list";

    //Used to send information back to Activity
    private LocalBroadcastManager broadcastManager;

    public ComicTvHttpService() {
        super("ComicTvHttpService");
//        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionAZList(Context context) {
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_FETCH_AZ_JSON);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action GET Popular List with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionPoplarList(Context context, String pageNumber) {
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_FETCH_POPULAR_JSON);
        intent.putExtra(PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_FETCH_AZ_JSON.equals(action)) {
                handleActionAZList();
            } else if (ACTION_FETCH_POPULAR_JSON.equals(action)) {
                final String pageNumber = intent.getStringExtra(PAGE_NUMBER);
                handleActionPopular(pageNumber);
            }
        }
    }

    public void sendComics(ArrayList<Comic> comics){
        Intent intent = new Intent(ACTION_SEND_COMIC_LIST_BROADCAST);
        intent.putParcelableArrayListExtra(BROADCAST_COMIC_LIST_TAG, comics);
        broadcastManager.sendBroadcast(intent);
    }

    /**
     *
     */
    private void handleActionAZList() {

    }

    /**
     * Handler method create the JSON Array Request and call it.
     * Sends a broadcast of a list of all Comics on this page.
     */
    private void handleActionPopular(String pageNumber) {
        String request = BASE_REQUEST_URL + "/popular-comics/" + pageNumber;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Get information from Array in a list and broadcast it
                List<Comic> comics = new ArrayList<>();
                //Loop through responses
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonComic = response.getJSONObject(i);
                        Comic comic = new Comic();
                        //Sets basic comic info
                        comic.setTitle(jsonComic.getString("title"));
                        comic.setLink(jsonComic.getString("link"));
                        comic.setThumbnailUrl(jsonComic.getString("img"));

                        JSONArray jsonGenreArray = jsonComic.getJSONArray("genre");
                        //Get the Genre of the comic if there is one
                        for(int j = 0; j < jsonGenreArray.length(); j++){
                            JSONObject jsonGenre = jsonGenreArray.getJSONObject(j);
                            Genre genre = new Genre(jsonGenre.getString("name"), jsonGenre.getString("genreLink"));
                            comic.addGenre(genre);
                        }

                        comics.add(comic);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //TODO:Need to broadcast information
                Log.d("Comic Object List",comics.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });
    VolleyWrapper.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
}
