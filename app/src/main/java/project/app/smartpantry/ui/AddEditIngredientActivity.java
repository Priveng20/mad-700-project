package project.app.smartpantry.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;
import java.util.Locale;
import project.app.smartpantry.R;
import project.app.smartpantry.data.DatabaseHelper;
import project.app.smartpantry.model.PantryItem;

// form for adding a new pantry item or editing an existing one
public class AddEditIngredientActivity extends AppCompatActivity {

    private TextInputLayout nameLayout;
    private TextInputLayout quantityLayout;
    private TextInputLayout expiryLayout;
    private EditText nameInput;
    private EditText quantityInput;

    private Spinner unitSpinner;
    private EditText expiryInput;
    private DatabaseHelper dbHelper;

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

        nameLayout = findViewById(R.id.name_layout);
        quantityLayout = findViewById(R.id.quantity_layout);
        expiryLayout = findViewById(R.id.expiry_layout);
        nameInput = findViewById(R.id.name_input);
        quantityInput = findViewById(R.id.quantity_input);

        dbHelper = new DatabaseHelper(this);

        findViewById(R.id.save_button).setOnClickListener(v -> {
            if (validateInput()) {
                saveItem();
            }
        });
    }

    // builds a PantryItem from the form, inserts it, and goes back to the list
    // only called after validateInput() passed, so quantity is a real number
    private void saveItem() {
        String name = nameInput.getText().toString().trim();
        double quantity = Double.parseDouble(quantityInput.getText().toString().trim());
        String unit = unitSpinner.getSelectedItem().toString();

        //an empty box means no expiry date
        String expiry = expiryInput.getText().toString();
        if (expiry.isEmpty()) {
            expiry = null;
        }

        long newId = dbHelper.insertPantryItem(new PantryItem(name, quantity, unit, expiry));
        if (newId == -1) {
            // stay on the form so nothing the user typed is lost
            Toast.makeText(this, R.string.error_save_failed, Toast.LENGTH_LONG).show();
            return;
        }
    // the list reloads itself in onResume, so it will show the new item
        finish();
    }



    // checks every field and shows an error under each one that's wrong
    // returns true only if everything is valid
    private boolean validateInput() {
        boolean valid = true;
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            nameLayout.setError(getString(R.string.error_name_required));
            valid = false;
        } else {

            nameLayout.setError(null);

        }

        String quantityText = quantityInput.getText().toString().trim();
        if (quantityText.isEmpty()) {
            quantityLayout.setError(getString(R.string.error_quantity_required));
            valid = false;
        } else {
            try {
                double quantity = Double.parseDouble(quantityText);
                if (quantity <= 0) {
                    quantityLayout.setError(getString(R.string.error_quantity_positive));
                    valid = false;
                } else {
                    quantityLayout.setError(null);
                }
            } catch (NumberFormatException e) {
                quantityLayout.setError(getString(R.string.error_quantity_invalid));
                valid = false;
            }
        }

        // expiry is optional, but must be yyyy-MM-dd
        String expiry = expiryInput.getText().toString();
        if (!expiry.isEmpty() && !expiry.matches("\\d{4}-\\d{2}-\\d{2}")) {
            expiryLayout.setError(getString(R.string.error_expiry_invalid));
            valid = false;
        } else {
            expiryLayout.setError(null);
        }

        return valid;
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




    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
