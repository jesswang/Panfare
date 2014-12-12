package itp341.wang.jessica.final_project.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MyImagesFragment extends Fragment {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final String IMAGE_DIRECTORY_NAME = "MyImages";
	private static Uri fileUri;
	private static File filePath;
	Bitmap photo;
	MyImagesAdapter adapter;
	GridView myImagesGrid;
	ArrayList<String> fileList = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fileList = getFilesFromStorage();
		View view = inflater.inflate(R.layout.my_images_fragment, container, false);
		myImagesGrid = (GridView) view.findViewById(R.id.myImagesGrid);
		adapter = new MyImagesAdapter(getActivity().getApplicationContext(), fileList);
		myImagesGrid.setAdapter(adapter);
		myImagesGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				Intent i = new Intent(getActivity().getApplicationContext(), ImageViewActivity.class);
				i.putExtra(ImageViewActivity.EXTRA_FILE_PATH, fileList.get(pos));
				startActivityForResult(i, 0);
			}
			
		});
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.my_images_action_bar, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_item_take_photo:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        fileUri = Uri.fromFile(getOutputMediaFile());
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
			fileList.add(fileUri.getPath());
			adapter.notifyDataSetChanged();
			/*BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);*/ 
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
 
        // save file url in bundle to prevent it from being null on screen orientation changes
        outState.putParcelable("file_uri", fileUri);
    }
 
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null) {
			fileUri = savedInstanceState.getParcelable("file_uri");
		}
	}
    
    private File getOutputMediaFile() {
    	 
        // External sdcard location
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = new File(file.getPath() + File.separator
        		+ "IMG_" + timeStamp + ".jpg");
 
        return mediaFile;
    }
    
    private ArrayList<String> getFilesFromStorage() {
    	// Retrieve files from directory
    	File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

    	File[] files = f.listFiles();

    	for(int i = 0; i < files.length; i++)
    	{
    		File file = files[i];
    		fileList.add(file.getPath());
    	}
    	return fileList;
    }
	
}
