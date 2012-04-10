package com.n0234219.fyp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

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
		// TODO Auto-generated method stub
		return null;
	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	  private class ImageCursorAdapter extends CursorAdapter {

	        private Context mContext;

	        public ImageCursorAdapter(Context context, Cursor c, int flags) {
	            super(context, c);
	            mContext = context;
	        }

	        @Override
	        public void bindView(View view, Context context, Cursor cursor) {
	            ImageView iv = (ImageView) view;
	            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
	            iv.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + id));
	        }

	        @Override
	        public View newView(Context context, Cursor cursor, ViewGroup parent) {
	            ImageView view = new ImageView(mContext);
	            return view;
	        }
	    }


}
