package com.seer.softraster.playjoy.d2;

import com.seer.math.Vertex;
import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.SoftRaster;

public class PolygonFrame extends BaseFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		Vertex a = new Vertex(200, 100, Color.Red);
		Vertex b = new Vertex(200, 300, Color.Blue);
		Vertex c = new Vertex(400, 400, Color.Green);
		Vertex d = new Vertex(400, 100, Color.White);
//		Vertex e = new Vertex(300, 50, Color.Blue);
//		Vertex f = new Vertex(250, 100, Color.Green);
//		f.color = Color.Green;
		raster.scanPolygon(new Vertex[]{a, b, c, d});
	}

	public static void main(String[] args) {
		new PolygonFrame().start();
	}
}