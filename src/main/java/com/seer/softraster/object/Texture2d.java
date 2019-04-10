package com.seer.softraster.object;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.seer.softraster.Color;

public class Texture2d {
	
	public int width;
	public int height;
	byte[] bytes;
	BufferedImage image;
	
	
	public Texture2d(String filePath) throws IOException
	{
		image = ImageIO.read(new File(filePath));
		width = image.getWidth();
		height = image.getHeight();
		DataBufferByte buffer = (DataBufferByte)(image.getRaster().getDataBuffer());
		bytes = buffer.getData();
	}
	
	public Color sample(float u, float v)
	{
		int x = (int) (width * u);
		int y = (int) (height * v);
		int index = (x + y * width) * 4;
		int a = bytes[index] & 0xff;
		int b = bytes[index + 1] & 0xff;
		int g = bytes[index + 2] & 0xff;
		int r = bytes[index + 3] & 0xff;
		return new Color(r, g, b, a);
	}
}
