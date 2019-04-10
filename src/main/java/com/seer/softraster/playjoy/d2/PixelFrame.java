package com.seer.softraster.playjoy.d2;

import com.seer.softraster.BaseFrame;
import com.seer.softraster.Color;
import com.seer.softraster.SoftRaster;

public class PixelFrame extends BaseFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new PixelFrame().start();
	}
	
	@Override
	public void render(SoftRaster raster) {
		clear(Color.BLACK);
		for(int i = 0; i < HEIGHT; i++)
		{
			raster.drawPixel(i, i, Color.Red);
		}
	}
}