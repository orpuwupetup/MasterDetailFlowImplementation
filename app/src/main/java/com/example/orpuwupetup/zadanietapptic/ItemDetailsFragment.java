package com.example.orpuwupetup.zadanietapptic;

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

        // return View
        return rootView;


        //TODO: make setter methods for the TextView text and ImageView image, save those to local variables, then display them from those variables in correct places
    }
}
