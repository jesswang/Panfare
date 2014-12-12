package itp341.wang.jessica.final_project.model;

import java.io.Serializable;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ingredient objects are stored in InventoryFragment
 * @author lemonzest73
 *
 */
public class Ingredient implements Serializable {
	private static final String JSON_NAME = "name";
	
	private String name;
	private String quantity;
	private String unit;
	private String preparation;

	public Ingredient() {
		super();
	}
	
	public Ingredient(String name) {
		super();
		this.name = name;
	}
	
	public Ingredient(String name, String quantity, String unit, String preparation) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Ingredient [name=" + name + "]";
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

}
