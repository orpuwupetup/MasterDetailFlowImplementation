package com.example.orpuwupetup.zadanietapptic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.orpuwupetup.zadanietapptic.Utils.ItemDetailsAsyncLoader;
import com.example.orpuwupetup.zadanietapptic.data.Item;
import com.squareup.picasso.Picasso;


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
    private TextView connectionWarning;
    private String itemName;
    private int itemIndex;
    public static boolean isConnected;
    private ProgressBar progressIndicator;
    private ImageButton refresh;

    // Mandatory empty constructor for initiating the fragment
    public ItemDetailsFragment(){}

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_details, container, false);

        // checking internet connection
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        /*
        boilerplate code for finding views (I did not use ButterKnife library or even better
        in my opinion DataBinding, because it wasn't listed in libraries that I can use in this
        exercise ¯\_(ツ)_/¯ )
        */
        itemImage = rootView.findViewById(R.id.detail_image);
        itemText = rootView.findViewById(R.id.detail_text);
        connectionWarning = rootView.findViewById(R.id.no_connection_warning);
        progressIndicator = rootView.findViewById(R.id.progress_bar_indicator);
        refresh = rootView.findViewById(R.id.refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.wasRecreatedAfterNoConnection = true;
                getActivity().recreate();
            }
        });

        // set correct variables to their savedInstanceState after device was rotated (etc.)
        if(savedInstanceState != null){
            itemName = savedInstanceState.getString(getString(R.string.item_name_key));
            itemIndex = savedInstanceState.getInt(getString(R.string.item_index_key));
        }

        /*
        initiate custom AsyncLoader to fetch data from the web on the background thread (if there is
        internet connection, if not, tell it to the user)
        */
        if(isConnected) {

            if(MainActivity.wasRecreatedAfterNoConnection){
                MainActivity.wasRecreatedAfterNoConnection = false;
                itemIndex = MainActivity.DEFAULT_SELECTED_ITEM_INDEX;
                itemName = MainActivity.DEFAULT_SELECTED_ITEM_NAME;
            }

            /*
            show progress bar while data is loading, so that user will know that something is
            happening (better user experience)
            */
            progressIndicator.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            connectionWarning.setVisibility(View.GONE);
            getLoaderManager().initLoader(2, null, this);
        }else{
            connectionWarning.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.VISIBLE);
        }

        // return View
        return rootView;
    }

    /*
    saving state of important variables when device is rotated (or fragment is killed in other way)
    */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(getString(R.string.item_name_key), itemName);
        outState.putInt(getString(R.string.item_index_key), itemIndex);
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

            Picasso.with(getActivity()).load(data.getImageUrl()).fit().centerInside().into(itemImage);

            // hide progress bar when data is loaded
            progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Item> loader) {
        loader.abandon();
    }
}
