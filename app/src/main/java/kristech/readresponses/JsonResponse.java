package kristech.readresponses;

import org.json.JSONObject;

/**
 * Created by saikrishna.pawar on 16/03/16.
 */
public interface JsonResponse extends BaseInterface {

    void jsonObjectResponse(JSONObject response);
}
