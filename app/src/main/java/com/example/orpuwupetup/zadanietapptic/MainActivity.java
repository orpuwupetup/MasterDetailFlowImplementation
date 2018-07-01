package com.example.orpuwupetup.zadanietapptic;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orpuwupetup.zadanietapptic.data.ItemAdapter;

/*
activity used to display list of Items while in portrait mode and landscape mode in smaller devices
and to display both list and details in landscape mode of bigger devices (tablets)
*/
public class MainActivity extends AppCompatActivity implements ItemListFragment.DeepClickListener {

    // constants
    private final static String PROVIDED_URL_ADDRESS = "http://dev.tapptic.com/test/json.php";
    private final static String DEFAULT_SELECTED_ITEM_NAME = "1";
    private final static int DEFAULT_SELECTED_ITEM_INDEX = 0;

    public static boolean isTablet;
    public static int BACK_PRESSED_IN_DETAILS = 0;
    ItemListFragment list;
    private android.support.v4.app.FragmentManager fragmentManager;
    private int selectedItemIndex = DEFAULT_SELECTED_ITEM_INDEX;
    private String selectedItemName = DEFAULT_SELECTED_ITEM_NAME;
    private TextView connectionWarning;
    private View divider;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting boolean indicating if app is opened on phone or on tablet
        isTablet = isTablet();

        // checking for internet connection
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // saving reference to fragmentManager for use in further parts of the app
        fragmentManager = getSupportFragmentManager();

        /*
        we are fetching intent here, because if Main is opened from the Details activity after it
        changed its mode from portrait to landscape on tablet device, the Intent is sent from which
        we have to extract data to display in ItemDetailsFragment
        */
        Intent landscapeIntent = getIntent();
        Bundle itemInfo = null;
        if (landscapeIntent != null) {
            itemInfo = landscapeIntent.getBundleExtra("infoBundle2");
        }
        /*
        fetching data from savedInstanceState, after activity is recreated after orientation change
        */
        if (savedInstanceState != null) {
            selectedItemIndex = savedInstanceState.getInt("selectedIndex");
            selectedItemName = savedInstanceState.getString("selectedName");
        }

        // TODO delete log
        Log.d("MainIndexTOP", "" + selectedItemIndex);

        /*
        code for creating new fragments, filling and displaying them according to when they are
        created or what device we are using (I THINK that it can be done in better way, so that it
        is not necessary to create new fragment every time activity is recreated, but I don't know
        this better way YET. every time I was not creating new ItemListFragment there were
        NullPointerException after activity recreation when I clicked on any list item, even after I
        was setting ListFragment deepListener manually myself in onCreate here, when
        savedInstanceState was different than null)
        */
        list = new ItemListFragment();
        list.setUrl(PROVIDED_URL_ADDRESS);
        list.setDeepListener(this);

