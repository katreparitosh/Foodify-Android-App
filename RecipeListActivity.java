package com.foodapp.food.recipe.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodapp.food.recipe.adapter.RecipeList_Adapter;
import com.foodapp.food.recipe.helper.DBHelper;
import com.foodapp.food.recipe.model.RecipeItem;
import com.foodapp.food.recipe.R;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity
{

    ArrayList<RecipeItem> recipes = new ArrayList<>();

    TextView txtTitle;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        recipes.clear();

        txtTitle = findViewById(R.id.txt_recipeListTitle);
        listView = findViewById(R.id.listview_recipelist);

        // connect to database
        DBHelper dbHelper = new DBHelper(this, "Recipes.db", null, 1);
        String title;

        //get category matching info from earlier activity
        Intent intent = getIntent();

        if(intent.hasExtra("category"))
        {
            title = intent.getStringExtra("category");  // getStringExtra returns a string directly

            Log.d( "Check","Title received is " + title );

            //set product list title from intent key "category"
            txtTitle.setText(title);

            recipes = dbHelper.recipes_SelectByCategory(title);
        }

        else if(intent.hasExtra("title"))
        {
            title = intent.getStringExtra("title");
            //set product list title from intent key "category"
            txtTitle.setText(title);

            ArrayList<Integer> reciveRecipeList =  intent.getIntegerArrayListExtra("list");

            for (int i = 0; i < reciveRecipeList.size(); i++)
            {
                RecipeItem getRecipe = dbHelper.recipes_SelectById(reciveRecipeList.get(i));
                recipes.add(getRecipe);
            }
        }

        //set ListView with Data
        listView.setAdapter(new RecipeList_Adapter(this,recipes, R.layout.activity_recipe_list_item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = recipes.get(position);
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe.get_recipeName());
                startActivity(intent);
                //Toast.makeText( RecipeListActivity.this, "Clicked", Toast.LENGTH_SHORT ).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

/*
 setDsiplayHomeAsUpEnabled()-icon and title in the action bar clickable so that “up”
 (ancestral) navigation can be provided
 setHomeButtonEnabled()- same as above, except the left arrow doesnt not show up
 unless parent activity is mentioned
 Both call onOptionsItemSelected
 */