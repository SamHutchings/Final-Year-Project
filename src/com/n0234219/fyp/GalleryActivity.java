package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


public class GalleryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {


	private static final int PHOTO_LIST_LOADER = 0x01;
	private GridView gridView;
	private Bitmap[] thumbs;
	private PhotoInfo[] info;
	private boolean[] selected;
	private ImageCursorAdapter adapter;
	private Cursor c;
	private Button selectButton;


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
		selectButton = (Button) findViewById(R.id.select_button);
		
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
		
		thumbs = new Bitmap[cursor.getCount()];
		info = new PhotoInfo[cursor.getCount()];
		selected = new boolean[cursor.getCount()];
		int id;
		for(int i = 0; i < cursor.getCount(); i++) {
			
			cursor.moveToPosition(i);
			id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
			String projection[] = {MediaStore.Images.ImageColumns.LATITUDE, MediaStore.Images.ImageColumns.LONGITUDE,
					MediaStore.Images.ImageColumns.DATE_TAKEN};
			Cursor imageCursor = getContentResolver().query(
					Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							String.valueOf(id)), projection, null, null, null);
			imageCursor.moveToPosition(0);
			thumbs[i] = MediaStore.Images.Thumbnails.getThumbnail(getApplicationContext().getContentResolver(),
					id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
			info[i] = new PhotoInfo();
			info[i].setLatitude(imageCursor.getDouble(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE)));
			info[i].setLongitude(imageCursor.getDouble(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE)));
			info[i].setTimeTaken(imageCursor.getLong(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)));
			info[i].setLocation(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
			imageCursor.close();
		}
		selectButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int len = selected.length;
				
				List<PhotoInfo> selectedInfo = new ArrayList<PhotoInfo>();
				for (int i =0; i<len; i++)
				{
					if (selected[i]){
						selectedInfo.add(info[i]);
					}
					
				}
				
				Intent intent = new Intent();
				intent.putParcelableArrayListExtra("Info", (ArrayList<? extends Parcelable>) selectedInfo);
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		adapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);

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
			iv.setImageBitmap(thumbs[cursor.getPosition()]);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View v = getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
			CheckBox cb = (CheckBox) v.findViewById(R.id.selection_box);
			cb.setId(cursor.getPosition());
			cb.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (selected[id]){
						cb.setChecked(false);
						selected[id] = false;
					} else {
						cb.setChecked(true);
						selected[id] = true;
					}
				}
			});
			return v;	        
		}
	}


}
