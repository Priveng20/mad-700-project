package project.app.smartpantry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import project.app.smartpantry.R;
import project.app.smartpantry.model.PantryItem;


// connects a list of PantryItems to the recyclerView
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> items;


    public PantryAdapter(List<PantryItem> items) {
        this.items = items;
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
                formatQuantity(item.getQuantity()), item.getUnit()));


        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.expiry.setText(R.string.item_no_expiry);
        } else {
            holder.expiry.setText(context.getString(R.string.item_expiry, item.getExpiryDate()));
        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    // show 3.0 as 3 but keeps 1.5 as 1.5
    private String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((long) quantity);
        }
        return String.valueOf(quantity);
    }



    // holds the three text views of one row so they are only looked up once
    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView expiry;



        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            expiry = itemView.findViewById(R.id.item_expiry);
        }
    }
}
