package itp341.wang.jessica.final_project.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import itp341.wang.jessica.final_project.model.Ingredient;
import itp341.wang.jessica.final_project.model.Recipe;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity class for individual recipe view
 * @author Jessica Wang
 *
 */
public class RecipeViewActivity extends Activity {
	public static final String RECIPE_SELECTION = "itp341.wang.jessica.final_project.app.recipe_obj";
	Recipe recipe;
	TextView recipeName, recipeCuisine, recipeServes, recipeIngredients, recipeDirections,
		labelDirections, labelIngredients;
	ImageView recipeImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_view);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cfe3")));
		
		recipeName = (TextView) findViewById(R.id.recipeName);
		recipeCuisine = (TextView) findViewById(R.id.recipeCuisine);
		recipeServes = (TextView) findViewById(R.id.recipeServes);
		recipeIngredients = (TextView) findViewById(R.id.recipeIngredients);
		recipeDirections = (TextView) findViewById(R.id.recipeDirections);
		labelIngredients = (TextView) findViewById(R.id.labelIngredients);
		labelDirections = (TextView) findViewById(R.id.labelDirections);
		recipeImg = (ImageView) findViewById(R.id.recipeImg);
		
		Intent i = getIntent();
		recipe = (Recipe) i.getSerializableExtra(RECIPE_SELECTION);
		
		if(recipe != null) {
			recipeName.setText(recipe.getName());
			recipeCuisine.setText(recipe.getCuisine());
			
			//request loading for image and recipe data in separate threads
			new ImageDownloader(this, findViewById(R.layout.activity_recipe_view), recipeImg).execute(recipe.getImg());
			new RequestRecipeData().execute(recipe.getUrl());
		}
	}
	
	/**
	 * This class allows the app to perform HTTP requests separate from the main thread
	 * @author Jessica Wang
	 *
	 */
	class RequestRecipeData extends AsyncTask<String, Integer, String>
	{	
		private static final String JSON_URL = "url";
		private static final String JSON_NAME = "name";
		private static final String JSON_IMG = "image";
		private static final String JSON_CUISINE = "cuisine";
		private static final String JSON_SERVES = "serves";
		private static final String JSON_INGREDIENTS = "ingredients";
		private static final String JSON_QUANTITY = "quantity";
		private static final String JSON_UNIT = "unit";
		private static final String JSON_PREPARATION = "preparation";
		private static final String JSON_DIRECTIONS = "directions";
		
		public RequestRecipeData() {

		}
		
		@Override
		protected String doInBackground(String... params) {
			//make HTTP GET request
			String data = "";
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(request);
				HttpEntity httpEntity = response.getEntity();
				InputStream input = httpEntity.getContent();
				
				if(input != null) {
					BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(input));
					StringBuilder jsonString = new StringBuilder();
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						// line breaks are omitted and irrelevant
						jsonString.append(line);
					}
			 
					// parse the JSON using JSONTokener
					JSONObject json = new JSONObject(jsonString.toString());
					recipe.setServingSize(json.getString(JSON_SERVES));
					JSONArray ingredients = json.getJSONArray(JSON_INGREDIENTS);
					JSONArray directions_arr = json.getJSONArray(JSON_DIRECTIONS);

					// build the ArrayList of ingredients from JSONObjects
					for (int i = 0; i < ingredients.length(); i++) {
						JSONObject jo = ingredients.getJSONObject(i);
						String name = jo.getString(JSON_NAME);
						String quantity = jo.getString(JSON_QUANTITY);
						String unit = jo.getString(JSON_UNIT);
						String preparation = jo.getString(JSON_PREPARATION);
						
						Ingredient ing = new Ingredient(name, quantity, unit, preparation);
						recipe.getIngredients().add(ing);
					}
					
					// populate String[] of Recipe object with directions
					recipe.setDirections(new String[directions_arr.length()]);
					for (int i = 0; i < directions_arr.length(); i++) {
						recipe.getDirections()[i] = directions_arr.getString(i);
					}
			        
			        input.close();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateLayout();
		}
		
		private void updateLayout() {
			StringBuilder str = new StringBuilder();
			StringBuilder strD = new StringBuilder();

			//update the layout with the recipe data
			for(Ingredient i : recipe.getIngredients()) {
				str.append("&#8226; ");
				if(i.getQuantity() != null && !i.getQuantity().equals("null")) {
					str.append(i.getQuantity());
					str.append(" ");
				}
				if(i.getUnit() != null && !i.getUnit().equals("null")) {
					str.append(i.getUnit());
					str.append(" ");
				}
				if(i.getPreparation() != null && !i.getPreparation().equals("null")) {
					str.append(i.getPreparation());
					str.append(" ");
				}
				str.append(i.getName());
				str.append("<br/>");
			}
			
			for(int i = 0; i < recipe.getDirections().length; i++) {
				strD.append(i+1);
				strD.append(". ");
				strD.append(recipe.getDirections()[i]);
				strD.append("<br/>");
			}
			
			labelIngredients.setVisibility(TextView.VISIBLE);
			labelDirections.setVisibility(TextView.VISIBLE);
			recipeServes.setText("Serves "+recipe.getServingSize());
			recipeIngredients.setText(Html.fromHtml(str.toString()));
			recipeDirections.setText(Html.fromHtml(strD.toString()));
		}
		
	}
}
