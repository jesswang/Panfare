package itp341.wang.jessica.final_project.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import itp341.wang.jessica.final_project.model.Ingredient;
import itp341.wang.jessica.final_project.model.IngredientDataStore;
import itp341.wang.jessica.final_project.model.Recipe;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Fragment class for search_fragment.xml
 * @author lemonzest73
 *
 */
public class SearchFragment extends Fragment {
	private static final String requestUrl = "http://api.pearson.com:80/kitchen-manager/v1/recipes?";
	private static final String requestParam = "ingredients-any=";
	
	public SearchFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_fragment, container, false);
		
		//create String request from list of ingredients stored in JSON
		StringBuffer str = new StringBuffer(requestUrl);
		ArrayList<Ingredient> ingredients = IngredientDataStore.loadInventory(getActivity());
		if(!ingredients.isEmpty()) {
			str.append(requestParam);
			for(Ingredient i : ingredients) {
				str.append(i.getName());
				str.append(",");
			}
			Log.d("Request string", str.toString());
		}
		new RequestSearchResults(getActivity(), view).execute(str.toString());
		
		return view;
	}

}

/**
 * This class allows the app to perform HTTP requests separate from the main thread
 * @author lemonzest73
 *
 */
class RequestSearchResults extends AsyncTask<String, Integer, String>
{
	private static final String JSON_RESULTS = "results";
	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	Context context;
	View view;
	ListView list;
	
	public RequestSearchResults(Context context, View view) {
		this.context = context;
		this.view = view;
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
				JSONArray results = json.getJSONArray(JSON_RESULTS);

				// build the array of recipes from JSONObjects
				for (int i = 0; i < results.length(); i++) {
					JSONObject jo = results.getJSONObject(i);
					Recipe r = new Recipe(jo);
					recipes.add(r);
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
		
		//update the ListView with the search results
		SearchAdapter adapter = new SearchAdapter(context, recipes);
		list = (ListView) view.findViewById(R.id.listViewSearch);
		list.setAdapter(adapter);
	}
	
}
