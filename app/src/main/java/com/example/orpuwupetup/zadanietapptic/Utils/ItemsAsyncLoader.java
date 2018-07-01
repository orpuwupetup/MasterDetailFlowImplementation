package com.example.orpuwupetup.zadanietapptic.Utils;

import android.content.Context;

import com.example.orpuwupetup.zadanietapptic.MainActivity;
import com.example.orpuwupetup.zadanietapptic.data.Item;
import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

/*
asynchronous loader which has to fetch data about all the items we want to display in the Recycler
View
*/
public class ItemsAsyncLoader extends android.support.v4.content.AsyncTaskLoader<List<Item>> {

    private String url;

    public ItemsAsyncLoader(Context context){
        super(context);
        this.url = MainActivity.PROVIDED_URL_ADDRESS;
    }

    @Override
    public List<Item> loadInBackground() {
        return NetworkUtils.getItemList(url);
    }

    // this method is overriden to start whatever this loader has to do as soon as it is created
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}