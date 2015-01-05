package edu.csu.mymap.android.graphic.object;

import edu.csu.mymap.android.programs.ColorShaderProgram;


/**
 * 
 * IDrawable
 * 
 * kin kin 2014-11-19 ионГ9:42:00
 * 
 * @version 1.0.0
 * 
 */
public interface IColorObjectDrawable {
	public void draw();

	public void bindData(ColorShaderProgram colorProgram);
}
