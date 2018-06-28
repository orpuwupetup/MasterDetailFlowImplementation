package com.example.orpuwupetup.zadanietapptic.data;

import com.example.orpuwupetup.zadanietapptic.R;

/**
 * Created by cezar on 28.06.2018.
 */

public class Item {

    private String name;
    private String imageUrl;

    public Item(String name, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // getter methods for the local variables
    public String getName(){return this.name;}
    public String getImageUrl(){return this.imageUrl;}
}
