# SmartPantryManager

A native Android application that helps users keep track of the food they have at home and suggests recipes that can be cooked **right now** using only the ingredients currently in their pantry.

Built as the practical assignment for **Mobile App Development 700**.

---

## 📱 App Overview

SmartPantryManager solves two everyday problems:

1. **Food waste** — ingredients expire in the cupboard because users forget what they have.
2. **Decision fatigue** — "What can I cook tonight?" is hard when you can't see your full inventory.

The app keeps a persistent pantry list, and applies a **strict-match algorithm** to suggest only recipes for which the user has *every* required ingredient. This ensures suggestions are always actionable — no "almost there" recipes cluttering the list.

### Target user
Home cooks, students, and families who want to make the most of the food they already have.

---

## ✨ Features

- **Pantry List** — view, edit, and delete ingredients (full CRUD).
- **Add/Edit Ingredient** — form with name, quantity, unit, and expiry date, including input validation.
- **Suggested Recipes** — strict-match algorithm shows only recipes the user can make right now.
- **Recipe Detail** — displays the full ingredient list and preparation steps.
- **All Recipes** — browse the complete recipe library (bypasses the strict match; useful for discovery).
- **Settings** — clear the pantry, reseed the recipe database, view all recipes, and see app info.
- **Pre-seeded Recipe Database** — 15 recipes including South African favourites (Chakalaka, Kota, Samp and Beans, Pap and Stew, Amagwinya, Milk Tart) plus international dishes (Spaghetti Carbonara, Lasagna, Chickpea Curry, and more).

---

## 🗄️ Database Choice — Room Persistence Library

The app uses **Room**, Google's official ORM (Object Relational Mapper) wrapper around SQLite.

### Why Room?

| Reason | Explanation |
|---|---|
| **Recommended by the study guide** | Chapter 3 explicitly recommends Room for persistent data. |
| **Compile-time verification** | Room validates all SQL queries at compile time, catching typos before runtime. |
| **Less boilerplate** | `@Entity`, `@Dao`, and `@Database` annotations replace the hundreds of lines of `SQLiteOpenHelper` code you would otherwise write by hand. |
| **Automatic object mapping** | Java objects map directly to database rows without manual `Cursor` handling. |
| **Migration support** | Schema changes are handled cleanly via `Migration` objects if the app evolves. |
| **Official Android support** | Actively maintained by Google and integrated with the Android Studio build system. |

### Database Structure

Three entities, managed by `AppDatabase`:

| Entity | Purpose | Key Fields |
|---|---|---|
| `Ingredient` | Pantry items the user owns | id (PK), name, quantity, unit, expiryDate |
| `Recipe` | Recipe names and preparation steps | id (PK), name, steps |
| `RecipeIngredient` | Join table linking a recipe to required ingredients | id (PK), recipeId (FK), ingredientName, requiredQuantity, requiredUnit |

This maps directly to the **ER diagram** in the written report.

---

## 🧠 The Strict-Match Algorithm

Core logic in `Utilities/PantryManager.java`:

> A recipe is added to the suggestion list **only if every one of its required ingredients is present in the user's pantry**.

Ingredient name comparison is normalized to handle real-world messiness:

- **Case-insensitive** — `"Tomato"` matches `"tomato"`.
- **Whitespace trimmed** — `"Carrot "` matches `"Carrot"`.
- **Singular/plural tolerance** — trailing `"s"` or `"es"` is stripped so `"Tomatoes"` matches `"Tomato"` and `"Eggs"` matches `"Egg"`.

This satisfies the assignment requirement for matching that is *"reasonably robust to simple real-world messiness"* without requiring a full NLP solution.

---

## 🚀 Setup & Run Instructions

### Prerequisites

| Tool | Version |
|---|---|
| Android Studio | Meerkat (2024.3.x) or later |
| JDK | **17** (the embedded JetBrains Runtime ships with Android Studio) |
| Android SDK Platform | **34 (Android 14)** — install via *Tools → SDK Manager* |
| Device / Emulator | Android 7.0 (API 24) or higher |

> ⚠️ **Important:** Do not use JDK 21 or 25 — the Android Gradle Plugin used here is compatible with JDK 17.

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/<your-username>/SmartPantryManager-Android.git
   ```

2. **Open in Android Studio:**
    - *File → Open* → select the `SmartPantryManager` folder.

3. **Wait for Gradle to sync.**
    - If you see a JDK error, go to *File → Project Structure → SDK Location*, then set **Gradle JDK** to **jbr-17** or the embedded JDK.

4. **Install Android SDK Platform 34** if prompted:
    - *Tools → SDK Manager → SDK Platforms* → tick **Android 14.0 (UpsideDownCake)** → Apply.

5. **Create an emulator (optional):**
    - *Tools → Device Manager → Create Device* → Pixel 7 → API 34 → Finish.

6. **Run the app:**
    - Click the green ▶ button (or press **Shift + F10**).
    - Choose your emulator or connected device.

### First Launch

On first launch the database is empty and the app automatically seeds **15 recipes** (ingredients for each are stored in the join table).

To see suggested recipes appear, add the required ingredients to your pantry via the **+** button.

**Example — to make Chakalaka appear:**

| Ingredient | Quantity | Unit |
|---|---|---|
| Carrots | 5 | units |
| Onion | 1 | unit |
| Bell Pepper | 2 | units |
| Canned Beans | 400 | g |
| Curry Powder | 2 | tbsp |

Tap **Suggest** — Chakalaka will now appear. Tap it to view the full recipe.

### To browse all recipes (without needing the pantry):

- Tap **Settings** → **View All Recipes**.

---

## 📁 Project Structure

```
app/src/main/java/com/example/smartpantrymanager/
├── MainActivity.java                 (Pantry List)
├── AddEditIngredientActivity.java    (Add / Edit form)
├── SuggestedRecipesActivity.java     (Strict-match results)
├── RecipeDetailActivity.java         (Full recipe view)
├── SettingsActivity.java             (App settings)
├── AllRecipesActivity.java           (Full recipe library)
├── Data/
│   ├── Ingredient.java               (Entity)
│   ├── Recipe.java                   (Entity)
│   ├── RecipeIngredient.java         (Entity / join table)
│   ├── AppDao.java                   (Data Access Object)
│   └── AppDatabase.java              (Room database instance)
├── Adapters/
│   ├── PantryAdapter.java
│   └── RecipeAdapter.java
└── Utilities/
    └── PantryManager.java            (Strict-match algorithm)

