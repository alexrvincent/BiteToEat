package com.example.ai_danica.recipeapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ai_Danica on 2/21/2016.
 */
public class Recipe implements Parcelable {

    Recipe(String id, String name, String ingredients, String instructions, String type, String tags){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.type = type;
        this.tags = tags;
        this.photo_id = R.drawable.ic_create_recipe;
    }
    Recipe(String id, String name, String ingredients, String instructions){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.photo_id = R.drawable.ic_create_recipe;
    }

    String name;
    String ingredients;
    String instructions;
    String id;
    String type;
    String tags;
    int photo_id;


    //Parcel Constructor Allows Object to be passed
    public Recipe(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);

        this.id = data[0];
        this.name = data[1];
        this.instructions = data[2];
        this.ingredients = data[3];
        this.type = data[4];
        this.tags = data[5];

    }

    @Override
    public int describeContents() {
        //TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        //TODO Auto-generated method stub
        dest.writeStringArray(new String[]{this.id,this.name,this.instructions,this.ingredients,this.type,this.tags});
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            //TODO Auto-generated method stub
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            //TODO Auto-generated method stub
            return new Recipe[size];
        }
    };

    public String getName(){
        return this.name;
    }

    public String getIngredients(){
        return this.ingredients;
    }

    public String getInstructions(){
        return this.instructions;
    }

    public String getUnique_id(){
        return this.id;
    }

    public String getType() {return this.type;}

    public String getTags() {return this.tags;}

    public void setName(String name){
        this.name = name;
    }
    public void setType(String name) {this.type = type;}
    public void setIngredients(String ing){
        this.ingredients = ing;
    }
    public void setInstructions(String instruct){
        this.instructions = instruct;
    }

    public void setPhoto_id(int id) { this.photo_id = id;}
}
