package com.seer.math;

public class Vector2 {
	
	public int x;
	public int y;
	
	public Vector2()
	{
		
	}
	
	public Vector2(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}
}