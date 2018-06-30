package com.example.orpuwupetup.zadanietapptic.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orpuwupetup.zadanietapptic.R;

import java.util.List;

/**
 * Created by cezar on 28.06.2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    final private ListItemClickListener onClickListener;

    private static int viewHolderCount;

    private List<Item> itemList;

    private int numberOfItems;

    public ItemAdapter(int numberOfItems, ListItemClickListener listener, List<Item> itemList){
        this.itemList = itemList;
        this.onClickListener = listener;
        this.numberOfItems = numberOfItems;
        viewHolderCount = 0;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.listItemText.setText(itemList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return this.numberOfItems;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listItemImage;
        TextView listItemText;

        public ItemViewHolder (View itemView){
            super(itemView);

            listItemImage = itemView.findViewById(R.id.list_item_image);
            listItemText = itemView.findViewById(R.id.list_item_text);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
