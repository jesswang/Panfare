package itp341.wang.jessica.final_project.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MyImagesAdapter extends BaseAdapter {
	Context context;
	String[] fileList;
	
	MyImagesAdapter(Context context, String[] fileList) {
		this.context = context;
		this.fileList = fileList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fileList.length;
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return fileList[pos];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		ImageView v;
		if (view == null) {
            v = new ImageView(context);
            v.setLayoutParams(new GridView.LayoutParams(185, 185));
            v.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.setPadding(5, 5, 5, 5);
        } else {
            v = (ImageView) view;
        }
		BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        File file = new File(fileList[pos]);
        try {
			BitmapFactory.decodeStream(new FileInputStream(file), null, o);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        final int REQUIRED_SIZE = 80;

        int scale = 1;
        while(o.outWidth/scale/2 >= REQUIRED_SIZE && o.outHeight/scale/2 >= REQUIRED_SIZE)
            scale *= 2;

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
			Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
			v.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}

}
