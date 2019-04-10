package com.seer.softraster.playjoy.d2;

import com.seer.math.Vertex;
import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.SoftRaster;

public class TriangleFrame extends BaseFrame {

	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		new TriangleFrame().start();
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		Vertex a = new Vertex(200, 100, Color.Red);
		Vertex b = new Vertex(200, 300, Color.Blue);
		Vertex c = new Vertex(400, 400, Color.Green);
		raster.drawTriangle(new Vertex[]{a, b, c});
	}
}