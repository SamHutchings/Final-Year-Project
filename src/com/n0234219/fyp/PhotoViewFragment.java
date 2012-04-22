package com.n0234219.fyp;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PhotoViewFragment extends Fragment {
	
	Bitmap bm; 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_view, container, false);
    }
	

	public void updateImage(String filePath) {
		if(null != bm) {
			bm.recycle();
		}
		ImageView image = (ImageView) getView();
		bm = BitmapFactory.decodeFile(filePath);
		image.setImageBitmap(bm);
				
	}
	
	@Override
    public void onStop() {
		super.onStop();
		if(null != bm) {
			bm.recycle();
		}
    }
}
