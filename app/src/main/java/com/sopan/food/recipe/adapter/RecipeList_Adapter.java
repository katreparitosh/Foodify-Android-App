package com.sopan.food.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopan.food.recipe.helper.ImageHelper;
import com.sopan.food.recipe.model.RecipeItem;
import com.sopan.food.recipe.R;

import java.util.ArrayList;

/**
 * Created by Sopan on 2017.03.03
 */

public class RecipeList_Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<RecipeItem> recipeList;
    private int layout;
    private ImageHelper imageHelper = new ImageHelper();

    public RecipeList_Adapter(Context context, ArrayList<RecipeItem> recipeList, int layout){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recipeList = recipeList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        RecipeItem recipeItem = recipeList.get(position);

        ImageView ListView_Image=(ImageView)convertView.findViewById(R.id.listItem_image);
        ListView_Image.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem.get_thumbnail()));

        TextView listView_title=(TextView)convertView.findViewById(R.id.listItem_title);
        TextView listView_author=(TextView)convertView.findViewById(R.id.listItem_author);
        TextView listView_likecount=(TextView)convertView.findViewById(R.id.listItem_likecount);

        listView_title.setText(recipeItem.get_recipeName());
        listView_author.setText(recipeItem.get_author());
        listView_likecount.setText(String.valueOf(recipeItem.get_likeCount()));

        return convertView;
    }
}
