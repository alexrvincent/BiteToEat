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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Ai_Danica on 2/21/2016.
 */
public class RecipeCreation extends AppCompatActivity {


    private static final String STRING_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 8;
    private String type = "Breakfast";
    private Spinner FromSpinner;
    private ArrayAdapter<CharSequence> FromMeasure;
    EditText nameOfRecipe;
    EditText instruct;
    EditText ingred;
    EditText tagsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_creation_layout);
        nameOfRecipe = (EditText) findViewById(R.id.name);
        instruct = (EditText) findViewById(R.id.instructions);
        ingred = (EditText) findViewById(R.id.ingredients);
        tagsTV = (EditText) findViewById(R.id.tags);


        createRecipeType();



    }

    //Users choose the type of recipe.
    private void createRecipeType(){
        FromSpinner = (Spinner) findViewById(R.id.recipe_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        FromMeasure= ArrayAdapter
                .createFromResource(this, R.array.recipe_type,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        FromMeasure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        FromSpinner.setAdapter(FromMeasure);
        clickedOnType();

    }


    /*
    Knowing what users clicked on. Set string to the respective type.
     */
    private void clickedOnType(){
        FromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                // System.out.println((String) parent.getItemAtPosition(position));
                String recipe_type = (String) parent.getItemAtPosition(position);
                //What users clicked on
                switch (recipe_type) {
                    case "Dessert":
                        type = "Dessert";
                        break;
                    case "Healthy":
                        type = "Healthy";
                        break;
                    case "Alcoholic":
                        type = "Alcoholic";
                        break;
                    case "Baby/Kids":
                        type = "Baby/Kids";
                        break;
                    case "Family":
                        type = "Family";
                        break;
                    case "Poultry":
                        type = "Poultry";
                        break;
                    case "Beef":
                        type = "Beef";
                        break;
                    case "Fruits":
                        type = "Fruits";
                        break;
                    case "Seafood":
                        type = "Seafood";
                        break;
                    case "Asian Culture":
                        type = "Asian Culture";
                        break;
                    case "Smoothies":
                        type = "Smoothies";
                        break;
                    default:
                        type = "Breakfast";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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
        EditText tagsTV = (EditText) findViewById(R.id.tags);

        String name = nameOfRecipe.getText().toString();
        String instructions = instruct.getText().toString();
        String ingredients = ingred.getText().toString();
        String tags = tagsTV.getText().toString();

        //Package the Stats into a String separated by ","
        String[] statsArray = new String[]{name, instructions, ingredients};
        StringBuilder statsString = new StringBuilder();
        for(int i = 0; i < statsArray.length; i++){
            statsString.append(statsArray[i]).append(",");
        }

        Recipe newRecipe = new Recipe(generateId(), name, instructions, ingredients, type, tags);
        //Open up its new SP file and make its editor
        SharedPreferences sharedStats = getSharedPreferences(newRecipe.getUnique_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedStats.edit();

        //Save the newly formed recipe and packaged string
        editor.putString("NAME", newRecipe.getName());
        editor.putString("INSTRUCTIONS", newRecipe.getInstructions());
        editor.putString("INGREDIENTS", newRecipe.getIngredients());
        editor.putString("TYPE", newRecipe.getType());
        editor.putString("TAGS", newRecipe.getTags());
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
