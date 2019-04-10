package com.seer.softraster.playjoy.d2;

import java.util.Random;

import com.seer.softraster.BaseFrame;
import com.seer.softraster.Bound;
import com.seer.softraster.Color;
import com.seer.softraster.Line2D;
import com.seer.softraster.SoftRaster;

public class LineClipFrame extends BaseFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new LineClipFrame().start();
	}
	
	Bound bound = new Bound(200, 100, 400, 400);
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		
		Random random = new Random(12);
		raster.drawLine2d(bound.minX, bound.minY, bound.minX + bound.width, bound.minY, Color.White);
		raster.drawLine2d(bound.minX + bound.width, bound.minY, bound.minX + bound.width, bound.minY + bound.heigh, Color.White);
		raster.drawLine2d(bound.minX + bound.width, bound.minY + bound.heigh, bound.minX, bound.minY + bound.heigh, Color.White);
		raster.drawLine2d(bound.minX, bound.minY + bound.heigh, bound.minX, bound.minY, Color.White);
		
		for(int i = 0; i < 100; i++)
		{
			int x1 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);
			int x2 = random.nextInt(WIDTH);
			int y2 = random.nextInt(HEIGHT);
			raster.drawLineInBound(new Line2D(x1, y1, x2, y2), bound, Color.Blue);
		}
}
}