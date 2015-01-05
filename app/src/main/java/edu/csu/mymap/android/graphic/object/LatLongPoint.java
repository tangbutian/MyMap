package edu.csu.mymap.android.graphic.object;

import android.util.Log;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.util.TileSystem;

/**
 * 
 * LatLongPoint 经纬度点
 * 
 * kin kin 2014-11-20 上午11:00:37
 * 
 * @version 1.0.0
 * 
 */
public class LatLongPoint {
	private static final String TAG = "LatLongPoint";
	private double longitude;
	private double latitude;

	public LatLongPoint(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	/**
	 * getPixelPoint(计算当前经纬度坐标在对应level下的像素点坐标) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param 地图level登记
	 * @return 像素点坐标
	 * @exception
	 * @since 1.0.0
	 */
	public PixelPoint getPixelPoint(int level) {
		int[] pixel = TileSystem.LatLongToPixelXY(latitude, longitude, level);
		if (LoggerConfig.ON)
			Log.v(TAG, "getPixelPoint from latLongPoint pixelX: " + pixel[0]
					+ ", pixelY: " + pixel[1]);
		return new PixelPoint(pixel[0], pixel[1]);
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

}
