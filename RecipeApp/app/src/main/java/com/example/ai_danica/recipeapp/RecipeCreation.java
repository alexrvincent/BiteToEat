package com.example.ai_danica.recipeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Ai_Danica on 2/21/2016.
 */
public class RecipeCreation extends AppCompatActivity {


    private static final String STRING_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_creation_layout);
        Button save = (Button) findViewById(R.id.save);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting the title and back button
        getSupportActionBar().setTitle("Create Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


    /*
    Once users clicked save, then, it will automatically be saved in the
    SharedPreferences.
     */
    public void clickedSave(View v){
        EditText nameOfRecipe = (EditText) findViewById(R.id.name);
        EditText instruct = (EditText) findViewById(R.id.instructions);
        EditText ingred = (EditText) findViewById(R.id.ingredients);

        String name = nameOfRecipe.getText().toString();
        String instructions = instruct.getText().toString();
        String ingredients = ingred.getText().toString();

        //Package the Stats into a String separated by ","
        String[] statsArray = new String[]{name, instructions, ingredients};
        StringBuilder statsString = new StringBuilder();
        for(int i = 0; i < statsArray.length; i++){
            statsString.append(statsArray[i]).append(",");
        }

        Recipe newRecipe = new Recipe(generateId(), name, instructions, ingredients);

        //Open up its new SP file and make its editor
        SharedPreferences sharedStats = getSharedPreferences(newRecipe.getUnique_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedStats.edit();

        //Save the newly formed character stats and packaged string
        editor.putString("NAME", newRecipe.getName());
        editor.putString("INSTRUCTIONS", newRecipe.getInstructions());
        editor.putString("INGREDIENTS", newRecipe.getIngredients());
        editor.apply(); //maybe commit here?

        //Alert User that the current character has been saved
        Toast.makeText(RecipeCreation.this, "New Recipe Created!", Toast.LENGTH_SHORT).show();


        //Returning to the MainActivity once users made something.
        Intent returnIntent = new Intent();
        returnIntent.putExtra("newRecipeTag", newRecipe);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /*
    Just generating an ID for each recipes
    Got it from Alex's code
     */
    private String generateId(){
        StringBuffer randomString = new StringBuffer();
        for(int i =0; i<RANDOM_STRING_LENGTH; ++i){
            int number = getRandomNumber();
            char ch = STRING_LIST.charAt(number);
            randomString.append(ch);
        }
        return randomString.toString();
    }

    /*
    Generating random number. Got it from Alex's code.
     */
    private int getRandomNumber(){
        int randomInt;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(STRING_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

}
