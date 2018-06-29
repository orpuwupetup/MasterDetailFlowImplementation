package com.example.orpuwupetup.zadanietapptic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.orpuwupetup.zadanietapptic.Utils.ItemsAsyncLoader;
import com.example.orpuwupetup.zadanietapptic.data.Item;
import com.example.orpuwupetup.zadanietapptic.data.ItemAdapter;

import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Item>>, ItemAdapter.ListItemClickListener {

    private RecyclerView list;
    private String url;
    private ItemAdapter adapter;
    private DeepClickListener deepListener;
    private List<Item> itemList;

    // Mandatory empty constructor for initiating the fragment
    public ItemListFragment(){}

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        // get reference for RecyclerView included in fragment layout
        list = (RecyclerView) rootView.findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        getLoaderManager().initLoader(1,null, this);

        // return View
        return rootView;

    }

    @NonNull
    @Override
    public Loader<List<Item>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ItemsAsyncLoader(getActivity(), this.url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Item>> loader, List<Item> data) {
        itemList = data;
        adapter = new ItemAdapter(data.size(), this, data);
        list.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Item>> loader) {
        loader.abandon();
    }

    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Item clickedItem = itemList.get(clickedItemIndex);
        deepListener.deepOnListClick(clickedItem.getName(), clickedItem.getImageUrl());
    }

    public void setDeepListener (DeepClickListener listener){
        this.deepListener = listener;
    }

    public interface DeepClickListener {
        void deepOnListClick(String name, String url);
    }
}
