package project.app.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


import project.app.smartpantry.R;
import project.app.smartpantry.adapter.PantryAdapter;
import project.app.smartpantry.data.DatabaseHelper;
import project.app.smartpantry.model.PantryItem;



// the list of everything in the pantry
public class PantryListActivity extends AppCompatActivity {


    private DatabaseHelper dbHelper;
    private PantryAdapter adapter;
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        emptyText = findViewById(R.id.empty_text);

        RecyclerView pantryList = findViewById(R.id.pantry_list);
        pantryList.setLayoutManager(new LinearLayoutManager(this));
        pantryList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        dbHelper = new DatabaseHelper(this);

        // starts empty onResume fills it from the database
        // tapping a row opens the same form in edit mode
        adapter = new PantryAdapter(new ArrayList<>(), item -> {
            Intent intent = new Intent(this, AddEditIngredientActivity.class);
            intent.putExtra(AddEditIngredientActivity.EXTRA_ITEM_ID, item.getId());
            startActivity(intent);
        }, item -> confirmDelete(item));
        pantryList.setAdapter(adapter);
        // names the exact screen to open
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditIngredientActivity.class)));
    }

    // runs every time this screen comes back to the front
    // so the list always matches what is in the db
    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }



    // reads the pantry from the database into the list and updates the empty message
    private void loadItems() {
        List<PantryItem> items = dbHelper.getAllPantryItems();
        adapter.setItems(items);

        // show the "pantry is empty" message only when there is nothing to list
        if (items.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    // confirm before deleting
    private void confirmDelete(PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(getString(R.string.delete_message, item.getName()))
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    dbHelper.deletePantryItem(item.getId());
                    loadItems();
                })
                .show();
    }




    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
