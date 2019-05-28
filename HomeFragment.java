package com.foodapp.food.recipe.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.foodapp.food.recipe.R;


public class HomeFragment extends Fragment
{

    public HomeFragment()
    {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);  // sets layout of home activity

        //for the magnifying glass search icon
        FloatingActionButton fab = view.findViewById(R.id.fab);  //fragment_home.xml
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Check search recipes menu on the slide menu
                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);  // from activity_main.xml
                navigationView.setCheckedItem(R.id.nav_search);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                SearchFragment searchFragment = new SearchFragment();    // jumps to searchFragment for search operations
                manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack( null ).commit();
            }
        });

        return view;
    }

}
