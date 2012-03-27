package com.n0234219.fyp;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class ThumbnailViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	 // member variables for
    private static final int PHOTO_LIST_LOADER = 0x01;
    private GridView gridView;
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
        gridView = (GridView) getView();
        gridView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                String projection[] = {MediaStore.Images.Thumbnails.IMAGE_ID};
                Cursor thumbCursor = getActivity().getContentResolver().query(
                        Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                        String.valueOf(id)), projection, null, null, null);
                if (thumbCursor.moveToFirst()) {
                    String imageId = thumbCursor.getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
                    thumbCursor.close();
                    String imageProjection[] = {MediaStore.Images.ImageColumns.LATITUDE, MediaStore.Images.ImageColumns.LONGITUDE};
                    Cursor imageCursor = getActivity().getContentResolver().query(
                            Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            imageId), imageProjection, null, null, null);
                    if (imageCursor.moveToFirst()) {
                        imageSelectionListener.onImageSelected(imageCursor.getString(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE)),
                                imageCursor.getString(imageCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE)));
                    } else {
                        imageCursor.close();
                    }

                } else {
                    thumbCursor.close();
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
        String[] projection = {MediaStore.Images.Thumbnails._ID};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
                null, null, null);
        return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
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
            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
            iv.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + id));
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            ImageView view = new ImageView(mContext);
            return view;
        }
    }

    public interface OnImageSelectedListener {

        public void onImageSelected(String lat, String lng);
    }
}
