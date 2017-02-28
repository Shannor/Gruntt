package comic.shannortrotty.gruntt.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comic.shannortrotty.gruntt.EventBusClasses.SendChapterPagesEvent;
import comic.shannortrotty.gruntt.EventBusClasses.SendChaptersEvent;
import comic.shannortrotty.gruntt.EventBusClasses.SendComicDescriptionEvent;
import comic.shannortrotty.gruntt.EventBusClasses.SendComicsEvent;
import comic.shannortrotty.gruntt.models.Comic;
import comic.shannortrotty.gruntt.models.ComicSpecifics;
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
    private static final String BASE_REQUEST_URL = "https://gruntt-156003.appspot.com/";
    private static final String ACTION_GET_SPECIFIC_COMIC_CHAPTERS_LIST = "comic.list.specific";
    private static final String ACTION_GET_SPECIFIC_COMIC_DESCRIPTION = "comic.specific.description";
    private static final String ACTION_GET_SPECIFIC_COMIC_PAGES = "comic.chapters.pages";

    private static final String CHAPTER_NUMBER = "comic.chapter.number";
    private static final String PAGE_NUMBER = "popular.comic.page.number";
    private static final String COMIC_NAME = "comic.name";
    private static final String FORMATTED_COMIC_LINK = "comic.formatted.link";
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

    /**
     *
     * @param context
     * @param formattedComicLink
     */
    public static void startActionGetChapterList(Context context, String formattedComicLink){
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_SPECIFIC_COMIC_CHAPTERS_LIST);
        intent.putExtra(FORMATTED_COMIC_LINK, formattedComicLink);
        context.startService(intent);
    }

    /**
     *
     * @param context
     * @param formattedComicLink
     */
    public static void startActionGetSpecificComicDescription(Context context, String formattedComicLink){
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_SPECIFIC_COMIC_DESCRIPTION);
        intent.putExtra(FORMATTED_COMIC_LINK, formattedComicLink);
        context.startService(intent);
    }

    public static void startActionGetChapterPages(Context context, String formattedComicLink, String chapterNumber){
        Intent intent = new Intent(context, ComicTvHttpService.class);
        intent.setAction(ACTION_GET_SPECIFIC_COMIC_PAGES);
        intent.putExtra(FORMATTED_COMIC_LINK, formattedComicLink);
        intent.putExtra(CHAPTER_NUMBER, chapterNumber);
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
            }else if (ACTION_GET_SPECIFIC_COMIC_CHAPTERS_LIST.equals(action)){
                final String comicName = intent.getStringExtra(FORMATTED_COMIC_LINK);
                handleActionGetComicChapters(comicName);
            }else if (ACTION_GET_SPECIFIC_COMIC_DESCRIPTION.equals(action)){
                final String comicName = intent.getStringExtra(FORMATTED_COMIC_LINK);
                handleActionGetSpecificComicDescription(comicName);
            }else if (ACTION_GET_SPECIFIC_COMIC_PAGES.equals(action)){
                final  String comicLink = intent.getStringExtra(FORMATTED_COMIC_LINK);
                final String chapterNumber = intent.getStringExtra(CHAPTER_NUMBER);
                handleActionComicPages(comicLink, chapterNumber);
            }
        }
    }

    private void handleActionGetSpecificComicDescription(final String formattedComicName){
        final String request = BASE_REQUEST_URL + formattedComicName + "/description";
        JsonObjectRequest descriptionJSONObject = new JsonObjectRequest(Request.Method.GET, request, null, new Response.Listener<JSONObject>() {
            ComicSpecifics comicSpecifics = new ComicSpecifics();
            @Override
            public void onResponse(JSONObject response) {
                try {
                    comicSpecifics.setName(response.getString("name"));
                    comicSpecifics.setAltName(response.getString("alternate Name"));
                    comicSpecifics.setAuthor(response.getString("author"));
                    comicSpecifics.setGenre(response.getString("genre"));
                    comicSpecifics.setReleaseDate(response.getString("year of Release"));
                    comicSpecifics.setLargeImgURL(response.getString("largeImg"));
                    comicSpecifics.setDescription(response.getString("description"));
                    comicSpecifics.setStatus(response.getString("status"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new SendComicDescriptionEvent(comicSpecifics));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error in call for Description.", error);
            }
        });
        VolleyWrapper.getInstance(getApplicationContext()).addToRequestQueue(descriptionJSONObject);
    }


    private void handleActionGetComicChapters(String formattedComicName){
        final String request = BASE_REQUEST_URL + "list-issues/" + formattedComicName;
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


    private void handleActionComicPages(String comicName, String chapterNumber){
        final String request = BASE_REQUEST_URL + "read-comic/" + comicName + "/" + chapterNumber;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> pagesLinks = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); ++i){
                        pagesLinks.add(response.getString(i));
                    }
                }catch(JSONException e){
                    Log.e(TAG,"Error: Reading Data from Chapter pages Request",e);
                }
                EventBus.getDefault().post(new SendChapterPagesEvent(pagesLinks));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyWrapper.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    /**
     * Handler method to create the JSON Array Request and call it.
     * Sends a broadcast of a list of all Comics on this page.
     */
    private void handleActionPopular(String pageNumber) {
        final String request = BASE_REQUEST_URL + "popular-comics/" + pageNumber;

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
