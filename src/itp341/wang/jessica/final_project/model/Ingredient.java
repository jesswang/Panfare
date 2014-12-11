package itp341.wang.jessica.final_project.model;

import java.io.Serializable;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient implements Serializable {
	private static final String JSON_NAME = "name";
	
	private String name;

	public Ingredient() {
		super();
	}
	
	public Ingredient(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * @param JSONobject json
	 * @throws JSONException
	 */
	public Ingredient(JSONObject json) throws JSONException {
		name = json.getString(JSON_NAME);
	}
	
	/**Converts Ingredient data into JSON object
	 * @return JSONobject
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_NAME, name);

		return json;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Ingredient [name=" + name + "]";
	}

}
