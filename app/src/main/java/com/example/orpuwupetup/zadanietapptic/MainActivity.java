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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = isTablet();

        // TODO: LOGS
        Log.d("mainactivitycreation", "islandscape" + isLandscape());
        Log.d("mainactivitycreation", "istablet" + isTablet);
        // creating new ItemListFragment and adding it to the activity via fragmentManager
        ItemListFragment list = new ItemListFragment();
        fragmentManager =  getSupportFragmentManager();

        list.setUrl(PROVIDED_URL_ADDRESS);
        list.setDeepListener(this);

        if(isTablet && isLandscape()){
            ItemDetailsFragment tabletDetails = new ItemDetailsFragment();
            Intent landscapeIntent = getIntent();
            if (landscapeIntent != null) {

                Bundle itemInfo = landscapeIntent.getBundleExtra("infoBundle");
                if(itemInfo != null) {
                    tabletDetails.setItemName(itemInfo.getString("itemName"));
                    tabletDetails.setItemImageUrl(itemInfo.getString("itemImageUrl"));
                }
            }
            fragmentManager.beginTransaction()
                    .add(R.id.details_tablet_container, tabletDetails)
                    .commit();
            fragmentManager.beginTransaction()
                    .add(R.id.list_tablet_container, list)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .add(R.id.list_containter, list)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(!isLandscape() && !isTablet || !isTablet) {
            super.onBackPressed();
        }
    }

    @Override
    public void deepOnListClick(String name, String url) {
        Toast.makeText(this, "clicked item name" + name + "\n" + "clicked item url" + url, Toast.LENGTH_SHORT).show();

        if(isTablet && isLandscape()){
            ItemDetailsFragment newTabletDetails = new ItemDetailsFragment();
            newTabletDetails.setItemName(name);
            newTabletDetails.setItemImageUrl(url);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_tablet_container, newTabletDetails)
                    .commit();

        }else{
            Bundle itemInfoBundle = new Bundle();
            itemInfoBundle.putString("itemName", name);
            itemInfoBundle.putString("itemImageUrl", url);
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
