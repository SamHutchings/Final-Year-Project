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
	public void onStop() {
		super.onStop();
		mapView.getOverlays().clear();
	}

	public void updateMapPosition(String latitude, String longitude) {

		double lat;
		double lng;
		try {
			lat = Double.valueOf(latitude);
			lng = Double.valueOf(longitude);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return;
		}
		GeoPoint p = new GeoPoint((int) (lat * microDegrees), (int)(lng * microDegrees));
		MapController mc = mapView.getController();
		OverlayItem overlayitem = new OverlayItem(p, "Photo Location", "is here");
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		MapOverlay itemizedoverlay = new MapOverlay(drawable, this.getActivity());
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.clear();
		mapOverlays.add(itemizedoverlay);
		mc.animateTo(p);
		mc.setZoom(17);
		mapView.invalidate();

	}

}
