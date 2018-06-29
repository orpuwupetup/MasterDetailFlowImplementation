package com.example.orpuwupetup.zadanietapptic;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ItemListFragment.DeepClickListener{

    private final static String PROVIDED_URL_ADDRESS = "http://dev.tapptic.com/test/json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // creating new ItemListFragment and adding it to the activity via fragmentManager
        ItemListFragment list = new ItemListFragment();
        android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();

        list.setUrl(PROVIDED_URL_ADDRESS);
        list.setDeepListener(this);

        fragmentManager.beginTransaction()
                .add(R.id.list_containter, list)
                .commit();
    }

    @Override
    public void deepOnListClick(String name, String url) {
        Toast.makeText(this, "clicked item name" + name + "\n" + "clicked item url" + url, Toast.LENGTH_SHORT).show();

        Bundle itemInfoBundle = new Bundle();
        itemInfoBundle.putString("itemName", name);
        itemInfoBundle.putString("itemImageUrl", url);

        Intent openDetailsIntent = new Intent(this, ItemDetailsActivity.class);
        openDetailsIntent.putExtra("infoBundle", itemInfoBundle);
        startActivity(openDetailsIntent);
    }
}
