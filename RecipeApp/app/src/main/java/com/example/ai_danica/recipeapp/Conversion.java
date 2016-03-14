package com.example.ai_danica.recipeapp;

/**
 * Created by Ai_Danica on 2/8/2016.
 * Different functions for the different conversion measurements
 *
 */
public class Conversion {


    //Teaspoon to tablspoon
    public float fromTeaspToTbsp(float num){
        float tbsp = 0.3333f;
        float result = (num) * tbsp;
        return result;
    }
    //Tablespoon to Teaspoon
    public float fromTbspToTeasp(float num){
        float teasp = (1/0.3333f);
        float result = (num) * teasp;
        return result;
    }
    //Teaspoon to Cups
    public float fromTeaspToCups(float num){
        float cups = 0.020833f;
        float result = (num) * cups;
        return result;
    }
    //Tablespoon to Cups
    public float fromTbspToCups(float num){
        float cups = 0.0625f;
        float result = (num) * cups;
        return result;

    }

    //Quart to Gallons
    public float fromQuartToGal(float num){
        float gal = 0.25f;
        float result = (num) * gal;
        return result;
    }


    //Pint to quarts
    public float fromPintToQuart(float num){
        float quart = 0.5f;
        float result = (num) * quart;
        return result;

    }

    //PoundsToOunce
    public float fromPoundstoOunce(float num){
            float ounce = 16f;
            float result = (num) * ounce;
            return result;
    }

    //Pint to fluid ounce
    public float fromPintToFluidOunce(float num){
        float flounce = 16f;
        float result = (num) * flounce;
        return result;
    }

    //Pint to Gallon
    public float fromPintToGal(float num){
        float gal = 0.125f;
        float result = (num) * gal;
        return result;
    }
}
