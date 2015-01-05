package edu.csu.mymap.android.graphic.object;

import android.util.Log;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.util.TileSystem;

/**
 * 
 * LatLongPoint ��γ�ȵ�
 * 
 * kin kin 2014-11-20 ����11:00:37
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
	 * getPixelPoint(���㵱ǰ��γ�������ڶ�Ӧlevel�µ����ص�����) (����������������������� �C ��ѡ)
	 * 
	 * @param ��ͼlevel�Ǽ�
	 * @return ���ص�����
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
