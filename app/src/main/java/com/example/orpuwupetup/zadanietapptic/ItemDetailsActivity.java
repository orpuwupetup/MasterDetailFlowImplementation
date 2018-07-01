package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

// root activity for details fragment while the app is in portrait mode
public class ItemDetailsActivity extends AppCompatActivity {

    private boolean isTablet;
    public static int selectedItemIndex;
    int itemIndex;
    private String itemName;
    public static boolean wasLandscapeFromDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // get data to be displayed about Item details from sent intent
        Intent info = getIntent();
        Bundle itemInfo = info.getBundleExtra("infoBundle");
        isTablet = itemInfo.getBoolean("isTablet");
        itemName = itemInfo.getString("itemName");
        itemIndex = itemInfo.getInt("itemIndex");

        // if device was rotated, get correct values from savedInstanceState
        if(savedInstanceState != null){
            itemIndex = savedInstanceState.getInt("itemIndex");
            itemName = savedInstanceState.getString("itemName");
        }

        /*
        if we rotate device into landscape mode while using tablet, we don't want to display single
        DetailsFragment on the screen, but we want to open MainActivity with both details and list
        fragments displayed on one screen
        */
        if(isLandscape() && isTablet){

            /*
            notify that while using tablet, landscape orientation was opened from the details
            activity, so that we can go back to it from Main, after user will change screen rotation
            to portrait again
            */
            wasLandscapeFromDetails = true;

            /*
            create intent and bundle to put into it, fill this bundle with correct data and send it
            to MainActivity
            */
            Intent upIntent = new Intent(this, MainActivity.class);
            Bundle infoBundle2 = new Bundle();
            infoBundle2.putInt("itemIndex", itemIndex);
            infoBundle2.putString("itemName", itemName);
            upIntent.putExtra("infoBundle2", itemInfo);
            startActivity(upIntent);



            /*
            if the savedInstanceState is "empty", create new item detail fragment and display it on
            the screen with help of fragmentManager
            */
        } else if (savedInstanceState == null) {
            ItemDetailsFragment details = new ItemDetailsFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            details.setItemIndex(itemIndex);
            details.setItemName(itemName);
            fragmentManager.beginTransaction()
                    .add(R.id.details_container, details)
                    .commit();
        }

        /*
        selectedItemIndex is static variable created so that we can know in MainActivity which
        Item (at what index) was displayed in details fragment before we go back from it with press
        of back button (in portrait mode)
        */
        selectedItemIndex = itemIndex;
    }

    @Override
    public void onBackPressed() {

        /*
        we are changing this static variable of the MainActivity on back pressed, so that it can
        know that we're coming back from details activity, and that it should take value of
        displayed item index from here
        */
        MainActivity.BACK_PRESSED_IN_DETAILS = 1;
        selectedItemIndex = itemIndex;
        super.onBackPressed();
    }

    // save correct values when device is rotated etc.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("itemIndex", itemIndex);
        outState.putString("itemName", itemName);
        super.onSaveInstanceState(outState);
    }

    /*
    check if device is in portrait or landscape mode by checking which dimension is bigger (height
    or width)
    */
    private boolean isLandscape() {
        Display getOrient = getWindowManager().getDefaultDisplay();
        return getOrient.getWidth() > getOrient.getHeight();
    }
}
