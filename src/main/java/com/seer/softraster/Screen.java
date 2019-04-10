package com.seer.softraster;

import com.seer.math.Matrix4x4;
import com.seer.math.Vector4;

public class Screen {
	
	public static int width;
	public static int height;
	public static Matrix4x4 viewPortMatrix;
	
	//(-1, 1)--->(0, width)
	
	public static void init(int width, int height)
	{
		Screen.width = width;
		Screen.height = height;
		viewPortMatrix = new Matrix4x4();
		
		viewPortMatrix.setRow1(new Vector4((width - 1)*0.5f, 0, 0, (width - 1) * 0.5f));
		viewPortMatrix.setRow2(new Vector4(0, (height -1) *0.5f, 0, (height - 1)*0.5f));
		viewPortMatrix.setRow3(new Vector4(0, 0, 1, 0));
		viewPortMatrix.setRow4(new Vector4(0, 0, 0, 1));
	}
}