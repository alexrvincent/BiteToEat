package com.example.ai_danica.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Ai_Danica on 2/7/2016.
 */
public class ConversionActivity extends AppCompatActivity{

    boolean touchToCups = false;
    boolean touchToTbsp = false;
    boolean touchToTeasp = false;
    boolean touchToGal = false;
    boolean touchToQuart = false;
    boolean touchFromQuart = false;
    boolean touchFromCups = false;
    boolean touchFromTbsp = false;
    boolean touchFromTeasp = false;
    boolean touchFromPint = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Setting the title and back button
        getSupportActionBar().setTitle("Convert Measurements");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting up the spinner
        fromMeasurement();
        toMeasurement();


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

    //Create the spinner for the "from" conversion and know what users clicked on.
    private void fromMeasurement(){
        Spinner FromSpinner = (Spinner) findViewById(R.id.From);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> FromMeasure= ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        FromMeasure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        FromSpinner.setAdapter(FromMeasure);
        FromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                System.out.println((String) parent.getItemAtPosition(position));
                String measurements = (String) parent.getItemAtPosition(position);
                //What users clicked on
                switch (measurements) {
                    case "Cups":
                        touchFromCups = true;
                        break;
                    case "Tablespoons":
                        touchFromTbsp = true;
                        break;
                    case "Teaspoons":
                        touchFromTeasp = true;
                        break;
                    case "Quart":
                        touchFromQuart = true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    //Create the spinner for the "To" conversion and know what users clicked on.
    private void toMeasurement(){
        Spinner ToSpinner = (Spinner) findViewById(R.id.To);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> ToAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        ToSpinner.setAdapter(ToAdapter);

        ToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                System.out.println((String) parent.getItemAtPosition(position));
                String measurements = (String) parent.getItemAtPosition(position);

                //What users clicked on
                switch (measurements){
                    case "Cups":
                        touchToCups = true;
                        break;
                    case "Tablespoons":
                        touchToTbsp = true;
                        break;
                    case "Teaspoons":
                        touchToTeasp = true;
                        break;
                    case "Gallon":
                        touchToGal = true;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    //If users, clicked on "Convert", do conversion
    public void clickedConversion(View v){
        calcConversion();
    }

    //Do the conversion according to what users want.
    private void calcConversion(){
        Conversion convert = new Conversion();
        EditText numText = (EditText) findViewById(R.id.UserNum);
        TextView convNum = (TextView) findViewById(R.id.convertedNum);
        float userNum = Float.valueOf(numText.getText().toString());
        if(touchFromTeasp && touchToTbsp) {
            float convertedNum = convert.fromTeaspToTbsp(userNum);
            convNum.setText("" + convertedNum);
        }
        else if(touchFromTeasp && touchToCups){
            float convertedNum = convert.fromTeaspToCups(userNum);
            convNum.setText("" + convertedNum);
        }
        else if(touchFromTbsp && touchToCups){
            float convertedNum = convert.fromTbspToCups(userNum);
            convNum.setText("" + convertedNum);
        }
        else if(touchFromTbsp && touchToTeasp){
            float convertedNum = convert.fromTbspToTeasp(userNum);
            convNum.setText("" + convertedNum);
        }
        else if(touchFromQuart && touchToGal){
            float convertedNum = convert.fromQuartToGal(userNum);
            convNum.setText("" + convertedNum);
        }
    }
}
