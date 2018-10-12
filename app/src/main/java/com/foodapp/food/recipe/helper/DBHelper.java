package com.foodapp.food.recipe.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.foodapp.food.recipe.model.CategoryItem;
import com.foodapp.food.recipe.model.RecipeItem;
import com.foodapp.food.recipe.model.SearchResultItem;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper
{
    ImageHelper imageHelper = new ImageHelper();

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create table recipename,howto,description,pic,image
        db.execSQL("CREATE TABLE IF NOT EXISTS RECIPES( _id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT,"
                + "recipeName TEXT, blank1 TEXT, blank2 TEXT, howTo TEXT, description TEXT,"
                + "thumbnail BLOB, mainImg BLOB, blank3 INTEGER);");

        // TABLE INGREDIENTS
        db.execSQL("CREATE TABLE IF NOT EXISTS INGREDIENTS( _id INTEGER PRIMARY KEY AUTOINCREMENT, recipeID INTEGER, ingreName TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }


    public ArrayList<String> ingredients_SelectByRecipeId(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> ingredients = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT ingreName FROM INGREDIENTS WHERE recipeID = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                ingredients.add(cursor.getString(0));
            }
        }
        cursor.close();
        db.close();

        return ingredients;


    }

    //called when ingredients are searched using the floating search button
    public ArrayList<SearchResultItem> ingredients_selectRecipeByIngredientName(ArrayList<String> ingredientsName)
    {
        // database is made readable
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<SearchResultItem> idRecipes = new ArrayList<>();
        String strNames = "";

        for (int i=0; i < ingredientsName.size(); i++)
        {
            strNames += "ingreName = '" + ingredientsName.get(i) + "'";
            if (i != ingredientsName.size()-1)
            {
                strNames += " OR ";
            }
        }

        Cursor cursor = db.rawQuery("SELECT recipeID, count(*) FROM INGREDIENTS WHERE "+
                strNames + "GROUP BY recipeID ", null);

        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                idRecipes.add(new SearchResultItem(cursor.getInt(0), cursor.getInt(1)));
            }
        }
        cursor.close();
        db.close();
        return idRecipes;
    }

    //called from catergoryFragment
    public ArrayList<CategoryItem> recipes_SelectCategory()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CategoryItem> categoryList = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT category, mainImg FROM RECIPES GROUP BY category HAVING max(_id)", null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                categoryList.add(new CategoryItem(
                        cursor.getString(0),
                        cursor.getBlob(1)
                ));
            }
        }
        cursor.close();
        db.close();
        return categoryList;
    }

    //called when NO search ingredients are matched and suggestions should be given
    public ArrayList<RecipeItem> recipes_SelectBest()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY blank3 DESC LIMIT 3", null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                allRecipes.add(new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    //called from RecipeListActivity
    public ArrayList<RecipeItem> recipes_SelectByCategory(String category)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();

        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE category = '" + category + "'", null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                allRecipes.add(new RecipeItem(
                        cursor.getInt( 0 ),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    //called from RecipeActivity
    public RecipeItem recipes_SelectByName(String name)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE recipeName = '" + name + "'", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeItem recipe = new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                );
                cursor.close();
                db.close();
                return recipe;
            }


        }
        return  null;
    }

    //called from RecipeListactivity
    public RecipeItem recipes_SelectById(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE _id = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeItem recipe = new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                );
                cursor.close();
                db.close();
                return recipe;
            }
        }
        return  null;
    }

}

/*
count(*) from INGREDIENTS where - basically counts number of records in table ingredients where ---
*/