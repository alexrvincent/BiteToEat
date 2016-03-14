package com.example.ai_danica.recipeapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A class for the navigation bar. Creates its own view.
 */
public class NavBarAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    //Ctor
    public NavBarAdapter(Activity context, String[] itemname, Integer[] imgid){
        super(context,R.layout.nav_layout,itemname);

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    //Makes a view for the the navigations. Adds images.
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.nav_layout, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nav_list_text);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);

        return rowView;
    };
}
