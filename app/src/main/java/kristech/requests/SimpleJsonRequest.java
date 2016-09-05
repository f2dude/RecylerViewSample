package kristech.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import kristech.readresponses.JsonResponse;

/**
 * Created by saikrishna.pawar on 16/03/16.
 */
public class SimpleJsonRequest {

    private static final String CANCEL_TAG = "cancel_simple_json_object"; //This tag is used for cancellation of a request
    private static SimpleJsonRequest simpleJsonRequest;
    private JsonObjectRequest jsonObjectRequest;

    /**
     * Constructor is made private
     */
    private SimpleJsonRequest() {

    }

    /**
     * Returns a static object of this class
     *
     * @return
     */
    public static SimpleJsonRequest getInstance() {

        if (simpleJsonRequest == null) {
            simpleJsonRequest = new SimpleJsonRequest();
        }

        return simpleJsonRequest;
    }

    /**
     * This methods should be called while initiating a request
     *
     * @param ctx          Activity context
     * @param jsonResponse Response from the web service
     * @param URL          The url that needs to be connected to
     */
    public JsonObjectRequest initiateRequest(Context ctx, final JsonResponse jsonResponse, String URL) {

        //request a json object response from the provided url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonResponse.jsonObjectResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonResponse.errorResponse(error);
            }
        });

        return jsonObjectRequest;
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
