package com.seer.math;

public class Vector3 {
	
	public static final Vector3 zero = new Vector3(0, 0, 0);
	public static final Vector3 one = new Vector3(1.0f, 1.0f, 1.0f);
	public float x, y, z;

	public Vector3()
	{

	}

	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3(Vector4 v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public Vector3 normalize()
	{
		float length = length();//(float) Math.sqrt(x*x + y*y + z*z);
		return new Vector3(x/length, y/length, z/length);
	}
	
	public Vector3 mul(float t)
	{
		this.x *= t;
		this.y *= t;
		this.z *= t;
		return this;
	}
	
	public Vector3 add(Vector3 v)
	{
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}
	
	public static float dot(Vector3 v1, Vector3 v2)
	{
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	public static Vector3 del(Vector3 v1, Vector3 v2)
	{
		return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}
	
	public static Vector3 add(Vector3 v1, Vector3 v2)
	{
		return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}
	
	public static Vector3 mul(Vector3 v, float m)
	{
		return new Vector3(v.x * m, v.y * m, v.z * m);
	}
	
	//v1 在 v2上的投影
	public Vector3 projection(Vector3 v1, Vector3 v2)
	{
		float dot = dot(v1, v2);
		float t = dot/(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
		return Vector3.mul(v2, t);
	}
	
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	@Override
	public String toString() {
		return "Vector3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public static Vector3 cross(Vector3 v1, Vector3 v2)
	{
		float x = v1.y * v2.z - v2.y * v1.z;
		float y = v1.z * v2.x - v1.x * v2.z;
		float z = v1.x * v2.y - v1.y * v2.x;
		return new Vector3(x, y, z);
	}
}