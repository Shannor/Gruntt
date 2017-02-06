package comic.shannortrotty.gruntt.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import comic.shannortrotty.gruntt.MainActivity;

/**
 * Created by shannortrotty on 2/3/17.
 * Classed to setup basic Volley
 * Can be extended for more specific implementations and settings.
 */

public class VolleyWrapper {
    private static VolleyWrapper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private ImageLoader mImageLoader;
    private static final String TAG = "VolleyWrapper";
//Use JSON Object for {}, list-comics use this
//Use JSON Array for [], popular comics use this
    private VolleyWrapper(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
        //Setup Image loader
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url,bitmap);
            }
        });
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

    public ImageLoader getmImageLoader(){
        return this.mImageLoader;
    }

    <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
