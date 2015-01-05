package edu.csu.mymap.android;

import android.app.Activity;
import android.os.Bundle;
import edu.csu.mymap.android.graphic.object.LatLongPoint;
import edu.csu.mymap.android.map.object.Layer;
import edu.csu.mymap.android.map.object.TiledLayer;
import edu.csu.mymap.android.map.view.MapView;

public class MyMapActivity extends Activity {
	private static final String TAG = "MyMapActivity";
	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapView = new MapView(this);
		mapView.setLevelOfDetial(7);
		LatLongPoint center = new LatLongPoint(28.16, 112.98);
		mapView.setCenter(center);
		Layer tiledLayerVec = new TiledLayer(
				"http://t0.tianditu.com/vec_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&FORMAT=tiles&TILEMATRIXSET=c&STYLE=default&LAYER=vec&TILEMATRIX=^l&TILEROW=^r&TILECOL=^c",
				"vec");
		mapView.addLayer(tiledLayerVec);

		Layer tiledLayerCav = new TiledLayer(
				"http://t0.tianditu.com/cva_c/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile&FORMAT=tiles&TILEMATRIXSET=c&STYLE=default&LAYER=cva&TILEMATRIX=^l&TILEROW=^r&TILECOL=^c",
				"cav");
		mapView.addLayer(tiledLayerCav);
		setContentView(mapView);
	}

	@Override
	protected void onPause() {
		super.onPause();

		mapView.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();

	}
}