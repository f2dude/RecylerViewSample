package kristech.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import kristech.cache.LruBitmapCache;


/**
 * Created by saikrishna.pawar on 15/03/16.
 */
public class SingletonInstance {

    private static SingletonInstance singletonInstance;
    private static Context ctx;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader, imageLoaderWithLruCache;

    /**
     * Private constructor implementation
     *
     * @param ctx activity context
     */
    private SingletonInstance(Context ctx) {

        SingletonInstance.ctx = ctx;
        requestQueue = getRequestQueue();

        //with default caching mechanism
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        //with Lrucache mechanism
        imageLoaderWithLruCache = new ImageLoader(requestQueue, new LruBitmapCache(
                LruBitmapCache.getCacheSize(ctx)));
    }

    /**
     * Provides single instance of this class object
     *
     * @param ctx context of the activity
     * @return SingletonInstance class object
     */
    public static synchronized SingletonInstance getInstance(Context ctx) {

        if (singletonInstance == null) {
            singletonInstance = new SingletonInstance(ctx);
        }
        return singletonInstance;
    }

    /**
     * Returns request queue object
     *
     * @return request queue object
     */
    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds the request to request queue object
     *
     * @param req request object
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Returns image loader object with default cache implementation
     *
     * @return image loader with default cache
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Returns image loader object with Lrucache mechanism
     *
     * @return image loader with lru cache
     */
    public ImageLoader getImageLoaderWithLruCache() {

        return imageLoaderWithLruCache;
    }
}
