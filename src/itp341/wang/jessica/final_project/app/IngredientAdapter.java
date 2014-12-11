package itp341.wang.jessica.final_project.app;

import itp341.wang.jessica.final_project.model.Ingredient;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class IngredientAdapter extends BaseAdapter {
	private static ArrayList<Ingredient> ingredients;
	private LayoutInflater inf;
	
	public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
		this.inf = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return ingredients.size();
	}

	@Override
	public Object getItem(int pos) {
		return ingredients.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int pos, View view, ViewGroup parent) {
		if(view == null) {
			view = inf.inflate(R.layout.ingredient_list_row, null);
		}
		Ingredient ingredient = ingredients.get(pos);
		
		ImageButton btnDelete = (ImageButton) view.findViewById(R.id.listIngredientDelete);
		TextView ingred = (TextView) view.findViewById(R.id.listRowIngredient);
		ingred.setText(ingredient.getName());
		
		btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ingredients.remove(pos);
				notifyDataSetChanged();
			}
		});
		
		return view;
	}
}
