package com.example.orpuwupetup.zadanietapptic;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemListActivity extends AppCompatActivity {

    private final static String PROVIDED_URL_ADDRESS = "http://dev.tapptic.com/test/json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // creating new ItemListFragment and adding it to the activity via fragmentManager
        ItemListFragment list = new ItemListFragment();
        android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();

        list.setUrl(PROVIDED_URL_ADDRESS);

        fragmentManager.beginTransaction()
                .add(R.id.list_containter, list)
                .commit();
    }
}
