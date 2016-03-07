package com.example.ai_danica.recipeapp;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {


    private ListView mDrawerList;
    private NavBarAdapter mAdapter;
    private RVAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String ActivityTitle;
    private int delete_dialog;
    private List<Recipe> recipes = new ArrayList();
    static final int NEW_RECIPE_REQUEST = 0;
    RecyclerView rv;
    private List<Recipe> storeRecipe = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //making the navigation bar.
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActivityTitle = getTitle().toString();

        //Recycle View Setup Handles and it's required layout manager
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(llm);

        addDrawerItems();
        setupDrawer();

        //On the start, create the recipes that users made previously.
        populateRecipeCards();
        // List<Character> characters = null;
        adapter = new RVAdapter(recipes);

        //Handle Clicks on any Card
        adapter.setOnItemClickListener(new RVAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Recipe sendRecipe = recipes.get(position);
                //This is going into to the recipes page.
                Intent intent = new Intent(MainActivity.this, RecipesPage.class);
                intent.putExtra("Recipe", sendRecipe);
                startActivity(intent);
                //System.out.println(recipes.get(0).getName());
            }
        });


        //Handle Long Clicks on any Card
        adapter.setOnLongItemClickListener(new RVAdapter.LongClickListener() {
            @Override
            public void onLongItemClick(int position, View v) {
                alertDialog(v, position);
            }
        });

        rv.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Floating action bar that allows players to add more recipes.
        setupFAB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        storeRecipe = recipes;
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final List<Recipe> filteredList = new ArrayList<>();

                for (int i = 0; i < recipes.size(); i++) {

                    final String text = recipes.get(i).getName();
                    if (text.contains(query)) {

                        filteredList.add(recipes.get(i));
                    }
                }

                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new RVAdapter(filteredList);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Recipe> filteredList = new ArrayList<>();

                for (int i = 0; i < recipes.size(); i++) {

                    final String text = recipes.get(i).getName();
                    if (text.contains(newText)) {

                        filteredList.add(recipes.get(i));
                    }
                }

                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new RVAdapter(filteredList);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //for testing purposes
        else if (id == R.id.action_conversion) {
            Intent intent = new Intent(MainActivity.this, RecipeCreation.class);
            startActivityForResult(intent, NEW_RECIPE_REQUEST);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Warning the users that they are about to delete a recipe. Give them a choice
    to continue or cancel
     */
    public void alertDialog(View v, int position) {
        final View viewer = v;
        final int pos = position;

        //Building the alert dialog
        AlertDialog.Builder warningMsg = new AlertDialog.Builder(MainActivity.this);

        //Setting the message for the dialog
        warningMsg.setMessage("Are you sure you want to delete this recipe?");

        //Give users option to cancel a message
        warningMsg.setCancelable(true);

        //Confirmation if want to delete a recipe
        warningMsg.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //getURL(viewer);
                        deleteRecipe(pos);
                        dialog.cancel();
                    }
                });

        warningMsg.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Showing the dialog message on screen to use
        AlertDialog alertShow = warningMsg.create();
        alertShow.show();
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Select an Option");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(ActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        String[] osArray = {"Tools Conversion", "Timer"};
        Integer[] imgid = {R.drawable.ic_tool_conversion, R.drawable.ic_tool_timer};
        // R.drawable.ic_action_name, R.drawable.ic_action_refresh,
        // R.drawable.ic_action_share};

        mAdapter = new NavBarAdapter(this, osArray, imgid);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, ConversionActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //TODO: Maybe implement a timer?
                        Intent timerIntent = new Intent(MainActivity.this, Timer.class);
                        startActivity(timerIntent);
                        break;
                    default:
                        //Toast.makeText(Hello_World.this, "OS", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        reloadSPManager();
        reloadRecipes();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadSPManager();
        reloadRecipes();

        //Update this to notifyItemChanged(position) to increase efficiency
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

     /* Recycle View Functions */

    private void populateRecipeCards() {
        initialLoad();
        //reloadCharacterCards();
    }

    private void reloadSPManager() {
        SharedPreferences sp_mngr = getSharedPreferences("SP_MANAGER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp_mngr.edit();
        //For every character in the array, update it's position-id pair in the SP_MNGR

        //use the recipe arrayList.
        for (int i = 0; i < recipes.size(); ++i) {
            String temp = recipes.get(i).getUnique_id();
            editor.putString(Integer.toString(i), temp);
            editor.apply();
        }
        editor.putInt("RECIPE_COUNT", recipes.size());
        editor.apply();
    }

    private void initialLoad() {
        SharedPreferences sp_mngr = getSharedPreferences("SP_MANAGER", Context.MODE_PRIVATE);
        int char_size = sp_mngr.getInt("RECIPE_COUNT", -1);
        //Toast.makeText(HomeActivity.this,"Size:"+char_size, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < char_size; ++i) {
            String sp_file_name = sp_mngr.getString(Integer.toString(i), "0");

            SharedPreferences sp_file = getSharedPreferences(sp_file_name, Context.MODE_PRIVATE);
            String unique_id_loaded = sp_file_name;

            String recipe_name = sp_file.getString("NAME", "ERROR_LOADING");
            String recipe_instructions = sp_file.getString("INSTRUCTIONS", "ERROR_LOADING");
            String recipe_ingredients = sp_file.getString("INGREDIENTS", "ERROR_LOADING");
            String recipe_type = sp_file.getString("TYPE", "ERROR_LOADING");
            String recipe_tags = sp_file.getString("TAGS", "ERROR_LOADING");

            Recipe loadedRecipe = new Recipe(unique_id_loaded, recipe_name, recipe_instructions, recipe_ingredients, recipe_type, recipe_tags);
            //loadedRecipe.setType(recipe_type);

            recipes.add(loadedRecipe);
        }
    }

    private void reloadRecipes() {
        //For each recipe, re-read it's stored values
        for (int i = 0; i < recipes.size(); ++i) {
            SharedPreferences sp_rec = getSharedPreferences(recipes.get(i).getUnique_id(), Context.MODE_PRIVATE);
            String updatedName = sp_rec.getString("NAME", "ERROR_LOADING");
            String updatedInstruction = sp_rec.getString("NAME", "ERROR_LOADING");
            String updatedIngredients = sp_rec.getString("INSTRUCTIONS", "ERROR_LOADING");
            String updatedTags = sp_rec.getString("TAGS", "ERROR_LOADING");
            String updatedType = sp_rec.getString("TYPE", "ERROR_LOADING");
            int updated_Icon_id;

            recipes.get(i).setIngredients(updatedIngredients);
            recipes.get(i).setInstructions(updatedInstruction);
            recipes.get(i).setName(updatedName);
            recipes.get(i).setType(updatedType);
        }
    }

    /*
        If users want to delete the recipe, they will have to touch it for a long time.
     */
    private void deleteRecipe(int position) {

        //Grab SP_Manager, the to-be deleted file's name, and the to-be deleted file
        SharedPreferences sp_mngr = getSharedPreferences("SP_MANAGER", Context.MODE_PRIVATE);
        String temp = recipes.get(position).getUnique_id();
        SharedPreferences sp_file = getSharedPreferences(temp, Context.MODE_PRIVATE);

        //Case: The file doesn't exist at the file name path
        if (sp_file == null) return;

            //Otherwise, clear the contents at the unique id name,
            //then remove the unique_id from the SP_Manager
        else {
            SharedPreferences.Editor sp_file_editor = sp_file.edit();
            sp_file_editor.clear();
            sp_mngr.edit().remove(temp);
        }

        //Alert the RVAdapter and re-initialize the SP_manager
        adapter.deleteItem(position);
        reloadSPManager();

        View coordLayoutView = findViewById(R.id.coordview);
        Snackbar snackbar = Snackbar.make(coordLayoutView, "Recipe Deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", null);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        snackbar.show();

    }

    /*
    After user created the recipe, come back to home screen and add it to the list.
     */
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == NEW_RECIPE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Recipe newResultChar = data.getParcelableExtra("newRecipeTag");
                createRecipe(recipes.size(), newResultChar);
            }
        }
    }

    /*
    Adds the new recipe to the list of recipe
     */
    private void createRecipe(int position, Recipe newRecipe) {
        adapter.insertItem(position, newRecipe);
        reloadSPManager();
        reloadRecipes();
    }

    /*
        Adding new recipe leads to another activity.
     */
    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is to create the new recipe. a new class.
                Intent intent = new Intent(MainActivity.this, RecipeCreation.class);
                startActivityForResult(intent, NEW_RECIPE_REQUEST);
            }

        });

    }
}
