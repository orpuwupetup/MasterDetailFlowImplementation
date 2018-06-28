package com.example.orpuwupetup.zadanietapptic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orpuwupetup.zadanietapptic.Utils.ItemsAsyncLoader;
import com.example.orpuwupetup.zadanietapptic.Utils.NetworkUtils;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private RecyclerView list;

    // Mandatory empty constructor for initiating the fragment
    public ItemListFragment(){}

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        // get reference for RecyclerView included in fragment layout
        list = (RecyclerView) rootView.findViewById(R.id.rv_items);

        getLoaderManager().initLoader(1,null, this);

        // return View
        return rootView;

        //TODO: create local list of items to display, and set recycler view here with data provided from item list activity
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new ItemsAsyncLoader(getActivity(), "http://dev.tapptic.com/test/json.php");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d("mamyJSONa", data );
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
