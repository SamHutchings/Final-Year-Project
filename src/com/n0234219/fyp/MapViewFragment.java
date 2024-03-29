package com.n0234219.fyp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class MapViewFragment extends LocalActivityManagerFragment {
	public static final double microDegrees = 1E6;

	private TabHost mTabHost;
	private MapView mapView;
	private List<Overlay> mapOverlays;
	MapController mc;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mapview_fragment, container, false);
		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mapView = (MapView) view.findViewById(R.id.mapview);
		mTabHost.setup(getLocalActivityManager());
		
		TabSpec tab = mTabHost.newTabSpec("map").setIndicator("map").setContent(new Intent(getActivity(), MapViewActivity.class));
		mTabHost.addTab(tab);
		mapView = (MapView) mTabHost.getCurrentView().findViewById(R.id.mapview);
		mapOverlays = mapView.getOverlays();

		return view;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}	
	
	@Override
	public void onStart() {
		super.onStart();
		mc = mapView.getController();
		mc.setZoom(17);
	}

	@Override
	public void onStop() {
		super.onStop();
		mapView.getOverlays().clear();
	}

	public void updateMapPosition(Double latitude, Double longitude) {

		GeoPoint p = new GeoPoint((int) (latitude * microDegrees), (int)(longitude * microDegrees));
		OverlayItem overlayitem = new OverlayItem(p, "Photo Location", "Latitude: " + latitude + " \nLongitude: " + longitude);
		Drawable drawable = this.getResources().getDrawable(R.drawable.mapspointer);
		MapOverlay itemizedoverlay = new MapOverlay(drawable, this.getActivity());
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.clear();
		mapOverlays.add(itemizedoverlay);
		mc.animateTo(p);
		mapView.invalidate();

	}

}
