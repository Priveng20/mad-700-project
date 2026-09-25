package project.app.smartpantry.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;





// creates and manages the smartpantry.db sqlite database
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smartpantry.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PANTRY_ITEM = "pantry_item";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date"    private static final String TABLE_RECIPE = "recipe";
    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "name";
    private static final String COLUMN_RECIPE_STEPS = "steps";
    private static final String TABLE_RECIPE_INGREDIENT = "recipe_ingredient";
    private static final String COLUMN_RI_ID = "id";
    private static final String COLUMN_RI_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RI_INGREDIENT_NAME = "ingredient_name";
    private static final String COLUMN_RI_QUANTITY = "quantity";
    private static final String COLUMN_RI_UNIT = "unit";

    private static final String CREATE_TABLE_PANTRY_ITEM =
            "CREATE TABLE " + TABLE_PANTRY_ITEM + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL, " +
                    COLUMN_EXPIRY_DATE + " TEXT)";



    private static final String CREATE_TABLE_RECIPE =
            "CREATE TABLE " + TABLE_RECIPE + " (" +
                    COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                    COLUMN_RECIPE_STEPS + " TEXT NOT NULL)";




    private static final String CREATE_TABLE_RECIPE_INGREDIENT =
            "CREATE TABLE " + TABLE_RECIPE_INGREDIENT + " (" +
                    COLUMN_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RI_RECIPE_ID + " INTEGER NOT NULL, " +
                    COLUMN_RI_INGREDIENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_RI_QUANTITY + " REAL NOT NULL, " +
                    COLUMN_RI_UNIT + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_RI_RECIPE_ID + ") REFERENCES " +
                    TABLE_RECIPE + "(" + COLUMN_RECIPE_ID + ") ON DELETE CASCADE)";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PANTRY_ITEM);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEM);
        onCreate(db);
    }

}
