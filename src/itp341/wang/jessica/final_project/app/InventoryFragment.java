package itp341.wang.jessica.final_project.app;

import itp341.wang.jessica.final_project.model.Ingredient;
import itp341.wang.jessica.final_project.model.IngredientDataStore;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment class for inventory_fragment.xml
 * @author Jessica Wang
 *
 */
public class InventoryFragment extends Fragment implements OnClickListener {
	EditText editIngredient;
	Button btnAdd;
	ListView list;
	IngredientAdapter adapter;
	ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(); //passed to adapter to populate ListView
	
	public InventoryFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.inventory_fragment, container, false);
		/*String menu = getArguments().getString("Menu");
        TextView text= (TextView) view.findViewById(R.id.detail);
        text.setText(menu);*/
		editIngredient = (EditText) view.findViewById(R.id.editIngredient);
		btnAdd = (Button) view.findViewById(R.id.btnAdd);
		list = (ListView) view.findViewById(R.id.listViewIngredients);
		
		btnAdd.setOnClickListener(this);
		
		ingredients = IngredientDataStore.loadInventory(getActivity());
		adapter = new IngredientAdapter(getActivity(), ingredients); //new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredients);
		list.setAdapter(adapter);

        return view;
	}
	
	
	@Override
	public void onClick(View v) {
		String ingred = editIngredient.getText().toString();
		//if value is valid
		if(!ingred.equals("")) {
			ingredients.add(new Ingredient(ingred));
			adapter.notifyDataSetChanged(); //update ListView
			editIngredient.setText("");
		}
		else {
			Toast.makeText(getActivity(), getResources().getString(R.string.msg_bad_input), Toast.LENGTH_LONG)
			.show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		IngredientDataStore.saveInventory(ingredients, getActivity().getApplicationContext());
	}
}
