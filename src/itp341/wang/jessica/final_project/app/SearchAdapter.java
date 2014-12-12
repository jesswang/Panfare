package itp341.wang.jessica.final_project.app;

import itp341.wang.jessica.final_project.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private static ArrayList<Recipe> recipes;
	private LayoutInflater inf;
	//ImageView img;
	
	public SearchAdapter(Context context, ArrayList<Recipe> recipes) {
		this.recipes = recipes;
		this.inf = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recipes.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return recipes.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if(view == null) {
			view = inf.inflate(R.layout.search_list_row, parent, false);
		}
		
		Recipe recipe = recipes.get(pos);
		
		TextView name = (TextView) view.findViewById(R.id.rowRecipeName);
		TextView cuisine = (TextView) view.findViewById(R.id.rowRecipeCuisine);
		name.setText(recipe.getName());
		cuisine.setText(recipe.getCuisine());
		
		//img = (ImageView) view.findViewById(R.id.rowRecipeImg);
		//new ImageDownloader(view.getContext(), view, img).execute(recipe.getThumbnail());
		
		return view;
	}

}