package com.example.ai_danica.recipeapp;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alex on 10/12/2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static ClickListener clickListener;
    private static LongClickListener longClickListener;
    private List<Recipe> recipes;
    private static final int FOOTER_VIEW = 1;

    //Constructor for RVAdapter//
    RVAdapter(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        RecipeViewHolder cvh = new RecipeViewHolder(v2);
        return cvh;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i){

        try {
            if (holder instanceof RecipeViewHolder) {
                RecipeViewHolder cvh = (RecipeViewHolder) holder;
                cvh.recipeName.setText(recipes.get(i).getName());
                cvh.recipeType.setText(recipes.get(i).getType());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount(){
        return recipes.size();
    }

    @Override
    public int getItemViewType(int position){
        if (position == recipes.size()){
            //Add footer here
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }


    public void setOnItemClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setOnLongItemClickListener(LongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public interface LongClickListener {
        void onLongItemClick(int position, View v);
    }


    public void deleteItem(int position){
        recipes.remove(position);
        notifyItemRemoved(position);
    }

    public void insertItem(int position, Recipe newRecipe){
        recipes.add(position, newRecipe);
        notifyItemInserted(position);
    }


    //Sub-Class: RecipeView Holder//
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView recipeName;
        TextView recipeType;

        //Constructor for CharacterViewHolder class
        RecipeViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            //itemView.setOnClickListener(clickListener);
            recipeName = (TextView)itemView.findViewById(R.id.recipe_name);
            recipeType = (TextView)itemView.findViewById(R.id.recipe_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(getAdapterPosition(), v);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    longClickListener.onLongItemClick(getAdapterPosition(),v);
                    return true;
                }
            });


        }

        //@Override
        //public void onClick(View v){
        //clickListener.onItemClick(getAdapterPosition(), v);
        //}

    }

    //FootViewHolder Sub-class
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    clickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }











}
