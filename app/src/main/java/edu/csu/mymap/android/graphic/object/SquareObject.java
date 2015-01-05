package edu.csu.mymap.android.graphic.object;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static edu.csu.mymap.android.map.view.Constants.BYTES_PER_FLOAT;
import edu.csu.mymap.android.graphic.data.VertexArray;
import edu.csu.mymap.android.graphic.util.TileSystem;
import edu.csu.mymap.android.programs.TextureShaderProgram;

/**
 * 
 * TiledMap 256*256像素的正方形，以正方形左上角的坐标(x,y)进行绘制
 * 
 * kin kin 2014-11-20 上午11:04:02
 * 
 * @version 1.0.0
 * 
 */
public class SquareObject extends GrapgicObject implements ITextureOjectDrawable {

	private int pixelX;
	private int pixelY;
	private static final int LENGTH = 256;
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;

	public SquareObject(int pixelX, int pixelY) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}

	private float[] initTiledMapVertexData() {
		float[] vertexData = { pixelX + LENGTH / 2, pixelY + LENGTH / 2, 0.5f,
				0.5f, pixelX, pixelY + LENGTH, 0f, 1f, pixelX + LENGTH,
				pixelY + LENGTH, 1f, 1f, pixelX + LENGTH, pixelY, 1f, 0f,
				pixelX, pixelY, 0f, 0f, pixelX, pixelY + LENGTH, 0f, 1f };
		return vertexData;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
	}

	@Override
	public void bindData(TextureShaderProgram textureProgram) {
		// TODO Auto-generated method stub

		initVertexArray();
		vertexArray.setVertexAttribPointer(0,
				textureProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);

		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
				textureProgram.getTextureCoordinatesAttributeLocation(),
				TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
	}

	@Override
	protected void initVertexArray() {
		// TODO Auto-generated method stub
		vertexArray = new VertexArray(initTiledMapVertexData());
	}

}
