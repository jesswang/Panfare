package itp341.wang.jessica.final_project.app;

import itp341.wang.jessica.final_project.model.Ingredient;
import itp341.wang.jessica.final_project.model.IngredientDataStore;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] drawerItemTitles;
	DrawerLayout drawerLayout;
	ListView drawerList;
	ArrayAdapter<String> drawerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Drawer Navigation set up
		drawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.drawer_list);
		drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItemTitles);
		drawerList.setAdapter(drawerAdapter);
		drawerList.setSelector(android.R.color.holo_blue_dark);
		
		changeFragment(0);
		
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				changeFragment(pos);
			}
			
		});
	}
	
	private void changeFragment(int pos) {
		Fragment fragment = null;
		
		switch(pos) {
		case 0:
			fragment = new InventoryFragment();
			break;
		case 1:
			fragment = new SearchFragment();
			break;
		case 2:
			fragment = new MyImagesFragment();
			break;
		default:
			break;
		}
		
		//display the Fragment corresponding to the selected DrawerList item
		if(fragment != null) {
			drawerLayout.closeDrawers();
			drawerList.setSelection(pos);
			getActionBar().setTitle(drawerItemTitles[pos]);
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
		}
	}
}
