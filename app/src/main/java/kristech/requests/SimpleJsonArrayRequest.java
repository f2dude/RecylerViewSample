package kristech.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import kristech.readresponses.JsonArrayResponse;

/**
 * Created by saikrishna.pawar on 16/03/16.
 */
public class SimpleJsonArrayRequest {

    private static final String CANCEL_TAG = "cancel_simple_json_array"; //This tag is used for cancellation of a request
    private static SimpleJsonArrayRequest simpleJsonArrayRequest;
    private JsonArrayRequest jsonArrayRequest;

    /**
     * Constructor is made private
     */
    private SimpleJsonArrayRequest() {

    }

    /**
     * Returns a static object of this class
     *
     * @return
     */
    public static SimpleJsonArrayRequest getInstance() {

        if (simpleJsonArrayRequest == null) {
            simpleJsonArrayRequest = new SimpleJsonArrayRequest();
        }

        return simpleJsonArrayRequest;
    }

    /**
     * This methods should be called while initiating a request
     *
     * @param ctx               Activity context
     * @param jsonArrayResponse Response from the web service
     * @param URL               The url that needs to be connected to
     */
    public JsonArrayRequest initiateRequest(Context ctx, final JsonArrayResponse jsonArrayResponse, String URL) {

        //request a json object response from the provided url
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jsonArrayResponse.readJsonArrayResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonArrayResponse.errorResponse(error);
            }
        });

        return jsonArrayRequest;
    }

    /**
     * This method should be called in onStop of an activity/fragment
     */
    public void cancelRequest(RequestQueue queue) {
        if (queue != null) {
            queue.cancelAll(CANCEL_TAG);
        }
    }
}
