package com.foodapp.food.recipe.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.foodapp.food.recipe.helper.DBHelper;
import com.foodapp.food.recipe.model.CategoryItem;
import com.foodapp.food.recipe.R;
import com.foodapp.food.recipe.activity.RecipeListActivity;
import com.foodapp.food.recipe.adapter.category_adapter;

import java.util.ArrayList;


public class CategoryFragment extends Fragment
{
    public CategoryFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.fragment_category, container, false);

        ListView listView = view.findViewById(R.id.listVeiw_category);

        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);

        final ArrayList <CategoryItem> categoryList = dbHelper.recipes_SelectCategory();

        listView.setAdapter(new category_adapter(this.getContext(), categoryList, R.layout.fragment_category_item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                CategoryItem selectCategory = categoryList.get(position); // gets Category position

                Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                Log.d( "Check", "Category is " + selectCategory.get_category() );
                intent.putExtra("category", selectCategory.get_category());
                startActivity(intent);
            }
        });

        return view;

    }




}
