package com.example.orpuwupetup.zadanietapptic;

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

/*
fragment containing list of the Items fetched from the web, implements LoaderCallbacks, to get
callback after loading of list is done
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

        /*
        boilerplate code for finding views (I did not use ButterKnife library or even better
        in my opinion DataBinding, because it wasn't listed in libraries that I can use in this
        exercise ¯\_(ツ)_/¯ )
        */
        itemImage = (ImageView) rootView.findViewById(R.id.detail_image);
        itemText = (TextView) rootView.findViewById(R.id.detail_text);

        // set correct variables to their savedInstanceState after device was rotated (etc.)
        if(savedInstanceState != null){
            itemName = savedInstanceState.getString("itemName");
            itemIndex = savedInstanceState.getInt("itemIndex");
        }

        // initiate custom AsyncLoader to fetch data from the web on the background thread
        getLoaderManager().initLoader(2, null, this);

        // return View
        return rootView;
    }
    /*
    saving state of important variables when device is rotated (or fragment is killed in other way)
    */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("itemName", itemName);
        outState.putInt("itemIndex", itemIndex);
        super.onSaveInstanceState(outState);
    }

    // setter methods for some variables
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemIndex(int clickedItemIndex){
        this.itemIndex = clickedItemIndex;
    }

    // mandatory methods for implementing LoaderCallbacks
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
