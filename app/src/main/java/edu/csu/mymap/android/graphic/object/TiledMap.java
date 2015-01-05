package edu.csu.mymap.android.graphic.object;

import org.apache.http.HttpException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import edu.csu.mymap.android.R;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.util.TextureHelper;
import edu.csu.mymap.android.graphic.util.TileSystem;
import edu.csu.mymap.android.programs.TextureShaderProgram;
import edu.csu.mymap.android.texturedownload.TextureDownload;

/**
 * 
 * TiledMap 地图上行列号对应的图元
 * 
 * kin kin 2014-11-20 下午12:56:35
 * 
 * @version 1.0.0
 * 
 */
public class TiledMap {
	private static final String TAG = "TiledMap";
	private int tileX;
	private int tileY;
	private int level;
	private SquareObject squareObject;
	private int texture;
	private TextureShaderProgram program;
	private String url;
	private boolean textureLoaded = false;

	public TiledMap(int tileX, int tileY, int level) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.level = level;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void Create(Context context) {
		// 获得图元top-left的像素坐标
		int[] pixel = TileSystem.TileXYToPixelXY(tileX, tileY);
		if (LoggerConfig.ON) {
			Log.v(TAG, "Create TiledMap on pixelX: " + pixel[0] + ", pixelY: "
					+ pixel[1]);
			Log.v(TAG, "Create TiledMap on tileX: " + tileX + ", tileY: "
					+ tileY + ", level: " + level);
			Log.v(TAG, url);
		}
		squareObject = new SquareObject(pixel[0], pixel[1]);
		program = new TextureShaderProgram(context);
//		DownloadTask task = new DownloadTask();
//		task.execute();
		texture = TextureHelper.loadTexture(context, R.drawable.map);
		try {
			texture = TextureHelper.loadTexture(TextureDownload
					.httpClientDownLoad(url, level, tileY, tileX));
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Draw(float[] projectionMatrix) {
		program.useProgram();
		program.setUniforms(projectionMatrix, texture);
		squareObject.bindData(program);
		squareObject.draw();
//		while (true) {
//			if (textureLoaded) {
//				program.useProgram();
//				program.setUniforms(projectionMatrix, texture);
//				squareObject.bindData(program);
//				squareObject.draw();
//				return;
//			}
//		}
	}

	class DownloadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				texture = TextureHelper.loadTexture(TextureDownload
						.httpClientDownLoad(url, level, tileY, tileX));
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			textureLoaded = true;
		}

	}
}
