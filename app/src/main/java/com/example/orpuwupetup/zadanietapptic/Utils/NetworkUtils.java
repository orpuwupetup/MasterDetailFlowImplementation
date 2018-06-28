package com.example.orpuwupetup.zadanietapptic.Utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
}
