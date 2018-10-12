package com.foodapp.food.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodapp.food.recipe.helper.ImageHelper;
import com.foodapp.food.recipe.model.CategoryItem;
import com.foodapp.food.recipe.R;

import java.util.ArrayList;


public class category_adapter extends BaseAdapter
{

    private LayoutInflater inflater;
    private ArrayList<CategoryItem> categoryList;
    private int layout; //layout is ID of the layout resource that getView() inflates to create the view.
    private ImageHelper imageHelper = new ImageHelper();

    public category_adapter(Context context, ArrayList<CategoryItem> categoryList, int layout)
    {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryList = categoryList;
        this.layout = layout;
    }

    @Override //returns the total number of items to be displayed in a list
    public int getCount()
    {
        return categoryList.size();
    }

    @Override  // return the object at position in data set
    public Object getItem(int position) {
        return categoryList.get(position).get_category();
    }

    @Override // returns a long value of item position to the adapter.
    public long getItemId(int position) {
        return position;
    }

    @Override // called when the list item view is ready to be displayed
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //A convert view is a view that is currently not in the screen and hence can be recycled
        //basically uses existing view
        if(convertView==null)
        {   //if it's not recycled, initialize some attributes
            convertView=inflater.inflate(layout,parent,false);
        }

        // sets model of category item
        CategoryItem categoryItem = categoryList.get(position);

        ImageView icon= convertView.findViewById(R.id.categoryitem_img);
        icon.setImageBitmap(imageHelper.getBitmapFromByteArray(categoryItem.get_mainImg()));
        // goes to "fragment_category_item" layout and sets up the layout and populate daata
        TextView name = convertView.findViewById(R.id.categoryitem_text);
        name.setText(categoryItem.get_category());
        return convertView;
    }
}
