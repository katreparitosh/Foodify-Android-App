package com.foodapp.food.recipe.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.foodapp.food.recipe.R;
import com.foodapp.food.recipe.fragment.CategoryFragment;
import com.foodapp.food.recipe.fragment.HomeFragment;
import com.foodapp.food.recipe.fragment.InformationFragment;
import com.foodapp.food.recipe.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup the toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar); //from app_bar_main.xml
        setSupportActionBar(toolbar); //place toolbar in place of actionbar

         manager = getSupportFragmentManager();

        drawer = findViewById(R.id.drawer_layout);  //activity_main
        toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)  //string resource
        {

            //Called when a drawer has settled in a completely closed state
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);

            }

            //Called when a drawer has settled in a completely open state
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                //Toast.makeText(getBaseContext(), "Open", Toast.LENGTH_SHORT).show();
            }

        };

        drawer.addDrawerListener(toggle);  //handles all drawer open/close operations
        toggle.syncState();     // hides the drawer icon

    }

    @Override
    protected void onStart()
    {
         super.onStart();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        HomeFragment homeFragment = new HomeFragment(); // to display the home page
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.root_layout, homeFragment, "Hi TAG").commit();  //content_main.xml
        homeFragment.getTag();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        if (id == R.id.nav_home)
        {
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction().replace(R.id.root_layout, homeFragment).addToBackStack( null ).commit();
        }
         else if (id == R.id.nav_category)
        {
            CategoryFragment categoryFragment = new CategoryFragment();
            manager.beginTransaction().replace(R.id.root_layout, categoryFragment).addToBackStack(null).commit();

        }
        else if (id == R.id.nav_search)
        {

            SearchFragment searchFragment = new SearchFragment();
            manager.beginTransaction().replace(R.id.root_layout, searchFragment).addToBackStack( null ).commit();
        }
        else if (id == R.id.nav_info)
        {

            InformationFragment informationFragment = new InformationFragment();
            manager.beginTransaction().replace(R.id.root_layout, informationFragment).addToBackStack( null ).commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);  //main_activity.xml
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}

/*
syncState()-
It will synchronize the drawer icon that rotates when the drawer is swiped gestured
left or right and if you try to removed the syncState() the synchronization will
fail thus resulting to buggy rotation or it wont even work.
.commit() - saves data synchronously
*/