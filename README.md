# Smart Pantry Manager

A Java Android application that helps reduce food waste by tracking the ingredients a user actually has at home and suggesting recipes that can be made using **strictly** those ingredients no shopping trip required.

## Problem it solves

Many people end up throwing away food because they forget what they have, or don't know what to cook with what's left over. Smart Pantry Manager solves this by letting users log their current pantry items and only surfacing recipes they can make *right now*, with everything already on hand.

## Core features

- **Pantry management** — add, edit, and delete pantry items (name, quantity, unit, optional expiry date)
- **Pantry list screen** — displays all current ingredients via a RecyclerView bound to a local database
- **Recipe collection** — a seeded set of 15–20 recipes, each with required ingredients and preparation steps
- **Suggested Recipes screen** — strict-matching logic that only shows recipes where every required ingredient is present in sufficient quantity
- **Recipe detail screen** — full ingredient list and method for a selected recipe
- **Settings screen** — user preferences
- Clear empty-state feedback when no recipes match the current pantry

## Database choice: SQLite

This project uses **SQLite**, implemented via `SQLiteOpenHelper`, for local on-device persistence.

**Why SQLite:**
- The app's use case (a personal pantry) doesn't need cloud sync or multi-device access, so a local database is sufficient and simpler
- No server setup, API keys, or ongoing hosting costs
- Full control over SQL queries, which made the strict-matching logic straightforward to implement and reason about
- Directly aligned with the persistence approach covered in the Mobile App Development 700 module

Data persists across app restarts, and full CRUD (Create, Read, Update, Delete) is implemented for pantry items.

## Tech stack

- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite (SQLiteOpenHelper)
- **UI:** XML layouts, RecyclerView with custom Adapter, ConstraintLayout/LinearLayout/RelativeLayout

## Setup & run instructions

1. Clone this repository:
2. 2. Open the project folder in **Android Studio**.
3. Wait for Gradle to sync (Android Studio will prompt automatically).
4. Create or start an Android Virtual Device via **Device Manager** (e.g. Pixel 6, API 34), or connect a physical Android device with USB debugging enabled.
5. Click the green **Run** button (or Shift+F10) to build and launch the app.

## Project structure

- `PantryItem.java` — data model for a pantry item
- `DatabaseHelper.java` — SQLite database helper with CRUD methods
- `PantryAdapter.java` — RecyclerView adapter for displaying pantry items
- `MainActivity.java` — pantry list screen
- `AddEditIngredientActivity.java` — add/edit form with input validation
