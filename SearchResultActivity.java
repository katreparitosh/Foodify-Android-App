package com.foodapp.food.recipe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.foodapp.food.recipe.R;
import com.foodapp.food.recipe.activity.RecipeListActivity;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity
{
    ArrayList<String> listItems = new ArrayList<>();
    GridView gridView;
    ArrayAdapter<String> adapter;
    ArrayList<Integer> savedReceiveMatches;
    ArrayList<Integer> savedRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //get previous intent
        Intent intent = getIntent();

        //get data from intenet
        final ArrayList<Integer> reciveRecipeList =  intent.getIntegerArrayListExtra("idrecipes");
        final ArrayList<Integer> receiveMatches = intent.getIntegerArrayListExtra("matches");

        this.savedReceiveMatches = receiveMatches; //saving as instance variables because I need after
        this.savedRecipeList = reciveRecipeList; //saving as instance variables because I need after

        final ArrayList<Integer> receiveMatchesNoDuplicates = intent.getIntegerArrayListExtra("matchesNoDuplicates");

        ArrayList<String> receiveMatchesNoDuplicatesString = createString(receiveMatchesNoDuplicates);
        //ArrayList<String> receiveMatchesString = createString(receiveMatches);

        gridView = (GridView)findViewById(R.id.idGridSearchResult);
        //adapter = new ArrayAdapter<String>(this, R.layout.searchresult_custom,receiveMatchesString);
        adapter = new ArrayAdapter<String>(this, R.layout.searchresult_custom,receiveMatchesNoDuplicatesString);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // find the position of the view clicked
                Integer positionView = gridView.getPositionForView(view);
                //call the new intent to send the data
                Intent intent = new Intent(getApplicationContext(),RecipeListActivity.class);
                //send the id of the recipes related to the position of the view clicked
                intent.putExtra("title", "Matching: "+ Integer.toString(receiveMatchesNoDuplicates.get(positionView)) + " ingredients");
                ArrayList<Integer> positions = new ArrayList<Integer>();

                int valuePosition = receiveMatchesNoDuplicates.get(positionView); //the value of the ingredient
                positions = findPositionIdResearch(valuePosition); //find the position that matches with the array of idRecipes called savedRecipeList

                ArrayList<Integer> idtosend = new ArrayList<>(); //create the array of id to send to the RecipeListActivity
                for(int i=0;i<positions.size();i++){
                    idtosend.add(savedRecipeList.get(positions.get(i))); //add the value when the value of position match the value of the RecipeList saved
                }
                intent.putExtra("list",idtosend);
                startActivity(intent);

            }
        });
    }


    /*
    Method that build the format of the string to show on the grid view
     */
    public ArrayList<String> createString(ArrayList<Integer> array){
        ArrayList<String> stringToShow = new ArrayList<>();
        for(int i=0;i<array.size();i++){

            if (array.get(i)==1){
                stringToShow.add("Matched\n" + array.get(i) + "\ningredient");
            }
            else{
                stringToShow.add("Matched\n" + array.get(i) + "\ningredients");
            }

        }
        return  stringToShow;
    }

    /*
    Method that research the position id of the matched when you have no duplicates, use to show the list of recipes related to matched ingredients
     */

    public ArrayList<Integer> findPositionIdResearch(int idIngredientMatched){
        ArrayList<Integer> idResearched = new ArrayList<>();
        for(int i =0;i<savedReceiveMatches.size();i++){
            if(savedReceiveMatches.get(i)==idIngredientMatched){
                idResearched.add((i));
            }
        }
        return idResearched;

    }


}

