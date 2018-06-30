package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class ItemDetailsActivity extends AppCompatActivity {

    private boolean isTablet;
    public static int selectedItemIndex;
    int itemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent info = getIntent();
        Bundle itemInfo = info.getBundleExtra("infoBundle");
        isTablet = itemInfo.getBoolean("isTablet");
        String itemName = itemInfo.getString("itemName");
        itemIndex = itemInfo.getInt("itemIndex");

        if(savedInstanceState != null){
            itemIndex = savedInstanceState.getInt("itemIndex");
        }

        if(isLandscape() && isTablet){
            Intent upIntent = new Intent(this, MainActivity.class);
            Bundle infoBundle2 = new Bundle();
            infoBundle2.putInt("itemIndex", itemIndex);
            infoBundle2.putString("itemName", itemName);
            upIntent.putExtra("infoBundle2", itemInfo);
            startActivity(upIntent);
        } else if (savedInstanceState == null) {
            ItemDetailsFragment details = new ItemDetailsFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();



            details.setItemIndex(itemIndex);
            details.setItemName(itemName);

            fragmentManager.beginTransaction()
                    .add(R.id.details_container, details)
                    .commit();
        }
        selectedItemIndex = itemIndex;
    }

    @Override
    public void onBackPressed() {
        MainActivity.BACK_PRESSED_IN_DETAILS = 1;
        selectedItemIndex = itemIndex;
        super.onBackPressed();
    }

    private boolean isLandscape() {

        Display getOrient = getWindowManager().getDefaultDisplay();
        return getOrient.getWidth() > getOrient.getHeight();

    }
}
