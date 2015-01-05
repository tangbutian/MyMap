package edu.csu.mymap.android.map.object;

import edu.csu.mymap.android.graphic.object.LatLongPoint;
import edu.csu.mymap.android.graphic.object.PixelPoint;
import edu.csu.mymap.android.graphic.util.TileSystem;
import edu.csu.mymap.android.map.view.MapView;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

/**
 * 
 * Layer 所有Layer的基类
 * 
 * kin kin 2014-11-20 上午11:21:53
 * 
 * @version 1.0.0
 * 
 */
public class Layer {
	protected String url;
	protected String name;

	public void drawLayer(float[] projectionMatrix) {
	};

	public void createLayer(Context contex, LatLongPoint center, int level,
			int viewWidth, int viewHeight) {
	};

}
