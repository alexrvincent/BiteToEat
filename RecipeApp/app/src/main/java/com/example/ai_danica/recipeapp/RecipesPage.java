package com.example.ai_danica.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Ai_Danica on 2/22/2016.
 * Just a simple test and layout of the Recipes page
 * Feel free to modify it.
 */
public class RecipesPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_page_layout);


        //Getting the intent from MainActivity.
        Recipe recipe = getIntent().getParcelableExtra("Recipe");

        TextView recipeName = (TextView) findViewById(R.id.name);
        TextView recipeIngred = (TextView) findViewById(R.id.ingredients);
        TextView recipeInstruct = (TextView) findViewById(R.id.instructions);

        //Setting the text to the respective fields.
        recipeName.setText(recipe.getName());
        recipeIngred.setText(recipe.getIngredients());
        recipeInstruct.setText(recipe.getInstructions());

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Setting the title and back button
        //getSupportActionBar().setTitle(recipe.getName());
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        //return true;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
