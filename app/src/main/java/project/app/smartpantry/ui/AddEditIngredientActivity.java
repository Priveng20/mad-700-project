package project.app.smartpantry.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import project.app.smartpantry.R;

// form for adding a new pantry item or editing an existing one
public class AddEditIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
    }
}
