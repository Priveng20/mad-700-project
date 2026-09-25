package project.app.smartpantry.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import project.app.smartpantry.R;



// the list of everything in the pantry
public class PantryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);
    }
}
