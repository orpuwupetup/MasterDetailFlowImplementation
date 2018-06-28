package com.example.orpuwupetup.zadanietapptic.Utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.orpuwupetup.zadanietapptic.data.Item;

import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemsAsyncLoader extends android.support.v4.content.AsyncTaskLoader<List<Item>> {
    private String url;

    public ItemsAsyncLoader(Context context, String url){
        super(context);
        this.url = url;
    }


    @Override
    public List<Item> loadInBackground() {
        return NetworkUtils.getItemList(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}