package project.app.smartpantry.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


import project.app.smartpantry.R;
import project.app.smartpantry.adapter.PantryAdapter;
import project.app.smartpantry.data.DatabaseHelper;



// the list of everything in the pantry
public class PantryListActivity extends AppCompatActivity {


    private DatabaseHelper dbHelper;
    private PantryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

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
        adapter.setItems(dbHelper.getAllPantryItems());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
