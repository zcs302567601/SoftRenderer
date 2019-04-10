package com.seer.softraster.playjoy.d2;

import java.util.Random;

import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.SoftRaster;

public class LineFrame extends BaseFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new LineFrame().start();
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);

		Random random = new Random(8);
		for(int i = 0; i < 10; i++)
		{
			int x1 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);
			int x2 = random.nextInt(WIDTH);
			int y2 = random.nextInt(HEIGHT);
			raster.drawLine2d(x1, y1, x2, y2, Color.Blue);
		}
	}
}