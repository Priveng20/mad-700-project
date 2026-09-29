package project.app.smartpantry.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import project.app.smartpantry.R;





public abstract class BaseMenuActivity extends AppCompatActivity {

    // puts main_menu.xml into the app bar, minus the screen already on
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int[] itemIds = {R.id.action_pantry, R.id.action_suggestions, R.id.action_settings};
        for (int itemId : itemIds) {
            if (screenFor(itemId) == getClass()) {
                menu.findItem(itemId).setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class<?> screen = screenFor(item.getItemId());
        if (screen != null) {
            Intent intent = new Intent(this, screen);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private Class<?> screenFor(int itemId) {
        if (itemId == R.id.action_pantry) {
            return PantryListActivity.class;
        }
        if (itemId == R.id.action_suggestions) {
            return SuggestedRecipesActivity.class;
        }
        if (itemId == R.id.action_settings) {
            return SettingsActivity.class;
        }
        return null;
    }
}
