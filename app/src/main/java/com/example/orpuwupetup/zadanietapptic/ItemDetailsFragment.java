package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orpuwupetup.zadanietapptic.Utils.ItemDetailsAsyncLoader;
import com.example.orpuwupetup.zadanietapptic.data.Item;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Item>{

    private ImageView itemImage;
    private TextView itemText;
    private String itemName;
    private int itemIndex;

    // Mandatory empty constructor for initiating the fragment
    public ItemDetailsFragment(){}

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_details, container, false);

        // get references for both Views in the fragment layout
        itemImage = (ImageView) rootView.findViewById(R.id.detail_image);
        itemText = (TextView) rootView.findViewById(R.id.detail_text);

        if(savedInstanceState != null){
            itemName = savedInstanceState.getString("itemName");
            itemIndex = savedInstanceState.getInt("itemIndex");
        }

        getLoaderManager().initLoader(2, null, this);

        // return View
        return rootView;

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("itemName", itemName);
        outState.putInt("itemIndex", itemIndex);
        //TODO add url to this bundle
        super.onSaveInstanceState(outState);
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemIndex(int clickedItemIndex){
        this.itemIndex = clickedItemIndex;
    }

    @NonNull
    @Override
    public Loader<Item> onCreateLoader(int id, @Nullable Bundle args) {
        return new ItemDetailsAsyncLoader(getActivity(), this.itemName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Item> loader, Item data) {
        if(data != null) {
            itemText.setText(data.getText());
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Item> loader) {
        loader.abandon();
    }
}
