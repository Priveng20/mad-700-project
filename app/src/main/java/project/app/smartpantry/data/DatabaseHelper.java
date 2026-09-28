package project.app.smartpantry.data;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import project.app.smartpantry.model.PantryItem;
import project.app.smartpantry.model.Recipe;
import project.app.smartpantry.model.RecipeIngredient;





// creates and manages the smartpantry.db sqlite database
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smartpantry.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PANTRY_ITEM = "pantry_item";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";
    private static final String TABLE_RECIPE = "recipe";
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

        // onCreate only runs when the database file is first made, so this seeds exactly once
        seedRecipes(db);
    }

    // inserts every recipe from SeedData with its ingredients, all at once
    private void seedRecipes(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            for (SeedData.SeedRecipe seed : SeedData.getRecipes()) {
                ContentValues recipeValues = new ContentValues();
                recipeValues.put(COLUMN_RECIPE_NAME, seed.getName());
                recipeValues.put(COLUMN_RECIPE_STEPS, seed.getSteps());



                long recipeId = db.insertOrThrow(TABLE_RECIPE, null, recipeValues);

                for (RecipeIngredient ingredient : seed.getIngredients()) {
                    ContentValues ingredientValues = new ContentValues();
                    ingredientValues.put(COLUMN_RI_RECIPE_ID, recipeId);
                    ingredientValues.put(COLUMN_RI_INGREDIENT_NAME, ingredient.getIngredientName());
                    ingredientValues.put(COLUMN_RI_QUANTITY, ingredient.getQuantity());
                    ingredientValues.put(COLUMN_RI_UNIT, ingredient.getUnit());
                    db.insertOrThrow(TABLE_RECIPE_INGREDIENT, null, ingredientValues);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEM);
        onCreate(db);
    }
    // add one pantry item and returns its new id or -1 if the insert failed
    public long insertPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();



        ContentValues values = pantryItemValues(item);


        return db.insert(TABLE_PANTRY_ITEM, null, values);
    }

    // reads every pantry row into a list
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY_ITEM, null, null, null, null, null, COLUMN_NAME + " ASC");

        // finally makes sure the cursor is closed even if reading a row fails
        try {
            while (cursor.moveToNext()) {
                items.add(readPantryItem(cursor));
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    // finds one pantry item by id, or returns null if  no row with that id
    public PantryItem getPantryItem(int id) {
        SQLiteDatabase db = getReadableDatabase();




        Cursor cursor = db.query(TABLE_PANTRY_ITEM, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        PantryItem item = null;
        try {
            if (cursor.moveToFirst()) {
                item = readPantryItem(cursor);
            }
        } finally {
            cursor.close();
        }

        return item;
    }

//saves new values over the row with the item's id, returns how many rows changed
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = pantryItemValues(item);

        return db.update(TABLE_PANTRY_ITEM, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    // removes the row with this id, returns how many rows were deleted (1 or 0)
    public int deletePantryItem(int id) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE_PANTRY_ITEM, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // reads every recipe, name and steps only, ingredients are fetched separately
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE, null, null, null, null, null, COLUMN_RECIPE_NAME + " ASC");

        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
                String steps = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_STEPS));
                recipes.add(new Recipe(id, name, steps));
            }
        } finally {
            cursor.close();
        }

        return recipes;
    }




    // reads the ingredients of one recipe, in the order each were added
    public List<RecipeIngredient> getIngredientsForRecipe(int recipeId) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE_INGREDIENT, null, COLUMN_RI_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)}, null, null, COLUMN_RI_ID + " ASC");

        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RI_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RI_INGREDIENT_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RI_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RI_UNIT));
                ingredients.add(new RecipeIngredient(id, recipeId, name, quantity, unit));
            }
        } finally {
            cursor.close();
        }

        return ingredients;
    }




    // puts an item's fields into ContentValues, shared by insert and update
    private ContentValues pantryItemValues(PantryItem item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_UNIT, item.getUnit());
        values.put(COLUMN_EXPIRY_DATE, item.getExpiryDate());

        return values;
    }





    // turns row the cursor is on into a PantryItem
    private PantryItem readPantryItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
        String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT));
        String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE));

        return new PantryItem(id, name, quantity, unit, expiryDate);
    }

}
