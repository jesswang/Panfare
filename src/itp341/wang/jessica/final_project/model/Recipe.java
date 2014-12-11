package itp341.wang.jessica.final_project.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Recipe objects correspond to individual search results in SearchFragment
 * @author lemonzest73
 *
 */
public class Recipe {
	private static final String JSON_NAME = "name";
	private static final String JSON_ID = "id";
	private static final String JSON_URL = "url";
	private static final String JSON_CUISINE = "cuisine";
	private static final String JSON_COOKING_METHOD = "cooking_method";
	private static final String JSON_INGREDIENTS = "ingredients";
	private static final String JSON_IMG = "image";
	private static final String JSON_THUMBNAIL = "thumb";
	
	String name, id, url, cuisine, cookingMethod, img, thumbnail;
	String[] ingredients;
	
	public Recipe() {
		super();
	}

	public Recipe(String name, String id, String url, String cuisine,
			String cookingMethod, String img, String thumbnail,
			String[] ingredients) {
		super();
		this.name = name;
		this.id = id;
		this.url = url;
		this.cuisine = cuisine;
		this.cookingMethod = cookingMethod;
		this.img = img;
		this.thumbnail = thumbnail;
		this.ingredients = ingredients;
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
		ingredients = new String[array.length()];
		for(int i = 0; i < array.length(); i++) {
			ingredients[i] = array.getString(i).toString();
		}
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

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
	
}
