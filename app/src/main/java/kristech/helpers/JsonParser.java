package kristech.helpers;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by saikrishna.pawar on 05/09/16.
 */

public class JsonParser {

//    public static <T extends Object> T parseJsonArray(String response, Class<T> modelClass) {
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<modelClass>>(){}.getType();
//        List<ModelDTO> dataList = (List<ModelDTO>)gson.fromJson(response, listType);
//
//        return (T) dataList;
//    }

    /**
     * Converts Json object to required java object
     *
     * @param jsonObject JsonObject which you want to parse
     * @param modelClass model class in which format you want to parse
     * @param <T>        Required Class Type as result
     * @return returns Object of input class type with binding json data
     */
    public static <T extends Object> T parseJson(Object jsonObject, Class<T> modelClass) {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = getInputStream(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reader == null) {
            return null;
        }
        try {
            return gson.fromJson(reader, modelClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts jsonobject to inputstream
     *
     * @param response json object of service
     * @return returns the input stream through reader
     */
    private static Reader getInputStream(String response) {
        InputStream result = null;
        try {
            result = new ByteArrayInputStream(response.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(result);
        return reader;
    }
}
