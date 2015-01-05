package edu.csu.mymap.android.graphic.object;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static edu.csu.mymap.android.map.view.Constants.BYTES_PER_FLOAT;
import android.util.Log;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.data.VertexArray;
import edu.csu.mymap.android.graphic.util.TileSystem;
import edu.csu.mymap.android.programs.ColorShaderProgram;

/**
 * 
 * PixelPoint ÏñËØµãÀà
 * 
 * kin kin 2014-11-20 ÉÏÎç10:57:15
 * 
 * @version 1.0.0
 * 
 */
public class PixelPoint extends GrapgicObject implements IColorObjectDrawable {
	private static final String TAG = "PixelPoint";
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int COLOR_COMPONENT_COUNT = 3;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;
	private int pixelX;// ÏñËØ×ø±ê
	private int pixelY;// ÏñËØ×ø±ê
	private float colorR = 0;
	private float colorG = 0;
	private float colorB = 0;

	public PixelPoint(int x, int y) {
		this.pixelX = x;
		this.pixelY = y;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		glDrawArrays(GL_POINTS, 0, 1);
	}

	@Override
	public void bindData(ColorShaderProgram colorProgram) {
		// TODO Auto-generated method stub
		initVertexArray();
		vertexArray.setVertexAttribPointer(0,
				colorProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);
		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
				colorProgram.getColorAttributeLocation(),
				COLOR_COMPONENT_COUNT, STRIDE);
	}

	@Override
	protected void initVertexArray() {
		// TODO Auto-generated method stub
		float[] vertexData = { pixelX, pixelY, colorR, colorG, colorB };

		vertexArray = new VertexArray(vertexData);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Point position in pixel x: " + pixelX + ", y: " + pixelY;
	}

	public LatLongPoint getLatLongPoint(int level) {
		double[] latLon = TileSystem.PixelXYToLatLong(pixelX, pixelY, level);
		if (LoggerConfig.ON)
			Log.v(TAG, "getLatLongPoint in LatLong Latitude: " + latLon[0]
					+ ", Longitude: " + latLon[1]);
		return new LatLongPoint(latLon[0], latLon[1]);
	}

	public void setColor(int r, int g, int b) {
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
	}

	public int getPixelX() {
		return pixelX;
	}

	public int getPixelY() {
		return pixelY;
	}

}
