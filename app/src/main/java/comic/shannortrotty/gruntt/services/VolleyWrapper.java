package comic.shannortrotty.gruntt.services;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class VolleyWrapper {
    private static VolleyWrapper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
//Use JSON Object for {}, list-comics use this
//Use JSON Array for [], popular comics use this
    private VolleyWrapper(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Singleton Inti for the the Volley class
     * @param context Application Context
     * @return VolleyWrapper Instance
     */
    public static synchronized VolleyWrapper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyWrapper(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
