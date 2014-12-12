package itp341.wang.jessica.final_project.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Recipe objects correspond to individual search results in SearchFragment 
 * and the data for RecipeViewActivity
 * @author Jessica Wang
 *
 */
public class Recipe implements Serializable {
	private static final String JSON_NAME = "name";
	private static final String JSON_ID = "id";
	private static final String JSON_URL = "url";
	private static final String JSON_CUISINE = "cuisine";
	private static final String JSON_COOKING_METHOD = "cooking_method";
	private static final String JSON_INGREDIENTS = "ingredients";
	private static final String JSON_IMG = "image";
	private static final String JSON_THUMBNAIL = "thumb";
	
	String name, id, url, cuisine, cookingMethod, img, thumbnail, servingSize;
	String[] directions;
	ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	public Recipe() {
		super();
	}

	public Recipe(String name, String id, String url, String cuisine,
			String cookingMethod, String img, String thumbnail) {
		super();
		this.name = name;
		this.id = id;
		this.url = url;
		this.cuisine = cuisine;
		this.cookingMethod = cookingMethod;
		this.img = img;
		this.thumbnail = thumbnail;
	}
	
	/**
	 * @param JSONobject json
	 * @throws JSONException
	 */
	public Recipe(JSONObject json) throws JSONException {
		name = json.getString(JSON_NAME);
		id = json.getString(JSON_ID);
		url = json.getString(JSON_URL);
		cuisine = json.getString(JSON_CUISINE);
		cookingMethod = json.getString(JSON_COOKING_METHOD);
		img = json.getString(JSON_IMG);
		thumbnail = json.getString(JSON_THUMBNAIL);
		
		JSONArray array = json.getJSONArray(JSON_INGREDIENTS);
	}
	
	/**Converts Recipe data into JSON object
	 * @return JSONobject
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_NAME, name);
		json.put(JSON_ID, id);
		json.put(JSON_URL, url);
		json.put(JSON_CUISINE, cuisine);
		json.put(JSON_COOKING_METHOD, cookingMethod);
		json.put(JSON_IMG, img);
		json.put(JSON_THUMBNAIL, thumbnail);
		json.put(JSON_INGREDIENTS, ingredients);

		return json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getCookingMethod() {
		return cookingMethod;
	}

	public void setCookingMethod(String cookingMethod) {
		this.cookingMethod = cookingMethod;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getServingSize() {
		return servingSize;
	}

	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String[] getDirections() {
		return directions;
	}

	public void setDirections(String[] directions) {
		this.directions = directions;
	}
	
}
