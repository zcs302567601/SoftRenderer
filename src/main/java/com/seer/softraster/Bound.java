package com.seer.softraster;

public class Bound {
	
	public int minX;
	public int minY;
	public int width;
	public int heigh;
	
	public int maxX;
	public int maxY;
	
	public Bound(int minX, int minY, int width, int height)
	{
		this.minX = minX;
		this.minY = minY;
		this.width = width;
		this.heigh = height;
		this.maxX = minX + width;
		this.maxY = minY + height;
	}
}
