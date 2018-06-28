package com.example.orpuwupetup.zadanietapptic;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // creating new ItemListFragment and adding it to the activity via fragmentManager
        ItemListFragment list = new ItemListFragment();
        android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.list_containter, list)
                .commit();
    }
}
