package com.foodapp.food.recipe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.foodapp.food.recipe.helper.DBHelper;
import com.foodapp.food.recipe.model.RecipeItem;
import com.foodapp.food.recipe.model.SearchResultItem;
import com.foodapp.food.recipe.R;
import com.foodapp.food.recipe.activity.RecipeListActivity;
import com.foodapp.food.recipe.activity.SearchResultActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchFragment extends Fragment implements View.OnClickListener
{

    EditText editText;
    Button addButton;
    Button buttonSearch;
    GridView gridView;

    //array list where the items entered will be stored
    ArrayList <String> listItems = new ArrayList<>();

    //adapter to populate
    ArrayAdapter <String> adapter;

    //to record how many times button has been clicked
    int clickCounter = 0;


    public SearchFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false); //inflates fragment_search.xml layout

        editText =  view.findViewById(R.id.typeIngredient);
        addButton = view.findViewById(R.id.addIngredient);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        gridView = view.findViewById(R.id.gridSearch);

        //set up adapter
        adapter = new ArrayAdapter<>(getContext(), R.layout.edit_text_custom_for_ingredients, listItems);
        gridView.setAdapter(adapter);

        //to remove on clicked data, not working
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //delete element onclick
                Object o = gridView.getItemAtPosition(position);
                listItems.remove(position);
                clickCounter--;
                adapter.notifyDataSetChanged();

            }
        });

        addButton.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {

        if (v.getId() == addButton.getId())
        {
            if (clickCounter < 20) //set max 20 ingredients
            {
                if (editText.getText().toString().trim().length() > 0)  //if its not blank string
                {
                      if(listItems.contains(editText.getText().toString())) // if a particular string is already added
                      {
                        Toast toast = Toast.makeText(getContext(), "Ingredient Duplicated", Toast.LENGTH_SHORT);
                        toast.show();
                      }
                      else
                      {
                        listItems.add(editText.getText().toString());
                        adapter.notifyDataSetChanged();
                        clickCounter++;
                        editText.setText("");    // sets blank text after one item is added
                      }
                }
                else
                {
                    //Toast toast = Toast.makeText(getContext(), "insert something", Toast.LENGTH_SHORT);
                   // toast.show();
                }
            }
            else    // if max no of items have been added
            {
                addButton.setClickable(false);
                Toast toast = Toast.makeText(getContext(), "Max number of ingredients reached", Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == buttonSearch.getId())
        {
            if (listItems.isEmpty()) //if nothing is added then display error
            {
                Toast toast = Toast.makeText(getContext(), "You didn't insert any ingredient", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                // creates object of helper class
                DBHelper dbHelper = new DBHelper(v.getContext(), "Recipes.db", null, 1);

                // calls ingredients_selectReceipeByIngredientName function from DBhelper.java, listitems is passed
                ArrayList <SearchResultItem> resultList = dbHelper.ingredients_selectRecipeByIngredientName(listItems);


                if(resultList.isEmpty())  //if didn't find anything display error message and recommend best recipes
                {

                    ArrayList<Integer> advices;
                    advices = findBestRecipes();

                    Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                    intent.putExtra("title", "Sorry, no ingredients matched :( Some Suggestions !");

                    intent.putExtra("list",advices);
                    startActivity(intent);
                    dbHelper.close();

                }
                else
                    {
                    ArrayList<Integer> idRecipes = new ArrayList<>();
                    ArrayList<Integer> matches = new ArrayList<>();

                    //to get recipes
                    for (int i = 0; i < resultList.size(); i++)
                    {
                        idRecipes.add( resultList.get( i ).get_recipeId() );
                    }

                    //to get the ingredient matches
                    for (int j = 0; j < resultList.size(); j++)
                    {
                        matches.add( resultList.get( j ).get_ingrCount() );
                    }

                    ArrayList<Integer> matchesNoDuplicates = removeDuplicates( matches );

                    //sent to Search Result intent idRecipes, matches and also the number of matches without duplicates
                    Intent intent = new Intent( getActivity(), SearchResultActivity.class );
                    intent.putExtra( "idrecipes", idRecipes );

                    intent.putExtra( "matchesNoDuplicates", matchesNoDuplicates );
                    intent.putExtra( "matches", matches );

                    startActivity( intent );

                    dbHelper.close();
                }
            }
        }
    }

    //suggesting recipes
    public ArrayList<Integer> findBestRecipes()
    {
        ArrayList<Integer> bestRecipes = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);

        ArrayList<RecipeItem> results = dbHelper.recipes_SelectBest();
        if(results.isEmpty())
        {

        }
        else
            {
            for(int i=0;i<results.size();i++)
            {
                bestRecipes.add(results.get(i).get_id());
            }
        }
        return  bestRecipes;
    }

    // function that removes the duplicates in an array of Integer

    public ArrayList<Integer> removeDuplicates(ArrayList<Integer> array)
    {
        ArrayList<Integer> noduplicates = new ArrayList<>();
        Set<Integer> withoutDuplicates = new LinkedHashSet<>(array);
        noduplicates.clear();
        noduplicates.addAll(withoutDuplicates);
        return noduplicates;
    }

}
