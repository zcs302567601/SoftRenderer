package com.seer.math;

public class Vector4 {
	
	public float x, y, z, w;

	public Vector4()
	{

	}

	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4(Vector3 v, float w)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = w;
	}

	@Override
	public String toString() {
		return "Vector4 [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}
}
