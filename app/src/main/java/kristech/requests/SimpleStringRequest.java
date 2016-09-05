package kristech.requests;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import kristech.readresponses.StringResponse;

/**
 * Created by saikrishna.pawar on 14/03/16.
 */
public class SimpleStringRequest {

    private static final String CANCEL_TAG = "cancel_simple"; //This tag is used for cancellation of a request
    private static SimpleStringRequest simpleStringRequest;
    private StringRequest stringRequest;

    /**
     * Constructor is made private
     */
    private SimpleStringRequest() {

    }

    /**
     * Returns a static object of this class
     *
     * @return
     */
    public static SimpleStringRequest getInstance() {

        if (simpleStringRequest == null) {
            simpleStringRequest = new SimpleStringRequest();
        }

        return simpleStringRequest;
    }

    /**
     * This methods should be called while initiating a request
     *
     * @param ctx            Activity context
     * @param stringResponse Response from the web service
     * @param URL            The url that needs to be connected to
     */
    public StringRequest initiateRequest(Context ctx, final StringResponse stringResponse, String URL) {

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
        }) {
            /**
             *
             * For adding request headers
             * @return
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            /**
             * For adding request params
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        stringRequest.setTag(CANCEL_TAG);//tag is set for cancelling a request

        return stringRequest;
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
