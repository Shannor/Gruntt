package comic.shannortrotty.gruntt.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendChaptersEvent;
import comic.shannortrotty.gruntt.EventBusClasses.SendComicsEvent;
import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.models.ComicEventBus;
import comic.shannortrotty.gruntt.models.Genre;
import comic.shannortrotty.gruntt.models.Chapter;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ComicTvHttpService extends IntentService {

    private static final String ACTION_GET_AZ_COMIC_LIST = "comic.az.list";
    private static final String ACTION_GET_POPULAR_COMIC_LIST = "comic.popular.list";
    private static final String BASE_REQUEST_URL = "https://gruntt-156003.appspot.com";
    private static final String ACTION_GET_SPECIFIC_COMIC_LIST = "comic.list.specific";

    private static final String CHAPTER_NUMBER = "comic.chapter.number";
    private static final String PAGE_NUMBER = "popular.comic.page.number";
    private static final String COMIC_NAME = "comic.name";
    public static final String TAG = "ComicTvHttpService";

    //Used to send information back to Activity
    public ComicTvHttpService() {
        super("ComicTvHttpService");
//        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionAZList(Context context) {
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_AZ_COMIC_LIST);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action GET Popular List with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPoplarList(Context context, String pageNumber) {
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_POPULAR_COMIC_LIST);
        intent.putExtra(PAGE_NUMBER, pageNumber);
        context.startService(intent);
    }

    public static void startActionGetChapterList(Context context, String comicName){
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_SPECIFIC_COMIC_LIST);
        intent.putExtra(COMIC_NAME, comicName);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_GET_AZ_COMIC_LIST.equals(action)) {
                handleActionAZList();
            } else if (ACTION_GET_POPULAR_COMIC_LIST.equals(action)) {
                final String pageNumber = intent.getStringExtra(PAGE_NUMBER);
                handleActionPopular(pageNumber);
            }else if (ACTION_GET_SPECIFIC_COMIC_LIST.equals(action)){
                final String comicName = intent.getStringExtra(COMIC_NAME);
                handleActionGetComicList(comicName);
            }
        }
    }

    private void handleActionGetComicList(String comicName){
        final String request = BASE_REQUEST_URL + "/list-issues/" + comicName;
        JsonArrayRequest mainJSONArray = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Chapter> chapters = new ArrayList<>();
                for(int i=0; i< response.length(); ++i){
                    try{
                        JSONObject issueInfo = response.getJSONObject(i);
                        Chapter chapter = new Chapter(issueInfo.getString("chapterName"),
                                issueInfo.getString("link"),
                                issueInfo.getString("releaseDate"));
                        chapters.add(chapter);

                    }catch (JSONException e){
                        Log.e(TAG,"Error:Reading Data from request",e);
                    }
                }
                EventBus.getDefault().post(new SendChaptersEvent(chapters));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Response error in GetComicList", error);
            }
        });
    VolleyWrapper.getInstance(getApplicationContext()).addToRequestQueue(mainJSONArray);
    }
    /**
     *
     */
    private void handleActionAZList() {

    }

    /**
     * Handler method to create the JSON Array Request and call it.
     * Sends a broadcast of a list of all Comics on this page.
     */
    private void handleActionPopular(String pageNumber) {
        final String request = BASE_REQUEST_URL + "/popular-comics/" + pageNumber;

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
                        Log.e(TAG,"JSON Exception: When trying to get Popular Comics.",e);
                    }
                }
                EventBus.getDefault().post(new SendComicsEvent(comics));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: Response error on PopularComics: ", error);
            }
        });
    VolleyWrapper.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }



}
