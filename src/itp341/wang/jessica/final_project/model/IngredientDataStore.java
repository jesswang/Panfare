package itp341.wang.jessica.final_project.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class IngredientDataStore {
	private static final String TAG = IngredientDataStore.class.getSimpleName();
	private static final String FILENAME = "inventory.json";


	public static void saveInventory(ArrayList<Ingredient> ingredients,
			Context context) {
		Log.d(TAG,"saveInventory");
		// build an array in JSON
		JSONArray array = new JSONArray();
		for (Ingredient i : ingredients)
			try {
				array.put(i.toJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// write the file to disk
		Writer writer = null;
		OutputStream out;
		try {
			out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ArrayList<Ingredient> loadInventory(Context context) {
		Log.d(TAG,"loadInventory");
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		BufferedReader reader = null;
		try {
			// open and read the file into a StringBuilder
			InputStream in = context.openFileInput(FILENAME);
			if (in != null) {

				reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					// line breaks are omitted and irrelevant
					jsonString.append(line);
				}
				// parse the JSON using JSONTokener
				JSONArray array = (JSONArray) new JSONTokener(
						jsonString.toString()).nextValue();
				// build the array of crimes from JSONObjects
				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);

					Log.d(TAG, "creating n from json");
					Ingredient ingred = new Ingredient(json);
					Log.d(TAG, "adding ingred to array");
					ingredients.add(ingred);
				}
			}
			if (reader != null)
				reader.close();
		} catch (FileNotFoundException e) {
			// we will ignore this one, since it happens when we start fresh
			e.printStackTrace();
		} catch (IOException e) {
			// we will ignore this one, since it happens when we start fresh
			e.printStackTrace();
		} catch (JSONException e) {
			// we will ignore this one, since it happens when we start fresh
			e.printStackTrace();
		}
		return ingredients;
	}
}
