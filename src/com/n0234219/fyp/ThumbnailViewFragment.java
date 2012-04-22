package com.n0234219.fyp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class ThumbnailViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	 // member variables for
    private static final int PHOTO_LIST_LOADER = 0x01;
    private GridView gridView;
    private Bitmap[] thumbs;
    private ImageCursorAdapter adapter;
    private OnImageSelectedListener imageSelectionListener;
    private Cursor c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(PHOTO_LIST_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thumbnail_view, container, false);
    }

    @Override
    public void onStart() {
    	super.onStart();
    	adapter = new ImageCursorAdapter(getActivity().getApplicationContext(), c, 0);
    	gridView = (GridView) getActivity().findViewById(R.id.photo_item);
    	gridView.setOnItemClickListener(new OnItemClickListener() {

    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {
    			String projection[] = {MediaStore.Images.ImageColumns.LATITUDE, MediaStore.Images.ImageColumns.LONGITUDE};
    			Cursor imageCursor = getActivity().getContentResolver().query(
    					Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
    							String.valueOf(id)), projection, null, null, null);

    			if (imageCursor.moveToFirst()) {
    				imageSelectionListener.onImageSelected(imageCursor.getDouble(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE)),
    						imageCursor.getDouble(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE)));
    			} else {
    				imageCursor.close();
    			}


    		}
    	});
    	gridView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            imageSelectionListener = (OnImageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnImageSelectedListener");
        }
    }

    // Loader manager methods
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				 MediaStore.Images.Media.DATA + " like ? ",
			        new String[] {"%DCIM%"}, MediaStore.Images.ImageColumns.DATE_TAKEN + " desc");
		return cursorLoader;
    }
    

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	thumbs = new Bitmap[cursor.getCount()];
    	int id;
    	for(int i = 0; i < cursor.getCount(); i++) {
    		cursor.moveToPosition(i);
    		id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
    		thumbs[i] = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getApplicationContext().getContentResolver(),
    				id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
    	}
    		adapter.swapCursor(cursor);

    }

    public void onLoaderReset(Loader<Cursor> cursor) {
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
            ImageView iv = (ImageView) view;
			iv.setImageBitmap(thumbs[cursor.getPosition()]);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            ImageView view = new ImageView(mContext);
            return view;
        }
    }

    public interface OnImageSelectedListener {

        public void onImageSelected(Double lat, Double lng);
    }
}
