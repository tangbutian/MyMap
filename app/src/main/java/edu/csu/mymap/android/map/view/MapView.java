package edu.csu.mymap.android.map.view;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.orthoM;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import edu.csu.mymap.android.android.config.LoggerConfig;
import edu.csu.mymap.android.graphic.object.LatLongPoint;
import edu.csu.mymap.android.graphic.object.PixelPoint;
import edu.csu.mymap.android.map.object.Layer;
import edu.csu.mymap.android.programs.ColorShaderProgram;

public class MapView extends GLSurfaceView implements IMapViewControl {

	private static final String TAG = "MyMapView";

	private int viewHeight;
	private int viewWidth;
	private int levelOfDetial;
	private boolean isFirstRender = true;

	private List<Layer> layers;

	public int getLevelOfDetial() {
		return levelOfDetial;
	}

	public void setLevelOfDetial(int levelOfDetial) {
		this.levelOfDetial = levelOfDetial;
	}

	private LatLongPoint center;

	public void setCenter(LatLongPoint center) {
		this.center = center;

	}

	private boolean rendererSet = false;
	private float[] projectionMatrix = new float[16];

	public MapView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setOpenGL();
		setWidtHeight();
		this.layers = new ArrayList<Layer>();
	}

	private void setOpenGL() {
		if (checkOpenGLSupport()) {
			// Request an OpenGL ES 2.0 compatible context.
			setEGLContextClientVersion(2);

			// Assign our renderer.
			setRenderer(new MapViewRenderer());
			// 脏模式绘图，当需要重绘时GLSurfaceView.requestRender()
			// setRenderMode(this.RENDERMODE_WHEN_DIRTY);

			rendererSet = true;
		} else {

			/*
			 * This is where you could create an OpenGL ES 1.x compatible
			 * renderer if you wanted to support both ES 1 and ES 2. Since we're
			 * not doing anything, the app will crash if the device doesn't
			 * support OpenGL ES 2.0. If we publish on the market, we should
			 * also add the following to AndroidManifest.xml:
			 * 
			 * <uses-feature android:glEsVersion="0x00020000"
			 * android:required="true" />
			 * 
			 * This hides our app from those devices which don't support OpenGL
			 * ES 2.0.
			 */
			Toast.makeText(getContext(),
					"This device does not support OpenGL ES 2.0.",
					Toast.LENGTH_LONG).show();
			return;
		}
	}

	private boolean checkOpenGLSupport() {
		// Check if the system supports OpenGL ES 2.0.
		ActivityManager activityManager = (ActivityManager) getContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		// Even though the latest emulator supports OpenGL ES 2.0,s
		// it has a bug where it doesn't set the reqGlEsVersion so
		// the above check doesn't work. The below will detect if the
		// app is running on an emulator, and assume that it supports
		// OpenGL ES 2.0.
		return configurationInfo.reqGlEsVersion >= 0x20000
				|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT
						.startsWith("generic")
						|| Build.FINGERPRINT.startsWith("unknown")
						|| Build.MODEL.contains("google_sdk")
						|| Build.MODEL.contains("Emulator") || Build.MODEL
							.contains("Android SDK built for x86")));

	}

	private void setWidtHeight() {
		this.viewHeight = this.getHeight();
		this.viewWidth = this.getWidth();
		if (LoggerConfig.ON) {
			Log.v(TAG, "The view height: " + viewHeight + ",The view width: "
					+ viewWidth);
		}
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if (rendererSet) {
			super.onPause();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (rendererSet) {
			super.onResume();
		}
	}

	class MapViewRenderer implements Renderer {

		ColorShaderProgram program;
		PixelPoint point;

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

			setWidtHeight();
			point = center.getPixelPoint(levelOfDetial);

			program = new ColorShaderProgram(getContext());
			for (Layer layer : layers) {
				layer.createLayer(getContext(), center, levelOfDetial,
						viewWidth, viewHeight);
			}
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub

			setWidtHeight();
			glViewport(0, 0, viewWidth, viewHeight);
			point = center.getPixelPoint(levelOfDetial);
			orthoM(projectionMatrix, 0, point.getPixelX() - viewWidth / 2,
					point.getPixelX() + viewWidth / 2, point.getPixelY()
							+ viewHeight / 2, point.getPixelY() - viewHeight
							/ 2, -1f, 1f);
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			glClear(GL_COLOR_BUFFER_BIT);
			for (Layer layer : layers) {
				layer.drawLayer(projectionMatrix);
			}
			program.useProgram();
			program.setUniforms(projectionMatrix);
			point.bindData(program);
			point.draw();
			// isFirstRender = false;
		}
	}

	@Override
	public void addLayer(Layer layer) {
		// TODO Auto-generated method stub
		this.layers.add(layer);
	}
}
