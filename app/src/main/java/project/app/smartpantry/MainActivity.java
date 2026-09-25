package project.app.smartpantry;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import project.app.smartpantry.data.DatabaseHelper;



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new DatabaseHelper(this).getWritableDatabase();
    }
}