        divider = findViewById(R.id.pane_divider);
        connectionWarning = findViewById(R.id.no_connection_warning);
        if (isTablet && isLandscape()) {

            if (isConnected) {

                connectionWarning.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
            /*
            we have to create ItemDetailsFragment only when we are in landscape orientation, while
            using tablet
            */
                ItemDetailsFragment tabletDetails = new ItemDetailsFragment();
                if (itemInfo != null) {

                    /*
                    itemInfo is a bundle included in landscapeIntent, so if it is different than
                    null, it means that we came from DetailsActivity, after switching from portrait
                    to landscape on tablet, so we have to take our values from there and set them
                    here
                    */
                    int clickedItemIndex = itemInfo.getInt("itemIndex");
                    String clickedItemName = itemInfo.getString("itemName");
                    selectedItemIndex = clickedItemIndex;
                    selectedItemName = clickedItemName;
                }

            /*
            if savedInstanceState is different than null, we want to to take values from it, even if
            landscapeIntent is different than null
            */
                if (savedInstanceState != null) {
                    selectedItemIndex = savedInstanceState.getInt("selectedIndex");
                    selectedItemName = savedInstanceState.getString("selectedName");
                }

                tabletDetails.setItemIndex(selectedItemIndex);
                tabletDetails.setItemName(selectedItemName);
                list.setSelectedItemIndex(selectedItemIndex);

            /*
            if savedInstanceState is null, we want to add new fragments to correct containers, but
            if its different than null, it means that one fragment is already placed there, and we
            want to replace it, and don't add another on top of it, so that we will have just one
            scrolling fragment, in stead of two, one scrolling, and one stationary (been there,
            done that)
            */
                if (savedInstanceState == null) {
                    fragmentManager.beginTransaction()
                            .add(R.id.details_tablet_container, tabletDetails)
                            .commit();
                    fragmentManager.beginTransaction()
                            .add(R.id.list_tablet_container, list)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_tablet_container, tabletDetails)
                            .commit();
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_tablet_container, list)
                            .commit();
                }
            } else {
                connectionWarning.setVisibility(View.VISIBLE);
                divider.setVisibility(View.GONE);
            }

        /*
        if we are using app on the tablet, and we are in DetailsActivity in portrait mode, and we
        turn our tablet to landscape mode, two-pane layout is opening, but after we go back to
        portrait mode, we want to display detailActivity again, because that was last activity that
        was opened before turning to landscape
        */
        } else if (isTablet && !isLandscape() && ItemDetailsActivity.wasLandscapeFromDetails) {

            // get data to be displayed about Item details from sent intent
            Intent info = new Intent(this, ItemDetailsActivity.class);
            itemInfo = new Bundle();
            itemInfo.putBoolean("isTablet", isTablet);
            itemInfo.putString("itemName", selectedItemName);
            itemInfo.putInt("itemIndex", selectedItemIndex);
            info.putExtra("infoBundle", itemInfo);
            ItemDetailsActivity.wasLandscapeFromDetails = false;
            startActivity(info);
        } else {
            list.setSelectedItemIndex(selectedItemIndex);

            // same as in comment in line 128
            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.list_containter, list)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.list_containter, list)
                        .commit();
            }
        }

        //TODO delete Log
        Log.d("MainIndexBOTTOM", selectedItemIndex + "");
        ItemAdapter.selectedItem = selectedItemIndex;
    }

    /*
    when we are in tablet landscape mode, pressing back button is disabled, so that we can't go
    back to the single ItemDetailsActivity while the device is rotated to landscape
    */
    @Override
    public void onBackPressed() {
        if (!isLandscape() && !isTablet) {
            super.onBackPressed();
        }
    }

    // saving important values when device is rotated (etc)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedIndex", selectedItemIndex);
        outState.putString("selectedName", selectedItemName);
        super.onSaveInstanceState(outState);
    }

    /*
    this is needed so that after coming back from the details activity with back button, we know
    which element we want to make selected or focused in list to indicate that it was last chosen
    */
    @Override
    protected void onPostResume() {
        if (BACK_PRESSED_IN_DETAILS == 1) {
            selectedItemIndex = ItemDetailsActivity.selectedItemIndex;
            list.setSelectedItemIndex(selectedItemIndex);

            /*
            if we go back from portrait details activity after returning there from tablet landscape
            orientation, we have to put our list fragment inside its container again
            */
            if (!isLandscape() && isTablet()) {
                fragmentManager.beginTransaction()
                        .replace(R.id.list_containter, list)
                        .commit();
            }
            // TODO delete log
            Log.d("indexfromdetails", "" + selectedItemIndex);
            BACK_PRESSED_IN_DETAILS = 0;
        }
        super.onPostResume();
    }

    /*
    here we are finally handling clicks on the items from the list, thrown here all the way "down"
    from ItemAdapter
    */
    @Override
    public void deepOnListClick(String name, int clickedItemIndex) {

        //TODO delete Toast
        Toast.makeText(this, "clicked item name" + name + "\n" + "clicked item index"
                + clickedItemIndex, Toast.LENGTH_SHORT).show();

        // save what Item was clicked to mark it as selected
        selectedItemIndex = clickedItemIndex;
        selectedItemName = name;

        //TODO delete log
        Log.d("main,clickedindex", "" + selectedItemIndex);

        /*
        if we are in landscape orientation while using tablet, we want to both mark Item in list as
        selected and display it in the ItemDetails fragment
        */
        if (isTablet && isLandscape()) {
            ItemDetailsFragment newTabletDetails = new ItemDetailsFragment();
            newTabletDetails.setItemName(name);
            newTabletDetails.setItemIndex(clickedItemIndex);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_tablet_container, newTabletDetails)
                    .commit();

        /*
        in all the other cases we want to pack all important data about what item was clicked, and
        what orientation device is currently in into bundle, and send it via intent to new item
        details activity
        */
        } else {
            Bundle itemInfoBundle = new Bundle();
            itemInfoBundle.putString("itemName", name);
            itemInfoBundle.putInt("itemIndex", clickedItemIndex);
            itemInfoBundle.putBoolean("isTablet", isTablet);
            Intent openDetailsIntent = new Intent(this, ItemDetailsActivity.class);
            openDetailsIntent.putExtra("infoBundle", itemInfoBundle);
            startActivity(openDetailsIntent);
        }
    }

    /*
    method which checks if device is in landscape mode by comparing its width and height (bigger
    width = landscape, bigger height = portrait
    */
    private boolean isLandscape() {
        Display getOrient = getWindowManager().getDefaultDisplay();
        return getOrient.getWidth() > getOrient.getHeight();
    }

    /*
    method which checks if we are using tablet by checking if Views specified only for tablets are
    created or not (both in landscape and portrait modes)
    */
    private boolean isTablet() {
        return findViewById(R.id.tablet_constraint_view) != null
                || findViewById(R.id.tablet_landscape_layout) != null;
    }
}
