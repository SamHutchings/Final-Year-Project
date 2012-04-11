package com.n0234219.fyp;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class GalleryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {


	private static final int PHOTO_LIST_LOADER = 0x01;
	private GridView gridView;
	private ImageCursorAdapter adapter;
	private Cursor c;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_view);
		getLoaderManager().initLoader(PHOTO_LIST_LOADER, null, this);
	}

	public void onStart() {
		super.onStart();
		adapter = new ImageCursorAdapter(getApplicationContext(), c, 0);
		gridView = (GridView) findViewById(R.id.gallery);
		gridView.setAdapter(adapter);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
		CursorLoader cursorLoader = new CursorLoader(this,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				 MediaStore.Images.Media.DATA + " like ? ",
			        new String[] {"%Camera%"}, null);
		return cursorLoader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);

	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);

	}

	private class GetThumbnailTask extends AsyncTask<Long, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(Long... params) {
			return MediaStore.Images.Thumbnails.getThumbnail(getApplicationContext().getContentResolver(),
					params[0], MediaStore.Images.Thumbnails.MICRO_KIND, null);
		}
		
	}




	private class ImageCursorAdapter extends CursorAdapter {

		private Context mContext;

		public ImageCursorAdapter(Context context, Cursor c, int flags) {
			super(context, c);
			mContext = context;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ImageView iv = (ImageView) view.findViewById(R.id.photo_thumb);
			long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
			AsyncTask<Long, Integer, Bitmap> task = new GetThumbnailTask().execute(id);
			try {
				iv.setImageBitmap(task.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View v = getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
			return v;	        
		}
	}


}
