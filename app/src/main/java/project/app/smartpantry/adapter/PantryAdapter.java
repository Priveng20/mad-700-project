package project.app.smartpantry.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.List;
import project.app.smartpantry.R;
import project.app.smartpantry.logic.ExpiryChecker;
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
    // comes from the settings screen's "highlight items expiring soon" switch
    private boolean highlightExpiring = false;


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

    // call before setItems so the redraw uses the current setting
    public void setHighlightExpiring(boolean highlightExpiring) {
        this.highlightExpiring = highlightExpiring;
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


        // rows get reused while scrolling
        holder.expiry.setTextColor(holder.normalExpiryColor);

        String expiry = item.getExpiryDate();
        Calendar today = Calendar.getInstance();
        if (expiry == null || expiry.isEmpty()) {
            holder.expiry.setText(R.string.item_no_expiry);
        } else if (highlightExpiring && ExpiryChecker.isExpired(expiry, today)) {
            holder.expiry.setText(context.getString(R.string.item_expired, expiry));
            holder.expiry.setTextColor(ContextCompat.getColor(context, R.color.warning));
        } else if (highlightExpiring && ExpiryChecker.isExpiringSoon(expiry, today)) {
            holder.expiry.setText(context.getString(R.string.item_expiring_soon, expiry));
            holder.expiry.setTextColor(ContextCompat.getColor(context, R.color.warning));
        } else {
            holder.expiry.setText(context.getString(R.string.item_expiry, expiry));
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
        // the expiry line's grey from the layout
        ColorStateList normalExpiryColor;



        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            expiry = itemView.findViewById(R.id.item_expiry);
            deleteButton = itemView.findViewById(R.id.delete_button);
            normalExpiryColor = expiry.getTextColors();
        }
    }
}
