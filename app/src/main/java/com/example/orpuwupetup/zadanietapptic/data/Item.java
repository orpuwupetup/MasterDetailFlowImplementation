package com.example.orpuwupetup.zadanietapptic.data;

import com.example.orpuwupetup.zadanietapptic.R;

/**
 * Created by cezar on 28.06.2018.
 */

// custom class for storing data loaded by the AsyncLoaders from the web
public class Item {

    private String text;
    private String imageUrl;

    public Item(String text, String imageUrl){
        this.text = text;
        this.imageUrl = imageUrl;
    }

    // getter methods for the local variables
    public String getText(){return this.text;}
    public String getImageUrl(){return this.imageUrl;}
}
