package itp341.wang.jessica.final_project.app;

import itp341.wang.jessica.final_project.model.Ingredient;
import itp341.wang.jessica.final_project.model.IngredientDataStore;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
	ActionBarDrawerToggle drawerToggle;
	ArrayAdapter<String> drawerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setProgressBarIndeterminateVisibility(true);
		
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cfe3")));
		
		//Drawer Navigation set up
		drawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.drawer_list);
		drawerToggle = new ActionBarDrawerToggle(this,
	            drawerLayout, R.drawable.ic_drawer,
	            R.string.drawer_open_content_desc,
	            R.string.drawer_close_content_desc);
		drawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItemTitles);
		drawerList.setAdapter(drawerAdapter);
		drawerList.setSelector(android.R.color.holo_blue_dark);
		drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true); 
        
		changeFragment(0);
		
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long id) {
				changeFragment(pos);
			}
			
		});
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    // Sync the toggle state after onRestoreInstanceState has occurred.
	    drawerToggle.syncState();
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
