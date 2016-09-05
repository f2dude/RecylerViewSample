package kristech.requests;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import kristech.readresponses.StringResponse;


/**
 * Created by saikrishna.pawar on 14/03/16.
 */
public class NetworkCacheRequest {

    private static final String CANCEL_TAG = "cancel_network_cache"; //This tag is used for cancellation of a request
    private static NetworkCacheRequest networkCacheRequest;
    private RequestQueue queue;
    private StringRequest stringRequest;

    /**
     * Constructor is made private
     */
    private NetworkCacheRequest() {

    }

    /**
     * Returns a static object of this class
     *
     * @return
     */
    public static NetworkCacheRequest getInstance() {

        if (networkCacheRequest == null) {
            networkCacheRequest = new NetworkCacheRequest();
        }

        return networkCacheRequest;
    }

    /**
     * This methods should be called while initiating a request
     *
     * @param ctx            Activity context
     * @param stringResponse Response from the web service
     * @param URL            The url that needs to be connected to
     */
    public void initiateRequest(Context ctx, final StringResponse stringResponse, String URL) {

        //Instantiate the cache
        Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024);//1 MB cap

        //Set up the network to use http url connection as http client
        Network network = new BasicNetwork(new HurlStack());

        //Instantiate the request queue
        queue = new RequestQueue(cache, network);

        //start the queue
        queue.start();

        //request a string response from the provided url
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stringResponse.stringResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stringResponse.errorResponse(error);
            }
        });

        stringRequest.setTag(CANCEL_TAG);//tag is set for cancelling a request
        queue.add(stringRequest);
    }

    /**
     * This method should be called in onStop of an activity/fragment
     */
    public void cancelRequest() {
        if (queue != null) {
            queue.cancelAll(CANCEL_TAG);
        }

    }
}
