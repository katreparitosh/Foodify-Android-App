package com.sopan.food.recipe.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sopan.food.recipe.helper.DBHelper;
import com.sopan.food.recipe.model.CategoryItem;
import com.sopan.food.recipe.R;
import com.sopan.food.recipe.activity.RecipeListActivity;
import com.sopan.food.recipe.adapter.category_adapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
        ListView listView = (ListView)view.findViewById(R.id.listVeiw_category);

        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);
        final ArrayList<CategoryItem> categotyList = dbHelper.recipes_SelectCategory();

        listView.setAdapter(new category_adapter(this.getContext(), categotyList, R.layout.fragment_category_item));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem selectCategory = categotyList.get(position);
                Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                intent.putExtra("category", selectCategory.get_category());
                startActivity(intent);
            }
        });

        return view;
    }

}
