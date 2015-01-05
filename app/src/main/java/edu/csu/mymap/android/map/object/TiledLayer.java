package edu.csu.mymap.android.map.object;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.object.LatLongPoint;
import edu.csu.mymap.android.graphic.object.PixelPoint;
import edu.csu.mymap.android.graphic.object.TiledMap;
import edu.csu.mymap.android.graphic.util.TileSystem;

public class TiledLayer extends Layer {
	private static final String TAG = "TiledLayer";
	private static final int MAX_SIZE = 512;
	private Map<String, TiledMap> tiledMapCollection;
	/**
	 * tileIndex:TODO��level,startX, startY, endX, endY��
	 * 
	 * @since 1.0.0
	 */
	private int[] tileIndex = new int[5];

	public TiledLayer(String url, String name) {
		tiledMapCollection = new HashMap<String, TiledMap>();
		this.url = url;
		this.name = name;

	}

	private void initTileIndex(LatLongPoint center, int level, int viewWidth,
			int viewHeight) {
		if (LoggerConfig.ON) {
			Log.v(TAG, "center point latitude: " + center.getLatitude()
					+ ", longitude: " + center.getLongitude());
		}
		// �����ĵ㾭γ������������ĵ��ڶ�Ӧlevel�µ���������
		PixelPoint point = center.getPixelPoint(level);
		if (LoggerConfig.ON) {
			Log.v(TAG, "center point pixelX: " + point.getPixelX()
					+ ", pixelY: " + point.getPixelY());
		}
		// ��ȡviewӳ���ڶ�Ӧlevel�ϵ��Ӵ����top-left��bottom-right��������
		PixelPoint topLeftPoint = new PixelPoint(point.getPixelX() - viewWidth
				/ 2, point.getPixelY() - viewHeight / 2);
		if (LoggerConfig.ON) {
			Log.v(TAG, "topLeftPoint point pixelX: " + topLeftPoint.getPixelX()
					+ ", pixelY: " + topLeftPoint.getPixelY());
		}
		PixelPoint bottomRightPoint = new PixelPoint(point.getPixelX()
				+ viewWidth / 2, point.getPixelY() + viewHeight / 2);
		if (LoggerConfig.ON) {
			Log.v(TAG,
					"bottomRightPoint point pixelX: "
							+ bottomRightPoint.getPixelX() + ", pixelY: "
							+ bottomRightPoint.getPixelY());
		}
		// ��ȡtop-left��bottom-right��Ӧ��tile
		int[] topLeftTile = TileSystem.PixelXYToTileXY(
				topLeftPoint.getPixelX(), topLeftPoint.getPixelY());
		int[] bottomRightTile = TileSystem.PixelXYToTileXY(
				bottomRightPoint.getPixelX(), bottomRightPoint.getPixelY());
		tileIndex[0] = level;
		tileIndex[1] = topLeftTile[0];
		tileIndex[2] = topLeftTile[1];
		tileIndex[3] = bottomRightTile[0];
		tileIndex[4] = bottomRightTile[1];
		if (LoggerConfig.ON) {
			Log.v(TAG, "tileIndex: " + tileIndex[0] + ' ' + tileIndex[1] + ' '
					+ tileIndex[2] + ' ' + tileIndex[3] + ' ' + tileIndex[4]);
		}
		// // ��ȡ��Ӧtile��top-left����������
		// int[] topLeftPointOfTopLeftTile = TileSystem.TileXYToPixelXY(
		// topLeftTile[0], topLeftTile[1]);
		// int[] bottomRightPointOfBottomRightTile = TileSystem.TileXYToPixelXY(
		// bottomRightTile[0], bottomRightTile[1]);
	}

	@Override
	public void drawLayer(float[] projectionMatrix) {
		// TODO Auto-generated method stub
		int level = tileIndex[0];
		for (int i = tileIndex[1]; i <= tileIndex[3]; i++) {
			for (int j = tileIndex[2]; j <= tileIndex[4]; j++) {
				String quadKey = TileSystem.TileXYToQuadKey(i, j, level);
				TiledMap map = tiledMapCollection.get(quadKey);
				map.Draw(projectionMatrix);
			}
		}
	}

	@Override
	public void createLayer(Context contex, LatLongPoint center, int level,
			int viewWidth, int viewHeight) {
		// TODO Auto-generated method stub
		if (LoggerConfig.ON) {
			Log.v(TAG, "view width: " + viewWidth + ", viewHeight: "
					+ viewHeight);
			Log.v(TAG, url + "");
		}
		initTileIndex(center, level, viewWidth, viewHeight);
		// ѭ��tileIndex
		for (int i = tileIndex[1]; i <= tileIndex[3]; i++) {
			for (int j = tileIndex[2]; j <= tileIndex[4]; j++) {
				String quadKey = TileSystem.TileXYToQuadKey(i, j, level);
				if (!tiledMapCollection.containsKey(quadKey)) {
					TiledMap map = new TiledMap(i, j, level);
					map.setUrl(url);
					map.Create(contex);
					// map.execute(contex);
					tiledMapCollection.put(quadKey, map);
				}
				// Params params = new Params(contex, level, i, j);
				// CreateTask createTask = new CreateTask();
				// createTask.execute(params);
			}
		}
	}

	class CreateTask extends AsyncTask<Params, Void, TiledMap> {
		String quadKey;

		@Override
		protected TiledMap doInBackground(Params... params) {
			// TODO Auto-generated method stub
			Context context = params[0].getContext();
			int level = params[0].getLevel();
			int tileX = params[0].getTileX();
			int tileY = params[0].getTileY();
			quadKey = TileSystem.TileXYToQuadKey(tileX, tileY, level);
			if (!tiledMapCollection.containsKey(quadKey)) {
				TiledMap map = new TiledMap(tileX, tileY, level);
				map.setUrl(url);
				map.Create(context);
				return map;
				// tiledMapCollection.put(quadKey, map);
			}
			return null;
		}

		@Override
		protected void onPostExecute(TiledMap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				tiledMapCollection.put(quadKey, result);
			}
		}
	}

	class Params {
		Context context;

		int level;

		public Context getContext() {
			return context;
		}

		public int getLevel() {
			return level;
		}

		public int getTileX() {
			return tileX;
		}

		public int getTileY() {
			return tileY;
		}

		int tileX;
		int tileY;

		public Params(Context context, int level, int tileX, int tileY) {
			super();
			this.context = context;

			this.level = level;

			this.tileX = tileX;
			this.tileY = tileY;
		}

	}

}
