package com.example.orpuwupetup.zadanietapptic.Utils;

import android.text.TextUtils;

import com.example.orpuwupetup.zadanietapptic.data.Item;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cezar on 28.06.2018.
 */

// class with static methods for web related tasks
public class NetworkUtils {

    // base url from which we are creating correct url for fetching item details
    private final static String BASE_URL_FOR_TEXT_AND_IMAGE = "http://dev.tapptic.com/test/json.php?";

    // method fetching JSON from provided url
    private static String getJSONString(String webURLString, String name) {

        // we have to catch IOException here, because we are trying to get data from the web
        try {

            /*
            this if is put here so that we can use one method for fetching data for entire list of
            items, as well as for single item details. because of this we are using just one
            OkHttpClient, and saving some of the phones memory
            */
            if (name != null) {

                /*
                if "name" argument is equal to null, that means that we want to fetch details data
                from different url then this for list, so we are building correct url here
                */
                HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL_FOR_TEXT_AND_IMAGE).newBuilder();
                urlBuilder.addQueryParameter("name", name);
                webURLString = urlBuilder.build().toString();
            }

            // get JSONResponse from the web, no matter what the actual url is
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(webURLString)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // return null if there were any problem with internet connection
        return null;
    }

    // public method which other classes can call to get list of Items from the web
    public static ArrayList<Item> getItemList(String url) {

        /*
        second argument here is null, indicating that we will be fetching item list, not item
        details
        */
        String JSONResponse = getJSONString(url, null);
        return parseJSONToItemList(JSONResponse);
    }

    // method used to get list of Items from JSON
    private static ArrayList<Item> parseJSONToItemList(String JSONString) {

        // if JSON was empty, return null, because we don't have anything to parse to the list
        if (TextUtils.isEmpty(JSONString)) {
            return null;
        }

        ArrayList<Item> items = new ArrayList<>();

        // try-catch block in case of JSONException
        try {

            /*
            get to the correct fields in the JSON and fetch correct data from it, save it to the
            item and add this item to the list that will be returned
            */
            JSONArray rootArray = new JSONArray(JSONString);
            JSONObject item;
            String name;
            String imageUrl;
            for (int i = 0; i < rootArray.length(); i++) {
                item = rootArray.getJSONObject(i);
                name = item.getString("name");
                imageUrl = item.getString("image");
                items.add(new Item(name, imageUrl));
            }
        } catch (JSONException jx) {
            jx.printStackTrace();
        }
        return items;
    }

    // method used to get details of the item we want to display
    private static Item parseJSONToItemDetails(String JSONString) {

        // if JSON was empty, return null, because we don't have anything to parse to the Item
        if (TextUtils.isEmpty(JSONString)) {
            return null;
        }

        Item itemDetails = null;

        // try-catch block in case of JSONException
        try {

            // find correct fields in JSON and parse them to Item to be returned
            JSONObject rootObject = new JSONObject(JSONString);
            String text = rootObject.getString("text");
            String imageUrl = rootObject.getString("image");
            itemDetails = new Item(text, imageUrl);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return itemDetails;
    }

    // public method which other classes can call to get details of Item from the web
    public static Item getItemDetails(String name) {
        String JSONResponse = getJSONString(BASE_URL_FOR_TEXT_AND_IMAGE, name);
        return parseJSONToItemDetails(JSONResponse);
    }
}
