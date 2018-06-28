package com.example.orpuwupetup.zadanietapptic.Utils;

import android.text.TextUtils;

import com.example.orpuwupetup.zadanietapptic.data.Item;
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

public class NetworkUtils {

    public static String getJSONString(String webURLString){

        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(webURLString)
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();

        }catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Item> getItemList (String url){

        String JSONResponse = getJSONString(url);
        ArrayList<Item> items = parseJSONToItemList(JSONResponse);
        return items;
    }

    public static ArrayList<Item> parseJSONToItemList(String JSONString){

        if (TextUtils.isEmpty(JSONString)){
            return null;
        }
        ArrayList<Item> items = new ArrayList<>();

        try {
            JSONArray rootArray = new JSONArray(JSONString);
            JSONObject item;
            String name;
            String imageUrl;
            for(int i = 0; i < rootArray.length(); i++){
                item = rootArray.getJSONObject(i);
                name = item.getString("name");
                imageUrl = item.getString("image");

                items.add(new Item(name, imageUrl));
            }


        } catch (JSONException jx){
            jx.printStackTrace();
        }

        return items;
    }
}
