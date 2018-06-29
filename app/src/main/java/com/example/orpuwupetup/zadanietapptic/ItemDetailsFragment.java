package com.example.orpuwupetup.zadanietapptic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemDetailsFragment extends Fragment {

    private ImageView itemImage;
    private TextView itemText;
    private String itemName;
    private String itemImageUrl;

    // Mandatory empty constructor for initiating the fragment
    public ItemDetailsFragment(){}

    // inflating the fragment layout, and finding views
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate item details fragment layout
        View rootView = inflater.inflate(R.layout.fragment_item_details, container, false);

        // get references for both Views in the fragment layout
        itemImage = (ImageView) rootView.findViewById(R.id.detail_image);
        itemText = (TextView) rootView.findViewById(R.id.detail_text);

        itemText.setText(itemName);

        // return View
        return rootView;

    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemImageUrl (String url){
        this.itemImageUrl = url;
    }
}
