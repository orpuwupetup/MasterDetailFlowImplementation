package com.example.orpuwupetup.zadanietapptic.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import com.example.orpuwupetup.zadanietapptic.data.Item;

/**
 * Created by cezar on 30.06.2018.
 */

/*
asynchronous loader which has to fetch data about details of the item to display from the web
in the back thread
*/
public class ItemDetailsAsyncLoader extends android.support.v4.content.AsyncTaskLoader<Item>  {

    private String name;

    public ItemDetailsAsyncLoader (Context context, String name){
        super(context);
        this.name = name;
    }

    @Nullable
    @Override
    public Item loadInBackground() {
        return NetworkUtils.getItemDetails(name);
    }

    // this method is overriden to start whatever this loader has to do as soon as it is created
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
