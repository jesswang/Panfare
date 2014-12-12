package itp341.wang.jessica.final_project.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	Context context;
	View view;
	ImageView img;

	public ImageDownloader(Context context, View view, ImageView img) {
		this.context = context.getApplicationContext();
		this.view = view;
		this.img = img;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		Bitmap icon = null;
		try {
			//InputStream in = new java.net.URL(urls[0]).openStream();
			URL url = new URL(urls[0]);
			icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);

		img.setImageBitmap(result);
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, result.getWidth(), context.getResources().getDisplayMetrics());
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, result.getHeight(), context.getResources().getDisplayMetrics());
		img.setMinimumWidth(width);
		img.setMinimumHeight(height);
	}

}