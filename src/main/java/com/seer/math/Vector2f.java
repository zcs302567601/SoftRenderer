package com.seer.math;

public class Vector2f {
	
	public float x, y;
	
	public Vector2f()
	{
		
	}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vector2f mul(float mul)
	{
		this.x *= mul;
		this.y *= mul;
		return this;
	}
	
	public static Vector2f mul(Vector2f v, float mul)
	{
		Vector2f f = new Vector2f(v.x * mul, v.y * mul);
		return f;
	}
	
	public static Vector2f add(Vector2f v1, Vector2f v2)
	{
		Vector2f f = new Vector2f(v1.x + v2.x, v1.y + v2.y);
		return f;
	}
	
	public static Vector2f del(Vector2f v1, Vector2f v2)
	{
		return new Vector2f(v1.x - v2.x, v1.y - v2.y);
	}
	
	public Vector2f add(Vector2f v)
	{
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}
}