app/src/main/res/
├── layout/                           (activity + item layouts)
└── menu/                             (bottom_nav_menu.xml)
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| UI | XML layouts, Material Components, RecyclerView, BottomNavigationView |
| Database | Room (SQLite) |
| Architecture | Activity-based with a manager class for business logic |
| Build System | Gradle (Kotlin DSL) |
| Min SDK | 24 (Android 7.0 Nougat) |
| Target / Compile SDK | 34 (Android 14) |

---

## 📝 Written Report Summary

A full PDF/Word report accompanies this repository. It covers:

1. **Cover page** — app name, student details, module, date.
2. **Table of contents**.
3. **Introduction** — the problem the app solves and its target audience.
4. **System design** — screen flow diagram and ER diagram.
5. **Screenshots** — every screen and core function (add, edit, delete, list view, validation, suggestions, recipe detail, settings, all recipes).
6. **Key code snippets** — 3–5 snippets including the strict-match algorithm and Room DAO.
7. **Challenges and solutions** — real problems encountered during development.
8. **Conclusion and reflection** — lessons learned and future improvements.
9. **Reference list** — Android Developer documentation, Room guide, Material Design.

---

## 🔑 Key Code Highlights

### 1. Room DAO — Data Access Object
File: `Data/AppDao.java`

```java
@Dao
public interface AppDao {
    @Insert long insertRecipe(Recipe recipe);   // returns auto-generated row ID
    @Query("SELECT * FROM recipes") List<Recipe> getAllRecipes();
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);
}
```

**Why:** Room requires DAOs to be interfaces or abstract classes. Returning `long` from `@Insert` gives us the auto-generated primary key, which is then used to attach ingredients to the correct recipe.

### 2. Strict-Match Algorithm
File: `Utilities/PantryManager.java`

```java
for (Recipe recipe : allRecipes) {
    List<RecipeIngredient> required = db.appDao().getIngredientsForRecipe(recipe.id);
    boolean canMake = true;
    for (RecipeIngredient r : required) {
        if (!pantryNames.contains(normalize(r.ingredientName))) {
            canMake = false;
            break;
        }
    }
    if (canMake) matchingRecipes.add(recipe);
}
```

**Why:** Enforces the assignment's requirement that only fully-cookable recipes are suggested. The early `break` keeps it efficient.

### 3. Database Seeding
File: `MainActivity.java`

```java
if (db.appDao().getAllRecipes().isEmpty()) {
    prePopulateDatabase();
}
```

**Why:** Runs only on first launch, keeping the app fast on subsequent runs.

---

## 🧗 Challenges and Solutions

| Challenge | Solution |
|---|---|
| Gradle sync failed with `Unknown host 'dl.google.com'` | Switched to a mobile hotspot to complete the initial dependency download. |
| `Project JDK is not defined` — JDK 25 refused to change | Set `org.gradle.java.home` in `gradle.properties` to point at Android Studio's embedded JBR 17. |
| `Unresolved reference: implementation` in `build.gradle.kts` | Converted Groovy-style syntax (`implementation '...'`) to Kotlin DSL syntax (`implementation("...")`). |
| Room returned recipe ID 0 in the join table | Changed `void insertRecipe(...)` to `long insertRecipe(...)` so the auto-generated ID is captured before adding ingredients. |
| Emulator rendered a black screen after launch | Enabled software rendering (GLES 2.0) in the emulator's Advanced Settings. |

---

## 🔮 Future Improvements

Given more time, the following could be added:

- An **"Almost There"** list showing recipes missing only 1 ingredient (bonus credit per the brief).
- **Unit conversion** (e.g., grams ↔ kilograms) for smarter matching.
- **Expiry-date notifications** to alert users before food goes off.
- Replace `allowMainThreadQueries()` with **background threading** using `ExecutorService` or Kotlin coroutines.
- **Search bar** on the Pantry screen.
- **Recipe images** and preparation-time estimates.

---

## 📚 References

- Android Developers (2024). *Save data in a local database using Room*. Available at: https://developer.android.com/training/data-storage/room
- Android Developers (2024). *Create dynamic lists with RecyclerView*. Available at: https://developer.android.com/develop/ui/views/layout/recyclerview
- Material Design (2024). *Navigation bar*. Available at: https://m3.material.io/components/navigation-bar
- Gradle (2024). *Gradle User Manual*. Available at: https://docs.gradle.org/current/userguide/userguide.html
- Stack Overflow (2024). *Community answers on JDK and Gradle configuration issues*.

---

## 👤 Author

**Thato Mashabela** — Mobile App Development 700