package project.app.smartpantry.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        adapter = new PantryAdapter(new ArrayList<>());
        pantryList.setAdapter(adapter);
    }

    // runs every time this screen comes back to the front
    // so the list always matches what is in the db
    @Override
    protected void onResume() {
        super.onResume();
        List<PantryItem> items = dbHelper.getAllPantryItems();
        adapter.setItems(items);

        // show the "pantry is empty" message only when there is nothing to list
        if (items.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
