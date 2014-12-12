package itp341.wang.jessica.final_project.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
	public static final String EXTRA_FILE_PATH = "itp341.wang.jessica.final_project.app.file_path";
	ImageView detailedImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cfe3")));
		detailedImage = (ImageView) findViewById(R.id.detailedImage);
		
		Intent i = getIntent();
		File file = new File(i.getStringExtra(EXTRA_FILE_PATH));
		BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
			BitmapFactory.decodeStream(new FileInputStream(file), null, o);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // scaling code referenced from StackOverflow
        final int REQUIRED_SIZE = 500;

        int scale = 1;
        while(o.outWidth/scale/2 >= REQUIRED_SIZE && o.outHeight/scale/2 >= REQUIRED_SIZE)
            scale *= 2;

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
			Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
			detailedImage.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
