package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class ItemDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        boolean isTablet = MainActivity.isTablet;

        Log.d("detailsactivitycreation", "islandscape" + isLandscape());
        Log.d("detailsactivitycreation", "istablet" + isTablet);
        if(isLandscape() && isTablet){
            Intent upIntent = new Intent(this, MainActivity.class);
            startActivity(upIntent);
        }

        ItemDetailsFragment details = new ItemDetailsFragment();
        android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();

        Intent info = getIntent();
        Bundle itemInfo = info.getBundleExtra("infoBundle");

        details.setItemImageUrl(itemInfo.getString("itemImageUrl"));
        details.setItemName(itemInfo.getString("itemName"));

        fragmentManager.beginTransaction()
                .add(R.id.details_container, details)
                .commit();
    }

    private boolean isLandscape() {

        Display getOrient = getWindowManager().getDefaultDisplay();
        return getOrient.getWidth() > getOrient.getHeight();

    }
}
