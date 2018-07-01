package com.example.orpuwupetup.zadanietapptic.data;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orpuwupetup.zadanietapptic.R;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

// Custom adapter for the RecyclerView
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    final private ListItemClickListener onClickListener;
    private List<Item> itemList;
    private int numberOfItems;
    private ColorStateList textColor;
    public static int selectedItem = 0;
    Context context;

    /*
    constructor taking List of items to display, custom clickListener to provide responsiveness
    to clicks, numberOfItems variable needed to construct RecyclerView and ColorStateList for
    changing color of the text according to what state it is in
    */
    public ItemAdapter(int numberOfItems, ListItemClickListener listener, List<Item> itemList, ColorStateList textColor, Context context) {
        this.itemList = itemList;
        this.onClickListener = listener;
        this.numberOfItems = numberOfItems;
        this.textColor = textColor;
        this.context = context;
    }

    /*
    mandatory method included in RecyclerView, in which we are creating ViewHolders for quicker
    access to Views (and improved memory usage)
    */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*
        storing some things in the variables, to make it easier to understand what we are putting
        in the view inflater
        */
        Context context = parent.getContext();
        int layoutForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflating view from correct template and context
        View view = inflater.inflate(layoutForListItem, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    /*
    another mandatory method from RecyclerView class, here we are putting in correct Views data that
    we want to be displayed in our list
    */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.listItemText.setText(itemList.get(position).getText());
        holder.listItemText.setTextColor(textColor);

        Picasso.with(context).load(itemList.get(position).getImageUrl()).fit().centerInside().into(holder.listItemImage);
        if(position == selectedItem) {
            holder.mainLayout.setSelected(true);
        }else{
            holder.mainLayout.setSelected(false);
        }
    }

    // last mandatory method for RecyclerView adapter
    @Override
    public int getItemCount() {
        return this.numberOfItems;
    }

    // interface made to provide click handling for our custom adapter
    public interface ListItemClickListener {


        /*
        we need just index of the clicked item, so we create one method, that took it as a parameter
        */
        void onListItemClick(int clickedItemIndex);
    }

    /*
    inner ViewHolder class, single instance of it corresponds to single item on the list, it is
    holding references to all the Views used in this item
    */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout mainLayout;
        ImageView listItemImage;
        TextView listItemText;

        public ItemViewHolder(View itemView) {
            super(itemView);

            /*
            boilerplate code for finding views (I did not use ButterKnife library or even better
            in my opinion DataBinding, because it wasn't listed in libraries that I can use in this
            exercise ¯\_(ツ)_/¯ )
            */
            listItemImage = itemView.findViewById(R.id.list_item_image);
            listItemText = itemView.findViewById(R.id.list_item_text);
            mainLayout = itemView.findViewById(R.id.constraint_layout_list_item);


            itemView.setOnClickListener(this);
        }

        /*
        in OnClickListener we are finding adapter position of clicked View, and "throwing" it
        to the upper layer of the app to handle it somewhere else
        */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);

        }
    }
}
