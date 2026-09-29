package project.app.smartpantry.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.android.material.switchmaterial.SwitchMaterial;
import project.app.smartpantry.R;






public class SettingsActivity extends BaseMenuActivity {

    private static final String PREFS_NAME = "smartpantry_settings";
    private static final String KEY_HIGHLIGHT_EXPIRING = "highlight_expiring";
    private static final String KEY_DEFAULT_UNIT = "default_unit";

    // used when nothing has been saved yet
    private static final boolean DEFAULT_HIGHLIGHT_EXPIRING = true;
    private static final String DEFAULT_UNIT = "g";

    // other screens read the settings through these two, so the keys only live in this class
    public static boolean isExpiryHighlightOn(Context context) {
        return prefs(context).getBoolean(KEY_HIGHLIGHT_EXPIRING, DEFAULT_HIGHLIGHT_EXPIRING);
    }

    public static String getDefaultUnit(Context context) {
        return prefs(context).getString(KEY_DEFAULT_UNIT, DEFAULT_UNIT);
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpExpirySwitch();
        setUpDefaultUnitSpinner();
    }




    // shows the saved value, then saves again every time it's flipped
    private void setUpExpirySwitch() {
        SwitchMaterial expirySwitch = findViewById(R.id.expiry_switch);
        expirySwitch.setChecked(isExpiryHighlightOn(this));
        expirySwitch.setOnCheckedChangeListener((button, isChecked) ->
                prefs(this).edit().putBoolean(KEY_HIGHLIGHT_EXPIRING, isChecked).apply());
    }




    private void setUpDefaultUnitSpinner() {
        Spinner unitSpinner = findViewById(R.id.default_unit_spinner);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);

        unitSpinner.setSelection(unitAdapter.getPosition(getDefaultUnit(this)));


        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String unit = parent.getItemAtPosition(position).toString();
                prefs(SettingsActivity.this).edit().putString(KEY_DEFAULT_UNIT, unit).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // a spinner with a fixed list always has something selected
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
