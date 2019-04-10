package com.seer.math;

import com.seer.softraster.Color;

public class Vertex3 {
	
	public Vector3 localPosition;
	public Vector3 worldPosition;
	public Vector4 eyePosition;
	public Vector4 ndcPosition;
	public Vector4 perspectivePosition;
	public Vector3 normal;
	public Vector2 screenPos;
	public Color color;
	public Vector2f uv;
	
	public Vertex3(Vector3 localPos)
	{
		this.localPosition = localPos;
	}
	
	public Vertex3(Vector3 localPos, Color color)
	{
		this.localPosition = localPos;
		this.color = color;
	}
	
	public Vertex3(Vector3 localPos, Vector2f uv, Color color)
	{
		this.localPosition = localPos;
		this.uv = uv;
		this.color = color;
	}
}