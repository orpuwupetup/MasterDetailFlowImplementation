package com.example.orpuwupetup.zadanietapptic.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemsAsyncLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    private String url;

    public ItemsAsyncLoader(Context context, String url){
        super(context);
        this.url = url;
    }


    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONString(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}