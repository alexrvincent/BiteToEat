package com.example.ai_danica.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Created by Ai_Danica on 2/22/2016.
 * Just a simple test and layout of the Recipes page
 * Feel free to modify it.
 */
public class RecipesPage extends AppCompatActivity {

    String imageFullPath = "ic_type_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_page_layout);


        //Getting the intent from MainActivity.
        Recipe recipe = getIntent().getParcelableExtra("Recipe");

        TextView recipeName = (TextView) findViewById(R.id.name);
        TextView recipeIngred = (TextView) findViewById(R.id.ingredients);
        TextView recipeInstruct = (TextView) findViewById(R.id.instructions);
        TextView recipeType = (TextView) findViewById(R.id.type);
        TextView recipeTags = (TextView) findViewById(R.id.tags);

        ImageView recipeTypeImage = (ImageView) findViewById(R.id.type_image);

        //Setting the text to the respective fields.
        recipeName.setText(recipe.getName());
        recipeIngred.setText(recipe.getIngredients());
        recipeInstruct.setText(recipe.getInstructions());
        recipeType.setText(recipe.getType());
        recipeTags.setText(recipe.getTags());

        //Setting images to the respective fields.
        switch (recipe.getType()) {
            case "Breakfast":
                recipeTypeImage.setImageResource(R.drawable.ic_type_breakfast);
                break;
            case "Dessert":
                recipeTypeImage.setImageResource(R.drawable.ic_type_dessert);
                break;
            case "Healthy":
                recipeTypeImage.setImageResource(R.drawable.ic_type_healthy);
                break;
            case "Alcoholic":
                recipeTypeImage.setImageResource(R.drawable.ic_type_alcohol);
                break;
            case "Baby/Kids":
                recipeTypeImage.setImageResource(R.drawable.ic_type_baby);
                break;
            case "Family":
                recipeTypeImage.setImageResource(R.drawable.ic_type_family);
                break;
            case "Poultry":
                recipeTypeImage.setImageResource(R.drawable.ic_type_chicken);
                break;
            case "Beef":
                recipeTypeImage.setImageResource(R.drawable.ic_type_beef);
                break;
            case "Fruits":
                recipeTypeImage.setImageResource(R.drawable.ic_type_apple);
                break;
            case "Seafood":
                recipeTypeImage.setImageResource(R.drawable.ic_type_seafood);
                break;
            case "Asian Culture":
                recipeTypeImage.setImageResource(R.drawable.ic_type_asian);
                break;
            case "Smoothies":
                recipeTypeImage.setImageResource(R.drawable.ic_type_smoothie);
                break;
            default:
                recipeTypeImage.setImageResource(R.drawable.ic_recipe_list);
                break;
        }


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
