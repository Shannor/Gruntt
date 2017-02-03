package comic.shannortrotty.gruntt.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    private static final String REQUEST_URL = "https://gruntt-156003.appspot.com";

    // TODO: Rename parameters
    private static final String CHAPTER_NUMBER = "comic.shannortrotty.gruntt.services.extra.PARAM1";
    private static final String PAGE_NUMBER = "comic.shannortrotty.gruntt.services.extra.PARAM2";
    private static final String COMIC_NAME = "";

    private RequestQueue queue;

    public ComicTvHttpService() {
        super("ComicTvHttpService");
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
     * Starts this service to perform action Baz with the given parameters. If
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
                final String param1 = intent.getStringExtra(PAGE_NUMBER);
                handleActionPopular(param1);
            }
        }
    }

    /**
     *
     */
    private void handleActionAZList() {
        queue = Volley.newRequestQueue(this);
    // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.d("Response","Response is: "+ response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error on Response","That didn't work!");
                }
            });
    // Add the request to the RequestQueue.
            queue.add(stringRequest);
    }

    /**
     *
     */
    private void handleActionPopular(String pageNumber) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
