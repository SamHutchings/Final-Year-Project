package com.n0234219.fyp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int PHOTO_LIST_LOADER = 0x01;
	private GridView gridView;
	private Bitmap[] thumbs;
	private PhotoInfo[] info;
	private boolean[] selected;
	private HashMap<Integer, ItemHolder> viewHolder;
	private ImageCursorAdapter adapter;
	private Button selectButton;
	private Button selectAllButton;
	private GallerySelectionInterface gallerySelectionInterface;

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_view, container, false);
    }
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewHolder = new HashMap<Integer, ItemHolder>();
		getLoaderManager().initLoader(PHOTO_LIST_LOADER, null, this);
		
	}

	public void onStart() {
		super.onStart();
		adapter = new ImageCursorAdapter();
		gridView = (GridView) getActivity().findViewById(R.id.gallery);
		selectButton = (Button) getActivity().findViewById(R.id.select_button);
		selectAllButton = (Button) getActivity().findViewById(R.id.select_all_button);
		
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, 
				MediaStore.Images.ImageColumns.LATITUDE, MediaStore.Images.ImageColumns.LONGITUDE, 
				MediaStore.Images.ImageColumns.DATE_TAKEN};
		CursorLoader cursorLoader = new CursorLoader(this.getActivity(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				 MediaStore.Images.Media.DATA + " like ? ",
			        new String[] {"%DCIM%"}, MediaStore.Images.ImageColumns.DATE_TAKEN + " desc");
		return cursorLoader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		thumbs = new Bitmap[cursor.getCount()];
		info = new PhotoInfo[cursor.getCount()];
		selected = new boolean[cursor.getCount()];
		gridView.setAdapter(adapter);
		int id;
		for(int i = 0; i < cursor.getCount(); i++) {
			
			cursor.moveToPosition(i);
			id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
			thumbs[i] = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getApplicationContext().getContentResolver(),
					id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
			info[i] = new PhotoInfo();
			info[i].setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE)));
			info[i].setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE)));
			info[i].setTimeTaken(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)));
			info[i].setLocation(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
		}
		selectAllButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//for(ItemHolder holder : viewHolder) {
					
				
			}
			
			
			
		});
		selectButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final int len = selected.length;
				
				ArrayList<PhotoInfo> selectedInfo = new ArrayList<PhotoInfo>();
				for (int i =0; i<len; i++)
				{
					if (selected[i]){
						selectedInfo.add(info[i]);
					}
					
				}
				
				gallerySelectionInterface.sendList(selectedInfo);
				
			}
		});
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			gallerySelectionInterface = (GallerySelectionInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnImageSelectedListener");
		}
	}


	public void onLoaderReset(Loader<Cursor> loader) {
	}


	public class ImageCursorAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public ImageCursorAdapter() {
			inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return thumbs.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ItemHolder holder;
			if (convertView == null) {
				holder = new ItemHolder();
				convertView = inflater.inflate(
						R.layout.gallery_item, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.photo_thumb);
				holder.cb = (CheckBox) convertView.findViewById(R.id.selection_box);

				convertView.setTag(holder);
			}
			else {
				holder = (ItemHolder) convertView.getTag();
			}
			holder.cb.setId(position);
			holder.iv.setId(position);
			viewHolder.put(position, holder);
			holder.iv.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					CheckBox cb = viewHolder.get(v.getId()).cb;
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
			
			holder.cb.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
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
			holder.iv.setImageBitmap(thumbs[position]);
			holder.cb.setChecked(selected[position]);
			holder.id = position;
			return convertView;
		}
	}
	class ItemHolder {
		ImageView iv;
		CheckBox cb;
		int id;
	}	
	
	
	public interface GallerySelectionInterface {
		
		public void sendList(ArrayList<PhotoInfo> selectedInfo);
	}
	
}
