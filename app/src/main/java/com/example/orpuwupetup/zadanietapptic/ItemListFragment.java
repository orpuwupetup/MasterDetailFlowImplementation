package com.example.orpuwupetup.zadanietapptic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.orpuwupetup.zadanietapptic.Utils.ItemsAsyncLoader;
import com.example.orpuwupetup.zadanietapptic.data.Item;
import com.example.orpuwupetup.zadanietapptic.data.ItemAdapter;

import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

// Fragment used for displaying Items list
public class ItemListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Item>>, ItemAdapter.ListItemClickListener {

    private RecyclerView list;
    private TextView connectionWarning;
    private ProgressBar progressIndicator;
    private String url;
    private int selectedItemIndex;
    private ItemAdapter adapter;
    private DeepClickListener deepListener;
    private List<Item> itemList;
    private boolean isConnected;

    // Mandatory empty constructor for initiating the fragment
    public ItemListFragment() {
    }

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        // checking internet connection
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        /*
        if savedInstanceState is not 'empty', check there which item on the list should be selected
        */
        if (savedInstanceState != null) {
            selectedItemIndex = savedInstanceState.getInt(getString(R.string.selected_item_index_key));
        }

        // get reference for RecyclerView included in fragment layout and set it up with manager
        list = rootView.findViewById(R.id.rv_items);
        connectionWarning = rootView.findViewById(R.id.no_connection_warning);
        progressIndicator = rootView.findViewById(R.id.progress_bar_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        /*
        initiate custom AsyncLoader to fetch data from the web on the background thread if there is
        internet connection available
        */
        if(isConnected) {
            connectionWarning.setVisibility(View.GONE);

            /*
            show progress bar while data is loading, so that user will know that something is
            happening (better user experience)
            */
            progressIndicator.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(1, null, this);
        }else{
            connectionWarning.setVisibility(View.VISIBLE);
        }


        // return View
        return rootView;
    }

    // save instanceState of selectedItemIndex variable
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(getString(R.string.selected_item_index_key), selectedItemIndex);
        super.onSaveInstanceState(outState);
    }

    // mandatory methods for implementing LoaderCallbacks
    @NonNull
    @Override
    public Loader<List<Item>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ItemsAsyncLoader(getActivity(), this.url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Item>> loader, List<Item> data) {
        itemList = data;

        // if there were no problem with fetching data, display it via adapter in RecyclerView
        if (data != null) {

            /*
            ColorStateList is set differently for text color (via code, programmatically) and for
            background color (via xml code in layout xml file) to test both methods
            */
            ColorStateList textColor = getResources().getColorStateList(R.color.state_dependent_text_color);
            adapter = new ItemAdapter(data.size(), this, data, textColor, getActivity());
            list.setAdapter(adapter);

            // hide progress bar when data is loaded
            progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Item>> loader) {
        loader.abandon();
    }

    /*
    on ListItemClick method 'throws' handling of clicks on the RecyclerView list even further 'up'
    to the deepListener which is implemented in the MainActivity
    */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        /*
        without this check, if we have clicked on the item in the list, and previously selected
        items Holder was not on screen, app would crash with NullPointerException
        */
        RecyclerView.ViewHolder v = list.findViewHolderForAdapterPosition(selectedItemIndex);
        if (v != null) {
            v.itemView.setSelected(false);
        }
        selectedItemIndex = clickedItemIndex;
        list.findViewHolderForAdapterPosition(selectedItemIndex).itemView.setSelected(true);

        /*
        we have to set this ItemAdapter static variable, so that the adapter will deselect views at
        the same ViewHolder, but with different item indexes
        */
        ItemAdapter.selectedItem = clickedItemIndex;

        /*
        if previously selected ViewHolder is not on screen, but list wasn't scrolled far enough for
        it to be recycled (and deselected), it is still null (won't be deselected automatically in
        line 152), so we have to notify adapter manually that data set has changed so that it will
        deselect it, and 2 views won't be selected simultaneously
        */
        if(v == null) {
            adapter.notifyDataSetChanged();
        }

        /*
        this method takes index of clicked item, finds correct Item name corresponding to it in the
        list and 'sends it' to be handled in MainActivity
        */
        Item clickedItem = itemList.get(clickedItemIndex);
        deepListener.deepOnListClick(clickedItem.getText(), clickedItemIndex);
    }

    // setter methods for some variables
    public void setUrl(String url) {
        this.url = url;
    }

    public void setDeepListener(DeepClickListener listener) {
        this.deepListener = listener;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    // interface needed to implement handling of clicks from RecyclerView in 'higher' classes
    public interface DeepClickListener {
        void deepOnListClick(String text, int clickedItemIndex);
    }
}
