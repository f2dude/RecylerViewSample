package kristech.requests;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import kristech.readresponses.ImageResponse;


/**
 * Created by saikrishna.pawar on 15/03/16.
 */
public class SimpleImageRequest {

    private static final String CANCEL_TAG = "cancel_image"; //This tag is used for cancellation of a request
    private static SimpleImageRequest simpleImageRequest;
    private ImageRequest imageRequest;

    /**
     * Constructor is made private
     */
    private SimpleImageRequest() {

    }

    /**
     * Returns a static object of this class
     *
     * @return
     */
    public static SimpleImageRequest getInstance() {

        if (simpleImageRequest == null) {
            simpleImageRequest = new SimpleImageRequest();
        }

        return simpleImageRequest;
    }

    /**
     * This methods should be called while initiating a request
     *
     * @param ctx           Activity context
     * @param imageResponse Response from the web service
     * @param URL           The url that needs to be connected to
     */
    public ImageRequest initiateRequest(Context ctx, final ImageResponse imageResponse, String URL) {

        //request a image response from the provided url
        imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageResponse.imageResponse(response);
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageResponse.errorResponse(error);
            }
        });
        imageRequest.setTag(CANCEL_TAG);

        return imageRequest;
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
