package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ItemListFragment.DeepClickListener{

    private final static String PROVIDED_URL_ADDRESS = "http://dev.tapptic.com/test/json.php";
    public static boolean isTablet;
    private android.support.v4.app.FragmentManager fragmentManager;
    private int selectedItemIndex;
    private String selectedItemName;
    ItemListFragment list;
    public static int BACK_PRESSED_IN_DETAILS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = isTablet();

        // creating new ItemListFragment and adding it to the activity via fragmentManager

        fragmentManager =  getSupportFragmentManager();

        Intent landscapeIntent = getIntent();
        Bundle itemInfo = null;
        if (landscapeIntent != null) {
            itemInfo = landscapeIntent.getBundleExtra("infoBundle2");
        }

        if(savedInstanceState != null){
            selectedItemIndex = savedInstanceState.getInt("selectedIndex");
            selectedItemName = savedInstanceState.getString("selectedName");
        }

        Log.d("MainIndexTOP", ""+selectedItemIndex);

        list = new ItemListFragment();
        list.setUrl(PROVIDED_URL_ADDRESS);
        list.setDeepListener(this);
        if(isTablet && isLandscape()){
            ItemDetailsFragment tabletDetails = new ItemDetailsFragment();

                if(itemInfo != null) {
                    int clickedItemIndex = itemInfo.getInt("itemIndex");
                    String clickedItemName = itemInfo.getString("itemName");
                    selectedItemIndex = clickedItemIndex;
                    selectedItemName = clickedItemName;
                }
            tabletDetails.setItemIndex(selectedItemIndex);
            tabletDetails.setItemName(selectedItemName);


            if(savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.details_tablet_container, tabletDetails)
                        .commit();
                fragmentManager.beginTransaction()
                        .add(R.id.list_tablet_container, list)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.details_tablet_container, tabletDetails)
                        .commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.list_tablet_container, list)
                        .commit();
            }
        }else{

            if(savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.list_containter, list)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.list_containter, list)
                        .commit();

            }
        }


        Log.d("MainIndexBOTTOM", selectedItemIndex+"");

    }

    @Override
    public void onBackPressed() {
        if(!isLandscape() && !isTablet || !isTablet) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedIndex", selectedItemIndex);
        outState.putString("selectedName", selectedItemName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPostResume() {
        if(BACK_PRESSED_IN_DETAILS == 1) {
            selectedItemIndex = ItemDetailsActivity.selectedItemIndex;
            list.setSelectedItemIndex(selectedItemIndex);
            Log.d("indexfromdetails", ""+ selectedItemIndex);
            BACK_PRESSED_IN_DETAILS = 0;
        }

        super.onPostResume();
    }

    @Override
    public void deepOnListClick(String name, int clickedItemIndex) {
        Toast.makeText(this, "clicked item name" + name + "\n" + "clicked item index" + clickedItemIndex, Toast.LENGTH_SHORT).show();

        selectedItemIndex = clickedItemIndex;
        selectedItemName = name;
        Log.d("main,clickedindex", "" + selectedItemIndex);

        if(isTablet && isLandscape()){
            ItemDetailsFragment newTabletDetails = new ItemDetailsFragment();
            newTabletDetails.setItemName(name);
            newTabletDetails.setItemIndex(clickedItemIndex);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_tablet_container, newTabletDetails)
                    .commit();

        }else{
            Bundle itemInfoBundle = new Bundle();
            itemInfoBundle.putString("itemName", name);
            itemInfoBundle.putInt("itemIndex", clickedItemIndex);
            itemInfoBundle.putBoolean("isTablet", isTablet);

            Intent openDetailsIntent = new Intent(this, ItemDetailsActivity.class);
            openDetailsIntent.putExtra("infoBundle", itemInfoBundle);
            startActivity(openDetailsIntent);
        }
    }

    private boolean isLandscape() {

        Display getOrient = getWindowManager().getDefaultDisplay();
        return getOrient.getWidth() > getOrient.getHeight();

    }

    private boolean isTablet(){
        return findViewById(R.id.tablet_constraint_view) != null
                || findViewById(R.id.tablet_landscape_layout) != null;
    }
}
