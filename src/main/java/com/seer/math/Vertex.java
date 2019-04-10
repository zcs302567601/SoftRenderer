package com.seer.math;

import com.seer.softraster.Color;

public class Vertex {
	
	public Vector2 position = new Vector2();
	public Vector2f uv = new Vector2f();
	public Vector4 perPosition = new Vector4();
	public Vector3 worldPosition = new Vector3();
	public Vector3 normal = new Vector3();
	
	public Color color;

	public Vertex()
	{
		
	}
	
	public Vertex(int x, int y, Color color)
	{
		this.position.x = x;
		this.position.y = y;
		this.color = color;
	}
	
	public Vertex(Vector2 pos, Color color)
	{
		this.position = pos;
		this.color = color;
	}
	
	public Vertex(Vector2 pos, Vector4 ndcPos, Color color)
	{
		this.position = pos;
		this.perPosition = ndcPos;
		this.color = color;
	}
	
	public Vertex(Vector2 pos, Vector2f uv, Vector4 ndcPos, Color color)
	{
		this.position = pos;
		this.uv = uv;
		this.perPosition = ndcPos;
		this.color = color;
	}
	
	public Vertex(Vector2 pos, Vector3 worldPos, Vector2f uv, Vector4 ndcPos, Color color)
	{
		this.position = pos;
		this.worldPosition = worldPos;
		this.uv = uv;
		this.perPosition = ndcPos;
		this.color = color;
	}
	
	public Vertex(Vector2 pos, Vector3 worldPos, Vector2f uv, Vector4 ndcPos, Vector3 normal, Color color)
	{
		this.position = pos;
		this.worldPosition = worldPos;
		this.uv = uv;
		this.perPosition = ndcPos;
		this.normal = normal;
		this.color = color;
	}
}
