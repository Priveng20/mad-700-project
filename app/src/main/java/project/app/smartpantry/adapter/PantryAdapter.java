package project.app.smartpantry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import project.app.smartpantry.R;
import project.app.smartpantry.model.PantryItem;


// connects a list of PantryItems to the recyclerView
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    // list opens the edit form
    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    // list asks for confirmation before deleting
    public interface OnDeleteClickListener {
        void onDeleteClick(PantryItem item);
    }

    private List<PantryItem> items;
    private final OnItemClickListener clickListener;
    private final OnDeleteClickListener deleteListener;


    public PantryAdapter(List<PantryItem> items, OnItemClickListener clickListener,
                         OnDeleteClickListener deleteListener) {
        this.items = items;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }



    // swaps in a fresh list and redraws every row
    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }




// called when the list needs a new row view
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    // called to fill a row view with the item at this position
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = items.get(position);
        Context context = holder.itemView.getContext();


        holder.name.setText(item.getName());
        holder.quantity.setText(context.getString(R.string.item_quantity,
                item.getQuantityText(), item.getUnit()));


        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.expiry.setText(R.string.item_no_expiry);
        } else {
            holder.expiry.setText(context.getString(R.string.item_expiry, item.getExpiryDate()));
        }

        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(item));
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDeleteClick(item));
    }



    @Override
    public int getItemCount() {
        return items.size();
    }



    // holds the text views of one row so its looked up once
    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView expiry;
        ImageButton deleteButton;



        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            expiry = itemView.findViewById(R.id.item_expiry);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
