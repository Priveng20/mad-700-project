package project.app.smartpantry.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;
import project.app.smartpantry.R;

// form for adding a new pantry item or editing an existing one
public class AddEditIngredientActivity extends AppCompatActivity {

    private Spinner unitSpinner;
    private EditText expiryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        setTitle(R.string.add_ingredient);
        // show back arrow in the app bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpUnitSpinner();


        // hitting the expiery field opens the calendar picker
        expiryInput = findViewById(R.id.expiry_input);
        expiryInput.setOnClickListener(v -> showDatePicker());
    }




    private void setUpUnitSpinner() {
        unitSpinner = findViewById(R.id.unit_spinner);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);
    }

    // opens a calendar on the date already in the box
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String current = expiryInput.getText().toString();
        if (!current.isEmpty()) {

            // the box only ever holds yyyy-MM-dd
            String[] parts = current.split("-");
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]) - 1;
            day = Integer.parseInt(parts[2]);
        }

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, pickedYear, pickedMonth, pickedDay) ->
                        expiryInput.setText(formatDate(pickedYear, pickedMonth, pickedDay)),
                year, month, day);

        // third button so the date can be removed again
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.clear),
                (d, which) -> expiryInput.setText(""));
        dialog.show();
    }

    // months from the picker start at 0 (jan is 0) so add 1 for the saved date
    private String formatDate(int year, int month, int day) {
        return String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day);
    }

    //close screen and return to the list
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
