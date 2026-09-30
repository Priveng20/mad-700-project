# Smart Pantry Manager

An Android app that tracks the ingredients you have at home and suggests recipes you can cook right now.
A recipe is only suggested if your pantry strictly has every ingredient it needs, in enough quantity so no partial matches, no shopping trip required.

App built in Java




## Features

- **Pantry management:** add, edit and delete ingredients (name, quantity, unit and an optional expiry date), with input validation on the form.
- **Pantry list:** every ingredient in a `RecyclerView` with a custom adapter, loaded from the database, with an empty-state message when there is nothing in it.
- **Recipe collection:** 20 recipes are stored in the database on first run, each with a name, its ingredients and simple steps.
- **Suggested recipes:** lists only the recipes the pantry can make right now, using the strict-matching rule below. If nothing matches, the screen says so instead of staying blank.
- **Recipe detail:** the full ingredient list and numbered method for a recipe.
- **Settings:** highlight ingredients that expire within 3 days (or have expired), and choose the default unit for new ingredients.
- **Navigation:** a toolbar menu (Pantry, Suggestions, Settings) on every screen, and Intents that pass `item_id` and `recipe_id` between screens.

## The strict matching rule

A recipe is suggested only if **every** ingredient it needs is in the pantry **in at least the required quantity**. If a recipe needs 5 ingredients and the pantry has 4 of them, it is not suggested. There are no partial matches in the list.

The rule lives in one class, `logic/RecipeMatcher`. To handle real world messiness, it:

1. **Normalises names:** trims, lowercases and turns plurals into singulars, so "Tomatoes", "tomato" and " TOMATOES " all match. A short exception list protects words like "hummus" and "couscous".
2. **Converts units to a base unit per family:** weight to grams (kg × 1000), volume to millilitres (l × 1000, cup × 250, tbsp × 15, tsp × 5) and count to pieces. 1 kg of flour therefore covers a recipe needing 500 g.
3. **Never compares different families:** 500 g of onion does not count as 2 onions. Unknown units are treated as not available rather than guessed.
4. **Adds up duplicate pantry rows:** two rows of 100 g and 150 g of flour count as 250 g.

It is covered by 52 JUnit tests in `app/src/test/`

## Database choice and why

The app uses **SQLite through `SQLiteOpenHelper`**, stored locally on the device.

- **It persists:** the pantry is still there after the app is closed and reopened, which the brief requires.
- **The data is relational:** one recipe has many ingredients, which fits two tables linked by a foreign key (`recipe_ingredient.recipe_id` → `recipe.id`, with `ON DELETE CASCADE`).
- **It is built into Android:** no server, no internet connection and no extra libraries are needed and it is the approach covered in the module's persistent data chapter.
- **Seeding is simple:** `onCreate` runs only when the database is first created, so the 20 recipes are inserted exactly once, inside a single transaction.

Tables: `pantry_item` (id, name, quantity, unit, expiry_date), `recipe` (id, name, steps) and `recipe_ingredient` (id, recipe_id, ingredient_name, quantity, unit). The pantry has full CRUD. Recipes are read-only.

The two settings are small on/off and choice values, not records, so they are stored in `SharedPreferences` instead of the database.

## Setup and run

**You need:** a recent version of Android Studio. The project pins the Gradle JDK to **JDK 21**. If you don't have it, Gradle downloads it automatically the first time.

1. Clone the repository:
   ```bash
   git clone https://github.com/Priveng20/mad-700-project.git
   ```
2. In Android Studio, choose **File → Open** and select the cloned `mad-700-project` folder.
3. Wait for the Gradle sync to finish. The project uses Gradle 8.13 and Android Gradle Plugin 8.13.2.
4. Create or start an emulator in **Device Manager**
	1. Tesing was done with API level 36.1
5. Press **Run**.

On first launch the pantry is empty and the 20 recipes are seeded. Add a few ingredients with the **+** button, then open **Suggestions**.

**To run the unit tests:** in the Project view, right-click `app/src/test/java` and choose **Run 'Tests in 'java''**.

**EG test data to add:** add Bread 2 pcs, Butter 10 g and Jam 20 g. 
Suggestions shows *Toast with butter and jam*. Delete the jam and it disappears.

## Project structure

```
app/src/main/java/project/app/smartpantry/
├── model/
├── data/
├── logic/
├── adapter/
└── ui/
```
