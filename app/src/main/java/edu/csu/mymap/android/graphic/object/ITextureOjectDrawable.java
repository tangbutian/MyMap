package edu.csu.mymap.android.graphic.object;

import edu.csu.mymap.android.programs.TextureShaderProgram;

public interface ITextureOjectDrawable {
	public void draw();

	void bindData(TextureShaderProgram textureProgram);
}
