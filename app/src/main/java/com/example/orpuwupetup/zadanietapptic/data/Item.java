package com.example.orpuwupetup.zadanietapptic.data;

import com.example.orpuwupetup.zadanietapptic.R;

/**
 * Created by cezar on 28.06.2018.
 */

public class Item {

    private String name;
    private int imageID;

    public Item(String name, int imageID){
        this.name = name;

        //TODO hardcoded image resource id
        this.imageID = R.mipmap.ic_launcher;
    }

    // setter methods for the local variables
    public void setName(String name){this.name = name;}
    public void setImageID(int imageID){this.imageID = imageID;}

    // getter methods for the local variables
    public String getName(){return this.name;}
    public int getImageID(){return this.imageID;}
}
